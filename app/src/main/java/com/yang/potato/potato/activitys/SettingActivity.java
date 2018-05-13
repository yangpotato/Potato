package com.yang.potato.potato.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yang.potato.potato.MyApplication;
import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lin_setting_info)
    RelativeLayout linSettingInfo;
    @BindView(R.id.lin_setting_safe)
    RelativeLayout linSettingSafe;
    @BindView(R.id.lin_setting_zan)
    RelativeLayout linSettingZan;

    @BindView(R.id.lin_setting_aboutme)
    RelativeLayout linSettingAboutme;
    @BindView(R.id.lin_setting_exit)
    RelativeLayout linSettingExit;
    @BindView(R.id.lin_setting_change)
    RelativeLayout linSettingChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setToolTitle("设置");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.lin_setting_info, R.id.lin_setting_safe, R.id.lin_setting_zan, R.id.lin_setting_aboutme, R.id.lin_setting_exit, R.id.lin_setting_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_setting_info:
                break;
            case R.id.lin_setting_safe:
                break;
            case R.id.lin_setting_zan:
                startToActivity(getZanActivity.class);
                break;
            case R.id.lin_setting_aboutme:
                break;
            case R.id.lin_setting_change:
                startActivity(new Intent(this, AddTagActivity.class));
                break;
            case R.id.lin_setting_exit:
                SPUtils.clear(this);

                SharedPreferences.Editor config = MyApplication.getContext()
                        .getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE)
                        .edit();
                config.putStringSet("cookie", null);
                config.commit();

                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
