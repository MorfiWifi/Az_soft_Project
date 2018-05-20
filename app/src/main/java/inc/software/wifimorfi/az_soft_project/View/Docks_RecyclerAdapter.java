package inc.software.wifimorfi.az_soft_project.View;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import inc.software.wifimorfi.az_soft_project.MainActivity;
import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Util.Init;


// Created by WifiMorfi on 3/25/2018.





public class Docks_RecyclerAdapter extends RecyclerView.Adapter<ViewHolder_message> {
    private List<Dock> docks;
    private static  RecyclerView recyclerView;
    private static AppCompatActivity activity;

    public Docks_RecyclerAdapter(List<Dock> docks) {
        this.docks = docks;
    }

    @Override
    public ViewHolder_message onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item , parent , false);
        return new ViewHolder_message(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder_message holder, final int position ) {
        final Dock sample_message = docks.get(position);

        // {holder.image = case {type of User} }
     /*   holder.t3.setText(sample_message.Recive_Date);
        holder.t1.setText(sample_message.Reciver_Type.toString()); // Use Message Header Insetead!
        holder.t2.setText(sample_message.Tags); // Minimall Tags! (Not All OF Them !)*/

        holder.t1.setText(sample_message.getName());
        //holder.t3.setText("-NO YET-"); // Use Message Header Insetead!
        //holder.t2.setText("-NO YET-"); // Minimall Tags! (Not All OF Them !)

        //Date d = new Date();
        Calendar c = Calendar.getInstance();
        //d = c.getTime();
        //Date d = new Dat
        c.set(Calendar.MINUTE , c.get(Calendar.MINUTE) - 30);



        if (sample_message.getDate().after(c.getTime())){
            holder.new_relise.setVisibility(View.VISIBLE);
        }else {
            holder.new_relise.setVisibility(View.INVISIBLE);
        }


        holder.im3dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activity instanceof MainActivity){
                    LinearLayout bottom_sheet = (LinearLayout)
                        activity.findViewById(R.id.bottom_sheet);
                    final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    // **************************************************

                    TextView tv_sender = (TextView) activity.findViewById(R.id.tv_message_sender);
                    // TODO: 4/7/2018 fix sender Type (Needs A query OR Fix !?)
                    //tv_sender.setText(docks.kind.Admin.toString());

                    tv_sender.setText("-YET NO USE-");

                    TextView tv_header = (TextView) activity.findViewById(R.id.tv_message_header);
                    // TODO: 4/7/2018 Fix Token (seperated and Moded!! power View)
                    //tv_header.setText(docks.get(position).Tags);
                    tv_header.setText(docks.get(position).getName());

                    TextView tv_matn = (TextView) activity.findViewById(R.id.tv_message_matn);
                    //tv_matn.setText(docks.get(position).Matn);
                    tv_matn.setText("-YET NO USE-");

                    TextView tv_date = (TextView) activity.findViewById(R.id.tv_message_date);
                    //tv_date.setText(docks.get(position).Recive_Date);
                    tv_date.setText("-YET NO USE-");

                    // TODO: 4/7/2018 Fix Message With Or Without Replay
                    Button bu_reply = (Button) activity.findViewById(R.id.bu_replat_message);
                    /*if (position == 1){
                        bu_reply.setVisibility(View.VISIBLE);
                    }else*/
                        bu_reply.setVisibility(View.GONE);


                    ImageView im_close = (ImageView) activity.findViewById(R.id.im_close_message);
                    im_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    });



                }
            }
        });


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(sample_message.getFullpath());
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(file),"application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(target, "Open "+sample_message.getName());
                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                    Init.Toas(activity , "یه برنامه برای pdf نصب کنی بد نیست");
                }

            }
        });

        /*holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Init.Kot_start_pop(activity , activity);
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return docks.size();
    }



    public static void Init(List<Dock> messages , AppCompatActivity activity){
        recyclerView = activity.findViewById(R.id.docs_recycle);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
        recyclerView.setLayoutManager(mLayoutManager);


        Docks_RecyclerAdapter.activity = activity;
        //recyclerView.refreshDrawableState();
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        //recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(new Docks_RecyclerAdapter(messages));

    }
}
