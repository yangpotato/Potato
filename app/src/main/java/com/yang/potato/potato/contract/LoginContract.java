package com.yang.potato.potato.contract;

import android.widget.EditText;
import android.widget.ImageView;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.User;

import java.util.Map;

import rx.Subscription;

/**
 * Created by potato on 2018/1/16.
 */

public interface LoginContract {
    public int PHONE = 1;
    public int PW = 2;

    interface Model {
        Subscription login(Map<String, String> map, BaseCallBack<BaseRequest<User>> callBack);
    }

    interface View {
        void startLoginButtonAnima();
        void backLoginButtonAnima();
        void setVisibility(ImageView imageView, int visibility);
        void setPwVisibility(boolean visibility);
        void clearText(int type);
        void showInfo(String str);
        void toMainActivity();
        void saveUser(User user);
    }

    interface Presenter {
        void login(Map<String, String> map);
        void setImageViewVisibility(EditText editText, ImageView imageView);
        void setPwVisibility();
        void clearText(int type);

    }
}
