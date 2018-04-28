package com.yang.potato.potato.activitys;

import android.os.Bundle;

import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }
}
