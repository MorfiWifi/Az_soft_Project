/*
package inc.software.wifimorfi.az_soft_project.View;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.morfiwifi.morfi_project_samane.R;
import com.apps.morfiwifi.morfi_project_samane.models.Message;
import com.apps.morfiwifi.morfi_project_samane.models.User;
import com.apps.morfiwifi.morfi_project_samane.ui.ReciverActivity;

import java.util.List;

*/
/**
 * Created by WifiMorfi on 3/25/2018.
 *//*




public class message_RecyclerAdapter extends RecyclerView.Adapter<ViewHolder_message> {
    private List<Message> messages;
    private static  RecyclerView recyclerView;
    private static AppCompatActivity activity;

    public message_RecyclerAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public ViewHolder_message onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item , parent , false);
        return new ViewHolder_message(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder_message holder, final int position ) {
        Message sample_message = messages.get(position);
        // TODO: 3/25/2018  Choose Aproprate Image for Message (Be aware !)
        // TODO: 3/25/2018  ocnverting Date To persian One!!
        // {holder.image = case {type of User} }
        holder.t3.setText(sample_message.Recive_Date);
        holder.t1.setText(sample_message.Reciver_Type.toString()); // Use Message Header Insetead!
        holder.t2.setText(sample_message.Tags); // Minimall Tags! (Not All OF Them !)

        holder.im3dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof ReciverActivity){
                    LinearLayout bottom_sheet = (LinearLayout)
                        activity.findViewById(R.id.bottom_sheet);
                    final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    // **************************************************

                    TextView tv_sender = (TextView) activity.findViewById(R.id.tv_message_sender);
                    // TODO: 4/7/2018 fix sender Type (Needs A query OR Fix !?)
                    tv_sender.setText(User.kind.Admin.toString());

                    TextView tv_header = (TextView) activity.findViewById(R.id.tv_message_header);
                    // TODO: 4/7/2018 Fix Token (seperated and Moded!! power View)
                    tv_header.setText(messages.get(position).Tags);


                    TextView tv_matn = (TextView) activity.findViewById(R.id.tv_message_matn);
                    tv_matn.setText(messages.get(position).Matn);

                    TextView tv_date = (TextView) activity.findViewById(R.id.tv_message_date);
                    tv_date.setText(messages.get(position).Recive_Date);

                    // TODO: 4/7/2018 Fix Message With Or Without Replay
                    Button bu_reply = (Button) activity.findViewById(R.id.bu_replat_message);
                    if (position == 1){
                        bu_reply.setVisibility(View.VISIBLE);
                    }else
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



    }


    @Override
    public int getItemCount() {
        return messages.size();
    }



    public static void Init(List<Message> messages , AppCompatActivity activity){
        recyclerView = activity.findViewById(R.id.rec_messages_recycle);
        message_RecyclerAdapter.activity = activity;
        //recyclerView.refreshDrawableState();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(new message_RecyclerAdapter(messages));

    }
}*/
