package inc.software.wifimorfi.az_soft_project.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLData;

public class Repository {

    private DaoMaster.DevOpenHelper helper;
    private DaoSession daoSession;

    /*public void getTH (){
        Dock dock = new Dock("max");
        dock.save();


    }*/

    public DaoSession GetInstant (Context context){
        if (daoSession == null){
            helper = new DaoMaster.DevOpenHelper(context,"azsoftwaredb" , null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }

        return daoSession;
    }

    public DaoSession GetNewInstant (Context context){
        //Force building new One
        helper = new DaoMaster.DevOpenHelper(context,"azsoftwaredb" , null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        return daoSession;
    }


}
