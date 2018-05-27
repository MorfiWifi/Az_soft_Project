package inc.software.wifimorfi.az_soft_project.Ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.Models.NetManager;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Util.Init;
import inc.software.wifimorfi.az_soft_project.View.Docks_RecyclerAdapter;
import inc.software.wifimorfi.az_soft_project.View.Recived_Docks_Recycler_Ad;

public class File_ChooserActivity extends AppCompatActivity {
    public static List<Dock> list;
    public static String json;
    public int choosen_count = 0;
    public Recived_Docks_Recycler_Ad recived_docks_recycler_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file__chooser);


        /*FireMissilesDialogFragment dial = new   FireMissilesDialogFragment();
        dial.show();*/




        if (list != null){
            Init.Toas(this , String.valueOf(NetManager.list.size()));
            list = new ArrayList<Dock>();
            list =  stringToArray(json , Dock[].class);
            //ArrayList<Dock> arrayList = (ArrayList<Dock>) list;
            Recived_Docks_Recycler_Ad.Init(list , this);
            //RecyclerView recyclerView = findViewById(R.id.docs_recycle_for_recive);


//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
//        recyclerView.setLayoutManager(mLayoutManager);


            //Recived_Docks_Recycler_Ad.activity = activity;
           /* //recyclerView.refreshDrawableState();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(new Docks_RecyclerAdapter(messages));




            Recived_Docks_Recycler_Ad.Init(NetManager.list , this);*/

           if (list.size() > 0){
               final Handler handler = new Handler();

               final Runnable r = new Runnable() {
                   public void run() {
                       //tv.append("Hello World");
                       list_cheker();
                       if (choosen_count > 0){
                           // Show buttom sheet
                           LinearLayout bottom_sheet = (LinearLayout)
                                   findViewById(R.id.bottom_sheet_for_recive);
                           final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
                           bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                       }else {
                           // Hide bittom sheet
                           LinearLayout bottom_sheet = (LinearLayout)
                                   findViewById(R.id.bottom_sheet_for_recive);
                           final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
                           bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                       }
                       handler.postDelayed(this, 500);
                   }
               };

               handler.postDelayed(r, 500);
           }
        }
        else{
            Init.Toas(this , "LIST WAS Unknown !!");
        }
    }


    private void  list_cheker (){
        choosen_count = 0;
        for (Dock doc: recived_docks_recycler_ad.getDocks()) {
            if (doc.isSelected){
                choosen_count = choosen_count +1;
            }
        }
    }


    public static <T> List<T> stringToArray (String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    public void get_IMEI(){
        // TODO: 5/20/2018 get Use of IMEI
        TelephonyManager mngr = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mngr.getDeviceId();
    }

    public void get_wanted_docks(View view) {
        list_cheker();
        if (choosen_count <= 0){
            Init.Toas(this , "چیزی انتخواب نشده!");
        }else{
            List<Dock> want = new ArrayList<>();
            for (Dock dock: list) {
                if (dock.isSelected){
                    want.add(dock);

                }
            }
            NetManager.want_list = want;
            NetManager.is_wantlist_ready = true;

            Intent intent = new Intent(this , Dialogue_recive_statesticsActivity.class);
            startActivity(intent);



            // Client Already Running!
            //NetManager.getNt(this).togle_client(this);



        }
    }
}
