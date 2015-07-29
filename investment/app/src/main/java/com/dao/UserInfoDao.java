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
public class UserInfoDao extends AbstractDao<UserInfoDO, String> {

    public static final String TABLENAME = "UserInfo";

    /**
     * Properties of entity UserInfoDO.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Servno = new Property(0, String.class, "servno", true, "SERVNO");
        public final static Property Aucode = new Property(1, String.class, "aucode", false, "AUCODE");
        public final static Property Compny = new Property(2, String.class, "compny", false, "COMPNY");
        public final static Property Inrank = new Property(3, String.class, "inrank", false, "INRANK");
        public final static Property Ivcode = new Property(4, String.class, "ivcode", false, "IVCODE");
        public final static Property Locatn = new Property(5, String.class, "locatn", false, "LOCATN");
        public final static Property Postin = new Property(6, String.class, "postin", false, "POSTIN");
        public final static Property Rscope = new Property(7, String.class, "rscope", false, "RSCOPE");
        public final static Property Tags = new Property(8, String.class, "tags", false, "TAGS");
        public final static Property Teleph = new Property(9, String.class, "teleph", false, "TELEPH");
        public final static Property Ualias = new Property(10, String.class, "ualias", false, "UALIAS");
        public final static Property Uisvip = new Property(11, String.class, "uisvip", false, "UISVIP");
        public final static Property Uphoto = new Property(12, String.class, "uphoto", false, "UPHOTO");
        public final static Property Uavail = new Property(13, Double.class, "uavail", false, "UAVAIL");
        public final static Property NameSort = new Property(14, String.class, "nameSort", false, "NAME_SORT");
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
                "'SERVNO' TEXT PRIMARY KEY NOT NULL ," + // 0: servno
                "'AUCODE' TEXT," + // 1: aucode
                "'COMPNY' TEXT," + // 2: compny
                "'INRANK' TEXT," + // 3: inrank
                "'IVCODE' TEXT," + // 4: ivcode
                "'LOCATN' TEXT," + // 5: locatn
                "'POSTIN' TEXT," + // 6: postin
                "'RSCOPE' TEXT," + // 7: rscope
                "'TAGS' TEXT," + // 8: tags
                "'TELEPH' TEXT," + // 9: teleph
                "'UALIAS' TEXT," + // 10: ualias
                "'UISVIP' TEXT," + // 11: uisvip
                "'UPHOTO' TEXT," + // 12: uphoto
                "'UAVAIL' REAL," + // 13: uavail
                "'NAME_SORT' TEXT);"); // 14: nameSort
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
 
        String servno = entity.getServno();
        if (servno != null) {
            stmt.bindString(1, servno);
        }
 
        String aucode = entity.getAucode();
        if (aucode != null) {
            stmt.bindString(2, aucode);
        }
 
        String compny = entity.getCompny();
        if (compny != null) {
            stmt.bindString(3, compny);
        }
 
        String inrank = entity.getInrank();
        if (inrank != null) {
            stmt.bindString(4, inrank);
        }
 
        String ivcode = entity.getIvcode();
        if (ivcode != null) {
            stmt.bindString(5, ivcode);
        }
 
        String locatn = entity.getLocatn();
        if (locatn != null) {
            stmt.bindString(6, locatn);
        }
 
        String postin = entity.getPostin();
        if (postin != null) {
            stmt.bindString(7, postin);
        }
 
        String rscope = entity.getRscope();
        if (rscope != null) {
            stmt.bindString(8, rscope);
        }
 
        String tags = entity.getTags();
        if (tags != null) {
            stmt.bindString(9, tags);
        }
 
        String teleph = entity.getTeleph();
        if (teleph != null) {
            stmt.bindString(10, teleph);
        }
 
        String ualias = entity.getUalias();
        if (ualias != null) {
            stmt.bindString(11, ualias);
        }
 
        String uisvip = entity.getUisvip();
        if (uisvip != null) {
            stmt.bindString(12, uisvip);
        }
 
        String uphoto = entity.getUphoto();
        if (uphoto != null) {
            stmt.bindString(13, uphoto);
        }
 
        Double uavail = entity.getUavail();
        if (uavail != null) {
            stmt.bindDouble(14, uavail);
        }
 
        String nameSort = entity.getNameSort();
        if (nameSort != null) {
            stmt.bindString(15, nameSort);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserInfoDO readEntity(Cursor cursor, int offset) {
        UserInfoDO entity = new UserInfoDO( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // servno
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // aucode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // compny
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // inrank
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // ivcode
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // locatn
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // postin
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // rscope
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // tags
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // teleph
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // ualias
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // uisvip
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // uphoto
            cursor.isNull(offset + 13) ? null : cursor.getDouble(offset + 13), // uavail
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // nameSort
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserInfoDO entity, int offset) {
        entity.setServno(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAucode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCompny(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setInrank(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIvcode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLocatn(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPostin(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRscope(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTags(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTeleph(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setUalias(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUisvip(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUphoto(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUavail(cursor.isNull(offset + 13) ? null : cursor.getDouble(offset + 13));
        entity.setNameSort(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(UserInfoDO entity, long rowId) {
        return entity.getServno();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(UserInfoDO entity) {
        if(entity != null) {
            return entity.getServno();
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
