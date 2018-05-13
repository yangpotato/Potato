package com.yang.potato.potato.model;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.LoginContract;
import com.yang.potato.potato.entity.User;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.Map;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by potato on 2018/1/16.
 */

public class LoginModel implements LoginContract.Model {

    @Override
    public Subscription login(Map<String, String> map, final BaseCallBack<BaseRequest<User>> callBack) {
        return RetrofitManage.login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.OnFailed(e.toString());
                    }

                    @Override
                    public void onNext(BaseRequest<User> userBaseRequest) {
                        callBack.onSuccess(userBaseRequest);
                    }
                });
    }
}
