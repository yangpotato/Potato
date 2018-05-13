package com.yang.potato.potato.presenter;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.AlbumInfoContract;
import com.yang.potato.potato.contract.BasePresenter;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.AlbumInfo;
import com.yang.potato.potato.entity.Status;
import com.yang.potato.potato.model.AlbumInfoModel;
import com.yang.potato.potato.utils.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by potato on 2018/5/1.
 */

public class AlbumInfoPresenter extends BasePresenter implements AlbumInfoContract.Presenter {
    private AlbumInfoContract.View view;
    private AlbumInfoContract.Model model;

    public AlbumInfoPresenter(AlbumInfoContract.View view) {
        this.view = view;
        model = new AlbumInfoModel();
    }

    @Override
    public void getAlbum(Map<String, String> map) {
        addSubscription(model.getAlbum(map, new BaseCallBack<BaseRequest<List<Album>>>() {
            @Override
            public void onSuccess(BaseRequest<List<Album>> listBaseRequest) {
                if(listBaseRequest.isOk()){
                    view.initRecycle(listBaseRequest.getData());
                }
            }

            @Override
            public void OnFailed(String error) {
                view.showInfo("请求失败");
                LogUtils.i(error);
            }
        }));
    }

    @Override
    public void getALbumInfo(String albumId) {
        addSubscription(model.getAlbumInfo(albumId, new BaseCallBack<BaseRequest<AlbumInfo>>() {
            @Override
            public void onSuccess(BaseRequest<AlbumInfo> albumInfoBaseRequest) {
                if(albumInfoBaseRequest.isOk()){
                    view.initView(albumInfoBaseRequest.getData());
                }
            }

            @Override
            public void OnFailed(String error) {
                view.showInfo("请求失败");
                LogUtils.i(error);
            }
        }));
    }

    @Override
    public void getStatus(Map<String, String> map) {
        addSubscription(model.getStatus(map, new BaseCallBack<BaseRequest<Status>>() {
            @Override
            public void onSuccess(BaseRequest<Status> statusBaseRequest) {
                if(statusBaseRequest.isOk()){
                    view.initZanStatus(statusBaseRequest.getData());
                }
            }

            @Override
            public void OnFailed(String error) {
                //view.showInfo("请求失败");
                view.showInfo(error);
                LogUtils.i(error);
            }
        }));
    }

    @Override
    public void collection(Map<String, String> map) {
        addSubscription(model.collection(map, new BaseCallBack<BaseRequest>() {
            @Override
            public void onSuccess(BaseRequest baseRequest) {
                if(baseRequest.isOk()){
                    view.setViewSelect();
                }
            }

            @Override
            public void OnFailed(String error) {
//                view.showInfo("请求失败");
                view.showInfo(error);
                LogUtils.i(error);
            }
        }));
    }
}
