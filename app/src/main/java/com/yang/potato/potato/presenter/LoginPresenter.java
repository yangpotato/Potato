package com.yang.potato.potato.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.umeng.socialize.UMShareAPI;
import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.BasePresenter;
import com.yang.potato.potato.contract.LoginContract;
import com.yang.potato.potato.entity.User;
import com.yang.potato.potato.model.LoginModel;
import com.yang.potato.potato.utils.LogUtils;

import java.util.Map;

/**
 * Created by potato on 2018/1/16.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private LoginContract.Model model;



    private boolean isPw = false;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        model = new LoginModel();
        Log.d("popo","1");
    }

    @Override
    public void login(Map<String, String> map) {
        addSubscription(model.login(map,new BaseCallBack<BaseRequest<User>>(){

            @Override
            public void onSuccess(BaseRequest<User> userBaseRequest) {
                if(userBaseRequest.isOk()) {
                    LogUtils.i("成功");

                    //view.backLoginButtonAnima();
                    view.saveUser(userBaseRequest.getData());
                    view.toMainActivity();

                }
                view.backLoginButtonAnima();

            }

            @Override
            public void OnFailed(String error) {
                view.backLoginButtonAnima();
                view.showInfo("登录失败");
                LogUtils.i(error);
            }
        }));
    }

    @Override
    public void setImageViewVisibility(final EditText editText, final ImageView imageView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("popo","2");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0){
                    view.setVisibility(imageView, View.VISIBLE);
                } else {
                    view.setVisibility(imageView, View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void setPwVisibility() {
        if(isPw){
            view.setPwVisibility(true);
        } else{
            view.setPwVisibility(false);
        }
        isPw = !isPw;
    }

    @Override
    public void clearText(int type) {
        view.clearText(type);
    }

}

