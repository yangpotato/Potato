package com.yang.potato.potato.model;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.HomeContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.Tag;
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
 * Created by potato on 2018/4/30.
 */

public class HomeModel implements HomeContract.Model {
    @Override
    public Subscription getTag(final BaseCallBack<BaseRequest<List<Tag>>> callBack) {
        return RetrofitManage.getTag()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<Tag>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.OnFailed(e.toString());
                    }

                    @Override
                    public void onNext(BaseRequest<List<Tag>> listBaseRequest) {
                        callBack.onSuccess(listBaseRequest);
                    }
                });
    }


    @Override
    public Subscription getAlbum(Map<String, String> map, final BaseCallBack<BaseRequest<List<Album>>> callBack) {
        return RetrofitManage.getAlbum(map)
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

    @Override
    public Subscription loadAlbum(Map<String, String> map, final BaseCallBack<BaseRequest<List<Album>>> callBack) {
        return RetrofitManage.getAlbum(map)
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
