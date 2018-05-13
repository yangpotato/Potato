package com.yang.potato.potato.contract;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;

import java.util.Map;

import rx.Subscription;

/**
 * Created by potato on 2018/1/25.
 */

public interface RegisterContract {
    interface Model {
        Subscription register(Map<String, String> map, BaseCallBack<BaseRequest> callBack);
    }

    interface View {
        void showInfo(String str);
        void startToLogin();
        void finish();
    }

    interface Presenter {
        void register(Map<String, String> map);
    }
}
