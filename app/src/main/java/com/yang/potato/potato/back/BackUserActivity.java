package com.yang.potato.potato.back;

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
import com.yang.potato.potato.entity.User;
import com.yang.potato.potato.retrofit.RetrofitManage;
import com.yang.potato.potato.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BackUserActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt)
    TextView edt;
    @BindView(R.id.edt_nickName)
    EditText edtNickName;
    @BindView(R.id.edt_headImg)
    EditText edtHeadImg;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_info)
    EditText edtInfo;
    @BindView(R.id.btn)
    Button btn;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_back_user;
    }

    @Override
    protected void initView() {
        setToolTitle("用户详情");
        getRightTextView().setVisibility(View.VISIBLE);
        setToolRight("确认");

        if (user != null) {
            edt.setText(user.getUserId());
            edtNickName.setText(user.getNickName());
            edtHeadImg.setText(user.getHeadImg());
            edtInfo.setText(user.getBackground());
            edtPhone.setText(user.getCode());
        }

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
    }

    private void request() {
        Map<String, String> map = new HashMap<>();
        map.put("id", user.getUserId());
        map.put("code", edtPhone.getText().toString());
        map.put("nickName", edtNickName.getText().toString());
        map.put("headImg", edtHeadImg.getText().toString());
        map.put("background", edtInfo.getText().toString());
        RetrofitManage.updateUser(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e.toString());
                        Toast.makeText(BackUserActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if (baseRequest.isOk()) {
                            Toast.makeText(BackUserActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            user = (User) getIntent().getSerializableExtra("user");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //request();
    }

    @OnClick(R.id.btn)
    public void onClick() {
        Map<String, String> map = new HashMap<>();
        map.put("id", user.getUserId());

        RetrofitManage.delectUser(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e.toString());
                        Toast.makeText(BackUserActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if (baseRequest.isOk()) {
                            Toast.makeText(BackUserActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

    }
}
