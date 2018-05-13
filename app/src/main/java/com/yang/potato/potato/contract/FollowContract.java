package com.yang.potato.potato.contract;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.Album;

import java.util.List;

import rx.Subscription;

/**
 * Created by potato on 2018/5/3.
 */

public interface FollowContract {
    interface Model {
        Subscription getFolloAlbum(BaseCallBack<BaseRequest<List<Album>>> callBack);
    }

    interface View {
        void initRecycle(List<Album> albums);
        void showInfo(String str);
    }

    interface Presenter {
        void getFolloAlbum();
    }
}
