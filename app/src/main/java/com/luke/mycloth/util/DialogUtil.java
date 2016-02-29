package com.luke.mycloth.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Luke on 2016/2/21 0021.
 */
public class DialogUtil {

    public static AlertDialog pickPhoto(final Activity activity,final int request_camera,final int request_adlum) {
        final CharSequence[] items = {"相册", "拍照"};
        AlertDialog dlg = new AlertDialog.Builder(activity).setTitle("选择图片").setItems(items,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // 这里item是根据选择的方式，
                        // 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
                        if (item == 1) {
                            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                            activity.startActivityForResult(getImageByCamera, request_camera);
                        } else {
                            Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                            getImage.addCategory(Intent.CATEGORY_OPENABLE);
                            getImage.setType("image/jpeg");
                            activity.startActivityForResult(getImage, request_adlum);
                        }
                    }
                }).create();
        return dlg;
    }
}
