package com.luke.mycloth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.luke.mycloth.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 2016/2/21 0021.
 */
public class PhotoDao extends BaseDao {
    public static final String TABLE = "photo";
    public static final String TABLE_ID = "photo_id";
    public static final String TABLE_NAME = "photo_name";
    public static final String TABLE_URL = "photo_url";
    public static final String TABLE_FILEPATH = "photo_filepath";
    public static final String TABLE_CATEGORY = "photo_category";
    public static final String TABLE_SEASON = "photo_season";
    public static final String TABLE_DESCRIPTION = "photo_description";
    public static final String TABLE_SPRING = "photo_spring";
    public static final String TABLE_SUMMER = "photo_summer";
    public static final String TABLE_AUTUMN = "photo_autumn";
    public static final String TABLE_WINTER = "photo_winter";

    public PhotoDao(Context context) {
        super(context);
    }

    public List<PhotoBean> query() {
        getReadableDatabase();
        List<PhotoBean> list = new ArrayList<>();
        Cursor cursor = dbRead.query(TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            PhotoBean item = new PhotoBean();
            item.id = cursor.getString(cursor.getColumnIndex(TABLE_ID));
            item.name = cursor.getString(cursor.getColumnIndex(TABLE_NAME));
            item.url = cursor.getString(cursor.getColumnIndex(TABLE_URL));
            item.filepath = cursor.getString(cursor.getColumnIndex(TABLE_FILEPATH));
            item.description = cursor.getString(cursor.getColumnIndex(TABLE_DESCRIPTION));
            item.category = cursor.getInt(cursor.getColumnIndex(TABLE_CATEGORY));
            item.spring = cursor.getInt(cursor.getColumnIndex(TABLE_SPRING));
            item.summer = cursor.getInt(cursor.getColumnIndex(TABLE_SUMMER));
            item.autumn = cursor.getInt(cursor.getColumnIndex(TABLE_AUTUMN));
            item.winter = cursor.getInt(cursor.getColumnIndex(TABLE_WINTER));
            list.add(item);
        }
        return list;
    }

    public List<PhotoBean> queryByCategory(int category) {
        getReadableDatabase();
        List<PhotoBean> list = new ArrayList<>();
        Cursor cursor = dbRead.query(TABLE, null, TABLE_CATEGORY + "=?", new String[]{category + ""}, null, null, null);
        while (cursor.moveToNext()) {
            PhotoBean item = new PhotoBean();
            item.category = category;
            item.id = cursor.getString(cursor.getColumnIndex(TABLE_ID));
            item.name = cursor.getString(cursor.getColumnIndex(TABLE_NAME));
            item.url = cursor.getString(cursor.getColumnIndex(TABLE_URL));
            item.filepath = cursor.getString(cursor.getColumnIndex(TABLE_FILEPATH));
            item.description = cursor.getString(cursor.getColumnIndex(TABLE_DESCRIPTION));
            item.spring = cursor.getInt(cursor.getColumnIndex(TABLE_SPRING));
            item.summer = cursor.getInt(cursor.getColumnIndex(TABLE_SUMMER));
            item.autumn = cursor.getInt(cursor.getColumnIndex(TABLE_AUTUMN));
            item.winter = cursor.getInt(cursor.getColumnIndex(TABLE_WINTER));
            list.add(item);
        }
        return list;
    }

    public boolean isExist(String id) {
        getReadableDatabase();
        Cursor cursor = dbRead.query(TABLE, null, TABLE_ID + "=?", new String[]{id}, null, null, null);
        if (null != cursor)
            return cursor.moveToNext();
        else
            return false;
    }

    public void insert(PhotoBean pb) {
        getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(TABLE_ID, pb.id);
        values.put(TABLE_NAME, pb.name);
        values.put(TABLE_URL, pb.url);
        values.put(TABLE_FILEPATH, pb.filepath);
        values.put(TABLE_CATEGORY, pb.category);
        values.put(TABLE_DESCRIPTION, pb.description);
        values.put(TABLE_SPRING, pb.spring);
        values.put(TABLE_SUMMER, pb.summer);
        values.put(TABLE_AUTUMN, pb.autumn);
        values.put(TABLE_WINTER, pb.winter);
        dbWrite.insert(TABLE, null, values);
    }

    public void replace(PhotoBean pb) {
        getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_ID, pb.id);
        values.put(TABLE_NAME, pb.name);
        values.put(TABLE_URL, pb.url);
        values.put(TABLE_FILEPATH, pb.filepath);
        values.put(TABLE_CATEGORY, pb.category);
        values.put(TABLE_DESCRIPTION, pb.description);
        values.put(TABLE_SPRING, pb.spring);
        values.put(TABLE_SUMMER, pb.summer);
        values.put(TABLE_AUTUMN, pb.autumn);
        values.put(TABLE_WINTER, pb.winter);
        dbWrite.replace(TABLE, null, values);
    }

    public void delete(PhotoBean pb) {
        getWritableDatabase();
        dbWrite.delete(TABLE, TABLE_ID + "=?", new String[]{pb.id});
    }
}
