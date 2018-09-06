package com.example.administrator.maps.common.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.administrator.maps.love.model.SortBean;
import com.example.administrator.maps.main.model.LovePoi;

import com.example.administrator.maps.common.db.SortBeanDao;
import com.example.administrator.maps.common.db.LovePoiDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sortBeanDaoConfig;
    private final DaoConfig lovePoiDaoConfig;

    private final SortBeanDao sortBeanDao;
    private final LovePoiDao lovePoiDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sortBeanDaoConfig = daoConfigMap.get(SortBeanDao.class).clone();
        sortBeanDaoConfig.initIdentityScope(type);

        lovePoiDaoConfig = daoConfigMap.get(LovePoiDao.class).clone();
        lovePoiDaoConfig.initIdentityScope(type);

        sortBeanDao = new SortBeanDao(sortBeanDaoConfig, this);
        lovePoiDao = new LovePoiDao(lovePoiDaoConfig, this);

        registerDao(SortBean.class, sortBeanDao);
        registerDao(LovePoi.class, lovePoiDao);
    }
    
    public void clear() {
        sortBeanDaoConfig.clearIdentityScope();
        lovePoiDaoConfig.clearIdentityScope();
    }

    public SortBeanDao getSortBeanDao() {
        return sortBeanDao;
    }

    public LovePoiDao getLovePoiDao() {
        return lovePoiDao;
    }

}
