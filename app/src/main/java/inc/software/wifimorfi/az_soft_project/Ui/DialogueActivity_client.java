package inc.software.wifimorfi.az_soft_project.Ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import inc.software.wifimorfi.az_soft_project.Models.DaoSession;
import inc.software.wifimorfi.az_soft_project.Models.NetManager;
import inc.software.wifimorfi.az_soft_project.Models.Repository;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Util.Init;

public class DialogueActivity_client extends AppCompatActivity {
    public static String NO_KEY = "NO_KEY";
    public static String Server_KEY =NO_KEY;


    public void connect_client(View view) {
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
        //getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Server_KEY =NO_KEY;
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
