package inc.software.wifimorfi.az_soft_project.View;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import inc.software.wifimorfi.az_soft_project.MainActivity;
import inc.software.wifimorfi.az_soft_project.Models.DaoSession;
import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.Models.Repository;
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


        holder.t1.setText(sample_message.getName());
        Calendar c = Calendar.getInstance();
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
                    tv_sender.setText("-YET NO USE-");
                    TextView tv_header = (TextView) activity.findViewById(R.id.tv_message_header);
                    tv_header.setText(docks.get(position).getName());
                    final RatingBar ratingBar = activity.findViewById(R.id.ra_dock_rate);
                    ratingBar.setRating((float) sample_message.getRate());
                    final TextInputEditText textInputEditText = activity.findViewById(R.id.in_edittx_dockreview);
                    textInputEditText.setText(sample_message.getComment());

                    ImageView im_close = (ImageView) activity.findViewById(R.id.im_close_message);
                    im_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sample_message.setComment(textInputEditText.getText().toString());
                            sample_message.setRate((int) ratingBar.getRating());
                            DaoSession session = Repository.GetInstant(activity);
                            session.getDockDao().save(sample_message);
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
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(new Docks_RecyclerAdapter(messages));

    }
}
