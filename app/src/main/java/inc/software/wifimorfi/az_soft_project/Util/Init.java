package inc.software.wifimorfi.az_soft_project.Util;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import inc.software.wifimorfi.az_soft_project.MainActivity;
import inc.software.wifimorfi.az_soft_project.Models.DaoSession;
import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.Models.NetManager;
import inc.software.wifimorfi.az_soft_project.Models.Repository;
import inc.software.wifimorfi.az_soft_project.Ui.File_ChooserActivity;
import inc.software.wifimorfi.az_soft_project.Ui.Net_setting;
import inc.software.wifimorfi.az_soft_project.Ui.TopSheetActivity;
import inc.software.wifimorfi.az_soft_project.View.Docks_RecyclerAdapter;

import static android.content.Context.MODE_PRIVATE;

public class Init {
    public static int REQ_COD = 100;
    public static int NOTHING = 101;
    public static int BACK_PRESSED = 102;
    public static String Extra_Kry = "json_setting";
    public static String MainAC_setting = "";
    //public static TopSheetActivity topSheetAC;
    private static String PEREF_KEY = "AZ_SOFT_PREFS";
    public static String NO_THING = "NON";
    public static AlertDialog alertDialog;
    private static Boolean can_continue = true;


    public static void Toas (Context context, String text ){
        Toast.makeText(context , text , Toast.LENGTH_LONG).show();
    }

    public static void terminal (String s){
        System.out.println(s);
    }

    public static void Kot_Ja_main (Context context , AppCompatActivity activity){

        //DaoSession session = Repository.GetInstant(context);
        DaoSession session = Repository.GetInstant(context);
        //List<?> current_docks2 =  (List<?>) session.getAllDaos();

        List<Dock> current_docks2 = session.getDockDao().loadAll();

        current_docks2.size();


       /* List<Dock> current_docks = session.getDockDao().queryBuilder()
                .list();*/

        String count = String.valueOf(current_docks2.size());
        //Toas(context ,count  );

        Docks_RecyclerAdapter.Init(current_docks2 , activity);

    }

    public static void Kot_start_pop(Context context ,AppCompatActivity activity  ){
        Intent intent = new Intent(activity, TopSheetActivity.class);
        intent.putExtra(Extra_Kry , MainAC_setting);
        activity.startActivity(intent , ActivityOptions.makeSceneTransitionAnimation(activity).toBundle() );

    }

    public static TextView find_tv_by_id (AppCompatActivity appCompatActivity ,  int m){
        TextView tv = (TextView) appCompatActivity.findViewById(m);
        return tv;
    }

    public static ImageView find_im_by_id (AppCompatActivity appCompatActivity , int m){
        ImageView im = (ImageView) appCompatActivity.findViewById(m);
        return im;
    }

    public static EditText find_et_by_id (AppCompatActivity appCompatActivity , int m){
        EditText et = (EditText) appCompatActivity.findViewById(m);
        return et;
    }

    public static SwitchCompat find_sw_by_id (AppCompatActivity appCompatActivity , int m){
        SwitchCompat et = (SwitchCompat) appCompatActivity.findViewById(m);
        return et;
    }

    public static void Save_String (AppCompatActivity appCompatActivity , String key , String Value){
        SharedPreferences.Editor editor = appCompatActivity.getSharedPreferences(PEREF_KEY, MODE_PRIVATE).edit();
        editor.putString(key, Value);
        editor.apply();
    }

    public  static String Load_String (AppCompatActivity appCompatActivity , String key , String def_val){
        SharedPreferences prefs = appCompatActivity.getSharedPreferences(PEREF_KEY, MODE_PRIVATE);
        String s = prefs.getString(key, def_val);
        if (s.equals(def_val))
            s = null;
        return s;
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
                if (activity instanceof MainActivity){
                    can_continue = false;
                    Init.terminal("TRY START NEW ACTIVITY ! TIMES - MAIN (INIT)");
                    activity.startActivity(new Intent(activity , File_ChooserActivity.class));
                }
            }
        }
    }



}
