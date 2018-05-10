package inc.software.wifimorfi.az_soft_project.Models;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inc.software.wifimorfi.az_soft_project.Util.Init;

public class NetManager  {
    String IPADDRESS_PATTERN =
            "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";


    public enum  server_state  { waiting_for_client , waiting_for_client_respons
        ,reciving_request,sending_date,sending_list  }

    public enum  client_state  { initiating_server , waiting_for_list
        ,reciving_list,reciving_date,waiting_for_date  }

    public enum ReqType {list_comment , file}


    private static String Discover_Request = "DISCOVER_FUIFSERVER_REQUEST";
    private static String Discovery_Respose = "DISCOVER_FUIFSERVER_RESPONSE";
    private static  String STOP_CODE = "EXIT";
    private   String KEY = "";
    ReqType rq = ReqType.list_comment;

    private DatagramSocket client_socket;
    private DatagramSocket server_socket;

    // TCP SERVER ----------------------------------
    private ServerSocket sSocket;
    private Socket tcp_server_socket;
    private DataOutputStream dos;
    private OutputStream os;
    private File file;
    // TCP SERVER ----------------------------------


    // TCP CLIENT HERE -----------------------------
    private Socket tcp_client_socket;
    private DataInputStream dis;
    private InputStream is;
    private File file2;
    // TCP CLIENT HERE -----------------------------

    public  void Stop_Server (){
        this.KEY = STOP_CODE;
        if (server_socket != null){
            server_socket.close();
            System.out.println("Socket Closed -Forced");
        }

    }

    public  void Stop_Server_tcp (){
        int t = 0;
        try {

            if ( sSocket == null){
                sSocket.close();
            }
            t = 1;
            if (tcp_server_socket == null){
                tcp_server_socket.close();
            }
            t = 0;
        }catch (Exception ex){
            if (t == 1 ){
                System.out.println("Couldn't close 2nd Socket");
            }else {
                System.out.println("maybe didn't close any!");
            }

        }

    }

    public void  Stop_Client_tcp () {

    }

    public Runnable get_serevr(){
        return new server();
    }

    public Runnable get_client(){
        return new client();
    }


    public void Stop_Client(){
        if (client_socket != null){
            client_socket.close();
            System.out.println("Closed Client Socket -Force");
        }
    }

    public String get_myIp(){
        String ip = "0";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                   // Init.terminal(addr.getCanonicalHostName() +" :getCanonicalHostName");
                    Init.terminal(addr.getAddress() +" :getAddress");
                    Init.terminal(addr.isSiteLocalAddress() +" :isSiteLocalAddress");
                    //Init.terminal(addr.getHostName() +" :getHostName");
                    Init.terminal(addr.getHostAddress() +" :getHostAddress");
//                    addr.getCanonicalHostName();
//                    addr.getAddress();
//                    addr.isSiteLocalAddress();
//                    addr.getHostName();


                    Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
                    Matcher matcher = pattern.matcher(addr.getHostAddress());

                    if (matcher.matches()){
                        Init.terminal("Pattern is A Correct IP");
                        ip = addr.getHostAddress();
                    }else {
                        Init.terminal("Non correct pattern");
                    }





                    /*if (addr.getHostAddress().length() < 13){
                        ip = addr.getHostAddress();
                    }*/
                    //System.out.println(iface.getDisplayName() + " " + ip);
                }
            }

            return ip;
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }


    private class server implements Runnable {


        @Override
        public void run() {
            try {


                //Keep a server_socket open to listen to all the UDP trafic that is destined for this port
                server_socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
                server_socket.setBroadcast(true);

                while (true) {
                    System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");

                    //Receive a packet
                    byte[] recvBuf = new byte[15000];
                    DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                    server_socket.receive(packet);

                    //Packet received
                    System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
                    System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()).trim());

                    //See if the packet holds the right command (message)
                    String message = new String(packet.getData()).trim();
                    if (message.equals(Discover_Request)) {
                        byte[] sendData = Discovery_Respose.getBytes();

                        //Send a response
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                        server_socket.send(sendPacket);

                        System.out.println(getClass().getName() + ">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
                    }
                    if (KEY.equals(STOP_CODE))
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(NetManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private class client implements Runnable {

        @Override
        public void run() {
            try {
                Gson gson = new Gson();
                //Open a random port to send the package
                client_socket = new DatagramSocket();
                client_socket.setBroadcast(true);

                byte[] sendData = Discover_Request.getBytes();

                //Try the 255.255.255.255 first
                try {
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
                    client_socket.send(sendPacket);
                    System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
                } catch (Exception e) {
                }

                // Broadcast the message over all the network interfaces
                Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                    if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                        continue; // Don't want to broadcast to the loopback interface
                    }

                    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                        InetAddress broadcast = interfaceAddress.getBroadcast();
                        if (broadcast == null) {
                            continue;
                        }

                        // Send the broadcast package!
                        try {
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                            client_socket.send(sendPacket);
                        } catch (Exception e) {
                        }

                        System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                    }
                }

                System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

                //Wait for a response
                byte[] recvBuf = new byte[15000];
                DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                client_socket.receive(receivePacket);



                //We have a response
                System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

                //Check if the message is correct
                String message = new String(receivePacket.getData()).trim();
                if (message.equals(Discovery_Respose)) {
                    // TODO: 5/6/2018 Found The Server IP ! <CAN USE IT>
                    //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
                    //Controller_Base.setServerIp(receivePacket.getAddress());
                }

                //Close the port!
                client_socket.close();
            } catch (IOException ex) {
                //Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class server_tcp implements Runnable{

        //private JFileChooser jfc = new JFileChooser();



        @Override
        public void run() {

            try {
                sSocket = new ServerSocket(8888);
                System.out.println("Waiting for incoming connection request...");
                tcp_server_socket = sSocket.accept();
                //jfc.showOpenDialog(null);
                //file = jfc.getSelectedFile();

                // TODO: 5/10/2018 give the Correct File -Fullpath
                FileInputStream fis = new FileInputStream(file);
                os = tcp_server_socket.getOutputStream();
                dos = new DataOutputStream(os);
                dos.writeUTF(file.getName());
                int count = 0;
                byte[] b = new byte[1000];
                System.out.println("Uploading File...");
                while ((count = fis.read(b)) != -1) {
                    dos.write(b, 0, count);
                }

                fis.close();
                tcp_server_socket.close();
                System.out.println("File Transfer Completed Successfully!");

            }catch (IOException ex){

            }

        }
    }

    private class client_tcp implements Runnable{


        @Override
        public void run() {
            try {
                if (rq.equals(ReqType.file)){
                    tcp_client_socket = new Socket("localhost", 8888);
                    is = tcp_client_socket.getInputStream();
                    dis = new DataInputStream(is);
                    System.out.println("Waiting for File");
                    //jfc.setSelectedFile(new File(dis.readUTF()));
                    //jfc.showSaveDialog(null);
                    file2 = new File("C:\\text.txt"); // TODO: 5/10/2018 chainge path throw - fullpath
                    //file2 = ;
                    //jfc.getSelectedFile();
                    FileOutputStream fos = new FileOutputStream(file2);
                    int count = 0;
                    byte[] b = new byte[1000];
                    System.out.println("Incoming File");
                    while((count = dis.read(b)) != -1){
                        fos.write(b, 0, count);
                    }

                    fos.close();
                    tcp_client_socket.close();
                    System.out.println("File Transfer Completed Successfully!");
                }else if (rq.equals(ReqType.list_comment)){



                }



            }catch (Exception ex){

            }
        }
    }
    public class requst {
        private Dock dock;
        private ReqType reqType;

        public String get_str(){
            Gson gson = new Gson();
            return gson.toJson(this);
        }

        public  requst get_from_str( String s){
            Gson gson = new Gson();
            return gson.fromJson(s , requst.class);
        }
    }


}

