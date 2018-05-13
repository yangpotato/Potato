package com.yang.potato.potato.presenter;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.BasePresenter;
import com.yang.potato.potato.contract.FollowContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.model.FollowModel;

import java.util.List;

/**
 * Created by potato on 2018/5/3.
 */

public class FollowPresenter extends BasePresenter implements FollowContract.Presenter {
    private FollowContract.View view;
    private FollowContract.Model model;

    public FollowPresenter(FollowContract.View view) {
        this.view = view;
        model = new FollowModel();
    }

    @Override
    public void getFolloAlbum() {
        addSubscription(model.getFolloAlbum(new BaseCallBack<BaseRequest<List<Album>>>() {
            @Override
            public void onSuccess(BaseRequest<List<Album>> listBaseRequest) {
                if(listBaseRequest.isOk()){
                    view.initRecycle(listBaseRequest.getData());
                }
            }

            @Override
            public void OnFailed(String error) {
                    view.showInfo(error);
            }
        }));
    }
}
