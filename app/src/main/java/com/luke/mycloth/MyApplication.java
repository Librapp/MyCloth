package com.luke.mycloth;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Luke on 2016/2/21 0021.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static int getVersionCode(Context context) {
        int code = 0;
        try {
            code = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }
}
