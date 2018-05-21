package inc.software.wifimorfi.az_soft_project.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import inc.software.wifimorfi.az_soft_project.R;

public class ViewHolder_recived_list_item extends RecyclerView.ViewHolder {

    public TextView t1; // Name
    public TextView t2; // Rate
    public TextView t3; // Review
    public ImageView dots3 ;
    public CheckBox checkBox;

    public ViewHolder_recived_list_item(View itemView) {
        super(itemView);

        t2 = (TextView) itemView.findViewById(R.id.tv_list_rivew_sm);
        t3 = (TextView) itemView.findViewById(R.id.tv_list_rate);
        t1 = (TextView) itemView.findViewById(R.id.tv_list_name);
        dots3 = itemView.findViewById(R.id.im_list_3dot);
        checkBox = itemView.findViewById(R.id.ch_list_item);

    }
}