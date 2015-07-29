package com.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.dao.UserInfoDO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table UserInfo.
*/
public class UserInfoDao extends AbstractDao<UserInfoDO, Void> {

    public static final String TABLENAME = "UserInfo";

    /**
     * Properties of entity UserInfoDO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Servno = new Property(1, String.class, "servno", true, "SERVNO");
        public final static Property Aucode = new Property(2, String.class, "aucode", false, "AUCODE");
        public final static Property Compny = new Property(3, String.class, "compny", false, "COMPNY");
        public final static Property Inrank = new Property(4, String.class, "inrank", false, "INRANK");
        public final static Property Ivcode = new Property(5, String.class, "ivcode", false, "IVCODE");
        public final static Property Locatn = new Property(6, String.class, "locatn", false, "LOCATN");
        public final static Property Postin = new Property(7, String.class, "postin", false, "POSTIN");
        public final static Property Rscope = new Property(8, String.class, "rscope", false, "RSCOPE");
        public final static Property Tags = new Property(9, String.class, "tags", false, "TAGS");
        public final static Property Teleph = new Property(10, String.class, "teleph", false, "TELEPH");
        public final static Property Ualias = new Property(11, String.class, "ualias", false, "UALIAS");
        public final static Property Uisvip = new Property(12, String.class, "uisvip", false, "UISVIP");
        public final static Property Uphoto = new Property(13, String.class, "uphoto", false, "UPHOTO");
        public final static Property Uavail = new Property(14, Double.class, "uavail", false, "UAVAIL");
        public final static Property NameSort = new Property(15, String.class, "nameSort", false, "NAME_SORT");
    };


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'UserInfo' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'SERVNO' TEXT PRIMARY KEY NOT NULL ," + // 1: servno
                "'AUCODE' TEXT," + // 2: aucode
                "'COMPNY' TEXT," + // 3: compny
                "'INRANK' TEXT," + // 4: inrank
                "'IVCODE' TEXT," + // 5: ivcode
                "'LOCATN' TEXT," + // 6: locatn
                "'POSTIN' TEXT," + // 7: postin
                "'RSCOPE' TEXT," + // 8: rscope
                "'TAGS' TEXT," + // 9: tags
                "'TELEPH' TEXT," + // 10: teleph
                "'UALIAS' TEXT," + // 11: ualias
                "'UISVIP' TEXT," + // 12: uisvip
                "'UPHOTO' TEXT," + // 13: uphoto
                "'UAVAIL' REAL," + // 14: uavail
                "'NAME_SORT' TEXT);"); // 15: nameSort
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'UserInfo'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserInfoDO entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String servno = entity.getServno();
        if (servno != null) {
            stmt.bindString(2, servno);
        }
 
        String aucode = entity.getAucode();
        if (aucode != null) {
            stmt.bindString(3, aucode);
        }
 
        String compny = entity.getCompny();
        if (compny != null) {
            stmt.bindString(4, compny);
        }
 
        String inrank = entity.getInrank();
        if (inrank != null) {
            stmt.bindString(5, inrank);
        }
 
        String ivcode = entity.getIvcode();
        if (ivcode != null) {
            stmt.bindString(6, ivcode);
        }
 
        String locatn = entity.getLocatn();
        if (locatn != null) {
            stmt.bindString(7, locatn);
        }
 
        String postin = entity.getPostin();
        if (postin != null) {
            stmt.bindString(8, postin);
        }
 
        String rscope = entity.getRscope();
        if (rscope != null) {
            stmt.bindString(9, rscope);
        }
 
        String tags = entity.getTags();
        if (tags != null) {
            stmt.bindString(10, tags);
        }
 
        String teleph = entity.getTeleph();
        if (teleph != null) {
            stmt.bindString(11, teleph);
        }
 
        String ualias = entity.getUalias();
        if (ualias != null) {
            stmt.bindString(12, ualias);
        }
 
        String uisvip = entity.getUisvip();
        if (uisvip != null) {
            stmt.bindString(13, uisvip);
        }
 
        String uphoto = entity.getUphoto();
        if (uphoto != null) {
            stmt.bindString(14, uphoto);
        }
 
        Double uavail = entity.getUavail();
        if (uavail != null) {
            stmt.bindDouble(15, uavail);
        }
 
        String nameSort = entity.getNameSort();
        if (nameSort != null) {
            stmt.bindString(16, nameSort);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public UserInfoDO readEntity(Cursor cursor, int offset) {
        UserInfoDO entity = new UserInfoDO( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // servno
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // aucode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // compny
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // inrank
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // ivcode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // locatn
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // postin
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // rscope
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // tags
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // teleph
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // ualias
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // uisvip
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // uphoto
            cursor.isNull(offset + 14) ? null : cursor.getDouble(offset + 14), // uavail
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15) // nameSort
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserInfoDO entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setServno(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAucode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCompny(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setInrank(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIvcode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLocatn(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPostin(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRscope(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTags(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setTeleph(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUalias(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUisvip(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUphoto(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setUavail(cursor.isNull(offset + 14) ? null : cursor.getDouble(offset + 14));
        entity.setNameSort(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(UserInfoDO entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(UserInfoDO entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
