package com.luke.mycloth.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.luke.mycloth.MyApplication;
import com.luke.mycloth.dao.ClothDao;

/**
 * Created by Luke on 2016/2/21 0021.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper dbHelper;

    public DbHelper(Context context) {
        super(context, "mycloth.db", null, MyApplication.getVersionCode(context));
    }

    public static DbHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createPhotoTable(db);
    }

    private void createPhotoTable(SQLiteDatabase db) {
        String sql = "create table if not exists " + ClothDao.TABLE +
                "(" +
                ClothDao.TABLE_ID +
                " int AUTO_INCREMENT PRIMARY KEY," +
                ClothDao.TABLE_NAME +
                " varchar," +
                ClothDao.TABLE_URL +
                " varchar," +
                ClothDao.TABLE_FILEPATH +
                " varchar," +
                ClothDao.TABLE_CATEGORY +
                " int," +
                ClothDao.TABLE_STYLE +
                " int," +
                ClothDao.TABLE_MATERIAL +
                " int," +
                ClothDao.TABLE_COLOR +
                " int," +
                ClothDao.TABLE_COUNT +
                " int," +
                ClothDao.TABLE_DESCRIPTION +
                " varchar," +
                ClothDao.TABLE_SPRING +
                " boolean," +
                ClothDao.TABLE_SUMMER +
                " boolean," +
                ClothDao.TABLE_AUTUMN +
                " boolean," +
                ClothDao.TABLE_WINTER +
                " boolean" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
