package inc.software.wifimorfi.az_soft_project.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;

@Entity  public class Dock  {

    public Dock (){


    }
    @Generated(hash = 1561703894)
    public Dock(Long id, String name, String fullpath, String comment, int rate,
            Long size, Date date) {
        this.id = id;
        this.name = name;
        this.fullpath = fullpath;
        this.comment = comment;
        this.rate = rate;
        this.size = size;
        this.date = date;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getRate() {
        return this.rate;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }
    public Long getSize() {
        return this.size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    

    @Id(autoincrement = true  )
    //@Index(value = "5", unique = true)
    Long id;
    String name ;
    String fullpath;
    String comment;
    int rate ;
    Long size;
    Date date;

    @Transient
    public boolean isSelected = false;






}
