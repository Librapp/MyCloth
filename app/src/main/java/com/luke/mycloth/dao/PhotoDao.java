package com.luke.mycloth.dao;

import android.content.ContentValues;
import android.content.Context;

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

    public PhotoDao(Context context) {
        super(context);
    }

    public List<PhotoBean> query() {
        getReadableDatabase();
        List<PhotoBean> list = new ArrayList<>();
        dbRead.query(TABLE, null, "where ", null, null, null, null);
        return list;
    }

    public void insert(PhotoBean pb) {
        getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_ID, pb.id);
        values.put(TABLE_NAME, pb.name);
        values.put(TABLE_URL, pb.url);
        values.put(TABLE_FILEPATH, pb.filepath);
        values.put(TABLE_CATEGORY, pb.category);
        values.put(TABLE_SEASON, pb.season);
        values.put(TABLE_DESCRIPTION, pb.description);
        dbWrite.insert(TABLE, TABLE_FILEPATH+","+TABLE_DESCRIPTION, values);
    }

    public void replace(PhotoBean pb) {
        getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_ID, pb.id);
        values.put(TABLE_NAME, pb.name);
        values.put(TABLE_URL, pb.url);
        values.put(TABLE_FILEPATH, pb.filepath);
        values.put(TABLE_CATEGORY, pb.category);
        values.put(TABLE_SEASON, pb.season);
        values.put(TABLE_DESCRIPTION, pb.description);
        dbWrite.replace(TABLE,TABLE_FILEPATH+","+TABLE_DESCRIPTION,values);
    }
}
