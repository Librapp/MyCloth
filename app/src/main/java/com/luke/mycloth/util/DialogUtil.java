package com.luke.mycloth.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.luke.mycloth.ClothEditActivity;
import com.luke.mycloth.bean.PhotoBean;
import com.luke.mycloth.dao.PhotoDao;

/**
 * Created by Luke on 2016/2/21 0021.
 */
public class DialogUtil {
    public static AlertDialog doWithPhoto(final Activity activity, final PhotoBean pb) {
        final CharSequence[] items = {"编辑", "删除"};
        AlertDialog dlg = new Builder(activity).setTitle("操作").setItems(items,
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent intent = new Intent(activity, ClothEditActivity.class);
                                intent.putExtra("photo", pb);
                                activity.startActivity(intent);
                                break;
                            case 1:
                                new PhotoDao(activity).delete(pb);
                                break;
                        }
                    }
                }).create();
        return dlg;
    }
}
