package com.luke.mycloth.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Luke on 2016/2/21 0021.
 */
public class Constants {
    public static final int CATEGORY_HEAD=1;

    public static final int SEASON_SPRING=1;
    public static final int SEASON_SUMMER=2;
    public static final int SEASON_AUTUMN=3;
    public static final int SEASON_WINTER=4;

    public static final String DCIM= Environment.getExternalStorageDirectory()+ File.separator+"MyCloth"+File.separator;

}
