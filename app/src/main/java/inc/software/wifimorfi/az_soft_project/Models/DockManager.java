package inc.software.wifimorfi.az_soft_project.Models;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.List;

import inc.software.wifimorfi.az_soft_project.Util.Init;

public class DockManager {


    public static void scan_memory (File dir , Context context) {
        DaoSession sesshion = Repository.GetInstant(context);
        DockDao dockDao = sesshion.getDockDao();
        //dockDao.deleteAll(); // for new DB build


        String pdfPattern = ".pdf";

        File listFile[] = dir.listFiles();

        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    scan_memory (listFile[i] , context);
                } else {
                    if (listFile[i].getName().endsWith(pdfPattern)){
                        //Do what ever u want

                        String full_path = listFile[i].getAbsolutePath();



                        String name =  listFile[i].getName();

                        name = name.replace(".pdf" ,"");
                        name = name.replace(".PDF" ,"");





                        List<Dock> current_dock = dockDao.queryBuilder()
                                .where(DockDao.Properties.Fullpath.eq(full_path))
                                .list();


                        if (current_dock.size() < 1 ){
                            Dock tempi = new Dock();
                            //tempi.Id = 1;
                            tempi.fullpath = full_path;
                            tempi.name = name;

                            Init.terminal("Right Before Insertion");
                            dockDao.insert(tempi); // Returnes The ID IN DB
                            Init.terminal("Right After Insertion");
                            Init.terminal(full_path);
                        }else {
                            Init.terminal("Db contains This one!");
                        }


                    }
                }
            }
        }
    }

    public enum StorageState{
        NOT_AVAILABLE, WRITEABLE, READ_ONLY
    }

    public static StorageState getExternalStorageState() {
        StorageState result = StorageState.NOT_AVAILABLE;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return StorageState.WRITEABLE;
        }
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return StorageState.READ_ONLY;
        }

        return result;
    }

    public static void openDock (Context context , Dock dock){
        // TODO: 5/7/2018 Fix using The Dock Not Stock
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }


}
