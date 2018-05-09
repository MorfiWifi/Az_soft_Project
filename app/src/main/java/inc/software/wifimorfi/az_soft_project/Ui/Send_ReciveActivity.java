package inc.software.wifimorfi.az_soft_project.Ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.R;

public class Send_ReciveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__recive);
    }

    public static void start(){

        Gson json=new Gson();
        Dock dock = new Dock();
        String dev =json.toJson(dock);
        Dock dock1 =json.fromJson(dev,Dock.class); // a simple json way


    }
}
