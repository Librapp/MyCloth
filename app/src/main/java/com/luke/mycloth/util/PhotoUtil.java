package com.luke.mycloth.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Luke on 2016/3/16 0016.
 */
public class PhotoUtil {
    public static final int REQUEST_ABLUM = 0;
    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_CROP = 2;
    public static Uri temp, result;

    public static AlertDialog pickPhoto(final Activity activity) {
        final CharSequence[] items = {"相册", "拍照"};
        AlertDialog dlg = new AlertDialog.Builder(activity).setTitle("选择图片").setItems(items,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // 这里item是根据选择的方式，
                        // 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
                        if (item == REQUEST_CAMERA) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // Ensure that there's a camera activity to handle the intent
                            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                                // Create the File where the photo should go
                                File photoFile = null;
                                try {
                                    photoFile = FileUtil.createImageFile();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                // Continue only if the File was successfully created
                                if (photoFile != null) {
                                    temp = Uri.fromFile(photoFile);
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, temp);
                                    activity.startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                                }
                            }
                        } else {
                            Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                            getImage.addCategory(Intent.CATEGORY_OPENABLE);
                            getImage.setType("image/jpeg");
                            activity.startActivityForResult(getImage, REQUEST_ABLUM);
                        }
                    }
                }).create();
        return dlg;
    }

    public static AlertDialog pickPhoto(final Fragment fragment) {
        final CharSequence[] items = {"相册", "拍照"};
        AlertDialog dlg = new AlertDialog.Builder(fragment.getContext()).setTitle("选择图片").setItems(items,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // 这里item是根据选择的方式，
                        // 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
                        if (item == REQUEST_CAMERA) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // Ensure that there's a camera activity to handle the intent
                            if (takePictureIntent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
                                // Create the File where the photo should go
                                File photoFile = null;
                                try {
                                    photoFile = FileUtil.createImageFile();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                // Continue only if the File was successfully created
                                if (photoFile != null) {
                                    temp = Uri.fromFile(photoFile);
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, temp);
                                    fragment.startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                                }
                            }
                        } else {
                            Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                            getImage.addCategory(Intent.CATEGORY_OPENABLE);
                            getImage.setType("image/jpeg");
                            fragment.startActivityForResult(getImage, REQUEST_ABLUM);
                        }
                    }
                }).create();
        return dlg;
    }

    public static void startPhotoZoom(final Activity activity, Uri uri) {
        File f = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCloth");
        if (!f.exists())
            f.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
                .format(new Date());
        File mediaFile = new File(f.getPath(), "MyCloth_"
                + timeStamp + ".jpg");
        result = Uri.fromFile(mediaFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 9);
        intent.putExtra("aspectY", 16);
        intent.putExtra("outputX", 1080);
        intent.putExtra("outputY", 1920);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, result);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, REQUEST_CROP);
    }

    public static void startPhotoZoom(final Fragment fragment, Uri uri) {
        File f = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCloth");
        if (!f.exists())
            f.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
                .format(new Date());
        File mediaFile = new File(f.getPath(), "MyCloth_"
                + timeStamp + ".jpg");
        result = Uri.fromFile(mediaFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 9);
        intent.putExtra("aspectY", 16);
        intent.putExtra("outputX", 1080);
        intent.putExtra("outputY", 1920);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, result);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        fragment.startActivityForResult(intent, REQUEST_CROP);
    }
}
