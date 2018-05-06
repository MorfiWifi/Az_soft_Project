package inc.software.wifimorfi.az_soft_project.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import inc.software.wifimorfi.az_soft_project.R;

/**
 * Created by WifiMorfi on 3/25/2018.
 */

public class ViewHolder_message extends RecyclerView.ViewHolder {

    public ImageView image ;
    public TextView t1;
    public TextView t2;
    public TextView t3;
    public ImageView im3dot;

    public ViewHolder_message(View itemView) {
        super(itemView);

        /*image = (ImageView) itemView.findViewById(R.id.im_message_image);
        im3dot = (ImageView) itemView.findViewById(R.id.im_message_more);
        t1 = (TextView) itemView.findViewById(R.id.tv_message_t1);
        t2 = (TextView) itemView.findViewById(R.id.tv_message_t2);
        t3 = (TextView) itemView.findViewById(R.id.tv_message_t3);*/
    }
}
