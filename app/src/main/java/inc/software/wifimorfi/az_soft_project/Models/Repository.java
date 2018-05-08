package inc.software.wifimorfi.az_soft_project.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLData;

public class Repository {

    private static DaoMaster.DevOpenHelper helper;
    private static DaoSession daoSession;

    /*public void getTH (){
        Dock dock = new Dock("max");
        dock.save();


    }*/

    public static DaoSession GetInstant (Context context){
        if (daoSession == null){
            helper = new DaoMaster.DevOpenHelper(context,"azsoftwaredb" , null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }

        return daoSession;
    }

    public static DaoSession GetNewInstant (Context context){
        //Force building new One
        helper = new DaoMaster.DevOpenHelper(context,"azsoftwaredb" , null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        return daoSession;
    }



}
