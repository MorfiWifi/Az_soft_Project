package inc.software.wifimorfi.az_soft_project.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;

@Entity  public class Dock  {

    public Dock (){

    }



    @Generated(hash = 320275541)
    public Dock(Long id, String name, String fullpath) {
        this.id = id;
        this.name = name;
        this.fullpath = fullpath;
    }



    public void setId(Long Id) {
        this.id = Id;
    }



    public Long getId() {
        return this.id;
    }



    public String getName() {
        return this.name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getFullpath() {
        return this.fullpath;
    }



    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    @Id(autoincrement = true  )
    //@Index(value = "5", unique = true)
    Long id;
    String name ;
    String fullpath;






}
