package inc.software.wifimorfi.az_soft_project.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import inc.software.wifimorfi.az_soft_project.R;

public class ViewHolder_ip extends RecyclerView.ViewHolder  {
//    public ImageView image ;
    public TextView ip;
    public LinearLayout lin;
    //public TextView t2;
    // public TextView t3;
//    public ImageView im3dot;
//    public ImageView new_relise;
//    public ImageView im_cross;

    public ViewHolder_ip (View itemView) {
        super(itemView);
//        image = (ImageView) itemView.findViewById(R.id.im_doc_image);
//        im3dot = (ImageView) itemView.findViewById(R.id.im_3dot);
        ip = (TextView) itemView.findViewById(R.id.tv_ip_of_reciver);
        lin= (LinearLayout) itemView.findViewById(R.id.lin_ip_parent);
//        new_relise = (ImageView) itemView.findViewById(R.id.im_new_relise);
        //t2 = (TextView) itemView.findViewById(R.id.tv_message_t2);
        //t3 = (TextView) itemView.findViewById(R.id.tv_message_t3);
//        im_cross = (ImageView) itemView.findViewById(R.id.im_cross);
    }
}
