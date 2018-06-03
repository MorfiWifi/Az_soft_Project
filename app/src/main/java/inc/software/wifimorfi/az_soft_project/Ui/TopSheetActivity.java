package inc.software.wifimorfi.az_soft_project.Ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import inc.software.wifimorfi.az_soft_project.MainActivity;
import inc.software.wifimorfi.az_soft_project.Models.DaoSession;
import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.Models.NetManager;
import inc.software.wifimorfi.az_soft_project.Models.Repository;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Util.Init;
import inc.software.wifimorfi.az_soft_project.View.Recived_Docks_Recycler_Ad;

public class TopSheetActivity extends AppCompatActivity {
    public String setting_json = "";
    public static int Exit_Cod = 0;
    private static Boolean can_continue = true;
    //public static MainActivity mainAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topsheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("شبکه");
        setSupportActionBar(toolbar);


        //setting_json =  getIntent().getStringExtra(Init.Extra_Kry);
        setting_json = Init.Load_String(this , Init.Extra_Kry , Init.NO_THING);


        Init.terminal(setting_json);

        load_setting(setting_json);

        //updater();
        //Init.topSheetAC = this;

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                //tv.append("Hello World");
                load_setting(NetManager.net_setting_glob_str);
                handler.postDelayed(this, 500);
            }
        };

        handler.postDelayed(r, 500);

        check_recived_list(this);

    }



    private void load_setting(String setting_json) {
        Gson gson = new Gson();

        Net_setting net_setting = gson.fromJson(setting_json , Net_setting.class);
        if (net_setting != null){
            if (net_setting.client_ST.equals(Net_setting.ReqType.couldnt_connect)){
                Init.find_im_by_id(this , R.id.im_topsheet_wifi).setImageResource(R.drawable.ic_no_wifi);

                Init.find_sw_by_id(this , R.id.sw_topsheet_wifi).setChecked(false);
                Init.find_tv_by_id(this , R.id.tv_topsheet_wifi).setText("خطا اتصال");
                Init.find_tv_by_id(this , R.id.tv_topsheet_wifi).setTextColor(0xffff0000);
                // TODO: 5/12/2018 chech if COLOR is red or NO !
            }else if (net_setting.client_ST.equals(Net_setting.ReqType.list_comment)){
                Init.find_im_by_id(this , R.id.im_topsheet_wifi).setImageResource(R.drawable.ic_wifi);
                Init.find_sw_by_id(this , R.id.sw_topsheet_wifi).setChecked(true);
                Init.find_tv_by_id(this , R.id.tv_topsheet_wifi).setText("لیست");
                Init.find_tv_by_id(this , R.id.tv_topsheet_wifi).setTextColor(0xff000000);
            }else if (net_setting.client_ST.equals(Net_setting.ReqType.file)){
                Init.find_im_by_id(this , R.id.im_topsheet_wifi).setImageResource(R.drawable.ic_wifi);
                Init.find_sw_by_id(this , R.id.sw_topsheet_wifi).setChecked(true);
                Init.find_tv_by_id(this , R.id.tv_topsheet_wifi).setText("فابل");
                Init.find_tv_by_id(this , R.id.tv_topsheet_wifi).setTextColor(0xff000000);
            }else{
                Init.find_im_by_id(this , R.id.im_topsheet_wifi).setImageResource(R.drawable.ic_no_wifi);
                Init.find_sw_by_id(this , R.id.sw_topsheet_wifi).setChecked(false);
                Init.find_tv_by_id(this , R.id.tv_topsheet_wifi).setText("");
                Init.find_tv_by_id(this , R.id.tv_topsheet_wifi).setTextColor(0xff000000);
            }

            if (net_setting.server_ST.equals(Net_setting.ReqType.list_comment)){
                Init.find_sw_by_id(this , R.id.sw_topsheet_share).setChecked(true);
                Init.find_tv_by_id(this , R.id.tv_topsheet_share).setText("لیست");
                Init.find_tv_by_id(this , R.id.tv_topsheet_share).setTextColor(0xff000000);

                Init.find_tv_by_id(this , R.id.tv_topsheet_key).setText(net_setting.KEY);
                Init.find_tv_by_id(this , R.id.tv_topsheet_key).setVisibility(View.VISIBLE);
                Init.find_tv_by_id(this , R.id.tv_topsheet_key_hint).setVisibility(View.VISIBLE);

            }else if(net_setting.server_ST.equals(Net_setting.ReqType.file)){
                Init.find_sw_by_id(this , R.id.sw_topsheet_share).setChecked(true);
                Init.find_tv_by_id(this , R.id.tv_topsheet_share).setText("فایل");
                Init.find_tv_by_id(this , R.id.tv_topsheet_share).setTextColor(0xff000000);

                Init.find_tv_by_id(this , R.id.tv_topsheet_key).setText(net_setting.KEY);
                Init.find_tv_by_id(this , R.id.tv_topsheet_key).setVisibility(View.VISIBLE);
                Init.find_tv_by_id(this , R.id.tv_topsheet_key_hint).setVisibility(View.VISIBLE);

            }else if(net_setting.server_ST.equals(Net_setting.ReqType.couldnt_start)){
                Init.find_sw_by_id(this , R.id.sw_topsheet_share).setChecked(false);
                Init.find_tv_by_id(this , R.id.tv_topsheet_share).setText("خطا شروع");
                Init.find_tv_by_id(this , R.id.tv_topsheet_share).setTextColor(0xffff0000);

                Init.find_tv_by_id(this , R.id.tv_topsheet_key).setText(net_setting.KEY);
                Init.find_tv_by_id(this , R.id.tv_topsheet_key).setVisibility(View.GONE);
                Init.find_tv_by_id(this , R.id.tv_topsheet_key_hint).setVisibility(View.GONE);
            }else {
                Init.find_sw_by_id(this , R.id.sw_topsheet_share).setChecked(false);
                Init.find_tv_by_id(this , R.id.tv_topsheet_share).setText("");
                Init.find_tv_by_id(this , R.id.tv_topsheet_share).setTextColor(0xff000000);

                //Init.find_tv_by_id(this , R.id.tv_topsheet_key).setText(net_setting.KEY);
                Init.find_tv_by_id(this , R.id.tv_topsheet_key).setVisibility(View.GONE);
                Init.find_tv_by_id(this , R.id.tv_topsheet_key_hint).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_sheet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void out_side(View view) {
        onBackPressed();
    }

    public void close_topsheet(MenuItem item) {
        onBackPressed();
    }

    public void go_share(View view) {
        // *************************************
        Init.terminal("before udp activated");
        Thread udp_server = new Thread(NetManager.getNt(this).get_client());
        udp_server.start(); // Hope for Paralell UDP
        Init.terminal("after udp activated");
        // *************************************


        DaoSession session = Repository.GetInstant(this);
        List<Dock> current_docks2 = session.getDockDao().loadAll();
        current_docks2.size();
        NetManager.list = current_docks2;
        NetManager.getNt(this).togle_server(this);
    }

    public void connect_lan(View view) {
        //***************************************
        // First Running THE UDP DISCOVER
        Init.terminal("before udp client");
        Thread udp_client = new Thread(NetManager.getNt(this).get_serevr());
        udp_client.start(); // Hope for Paralell UDP
        Init.terminal("after udp client");
        //***************************************
        if (NetManager.net_setting_glob == null){
            Intent intent = new Intent(this , DialogueActivity_client.class);
            startActivity(intent);
        }else if (NetManager.net_setting_glob.client_ST.equals(Net_setting.ReqType.OFF)){
            Intent intent = new Intent(this , DialogueActivity_client.class);
            startActivity(intent);
        }else {
            NetManager.getNt(this).togle_client(this);
        }
    }

    @Override
    public void onBackPressed() {
        //setResult(Init.BACK_PRESSED);
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        //setting_json = ""; //YET NOT SURE !
        //Exit_Cod = 6;
        super.onPause();
    }



    public static void  check_recived_list(final AppCompatActivity activity){

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                //tv.append("Hello World");
                list_cheker(activity);
                if (!NetManager.isReciving_file){
                    if (can_continue){
                        handler.postDelayed(this, 500);
                    }
                }
            }
        };

        handler.postDelayed(r, 500);

    }

    private static void list_cheker(AppCompatActivity activity) {
        if (NetManager.net_setting_glob != null){
            if (NetManager.net_setting_glob.client_ST.equals(Net_setting.ReqType.file)){
                if (activity instanceof TopSheetActivity){
                    can_continue = false;
                    Init.terminal("TRY START NEW ACTIVITY ! TIMES - TOPSHEET");
                    activity.startActivity(new Intent(activity , File_ChooserActivity.class));
                }
            }
        }
    }

}
