package inc.software.wifimorfi.az_soft_project.View;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import inc.software.wifimorfi.az_soft_project.MainActivity;
import inc.software.wifimorfi.az_soft_project.Models.DaoSession;
import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.Models.Repository;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Ui.Dialogue;
import inc.software.wifimorfi.az_soft_project.Util.Init;

public class Ip_Adapter extends RecyclerView.Adapter<ViewHolder_ip> {

    private List<String> ips;
    private static  RecyclerView recyclerView;
    private static AppCompatActivity activity;

    public Ip_Adapter (List<String> ips) {
        this.ips = ips;
    }

    @Override
    public ViewHolder_ip onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ip_item , parent , false);
        return new ViewHolder_ip(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder_ip holder, final int position ) {
        final String ip = ips.get(position);


        holder.ip.setText(ip);
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Init.terminal("selected IP Is : "+ ip);
                // TODO: 6/3/2018 add other futures for Auto connect ........
                // fill ohter text view and continuee normally
                String[] parts = ip.split("\\.");

                if (parts.length >2){
                    Init.find_et_by_id(activity , R.id.et_dialogue_server_key).setText(parts[3]);
                }else {
                    Init.Toas(activity , "آیپی منایسب نیست!");
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return ips.size();
    }



    public static void Init(List<String> ips , AppCompatActivity activity){
        recyclerView = (RecyclerView) activity.findViewById(R.id.rec_list_ips);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        Ip_Adapter.activity = activity;
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(new Ip_Adapter(ips));

    }

}
