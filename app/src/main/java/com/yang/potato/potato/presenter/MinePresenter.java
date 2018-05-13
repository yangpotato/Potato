package com.yang.potato.potato.presenter;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.BasePresenter;
import com.yang.potato.potato.contract.MineContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.model.MineModel;

import java.util.List;

/**
 * Created by potato on 2018/4/30.
 */

public class MinePresenter extends BasePresenter implements MineContract.Presenter {
    private MineContract.View view;
    private MineModel model;

    public MinePresenter(MineContract.View view) {
        this.view = view;
        model = new MineModel();
    }

    @Override
    public void getMyAlbum() {
        addSubscription(model.getMyAlbum(new BaseCallBack<BaseRequest<List<Album>>>() {
            @Override
            public void onSuccess(BaseRequest<List<Album>> listBaseRequest) {
                if(listBaseRequest.isOk()) {
                    view.setAdapter(listBaseRequest.getData());
                }
            }

            @Override
            public void OnFailed(String error) {
                view.showInfo("请求失败");
            }
        }));
    }
}
