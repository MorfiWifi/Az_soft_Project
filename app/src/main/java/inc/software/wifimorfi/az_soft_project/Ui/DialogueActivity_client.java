package inc.software.wifimorfi.az_soft_project.Ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import inc.software.wifimorfi.az_soft_project.Models.DaoSession;
import inc.software.wifimorfi.az_soft_project.Models.NetManager;
import inc.software.wifimorfi.az_soft_project.Models.Repository;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Util.Init;
import inc.software.wifimorfi.az_soft_project.View.Ip_Adapter;

public class DialogueActivity_client extends AppCompatActivity {
    public static String NO_KEY = "NO_KEY";
    public static String Server_KEY =NO_KEY;
    private boolean btn_clicked = false;


    public void connect_client(View view) {
        btn_clicked = true;
        String key = Init.find_et_by_id(this , R.id.et_dialogue_server_key).getText().toString();
        check_key(key); // chek validation in sert
        onBackPressed();
    }

    private void check_key(String s){
        s = s.trim();
        if (s.length()>0){

            try {
                int num = Integer.parseInt(s);
                if (num >= 0 && num <= 255){
                    Server_KEY = s;
                }
            }catch (Exception ex){
                // Wasnt a Number !!
                Server_KEY = NO_KEY;
            }
        }else {
            Server_KEY = NO_KEY;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue_client);

        setTitle("");

        Server_KEY =NO_KEY;

        final Handler handler = new Handler();

        final Runnable runnable  = new Runnable() {
            @Override
            public void run() {
                update_recycler();
                if (!btn_clicked){
                    handler.postDelayed(this  , 500);
                }
            }
        };

        handler.postDelayed(runnable , 500);

    }

    private void update_recycler() {
        if (NetManager.ips.size() < 1){
            TextView tv_empty_list = (TextView) findViewById(R.id.tv_emty_ips);
            tv_empty_list.setVisibility(View.VISIBLE);
            RecyclerView recycler = (RecyclerView) findViewById(R.id.rec_list_ips);
            recycler.setVisibility(View.GONE);

        }else {
            TextView tv_empty_list = (TextView) findViewById(R.id.tv_emty_ips);
            tv_empty_list.setVisibility(View.GONE);
            RecyclerView recycler = (RecyclerView) findViewById(R.id.rec_list_ips);
            recycler.setVisibility(View.VISIBLE);
            Ip_Adapter.Init(NetManager.ips , this);

        }
    }

    @Override
    public void onBackPressed() {
        if (!Server_KEY.equals(NO_KEY)){
            NetManager.getNt(this).togle_client(this);
        }else {
            Init.Toas(this , "کلید صحیح نیست");
        }
        super.onBackPressed();
    }
}
