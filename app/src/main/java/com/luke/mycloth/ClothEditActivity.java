package com.luke.mycloth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.luke.mycloth.bean.Cloth;
import com.luke.mycloth.dao.ClothDao;
import com.luke.mycloth.util.PhotoUtil;

public class ClothEditActivity extends BaseActivity implements OnClickListener {
    private ImageView preview;
    private Cloth photo = new Cloth();
    private Spinner s_category;
    private CheckBox spring, summer, autumn, winter;
    private EditText et_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initView();
        initData();
    }

    private void initData() {
        Intent i = getIntent();
        if (i.hasExtra("photo")) {
            photo = (Cloth) i.getSerializableExtra("photo");
        } else {
            if (null != PhotoUtil.result) {
                photo.filepath = PhotoUtil.result.getPath();
            }
        }
        preview.setImageURI(Uri.parse(photo.filepath));
        s_category.setSelection(photo.category);
        spring.setChecked(photo.spring == 1);
        summer.setChecked(photo.summer == 1);
        autumn.setChecked(photo.autumn == 1);
        winter.setChecked(photo.winter == 1);
    }

    private void initView() {
        preview = (ImageView) findViewById(R.id.iv_photo);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoUtil.pickPhoto(ClothEditActivity.this).show();
            }
        });
        s_category = (Spinner) findViewById(R.id.s_category);
        spring = (CheckBox) findViewById(R.id.cb_spring);
        summer = (CheckBox) findViewById(R.id.cb_summer);
        autumn = (CheckBox) findViewById(R.id.cb_autumn);
        winter = (CheckBox) findViewById(R.id.cb_winter);
        et_description = (EditText) findViewById(R.id.et_description);
        findViewById(R.id.b_save).setOnClickListener(this);
    }

    private void save() {
        photo.category = s_category.getSelectedItemPosition();
        photo.spring = spring.isChecked() ? 1 : 0;
        photo.summer = summer.isChecked() ? 1 : 0;
        photo.autumn = autumn.isChecked() ? 1 : 0;
        photo.winter = winter.isChecked() ? 1 : 0;
        if (null != et_description.getText())
            photo.description = et_description.getText().toString();
        ClothDao clothDao = new ClothDao(this);
        if (null != photo.id && clothDao.isExist(photo.id))
            clothDao.replace(photo);
        else
            clothDao.insert(photo);
        finish();
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
                    if (null != PhotoUtil.result) {
                        photo.filepath = PhotoUtil.result.getPath();
                        preview.setImageURI(PhotoUtil.result);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_save:
                save();
                break;
        }
    }
}
