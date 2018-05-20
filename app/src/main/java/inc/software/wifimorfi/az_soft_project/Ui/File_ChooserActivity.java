package inc.software.wifimorfi.az_soft_project.Ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;

import java.util.List;

import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.Models.NetManager;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Util.Init;
import inc.software.wifimorfi.az_soft_project.View.Docks_RecyclerAdapter;
import inc.software.wifimorfi.az_soft_project.View.Recived_Docks_Recycler_Ad;

public class File_ChooserActivity extends AppCompatActivity {
    public static List<Dock> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file__chooser);


        /*FireMissilesDialogFragment dial = new   FireMissilesDialogFragment();
        dial.show();*/




        if (list != null){
            Init.Toas(this , String.valueOf(NetManager.list.size()));

            Object recyclerView = findViewById(R.id.docs_recycle_for_recive);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
//        recyclerView.setLayoutManager(mLayoutManager);


            //Recived_Docks_Recycler_Ad.activity = activity;
           /* //recyclerView.refreshDrawableState();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(new Docks_RecyclerAdapter(messages));




            Recived_Docks_Recycler_Ad.Init(NetManager.list , this);*/


        }
        else{
            Init.Toas(this , "LIST WAS NULL !!");
        }

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
}
