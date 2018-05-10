package inc.software.wifimorfi.az_soft_project.Util;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import inc.software.wifimorfi.az_soft_project.Models.DaoSession;
import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.Models.Repository;
import inc.software.wifimorfi.az_soft_project.Ui.TopSheetActivity;
import inc.software.wifimorfi.az_soft_project.View.Docks_RecyclerAdapter;

public class Init {
    public static void Toas (Context context, String text ){
        Toast.makeText(context , text , Toast.LENGTH_LONG).show();
    }

    public static void terminal (String s){
        System.out.println(s);
    }

    public static void Kot_Ja_main (Context context , AppCompatActivity activity){

        DaoSession session = Repository.GetInstant(context);
        session = Repository.GetInstant(context);
        //List<?> current_docks2 =  (List<?>) session.getAllDaos();

        List<Dock> current_docks2 = session.getDockDao().loadAll();

        current_docks2.size();


       /* List<Dock> current_docks = session.getDockDao().queryBuilder()
                .list();*/

        String count = String.valueOf(current_docks2.size());
        Toas(context ,count  );

        Docks_RecyclerAdapter.Init(current_docks2 , activity);

    }

    public static void Kot_start_pop(Context context ,AppCompatActivity activity  ){
        context.startActivity(new Intent(activity, TopSheetActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }

    public static TextView find_tv_by_id (AppCompatActivity appCompatActivity ,  int m){
        TextView tv = (TextView) appCompatActivity.findViewById(m);
        return tv;
    }

    public static ImageView find_im_by_id (AppCompatActivity appCompatActivity , int m){
        ImageView im = (ImageView) appCompatActivity.findViewById(m);
        return im;
    }

}
