package com.yang.potato.potato.activitys;

import android.os.Bundle;
import android.widget.EditText;

import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.custView.LoginAnimationView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.edt_register_nick)
    EditText edtRegisterNick;
    @BindView(R.id.edt_register_phone)
    EditText edtRegisterPhone;
    @BindView(R.id.edt_register_pw)
    EditText edtRegisterPw;
    @BindView(R.id.edt_register_2pw)
    EditText edtRegister2pw;
    @BindView(R.id.btn_register)
    LoginAnimationView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnRegister.setText("注  册");
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public boolean setStatusBarTransparent() {
        return true;
    }

    @OnClick(R.id.btn_register)
    public void onClick() {
        btnRegister.start();
    }
}
