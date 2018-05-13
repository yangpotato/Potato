package com.yang.potato.potato.contract;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.Album;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by potato on 2018/4/30.
 */

public interface MineContract {
    interface Model {
        Subscription getMyAlbum(BaseCallBack<BaseRequest<List<Album>>> callBack);
    }

    interface View {
        void setAdapter(List<Album> albums);
        void showInfo(String str);
    }

    interface Presenter {
        void getMyAlbum();
    }
}
