package inc.software.wifimorfi.az_soft_project.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {

    @Id(autoincrement = true)
    Long id;
    String Name;
    String IMEI;
    @Generated(hash = 2051133476)
    public User(Long id, String Name, String IMEI) {
        this.id = id;
        this.Name = Name;
        this.IMEI = IMEI;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getIMEI() {
        return this.IMEI;
    }
    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }



}
