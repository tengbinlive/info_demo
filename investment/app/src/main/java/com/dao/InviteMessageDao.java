package com.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.dao.InviteMessageDO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table InviteMessage.
*/
public class InviteMessageDao extends AbstractDao<InviteMessageDO, Long> {

    public static final String TABLENAME = "InviteMessage";

    /**
     * Properties of entity InviteMessageDO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property From = new Property(1, String.class, "from", false, "FROM");
        public final static Property Time = new Property(2, Long.class, "time", false, "TIME");
        public final static Property Reason = new Property(3, String.class, "reason", false, "REASON");
        public final static Property Status = new Property(4, Integer.class, "status", false, "STATUS");
        public final static Property UnreadCount = new Property(5, Integer.class, "unreadCount", false, "UNREAD_COUNT");
        public final static Property GroupId = new Property(6, String.class, "groupId", false, "GROUP_ID");
        public final static Property GroupName = new Property(7, String.class, "groupName", false, "GROUP_NAME");
        public final static Property Headphoto = new Property(8, String.class, "headphoto", false, "HEADPHOTO");
    };


    public InviteMessageDao(DaoConfig config) {
        super(config);
    }
    
    public InviteMessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'InviteMessage' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'FROM' TEXT," + // 1: from
                "'TIME' INTEGER," + // 2: time
                "'REASON' TEXT," + // 3: reason
                "'STATUS' INTEGER," + // 4: status
                "'UNREAD_COUNT' INTEGER," + // 5: unreadCount
                "'GROUP_ID' TEXT," + // 6: groupId
                "'GROUP_NAME' TEXT," + // 7: groupName
                "'HEADPHOTO' TEXT);"); // 8: headphoto
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'InviteMessage'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, InviteMessageDO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String from = entity.getFrom();
        if (from != null) {
            stmt.bindString(2, from);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(3, time);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(4, reason);
        }
 
        Integer status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(5, status);
        }
 
        Integer unreadCount = entity.getUnreadCount();
        if (unreadCount != null) {
            stmt.bindLong(6, unreadCount);
        }
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(7, groupId);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(8, groupName);
        }
 
        String headphoto = entity.getHeadphoto();
        if (headphoto != null) {
            stmt.bindString(9, headphoto);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public InviteMessageDO readEntity(Cursor cursor, int offset) {
        InviteMessageDO entity = new InviteMessageDO( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // from
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // time
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // reason
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // status
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // unreadCount
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // groupId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // groupName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // headphoto
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, InviteMessageDO entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFrom(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTime(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setReason(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStatus(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setUnreadCount(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setGroupId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGroupName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setHeadphoto(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(InviteMessageDO entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(InviteMessageDO entity) {
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
