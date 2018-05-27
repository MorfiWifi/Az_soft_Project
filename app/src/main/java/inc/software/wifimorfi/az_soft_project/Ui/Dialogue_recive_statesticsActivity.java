package inc.software.wifimorfi.az_soft_project.Ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import inc.software.wifimorfi.az_soft_project.MainActivity;
import inc.software.wifimorfi.az_soft_project.Models.NetManager;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Util.Init;

public class Dialogue_recive_statesticsActivity extends AppCompatActivity {
    private Boolean ready_for_close = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue_recive_statestics);

        final TextView tv_neme = Init.find_tv_by_id(this, R.id.tv_stat_name);
        final TextView tv_size = Init.find_tv_by_id(this, R.id.tv_stat_size);
        final TextView tv_per = Init.find_tv_by_id(this, R.id.tv_stat_persent);


        if (NetManager.is_reciving_dock != null){
            tv_neme.setText(NetManager.is_reciving_dock.getName());
            tv_size.setText((NetManager.is_reciving_dock.getSize()/1048576) + " MB");
            Long readed_size = (long)( NetManager.buff_size *  NetManager.reciving_buf_count);
            tv_per.setText((readed_size/NetManager.is_reciving_dock.getSize())*100 +" %");
        }else{
            tv_neme.setText("?");
            tv_size.setText("?");
            //Long readed_size = (long)( NetManager.buff_size *  NetManager.reciving_buf_count);
            tv_per.setText("?");
        }



        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                //tv.append("Hello World");

                update_view(tv_neme , tv_size , tv_per);
                if (!ready_for_close){
                    handler.postDelayed(this, 200);
                }
            }
        };

        handler.postDelayed(r, 200);
    }

    private void update_view(TextView tv_neme ,TextView tv_size,TextView tv_per){


        if (!NetManager.is_EX){
            if (NetManager.is_reciving_finished){
                Init.Toas(this  ,"تمام شد");
                ready_for_close = true;

                // THis Finished HERE >>>>>>>>>>>>>>>>>>>>
                Intent intent = new Intent(this , MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // TEST >>>>>
                startActivity(intent);



            }else {
                if (NetManager.is_reciving_dock != null){
                    tv_neme.setText(NetManager.is_reciving_dock.getName());
                    tv_size.setText((NetManager.is_reciving_dock.getSize()/1048576) + " MB");
                    Long readed_size = (long)( NetManager.buff_size *  NetManager.reciving_buf_count);
                    tv_per.setText((((float)readed_size /(float)NetManager.is_reciving_dock.getSize()))*100 +" %");
                }else{
                    tv_neme.setText("?");
                    tv_size.setText("?");
                    //Long readed_size = (long)( NetManager.buff_size *  NetManager.reciving_buf_count);
                    tv_per.setText("?");
                }
            }


        }else {
            Init.Toas(this  ,"شبکه پوکید!");
            Intent intent = new Intent(this , MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // TEST >>>>>
            startActivity(intent);
            ready_for_close = true;
        }



    }

    @Override
    public void onBackPressed() {
        if (ready_for_close){

            //test Things............
            super.onBackPressed();
        }

    }
}
