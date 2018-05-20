package inc.software.wifimorfi.az_soft_project.Models;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inc.software.wifimorfi.az_soft_project.Ui.File_ChooserActivity;
import inc.software.wifimorfi.az_soft_project.Ui.Net_setting;
import inc.software.wifimorfi.az_soft_project.Util.Init;

public class NetManager  {
    String IPADDRESS_PATTERN =
            "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";


    public static int PORT = 5602;
    public enum  server_state  { waiting_for_client , waiting_for_client_respons
        ,reciving_request,sending_date,sending_list  }

    public enum  client_state  { initiating_server , waiting_for_list
        ,reciving_list,reciving_date,waiting_for_date  }

    //public enum ReqType {list_comment , file}


    private static String Discover_Request = "DISCOVER_FUIFSERVER_REQUEST";
    private static String Discovery_Respose = "DISCOVER_FUIFSERVER_RESPONSE";
    private static  String STOP_CODE = "EXIT";
    private   String KEY = "";
    //public static ReqType rq = ReqType.list_comment;

    private DatagramSocket client_socket;
    private DatagramSocket server_socket;
    public static List<Dock> list;

    // TCP SERVER ----------------------------------
    private ServerSocket sSocket;
    private Socket tcp_server_socket;
    private DataOutputStream dataOutputStream;
    private OutputStream outputStream;
    private File file;
    // TCP SERVER ----------------------------------


    // TCP CLIENT HERE -----------------------------
    private Socket tcp_client_socket;
    private DataInputStream dataInputStream;
    private InputStream inputStream;
    private File file2;
    public String host = "";
    private static File path ;
    public static  AppCompatActivity appCompatActivity;
    // TCP CLIENT HERE -----------------------------


    // SECRET ACCESS PART >>--------------------------------------------
    //private static Client_status cs = Client_status.OFF;
    //private static Server_status ss = Server_status.OFF;
    private static NetManager nt = new NetManager();
    private static Thread server ;
    private static Thread client ;
    private static NetManager netManager;
    public static Net_setting net_setting_glob;
    public static String net_setting_glob_str;
    public static Boolean isReciving_file = false;
    //public AppCompatActivity activity;

    public static NetManager getNt (AppCompatActivity activity){
        if (netManager == null){
            netManager = new NetManager();
        }
        //netManager.activity = activity;
        return netManager;
    }


    public static Thread get_thread_instance_server (){
        if (server == null){
            server = new Thread(nt.get_serevr_tcp());
        }
        return server;
    }

    public static Thread get_thread_instance_client (){
        if (client == null){
            client = new Thread(nt.get_client_tcp());
        }
        return client;
    }

    private Net_setting get_setting(){
        String s = null; //Init.Load_String(activity , Init.Extra_Kry , Init.NO_THING); // Auto Null Loader! ins deg_val


        Gson gson = new Gson();
        Net_setting net_setting = gson.fromJson(s , Net_setting.class);
        if (net_setting != null){
            if (net_setting == null){
                net_setting = new Net_setting();
                net_setting_glob = net_setting;
            }
        }else {
            if (net_setting_glob == null){
                net_setting = new Net_setting();
                net_setting_glob = net_setting;
            }
        }

        return  net_setting_glob;
    }

    public  void togle_client(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        path = appCompatActivity.getFilesDir();
        Net_setting net_setting = get_setting();
        Gson gson = new Gson();
        if (net_setting.client_ST.equals(Net_setting.ReqType.OFF)){

            if (!net_setting.server_ST.equals(Net_setting.ReqType.OFF)){
                togle_server(appCompatActivity);
            }
            //Init.Toas(appCompatActivity , "Got after getting KEY");


            setHost(net_setting.KEY); // TODO: 5/14/2018 Use Host to connect
            // The END KET

            String key =  "192.168.1.102"; // Fixed value YET
                    //Init.find_et_by_id(this  , R.id.et_dialogue_server_key).getText().toString();
            host = key;
            //nt.setHost(key);

            // TODO: 5/11/2018  Connecting To Host is hard Coded At time! -- > For Debugging

            net_setting.client_ST = Net_setting.ReqType.list_comment;
            net_setting_glob_str = gson.toJson(get_setting());
            get_thread_instance_client().start(); // Can change th State in this code
            //cs = DialogueActivity_client.Client_status.ON;
            //Init.find_tv_by_id(this , R.id.tv_dialogue_tv2).setText("Client_ON");
        }else {
            get_setting().client_ST = Net_setting.ReqType.OFF;
            net_setting_glob_str = gson.toJson(get_setting());
            Stop_Client_tcp();
            //cs = DialogueActivity_client.Client_status.OFF;
            //Init.find_tv_by_id(this , R.id.tv_dialogue_tv2).setText("Client_OFF");
        }
    }

    public  void togle_server(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        path = appCompatActivity.getFilesDir();
        Net_setting net_setting = get_setting();
        Gson gson = new Gson();
        if (net_setting.server_ST.equals(Net_setting.ReqType.OFF)){


            if (!net_setting.client_ST.equals(Net_setting.ReqType.OFF)){
                togle_client(appCompatActivity);
            }


            DaoSession session = Repository.GetInstant(appCompatActivity);
            NetManager.list =  session.getDockDao().loadAll();
            net_setting.server_ST = Net_setting.ReqType.list_comment;
            //Init.MainAC_setting = gson.toJson(get_setting());
            //Init.Save_String(activity , Init.Extra_Kry , gson.toJson(get_setting())); //Yet Disabled THIS CODE

            show_client_ip();
            net_setting_glob_str = gson.toJson(get_setting());



            get_thread_instance_server().start(); // Check nex value's here
             // how the KEY - UP
            //ss = DialogueActivity_client.Server_status.ON;
            //Init.find_tv_by_id(this , R.id.tv_dialogue_tv1).setText("Server_ON"); // No dialogue YET
        }else {
            show_client_ip();
            get_setting().server_ST = Net_setting.ReqType.OFF;
            net_setting_glob_str = gson.toJson(get_setting());
            Stop_Server_tcp();
            server = null; // Releasing Th thread (Should stop guess)


            //ss = DialogueActivity_client.Server_status.OFF;
            //Init.find_tv_by_id(this , R.id.tv_dialogue_tv1).setText("Server_OFF");
        }


    }

    public  void show_client_ip(){
        String s = get_myIp();
        String[] ss = s.split("\\.");
        int m  = ss.length;
        if (m>0){
            get_setting().KEY = ss[m-1];
        }
    }

    // SECRET ACCESS PART >>--------------------------------------------















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
        try {
            if (tcp_client_socket != null)
                tcp_client_socket.close();
            Init.terminal("Client TCP stop -Forced");
        }catch (Exception ex){

        }

    }

    public void setHost(String s){
        String ip = get_myIp();
        String[] ips = ip.split("\\.");
        if (ips.length > 1){
            ips[ips.length-1] = s;
            host = ips[0];
            for (int i = 1; i < ips.length; i++) {
                host = host+"."+ips[i];
            }
        }
    }

    public Runnable get_serevr(){
        return new server();
    }

    public Runnable get_client(){
        return new client();
    }

    public Runnable get_serevr_tcp(){
        return new server_tcp();
    }

    public Runnable get_client_tcp(){
        return new client_tcp();
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
                    Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
                    Matcher matcher = pattern.matcher(addr.getHostAddress());

                    if (matcher.matches()){
                        Init.terminal("Pattern inputStream A Correct IP");
                        ip = addr.getHostAddress();
                    }else {
                        Init.terminal("Non correct pattern");
                    }
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


                //Keep a server_socket open to listen to all the UDP trafic that inputStream destined for this port
                server_socket = new DatagramSocket(PORT, InetAddress.getByName("0.0.0.0"));
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
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), PORT);
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
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, PORT);
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

                //Check if the message inputStream correct
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

        @Override
        public void run() {
            Gson gson = new Gson();
            Net_setting net_setting = get_setting();

            try {

                if (net_setting.server_ST.equals(Net_setting.ReqType.list_comment)||net_setting.server_ST.equals(Net_setting.ReqType.OFF)){
                    String s = gson.toJson(list);

                    sSocket = new ServerSocket(PORT);
                    System.out.println("Waiting for incoming connection request...");
                    tcp_server_socket = sSocket.accept();
                    outputStream = tcp_server_socket.getOutputStream();
                    dataOutputStream = new DataOutputStream(outputStream);
                    //dataOutputStream.writeUTF(file.getName());
                    int count = 0;
                    byte[] b = s.getBytes();
                    System.out.println("Uploading List...");
                    dataOutputStream.write(b, 0, b.length);
                    System.out.println("Lengh of Bytes" + b.length);


                    //fis.close();
                    tcp_server_socket.close(); // Do not Close This !!




                    net_setting.server_ST = Net_setting.ReqType.file;
                    net_setting_glob_str = gson.toJson(get_setting());
                    System.out.println("List Transfer Completed Successfully!");

                    // ------------------------------->>>>>>>>>> Second part

                    /*while (true){
                        // TODO: 5/12/2018 Implement File Transfer
                        sSocket = new ServerSocket(PORT);
                        System.out.println("Waiting for incoming connection request...");
                        tcp_server_socket = sSocket.accept();
                        inputStream = tcp_server_socket.getInputStream();
                        //inputStream.read()

                        // NOW GET THE DOCK THAT WANT !!!! EVEN ID IS ENOUGH!


                        //jfc.showOpenDialog(null);
                        //file = jfc.getSelectedFile();
                        file = new File(path, "my-file-name.txt");


                        FileInputStream fis = new FileInputStream(file);
                        outputStream = tcp_server_socket.getOutputStream();
                        dataOutputStream = new DataOutputStream(outputStream);
                        dataOutputStream.writeUTF(file.getName());
                        count = 0;
                        b = new byte[1000];
                        System.out.println("Uploading File...");
                        while ((count = fis.read(b)) != -1) {
                            dataOutputStream.write(b, 0, count);
                        }

                        fis.close();
                        tcp_server_socket.close();
                        System.out.println("File Transfer Completed Successfully!");
                    }*/


                }else if (net_setting.server_ST.equals(Net_setting.ReqType.file)){

                    // TODO: 5/12/2018 This is Redundent YET>>>>>>>>>>
                    sSocket = new ServerSocket(PORT);
                    System.out.println("Waiting for incoming connection request...");
                    tcp_server_socket = sSocket.accept();
                    //jfc.showOpenDialog(null);
                    //file = jfc.getSelectedFile();
                    FileInputStream fis = new FileInputStream(file);
                    outputStream = tcp_server_socket.getOutputStream();
                    dataOutputStream = new DataOutputStream(outputStream);
                    dataOutputStream.writeUTF(file.getName());
                    int count = 0;
                    byte[] b = new byte[1000];
                    System.out.println("Uploading File...");
                    while ((count = fis.read(b)) != -1) {
                        dataOutputStream.write(b, 0, count);
                    }

                    fis.close();
                    tcp_server_socket.close();
                    System.out.println("File Transfer Completed Successfully!");

                }



            }catch (Exception ex){
                sSocket = null;
                tcp_server_socket = null;
                //get_setting().server_ST = Net_setting.ReqType.OFF;
            }

        }
    }

    private class client_tcp implements Runnable{





        @Override
        public void run() {
            try {
                Gson gson = new Gson();
                Net_setting net_setting = get_setting();

                if (   net_setting.client_ST.equals(Net_setting.ReqType.file)){

                    isReciving_file = true;
                    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  SHOULD BE IN WHILE LOOP FOR LIST SELEVTED!
                    tcp_client_socket = new Socket(host, PORT);
                    inputStream = tcp_client_socket.getInputStream();

                    //tcp_client_socket.getOutputStream();
                    dataInputStream = new DataInputStream(inputStream);
                    System.out.println("Waiting for File");
                    //jfc.setSelectedFile(new File(dataInputStream.readUTF()));
                    //jfc.showSaveDialog(null);
                    file2 = new File("C:\\text.txt"); // TODO: 5/10/2018 chainge path throw - fullpath
                    //file2 = ;
                    //jfc.getSelectedFile();
                    FileOutputStream fos = new FileOutputStream(file2);
                    int count = 0;
                    byte[] b = new byte[1000];
                    System.out.println("Incoming File");
                    while((count = dataInputStream.read(b)) != -1){
                        // count is the tru size ! (Some may not used)
                        fos.write(b, 0, count);
                    }

                    fos.close();
                    tcp_client_socket.close();
                    System.out.println("File Transfer Completed Successfully!");


                }else if (net_setting.client_ST.equals(Net_setting.ReqType.list_comment)||net_setting.client_ST.equals(Net_setting.ReqType.OFF) ){
                    // TODO: 5/12/2018 implement TCP File Client
                    Init.terminal("CLIENT IS RECIVING LIST ? TRYIND TOO");


                    //host
                    tcp_client_socket = new Socket("192.168.1.102", PORT);
                    Init.terminal("CLIENT IS RECIVING LIST 2");
                    inputStream = tcp_client_socket.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // For Reading String
                    System.out.println("Waiting for File");

                    // byte[] b = new byte[1000];
                    System.out.println("Incoming File");

                    String jsoon = "";
                    String str;
                    while ((str = bufferedReader.readLine()) != null) {
                        jsoon = jsoon + str;
                    }

                    Init.terminal(jsoon);

                    List<Dock> list2 = new ArrayList<>();

                    try {
                        list = gson.fromJson(jsoon , list2.getClass());
                        Init.terminal("Converted Json To LIST" + jsoon);
                        File_ChooserActivity.list = list;
                    }catch (Exception ex){
                        Init.terminal("Couldnt Convert JSON");
                    }





                    tcp_client_socket.close();
                    System.out.println("File Transfer Completed Successfully!");


                    // Should Go To File Recive MODE!! >> FIRST SEND FILE INFO THEN RECIVE!! and loop
                    get_setting().client_ST = Net_setting.ReqType.file;

                }

            }catch (Exception ex){
                Init.terminal("SOME EXCEPTION ON CLIENT TCP _ AWARE" + ex.getMessage());
                if (appCompatActivity != null){
                    //Init.Toas( appCompatActivity,"اتصال رد شد!" ); //EXCEPTION OCCUR
                    togle_client(appCompatActivity);
                }

                tcp_client_socket = null;


                //get_setting().client_ST = Net_setting.ReqType.OFF;
            }
        }
    }
    public class requst {
        private Dock dock;
        //private ReqType reqType;

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

