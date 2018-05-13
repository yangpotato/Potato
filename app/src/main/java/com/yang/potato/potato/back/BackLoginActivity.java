package com.yang.potato.potato.back;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.Admin;
import com.yang.potato.potato.retrofit.RetrofitManage;
import com.yang.potato.potato.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BackLoginActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    public static Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_back_login;
    }

    @Override
    protected void initView() {
        setToolTitle("管理员登录");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                break;
        }
    }

    private void login(){
        Map<String, String> map = new HashMap<>();
        map.put("user_name", edtPhone.getText().toString());
        map.put("pwd", edtPwd.getText().toString());
        RetrofitManage.backLogin(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<Admin>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e.toString());
                    }

                    @Override
                    public void onNext(BaseRequest<Admin> adminBaseRequest) {
                        if(adminBaseRequest.isOk()){
                            Toast.makeText(BackLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            admin = adminBaseRequest.getData();
                            startActivity(new Intent(BackLoginActivity.this, InfoActivity.class).putExtra("admin", admin));
                            finish();
                        }
                    }
                });
    }
}
