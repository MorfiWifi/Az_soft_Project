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

    enum Client_status {
        OFF , ON
    }

    enum Server_status{
        OFF , ON
    }

    private Thread get_thread_instance_server (Runnable r){
        if (server == null){
            server = new Thread(nt.get_serevr());
        }
        return server;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);
        setTitle("NO Tile");
    }

    public void togle_client(View view) {
        if (cs.equals(Client_status.OFF) ){



            cs = Client_status.ON;
            Init.find_tv_by_id(this , R.id.tv_dialogue_tv2).setText("Client_ON");
        }else {



            cs = Client_status.OFF;
            Init.find_tv_by_id(this , R.id.tv_dialogue_tv2).setText("Client_OFF");
        }
    }

    public void togle_server(View view) {
        if (ss.equals(Server_status.OFF)){
            get_thread_instance_server(nt.get_serevr()).start();
            ss = Server_status.ON;
            Init.find_tv_by_id(this , R.id.tv_dialogue_tv1).setText("Server_ON");
        }else {
            nt.Stop_Server();
            server = null; // Releasing Th thread (Should stop guess)
            ss = Server_status.OFF;
            Init.find_tv_by_id(this , R.id.tv_dialogue_tv1).setText("Server_OFF");
        }


    }
}
