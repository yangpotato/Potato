package com.yang.potato.potato.contract;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.AlbumInfo;
import com.yang.potato.potato.entity.Status;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;

/**
 * Created by potato on 2018/5/1.
 */

public interface AlbumInfoContract {
    interface Model {
        Subscription getAlbum(Map<String, String> map, BaseCallBack<BaseRequest<List<Album>>> callBack);
        Subscription getAlbumInfo(String albumId, BaseCallBack<BaseRequest<AlbumInfo>> callBack);
        Subscription getStatus(Map<String, String> map, BaseCallBack<BaseRequest<Status>> callBack);
        Subscription collection(Map<String, String> map, BaseCallBack<BaseRequest> callBack);
    }

    interface View {
        void initRecycle(List<Album> albums);
        void initView(AlbumInfo info);
        void showInfo(String string);
        void initZanStatus(Status status);
        void setViewSelect();
        void setViewUnSelect();
    }

    interface Presenter {
        void getAlbum(Map<String, String> map);
        void getALbumInfo(String albumId);
        void getStatus(Map<String, String> map);
        void collection(Map<String, String> map);
    }
}
