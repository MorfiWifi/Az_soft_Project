package inc.software.wifimorfi.az_soft_project.Util;

import android.content.Context;
import android.widget.Toast;

public class Init {
    public static void Toas (Context context, String text ){
        Toast.makeText(context , text , Toast.LENGTH_LONG).show();
    }

    public static void terminal (String s){
        System.out.println(s);
    }

}
