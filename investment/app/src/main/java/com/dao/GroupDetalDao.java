package com.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.dao.GroupDetalDO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table GroupDetal.
*/
public class GroupDetalDao extends AbstractDao<GroupDetalDO, Void> {

    public static final String TABLENAME = "GroupDetal";

    /**
     * Properties of entity GroupDetalDO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Approval = new Property(1, Boolean.class, "approval", false, "APPROVAL");
        public final static Property Canadd = new Property(2, String.class, "canadd", false, "CANADD");
        public final static Property Desc = new Property(3, String.class, "desc", false, "DESC");
        public final static Property Gphoto = new Property(4, String.class, "gphoto", false, "GPHOTO");
        public final static Property Groupid = new Property(5, String.class, "groupid", true, "GROUPID");
        public final static Property Groupname = new Property(6, String.class, "groupname", false, "GROUPNAME");
        public final static Property Maxusers = new Property(7, Integer.class, "maxusers", false, "MAXUSERS");
        public final static Property Memnum = new Property(8, Integer.class, "memnum", false, "MEMNUM");
        public final static Property Owner = new Property(9, String.class, "owner", false, "OWNER");
        public final static Property Payfor = new Property(10, Integer.class, "payfor", false, "PAYFOR");
        public final static Property Validt = new Property(11, String.class, "validt", false, "VALIDT");
        public final static Property Visble = new Property(12, String.class, "visble", false, "VISBLE");
        public final static Property Istoll = new Property(13, String.class, "istoll", false, "ISTOLL");
    };


    public GroupDetalDao(DaoConfig config) {
        super(config);
    }
    
    public GroupDetalDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'GroupDetal' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'APPROVAL' INTEGER," + // 1: approval
                "'CANADD' TEXT," + // 2: canadd
                "'DESC' TEXT," + // 3: desc
                "'GPHOTO' TEXT," + // 4: gphoto
                "'GROUPID' TEXT PRIMARY KEY NOT NULL ," + // 5: groupid
                "'GROUPNAME' TEXT," + // 6: groupname
                "'MAXUSERS' INTEGER," + // 7: maxusers
                "'MEMNUM' INTEGER," + // 8: memnum
                "'OWNER' TEXT," + // 9: owner
                "'PAYFOR' INTEGER," + // 10: payfor
                "'VALIDT' TEXT," + // 11: validt
                "'VISBLE' TEXT," + // 12: visble
                "'ISTOLL' TEXT);"); // 13: istoll
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'GroupDetal'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, GroupDetalDO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Boolean approval = entity.getApproval();
        if (approval != null) {
            stmt.bindLong(2, approval ? 1l: 0l);
        }
 
        String canadd = entity.getCanadd();
        if (canadd != null) {
            stmt.bindString(3, canadd);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(4, desc);
        }
 
        String gphoto = entity.getGphoto();
        if (gphoto != null) {
            stmt.bindString(5, gphoto);
        }
 
        String groupid = entity.getGroupid();
        if (groupid != null) {
            stmt.bindString(6, groupid);
        }
 
        String groupname = entity.getGroupname();
        if (groupname != null) {
            stmt.bindString(7, groupname);
        }
 
        Integer maxusers = entity.getMaxusers();
        if (maxusers != null) {
            stmt.bindLong(8, maxusers);
        }
 
        Integer memnum = entity.getMemnum();
        if (memnum != null) {
            stmt.bindLong(9, memnum);
        }
 
        String owner = entity.getOwner();
        if (owner != null) {
            stmt.bindString(10, owner);
        }
 
        Integer payfor = entity.getPayfor();
        if (payfor != null) {
            stmt.bindLong(11, payfor);
        }
 
        String validt = entity.getValidt();
        if (validt != null) {
            stmt.bindString(12, validt);
        }
 
        String visble = entity.getVisble();
        if (visble != null) {
            stmt.bindString(13, visble);
        }
 
        String istoll = entity.getIstoll();
        if (istoll != null) {
            stmt.bindString(14, istoll);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public GroupDetalDO readEntity(Cursor cursor, int offset) {
        GroupDetalDO entity = new GroupDetalDO( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0, // approval
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // canadd
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // desc
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // gphoto
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // groupid
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // groupname
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // maxusers
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // memnum
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // owner
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // payfor
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // validt
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // visble
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // istoll
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, GroupDetalDO entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setApproval(cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0);
        entity.setCanadd(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDesc(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGphoto(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGroupid(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setGroupname(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMaxusers(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setMemnum(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setOwner(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPayfor(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setValidt(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setVisble(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setIstoll(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(GroupDetalDO entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(GroupDetalDO entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
