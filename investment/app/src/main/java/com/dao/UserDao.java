package com.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.dao.UserDO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table User.
*/
public class UserDao extends AbstractDao<UserDO, Long> {

    public static final String TABLENAME = "User";

    /**
     * Properties of entity UserDO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UnreadMsgCount = new Property(1, Integer.class, "unreadMsgCount", false, "UNREAD_MSG_COUNT");
        public final static Property Header = new Property(2, String.class, "header", false, "HEADER");
        public final static Property Avatar = new Property(3, String.class, "avatar", false, "AVATAR");
        public final static Property Type = new Property(4, String.class, "type", false, "TYPE");
    };


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'User' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'UNREAD_MSG_COUNT' INTEGER," + // 1: unreadMsgCount
                "'HEADER' TEXT," + // 2: header
                "'AVATAR' TEXT," + // 3: avatar
                "'TYPE' TEXT);"); // 4: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'User'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserDO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer unreadMsgCount = entity.getUnreadMsgCount();
        if (unreadMsgCount != null) {
            stmt.bindLong(2, unreadMsgCount);
        }
 
        String header = entity.getHeader();
        if (header != null) {
            stmt.bindString(3, header);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(4, avatar);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserDO readEntity(Cursor cursor, int offset) {
        UserDO entity = new UserDO( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // unreadMsgCount
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // header
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // avatar
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // type
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserDO entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUnreadMsgCount(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setHeader(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAvatar(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserDO entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserDO entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
