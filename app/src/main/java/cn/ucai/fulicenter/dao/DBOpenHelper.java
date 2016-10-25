package cn.ucai.fulicenter.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.ucai.fulicenter.I;

//对数据库的打开以及关闭的操作并且获得数据库的读和写的权限
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static DBOpenHelper instance;
    //数据库表
    private static final String FULICENTER_USER_TABLE_CREATE = "CREATE TABLE "
            + UserDao.USER_TABLE_NAME + " ("
            + UserDao.USER_COLUMN_NAME + " TEXT PRIMARY KEY,"
            + UserDao.USER_COLUMN_NICK + " TEXT, "
            + UserDao.USER_COLUMN_AVATAR_ID + " INTEGER, "
            + UserDao.USER_COLUMN_AVATAR_TYPE + " INTEGER, "
            + UserDao.USER_COLUMN_AVATAR_PATH + " TEXT, "
            + UserDao.USER_COLUMN_AVATAR_SUFFIX + " TEXT, "
            + UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME + " TEXT);";

    public static DBOpenHelper getInstance(Context context){
        if (instance == null){
            instance = new DBOpenHelper(context.getApplicationContext());
        }
        return instance;
    }
    public DBOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null,DATABASE_VERSION);
    }
//数据库的名称
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
