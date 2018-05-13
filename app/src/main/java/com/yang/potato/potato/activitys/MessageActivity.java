package com.yang.potato.potato.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;

public class MessageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        setToolTitle("消息");
    }

    @Override
    protected void initData() {

    }
}
