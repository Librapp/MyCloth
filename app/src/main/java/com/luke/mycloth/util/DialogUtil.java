package com.luke.mycloth.util;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.luke.mycloth.ClothEditActivity;
import com.luke.mycloth.bean.Cloth;
import com.luke.mycloth.dao.ClothDao;

/**
 * Created by Luke on 2016/2/21 0021.
 */
public class DialogUtil {
    public static Dialog doWithPhoto(final Activity activity, final Cloth pb) {
        final CharSequence[] items = {"编辑", "删除"};
        Dialog dlg = new Builder(activity).setTitle("操作").setItems(items,
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent intent = new Intent(activity, ClothEditActivity.class);
                                intent.putExtra("photo", pb);
                                activity.startActivity(intent);
                                break;
                            case 1:
                                new ClothDao(activity).delete(pb);
                                break;
                        }
                    }
                }).create();
        return dlg;
    }
}
