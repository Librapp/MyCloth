package com.luke.mycloth.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.luke.mycloth.db.DbHelper;

/**
 * 数据库操作的基类
 *
 * @author wangli
 */
public class BaseDao {
    private DbHelper dbHelper;
    private Context context;
    protected static SQLiteDatabase dbWrite;
    protected static SQLiteDatabase dbRead;

    public BaseDao(Context context) {
        this.context = context;
    }

    protected void getWritableDatabase() {
        if (dbHelper == null) {
            dbHelper = DbHelper.getInstance(context);
        }
        dbWrite = dbHelper.getWritableDatabase();
    }

    protected void getReadableDatabase() {
        if (dbHelper == null) {
            dbHelper = DbHelper.getInstance(context);
        }
        dbRead = dbHelper.getReadableDatabase();
    }

    public static void dbClose() {
        if (dbWrite != null) {
            dbWrite.close();
            dbWrite = null;
        }
        if (dbRead != null) {
            dbRead.close();
            dbWrite = null;
        }
    }
}
