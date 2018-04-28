package com.yang.potato.potato.activitys;

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

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.contract.LoginContract;
import com.yang.potato.potato.custView.LoginAnimationView;
import com.yang.potato.potato.custView.LoginVideoView;
import com.yang.potato.potato.presenter.LoginPresenter;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

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

    private LoginContract.Presenter presenter;

    UMShareAPI mShareAPI;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareAPI = UMShareAPI.get(this);
        presenter = new LoginPresenter(this);

        presenter.setImageViewVisibility(edtLoginPhone,imgLoginCancel1);
        presenter.setImageViewVisibility(edtLoginPw,imgLoginCancel2);

        diplayBackground();
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


    @OnClick({R.id.img_login_cancel_1, R.id.img_login_visible, R.id.img_login_cancel_2, R.id.loginBtn_login})
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
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.i("ygy", "开始");
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();
                        Log.i("ygy", "成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Log.i("ygy", "i="+i+"->"+throwable.toString());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Log.i("ygy", "取消");
                    }
                });
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
}
