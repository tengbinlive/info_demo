package com.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.dao.ConfigDO;
import com.dao.GroupDetalDO;
import com.dao.UserInfoDO;
import com.dao.UserDO;

import com.dao.ConfigDao;
import com.dao.GroupDetalDao;
import com.dao.UserInfoDao;
import com.dao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig configDaoConfig;
    private final DaoConfig groupDetalDaoConfig;
    private final DaoConfig userInfoDaoConfig;
    private final DaoConfig userDaoConfig;

    private final ConfigDao configDao;
    private final GroupDetalDao groupDetalDao;
    private final UserInfoDao userInfoDao;
    private final UserDao userDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        configDaoConfig = daoConfigMap.get(ConfigDao.class).clone();
        configDaoConfig.initIdentityScope(type);

        groupDetalDaoConfig = daoConfigMap.get(GroupDetalDao.class).clone();
        groupDetalDaoConfig.initIdentityScope(type);

        userInfoDaoConfig = daoConfigMap.get(UserInfoDao.class).clone();
        userInfoDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        configDao = new ConfigDao(configDaoConfig, this);
        groupDetalDao = new GroupDetalDao(groupDetalDaoConfig, this);
        userInfoDao = new UserInfoDao(userInfoDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(ConfigDO.class, configDao);
        registerDao(GroupDetalDO.class, groupDetalDao);
        registerDao(UserInfoDO.class, userInfoDao);
        registerDao(UserDO.class, userDao);
    }
    
    public void clear() {
        configDaoConfig.getIdentityScope().clear();
        groupDetalDaoConfig.getIdentityScope().clear();
        userInfoDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
    }

    public ConfigDao getConfigDao() {
        return configDao;
    }

    public GroupDetalDao getGroupDetalDao() {
        return groupDetalDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
