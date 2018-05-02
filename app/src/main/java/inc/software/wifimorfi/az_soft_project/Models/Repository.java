package inc.software.wifimorfi.az_soft_project.Models;

public class Repository {


    /*public void getTH (){
        Dock dock = new Dock("max");
        dock.save();


    }*/


    public void max (){
        DaoSession daoSession = DaoMaster.newDevSession(getApplicationContext(),"DB_DB");
        dockdao = daoSession.getDockDao();
    }

}
