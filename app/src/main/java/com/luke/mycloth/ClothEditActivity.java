package com.luke.mycloth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.luke.mycloth.bean.PhotoBean;
import com.luke.mycloth.util.PhotoUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ClothEditActivity extends BaseActivity {
    private ImageView preview;
    private Bitmap myBitmap;
    private byte[] mContent;
    private PhotoBean photo = new PhotoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initData();
    }

    private void initData() {
        Intent i = getIntent();
        if (i.hasExtra("photo")) {
            photo = (PhotoBean) i.getSerializableExtra("photo");
        } else {

        }
    }

    private void initView() {
        preview = (ImageView) findViewById(R.id.iv_photo);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dlg = PhotoUtil.pickPhoto(ClothEditActivity.this);
                dlg.show();
            }
        });
    }

    private void save() {

    }

    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ContentResolver resolver = getContentResolver();
        /**
         * 因为两种方式都用到了startActivityForResult方法，
         * 这个方法执行完后都会执行onActivityResult方法， 所以为了区别到底选择了那个方式获取图片要进行判断，
         * 这里的requestCode跟startActivityForResult里面第二个参数对应
         */
        if (resultCode == Activity.RESULT_OK) {
            try {
                if (requestCode == PhotoUtil.REQUEST_ABLUM) {
                    // 获得图片的uri
                    Uri originalUri = data.getData();
                    // 将图片内容解析成字节数组
                    mContent = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));
                    // 将字节数组转换为ImageView可调用的Bitmap对象
                    myBitmap = getPicFromBytes(mContent, null);
                    // //把得到的图片绑定在控件上显示
                    preview.setImageBitmap(myBitmap);
                } else if (requestCode == PhotoUtil.REQUEST_CAMERA) {
                    super.onActivityResult(requestCode, resultCode, data);
                    Bundle extras = data.getExtras();
                    myBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    mContent = baos.toByteArray();
                    // 把得到的图片绑定在控件上显示
                    preview.setImageBitmap(myBitmap);//把拍摄的照片转成圆角显示在预览控件上
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
