package inc.software.wifimorfi.az_soft_project.Ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import inc.software.wifimorfi.az_soft_project.Models.DaoSession;
import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.Models.NetManager;
import inc.software.wifimorfi.az_soft_project.Models.Repository;
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
            server = new Thread(nt.get_serevr_tcp());
        }
        return server;
    }

    private Thread get_thread_instance_client (){
        if (client == null){
            client = new Thread(nt.get_client_tcp());
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
            String key =  Init.find_et_by_id(this  , R.id.et_dialogue_server_key).getText().toString();
            nt.host = key;
            //nt.setHost(key);

            // TODO: 5/11/2018  Connecting To Host is hard Coded At time! -- > For Debugging

            get_thread_instance_client().start();
            cs = Client_status.ON;




            Init.find_tv_by_id(this , R.id.tv_dialogue_tv2).setText("Client_ON");
        }else {
            nt.Stop_Client_tcp();
            cs = Client_status.OFF;



            Init.find_tv_by_id(this , R.id.tv_dialogue_tv2).setText("Client_OFF");
        }
    }

    public void togle_server(View view) {
        if (ss.equals(Server_status.OFF)){
            DaoSession session = Repository.GetInstant(getApplicationContext());
            NetManager.list =  session.getDockDao().loadAll();
            get_thread_instance_server().start();
            show_client_ip(nt.get_myIp());
            ss = Server_status.ON;
            Init.find_tv_by_id(this , R.id.tv_dialogue_tv1).setText("Server_ON");
        }else {
            nt.Stop_Server_tcp();
            server = null; // Releasing Th thread (Should stop guess)
            show_client_ip("NON.NON");
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
