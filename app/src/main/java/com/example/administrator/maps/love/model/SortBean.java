package com.example.administrator.maps.love.model;

import com.example.administrator.maps.init.base.MyApplication;
import com.example.administrator.maps.main.model.LovePoi;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.example.administrator.maps.common.db.DaoSession;
import com.example.administrator.maps.common.db.LovePoiDao;
import com.example.administrator.maps.common.db.SortBeanDao;

/**
 * Created by ldq on 2018/6/4.
 */

@Entity
public class SortBean {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    @ToMany(referencedJoinProperty = "sortId")
    private List<LovePoi> lovePois;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 927633451)
    private transient SortBeanDao myDao;
    
    @Generated(hash = 38179059)
    public SortBean() {
    }
    @Generated(hash = 2080402061)
    public SortBean(Long id, String title) {
        this.id = id;
        this.title = title;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2065530585)
    public List<LovePoi> getLovePois() {
        if (lovePois == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LovePoiDao targetDao = daoSession.getLovePoiDao();
            List<LovePoi> lovePoisNew = targetDao._querySortBean_LovePois(id);
            synchronized (this) {
                if (lovePois == null) {
                    lovePois = lovePoisNew;
                }
            }
        }
        return lovePois;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1600635833)
    public synchronized void resetLovePois() {
        lovePois = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2023158131)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSortBeanDao() : null;
    }

    /**
     * 将分类写入数据库
     * @param application application
     * @param sortBean 写入数据
     */
    public static void InsertSortFromDB(MyApplication application, SortBean sortBean){
        SortBeanDao sortBeanDao = application.getDaoSession().getSortBeanDao();
        sortBeanDao.insert(sortBean);
    }

    /**
     * 查询分类列表
     * @param application application
     * @param s 查询的title
     * @return
     */
    public static List<SortBean> SelectSortFromDB(MyApplication application, String s){
        SortBeanDao sortBeanDao = application.getDaoSession().getSortBeanDao();
        if (s.isEmpty()){
            return sortBeanDao.loadAll();
        }else {
            return sortBeanDao.queryRaw("where TITLE = ?", s);
        }
    }

}
