package com.luke.mycloth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.luke.mycloth.bean.PhotoBean;
import com.luke.mycloth.util.PhotoUtil;

public class ClothEditActivity extends BaseActivity {
    private ImageView preview;
    private PhotoBean photo = new PhotoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initData();
    }

    private void initData() {
        Intent i = getIntent();
        if (i.hasExtra("photo")) {
            photo = (PhotoBean) i.getSerializableExtra("photo");
        } else {
            if (null != PhotoUtil.result) {
                photo.filepath = PhotoUtil.result.getPath();
                preview.setImageURI(PhotoUtil.result);
            }
        }
    }

    private void initView() {
        preview = (ImageView) findViewById(R.id.iv_photo);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoUtil.pickPhoto(ClothEditActivity.this).show();
            }
        });
    }

    private void save() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 因为两种方式都用到了startActivityForResult方法，
         * 这个方法执行完后都会执行onActivityResult方法， 所以为了区别到底选择了那个方式获取图片要进行判断，
         * 这里的requestCode跟startActivityForResult里面第二个参数对应
         */
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PhotoUtil.REQUEST_ABLUM:
                    Uri originalUri = data.getData();
                    PhotoUtil.startPhotoZoom(this, originalUri);
                    break;
                case PhotoUtil.REQUEST_CAMERA:
                    PhotoUtil.startPhotoZoom(this, PhotoUtil.temp);
                    break;
                case PhotoUtil.REQUEST_CROP:
                    if (null != PhotoUtil.result)
                        preview.setImageURI(PhotoUtil.result);
                    break;
                default:
                    break;
            }
        }
    }

}
