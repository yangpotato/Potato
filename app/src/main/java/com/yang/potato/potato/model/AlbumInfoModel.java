package com.yang.potato.potato.model;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.AlbumInfoContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.AlbumInfo;
import com.yang.potato.potato.entity.Status;
import com.yang.potato.potato.retrofit.RetrofitManage;
import com.yang.potato.potato.utils.LogUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by potato on 2018/5/1.
 */

public class AlbumInfoModel implements AlbumInfoContract.Model {
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
    public Subscription getAlbumInfo(final String albumId, final BaseCallBack<BaseRequest<AlbumInfo>> callBack) {
        return RetrofitManage.getAlbumInfo(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<AlbumInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.OnFailed(e.toString());
                    }

                    @Override
                    public void onNext(BaseRequest<AlbumInfo> listBaseRequest) {
                        callBack.onSuccess(listBaseRequest);
                    }
                });
    }

    @Override
    public Subscription getStatus(Map<String, String> map, final BaseCallBack<BaseRequest<Status>> callBack) {
        return  RetrofitManage.getZanStatus(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<Status>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.OnFailed(e.toString());
                    }

                    @Override
                    public void onNext(BaseRequest<Status> statusBaseRequest) {
                            callBack.onSuccess(statusBaseRequest);
                    }
                });
    }

    @Override
    public Subscription collection(Map<String, String> map, final BaseCallBack<BaseRequest> callBack) {
        return  RetrofitManage.collection(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.OnFailed(e.toString());
                        LogUtils.i(e.toString());
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        callBack.onSuccess(baseRequest);
                    }
                });
    }
}
