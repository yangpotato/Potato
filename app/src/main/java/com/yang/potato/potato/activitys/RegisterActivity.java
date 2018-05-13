package com.yang.potato.potato.activitys;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.ui.register.InputPhoneFragment;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.fragment)
    FrameLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fm = new InputPhoneFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fm).commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @Override
    public boolean setStatusBarTransparent() {
        return true;
    }


}
