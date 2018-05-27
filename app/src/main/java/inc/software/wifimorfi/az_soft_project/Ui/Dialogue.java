package inc.software.wifimorfi.az_soft_project.Ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import java.io.File;

import inc.software.wifimorfi.az_soft_project.Models.DaoSession;
import inc.software.wifimorfi.az_soft_project.Models.Dock;
import inc.software.wifimorfi.az_soft_project.Models.Repository;
import inc.software.wifimorfi.az_soft_project.R;
import inc.software.wifimorfi.az_soft_project.Util.Init;

public class Dialogue {

    public AlertDialog Build_yes_no(AppCompatActivity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.dialog_fire_missiles);
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();

        return dialog;
    }

    public AlertDialog Build_user_pass (AppCompatActivity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Get the layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                // Add action buttons
                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Init.alertDialog.cancel(); // Thout this whould be in init
                    }
                });
        return builder.create();
    }

    public AlertDialog Remove_file (final AppCompatActivity activity , final Dock dock){
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Get the layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView( null)
                .setTitle("حذفش کنم ؟؟؟")
                // Add action buttons
                .setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        File file = new File(dock.getFullpath());
                        boolean res = file.delete();

                        if (res){
                            DaoSession session = Repository.GetInstant(activity);
                            session.getDockDao().delete(dock);
                            Init.Toas(activity ,"حدف شد !" );
                        }else {
                            //DaoSession session = Repository.GetInstant(activity);
                            //session.getDockDao().delete(dock);
                            Init.Toas(activity ,"حدف نمیشه !! عجب" );
                        }




                        dialog.cancel();


                        // sign in the user ...
                    }
                })
                .setNegativeButton("ولش کن", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                       //cancel(); // Thout this whould be in init
                    }
                });
        return builder.create();
    }



}
