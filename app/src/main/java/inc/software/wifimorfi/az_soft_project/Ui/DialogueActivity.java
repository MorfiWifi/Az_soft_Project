package inc.software.wifimorfi.az_soft_project.Ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import inc.software.wifimorfi.az_soft_project.Models.NetManager;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Util.Init;

public class DialogueActivity extends AppCompatActivity {
    private static Client_status cs = Client_status.OFF;
    private static Server_status ss = Server_status.OFF;
    private static NetManager nt = new NetManager();
    Thread server ;
    Thread client ;

    enum Client_status {
        OFF , ON
    }

    enum Server_status{
        OFF , ON
    }

    private Thread get_thread_instance_server (){
        if (server == null){
            server = new Thread(nt.get_serevr());
        }
        return server;
    }

    private Thread get_thread_instance_client (){
        if (client == null){
            client = new Thread(nt.get_client());
        }
        return client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);
        setTitle("NO Tile");
    }

    public void togle_client(View view) {
        if (cs.equals(Client_status.OFF) ){
            get_thread_instance_client().start();
            cs = Client_status.ON;

            show_client_ip(nt.get_myIp());

            Init.find_tv_by_id(this , R.id.tv_dialogue_tv2).setText("Client_ON");
        }else {
            nt.Stop_Client();
            cs = Client_status.OFF;

            show_client_ip("NON.NON");

            Init.find_tv_by_id(this , R.id.tv_dialogue_tv2).setText("Client_OFF");
        }
    }

    public void togle_server(View view) {
        if (ss.equals(Server_status.OFF)){
            get_thread_instance_server().start();
            ss = Server_status.ON;
            Init.find_tv_by_id(this , R.id.tv_dialogue_tv1).setText("Server_ON");
        }else {
            nt.Stop_Server();
            server = null; // Releasing Th thread (Should stop guess)
            ss = Server_status.OFF;
            Init.find_tv_by_id(this , R.id.tv_dialogue_tv1).setText("Server_OFF");
        }


    }

    public void show_client_ip(String s){
        String[] ss = s.split("\\.");
        int m  = ss.length;
        if (m>0){
            Init.find_tv_by_id(this , R.id.tv_dialogue_tv3).setText(ss[m-1]);
        }
    }
}
