package com.yang.potato.potato.contract;

import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by potato on 2018/1/16.
 */

public interface LoginContract {
    public int PHONE = 1;
    public int PW = 2;

    interface Model {
    }

    interface View {
        void startLoginButtonAnima();
        void backLoginButtonAnima();
        void setVisibility(ImageView imageView, int visibility);
        void setPwVisibility(boolean visibility);
        void clearText(int type);
    }

    interface Presenter {
        void setImageViewVisibility(EditText editText, ImageView imageView);
        void setPwVisibility();
        void clearText(int type);
    }
}
