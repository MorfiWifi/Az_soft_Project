package inc.software.wifimorfi.az_soft_project.Models;

import com.orm.SugarRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity  public class Dock  {

    public Dock (){

    }
    public Dock(String name) {
        this.name = name;


}
    @Generated(hash = 205669810)
    public Dock(long Id, String name) {
        this.Id = Id;
        this.name = name;
    }
    public long getId() {
        return this.Id;
    }
    public void setId(long Id) {
        this.Id = Id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @org.greenrobot.greendao.annotation.Id
    long Id;
    String name ;





}
