package com.yang.potato.potato.activitys;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.contract.LoginContract;
import com.yang.potato.potato.custView.LoginAnimationView;
import com.yang.potato.potato.custView.LoginVideoView;
import com.yang.potato.potato.entity.User;
import com.yang.potato.potato.presenter.LoginPresenter;
import com.yang.potato.potato.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.video_login_bg)
    LoginVideoView videoLoginBg;
    @BindView(R.id.loginBtn_login)
    LoginAnimationView loginBtnLogin;
    @BindView(R.id.edt_login_phone)
    EditText edtLoginPhone;
    @BindView(R.id.img_login_cancel_1)
    ImageView imgLoginCancel1;
    @BindView(R.id.edt_login_pw)
    EditText edtLoginPw;
    @BindView(R.id.img_login_visible)
    ImageView imgLoginVisible;
    @BindView(R.id.img_login_cancel_2)
    ImageView imgLoginCancel2;

    private LoginPresenter presenter;

    UMShareAPI mShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareAPI = UMShareAPI.get(this);
        presenter = new LoginPresenter(this);

        presenter.setImageViewVisibility(edtLoginPhone,imgLoginCancel1);
        presenter.setImageViewVisibility(edtLoginPw,imgLoginCancel2);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        diplayBackground();
    }

    @Override
    protected void initData() {
            if(getIntent() != null){
                edtLoginPhone.setText(getIntent().getStringExtra("phone"));
            }
    }

    @Override
    public boolean setStatusBarTransparent() {
        return true;
    }

    @Override
    public boolean setNavigationBarTran() {
        return true;
    }

    private void diplayBackground() {
        String URI = "android.resource://" + getPackageName() + "/" + R.raw.bg_login;
        videoLoginBg.setVideoURI(Uri.parse(URI));
        videoLoginBg.start();
        //循环播放
        videoLoginBg.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoLoginBg.start();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoLoginBg.start();
    }


    @OnClick({R.id.img_login_cancel_1, R.id.img_login_visible, R.id.img_login_cancel_2, R.id.loginBtn_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_login_cancel_1:
                presenter.clearText(LoginContract.PHONE);
                break;
            case R.id.img_login_visible:
                presenter.setPwVisibility();
                break;
            case R.id.img_login_cancel_2:
                presenter.clearText(LoginContract.PW);
                break;
            case R.id.loginBtn_login:
                if(TextUtils.isEmpty(getString(edtLoginPhone))||TextUtils.isEmpty(getString(edtLoginPw))){
                    Toast.makeText(this, "请输入手机号和密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getString(edtLoginPhone).length() != 11){
                    Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginBtnLogin.start();
                Map<String, String> map = new HashMap<>();
                map.put("code", edtLoginPhone.getText().toString());
                map.put("pwd", edtLoginPw.getText().toString());
                map.put("type", "0");
                presenter.login(map);
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private String getString(EditText editText){
        return editText.getText().toString().trim();
    }

    @Override
    public void startLoginButtonAnima() {
        loginBtnLogin.start();
    }

    @Override
    public void backLoginButtonAnima() {
        loginBtnLogin.back();
    }

    @Override
    public void setVisibility(ImageView imageView, int visibility) {
        imageView.setVisibility(visibility);
    }

    @Override
    public void setPwVisibility(boolean visibility) {
        if (visibility){
            edtLoginPw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imgLoginVisible.setSelected(true);
        } else {
            edtLoginPw.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imgLoginVisible.setSelected(false);
        }
        CharSequence charSequence = edtLoginPw.getText();
        //保持输入焦点在文本最后一位
        if (charSequence != null) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @Override
    public void clearText(int type) {
        if (type == LoginContract.PHONE){
            edtLoginPhone.setText("");
        }else{
            edtLoginPw.setText("");
        }
    }

    @Override
    public void showInfo(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void saveUser(User user) {
        Utils.saveUser(this, user);
        JPushInterface.setAlias(LoginActivity.this, user.getUserId(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d("Debug:", "i:"+i+"别名:"+s);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }
}
