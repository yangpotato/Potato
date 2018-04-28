package com.yang.potato.potato.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.umeng.socialize.UMShareAPI;
import com.yang.potato.potato.contract.LoginContract;

/**
 * Created by potato on 2018/1/16.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private LoginContract.Model model;



    private boolean isPw = false;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        Log.d("popo","1");
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

