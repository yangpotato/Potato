package com.yang.potato.potato.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.potato.potato.MyApplication;
import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChangePwdActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_lod_pwd)
    EditText edtLodPwd;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.edt_pwd2)
    EditText edtPwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    private void changePwd() {
        Map<String, String> map = new HashMap<>();
        map.put("pwd", edtLodPwd.getText().toString());
        map.put("newPwd", edtPwd.getText().toString());
        RetrofitManage.changePwd(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ChangePwdActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.isOk()){
                            Toast.makeText(ChangePwdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor config = MyApplication.getContext()
                                    .getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE)
                                    .edit();
                            config.putStringSet("cookie", null);
                            config.commit();

                            Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
    }
}
