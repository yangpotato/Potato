package com.yang.potato.potato.model;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.MineContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by potato on 2018/4/30.
 */

public class MineModel implements MineContract.Model {
    @Override
    public Subscription getMyAlbum(final BaseCallBack<BaseRequest<List<Album>>> callBack) {
        return RetrofitManage.getAlbumById()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<Album>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.OnFailed(e.toString());
                    }

                    @Override
                    public void onNext(BaseRequest<List<Album>> listBaseRequest) {
                        callBack.onSuccess(listBaseRequest);
                    }
                });
    }
}
