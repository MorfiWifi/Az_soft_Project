package inc.software.wifimorfi.az_soft_project.Models;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetManager  {
    public enum  server_state  { waiting_for_client , waiting_for_client_respons
        ,reciving_request,sending_date,sending_list  }

    public enum  client_state  { initiating_server , waiting_for_list
        ,reciving_list,reciving_date,waiting_for_date  }

    public enum ReqType {list_comment , file}


    private static String Discover_Request = "DISCOVER_FUIFSERVER_REQUEST";
    private static String Discovery_Respose = "DISCOVER_FUIFSERVER_RESPONSE";
    private static  String STOP_CODE = "EXIT";
    private   String KEY = "";

    private DatagramSocket c;
    private DatagramSocket socket;


    public  void Stop_Server (){
        this.KEY = STOP_CODE;
        if (socket != null){
            socket.close();
            System.out.println("Socket Closed -Forced");
        }

    }

    public Runnable get_serevr(){
        return new server();
    }

    public Runnable get_client(){
        return new client();
    }


    private void Stop_Client(){
        if (c != null){
            c.close();
            System.out.println("Closed Client Socket -Force");
        }
    }


    private class server implements Runnable {
        ReqType rq = ReqType.list_comment;

        @Override
        public void run() {
            try {
                //Keep a socket open to listen to all the UDP trafic that is destined for this port
                socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
                socket.setBroadcast(true);

                while (true) {
                    System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");

                    //Receive a packet
                    byte[] recvBuf = new byte[15000];
                    DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                    socket.receive(packet);

                    //Packet received
                    System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
                    System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

                    //See if the packet holds the right command (message)
                    String message = new String(packet.getData()).trim();
                    if (message.equals(Discover_Request)) {
                        byte[] sendData = Discovery_Respose.getBytes();

                        //Send a response
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                        socket.send(sendPacket);

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
                DatagramSocket c = new DatagramSocket();
                c.setBroadcast(true);

                byte[] sendData = Discover_Request.getBytes();

                //Try the 255.255.255.255 first
                try {
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
                    c.send(sendPacket);
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
                            c.send(sendPacket);
                        } catch (Exception e) {
                        }

                        System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                    }
                }

                System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

                //Wait for a response
                byte[] recvBuf = new byte[15000];
                DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                c.receive(receivePacket);

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
                c.close();
            } catch (IOException ex) {
                //Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

