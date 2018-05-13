package com.yang.potato.potato.back;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.entity.Admin;
import com.yang.potato.potato.entity.User;

import butterknife.BindView;
import butterknife.OnClick;

public class InfoActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_user)
    Button btnUser;
    @BindView(R.id.btn_album)
    Button btnAlbum;
    @BindView(R.id.btn_video)
    Button btnVideo;
    @BindView(R.id.tv_code)
    TextView tvCode;

    private Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    protected void initView() {
        setToolTitle("菜单管理");
        tvCode.setText(admin.getInvite_code());
    }

    @Override
    protected void initData() {
        if(getIntent() != null){
            admin = (Admin) getIntent().getSerializableExtra("admin");
        }
    }

    @OnClick({R.id.btn_user, R.id.btn_album, R.id.btn_video})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_user:
                startToActivity(UserListActivity.class);
                break;
            case R.id.btn_album:
                startToActivity(AlbumListActivity.class);
                break;
            case R.id.btn_video:
                startToActivity(VideoListActivity.class);
                break;
        }
    }
}
