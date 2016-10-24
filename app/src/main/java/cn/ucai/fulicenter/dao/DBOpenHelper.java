package cn.ucai.fulicenter.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.ucai.fulicenter.I;


public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static DBOpenHelper instance;
    private static final String FULICENTER_USER_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_USER_NAME + " ("
            + UserDao.TABLE_COLUMN_NAME + " TEXT PRIMARY KEY ,"
            + UserDao.TABLE_COLUMN_NICK + " TEXT, "
            + UserDao.TABLE_COLUMN_AVATAR_ID + " INTEGER, "
            + UserDao.TABLE_COLUMN_AVATAR_TYPE + " INTEGER, "
            + UserDao.TABLE_COLUMN_AVATAR_PATH + " TEXT, "
            + UserDao.TABLE_COLUMN_AVATAR_SUFFIX + " TEXT "
            + UserDao.TABLE_COLUMN_AVATAR_LASTUPDATE_TIME + " TEXT);";

    public static DBOpenHelper onInit(Context context){
        if (instance == null){
            instance = new DBOpenHelper(context);
        }
        return instance;
    }
    public DBOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null,DATABASE_VERSION);
    }

    private static String getUserDatabaseName() {
        return I.User.TABLE_NAME + "_demo.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FULICENTER_USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //关闭数据库
    public void closeDB(){
        if (instance != null){
            SQLiteDatabase db = instance.getWritableDatabase();
            db.close();
            instance = null;
        }
    }
}
