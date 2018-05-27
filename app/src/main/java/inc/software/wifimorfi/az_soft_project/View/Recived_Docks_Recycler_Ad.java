package inc.software.wifimorfi.az_soft_project.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Ui.Dialoge_about_dock_Activity;
import inc.software.wifimorfi.az_soft_project.Ui.File_ChooserActivity;

public class Recived_Docks_Recycler_Ad extends RecyclerView.Adapter<ViewHolder_recived_list_item> {
    private List<Dock> docks;
    private static  RecyclerView recyclerView;
    private static AppCompatActivity activity;

    public List<Dock> getDocks (){
        return docks;
    }

    public Recived_Docks_Recycler_Ad(List<Dock> docks) {
        this.docks = docks;
    }

    @Override
    public ViewHolder_recived_list_item onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dock_list_item , parent , false);
        return new ViewHolder_recived_list_item(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder_recived_list_item holder, final int position ) {
        final Dock sample_message = docks.get(position);

        holder.t1.setText(sample_message.getName());
        holder.t3.setText(sample_message.getComment()); // CUT THIS LESS !!! MAY NEED
        holder.t2.setText(String.valueOf(sample_message.getRate()));

        holder.dots3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialoge_about_dock_Activity.dock = sample_message;
                activity.startActivity(new Intent(activity , Dialoge_about_dock_Activity.class));


            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){
                    sample_message.isSelected = true;
                }else {
                    sample_message.isSelected = false;
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return docks.size();
    }



    public static void Init(List<Dock> messages , AppCompatActivity activity){
        recyclerView = (RecyclerView) activity.findViewById(R.id.docs_recycle_for_recive);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
//        recyclerView.setLayoutManager(mLayoutManager);


        Recived_Docks_Recycler_Ad.activity = activity;
        //recyclerView.refreshDrawableState();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(false);
        Recived_Docks_Recycler_Ad rec_view = new Recived_Docks_Recycler_Ad(messages);
        recyclerView.setAdapter(rec_view);

        if (activity instanceof File_ChooserActivity){
            ((File_ChooserActivity) activity).recived_docks_recycler_ad  = rec_view;

        }

    }
}
