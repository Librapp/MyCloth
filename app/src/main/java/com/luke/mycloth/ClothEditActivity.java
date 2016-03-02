package com.luke.mycloth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.luke.mycloth.bean.PhotoBean;
import com.luke.mycloth.dao.PhotoDao;
import com.luke.mycloth.util.Constants;
import com.luke.mycloth.util.DialogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClothEditActivity extends BaseActivity {
    private final int REQUEST_CAMERA = 1;
    private final int REQUEST_ABLUM = 0;
    private ImageView preview;
    private Bitmap myBitmap;
    private byte[] mContent;
    private PhotoBean photo = new PhotoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_edit);
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
                AlertDialog dlg = DialogUtil.pickPhoto(ClothEditActivity.this, REQUEST_CAMERA, REQUEST_ABLUM);
                dlg.show();
            }
        });
        findViewById(R.id.b_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    savePhoto();
                } catch (IOException e) {
                    Toast.makeText(ClothEditActivity.this, "保存照片失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_ABLUM) {
                try {
                    // 获得图片的uri
                    Uri originalUri = data.getData();
                    // 将图片内容解析成字节数组
                    mContent = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));
                    // 将字节数组转换为ImageView可调用的Bitmap对象
                    myBitmap = getPicFromBytes(mContent, null);
                    // //把得到的图片绑定在控件上显示
                    preview.setImageBitmap(myBitmap);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (requestCode == REQUEST_CAMERA) {
                try {
                    super.onActivityResult(requestCode, resultCode, data);
                    Bundle extras = data.getExtras();
                    myBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    mContent = baos.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 把得到的图片绑定在控件上显示
                preview.setImageBitmap(myBitmap);//把拍摄的照片转成圆角显示在预览控件上
            }
        }
    }

    private void savePhoto() throws IOException {
        //保存图片文件
        String fileName;
        if (null != photo.filepath && new File(photo.filepath).exists()) {
            fileName = photo.filepath;
        } else {
            fileName = Constants.DCIM + new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date()) + ".jpg";
            File f = new File(Constants.DCIM);
            if (!f.exists()) {
                f.mkdirs();
            }
            File file = new File(fileName);
            file.createNewFile();
            photo.filepath=fileName;
        }
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(mContent);
        fos.flush();
        fos.close();

        PhotoDao photoDao=new PhotoDao(ClothEditActivity.this);
        photoDao.replace(photo);
    }
}
