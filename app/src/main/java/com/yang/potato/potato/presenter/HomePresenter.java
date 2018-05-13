package com.yang.potato.potato.presenter;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.BasePresenter;
import com.yang.potato.potato.contract.HomeContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.Tag;
import com.yang.potato.potato.model.HomeModel;
import com.yang.potato.potato.utils.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by potato on 2018/4/30.
 */

public class HomePresenter extends BasePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private HomeModel model;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        model = new HomeModel();
    }

    @Override
    public void getTag() {
        addSubscription(model.getTag(new BaseCallBack<BaseRequest<List<Tag>>>() {
            @Override
            public void onSuccess(BaseRequest<List<Tag>> listBaseRequest) {
                if(listBaseRequest.isOk()){
                    view.setTabLayout(listBaseRequest.getData());
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
    public void getAlbum(Map<String, String> map) {
            addSubscription(model.getAlbum(map, new BaseCallBack<BaseRequest<List<Album>>>() {
                @Override
                public void onSuccess(BaseRequest<List<Album>> listBaseRequest) {
                    if(listBaseRequest.isOk()){
                        view.setAdapter(listBaseRequest.getData());
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
    public void loadAlbum(Map<String, String> map) {
        addSubscription(model.getAlbum(map, new BaseCallBack<BaseRequest<List<Album>>>() {
            @Override
            public void onSuccess(BaseRequest<List<Album>> listBaseRequest) {
                if(listBaseRequest.isOk()){
                    view.loadAlbum(listBaseRequest.getData());
                }
            }

            @Override
            public void OnFailed(String error) {
                view.showInfo("请求失败");
                LogUtils.i(error);
            }
        }));
    }
}
