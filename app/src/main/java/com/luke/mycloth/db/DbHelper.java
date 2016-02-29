package com.luke.mycloth.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.luke.mycloth.MyApplication;
import com.luke.mycloth.dao.PhotoDao;

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
        String sql = "create table if not exists " + PhotoDao.TABLE +
                "(" +
                PhotoDao.TABLE_ID +
                " int," +
                PhotoDao.TABLE_NAME +
                " varchar," +
                PhotoDao.TABLE_URL +
                " varchar," +
                PhotoDao.TABLE_FILEPATH +
                " varchar," +
                PhotoDao.TABLE_CATEGORY +
                " int," +
                PhotoDao.TABLE_SEASON +
                " int," +
                PhotoDao.TABLE_DESCRIPTION + " varchar)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
