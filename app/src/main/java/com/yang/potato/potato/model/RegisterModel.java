package com.yang.potato.potato.model;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.RegisterContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by potato on 2018/1/25.
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Subscription register(Map<String, String> map, final BaseCallBack<BaseRequest> callBack) {
        return RetrofitManage.register(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.OnFailed(e.toString());
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        callBack.onSuccess(baseRequest);
                    }
                });
    }
}
