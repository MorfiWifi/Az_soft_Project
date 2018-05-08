package inc.software.wifimorfi.az_soft_project.Ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import inc.software.wifimorfi.az_soft_project.R;

public class PopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
    }

    public void out_side(View view) {
        onBackPressed();
    }
}
