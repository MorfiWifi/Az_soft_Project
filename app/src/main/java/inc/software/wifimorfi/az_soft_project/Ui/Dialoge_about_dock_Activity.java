package inc.software.wifimorfi.az_soft_project.Ui;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.R;

public class Dialoge_about_dock_Activity extends AppCompatActivity {
    public static Dock dock = new Dock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialoge_about_dock_);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rate_dialogue);
        TextView textView = (TextView) findViewById(R.id.tv_dialogue_comment);
        String text = dock.getComment();
        if (text == null){
            textView.setText("بدون نظر");
        }else {
            if (text.length() < 1){
                textView.setText("بدون نظر");
            }else {
                textView.setText(dock.getComment());
            }
        }


        ratingBar.setNumStars(5);
        ratingBar.setRating((float) dock.getRate());
        ratingBar.setEnabled(false);
    }

    public void close_dialogue(View view) {
        onBackPressed();
    }
}
