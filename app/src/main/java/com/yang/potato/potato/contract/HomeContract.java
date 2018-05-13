package com.yang.potato.potato.contract;

import android.content.Intent;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.Tag;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;

/**
 * Created by potato on 2018/4/30.
 */

public interface HomeContract {
    interface Model {
        Subscription getTag(BaseCallBack<BaseRequest<List<Tag>>> callBack);
        Subscription getAlbum(Map<String, String> map, BaseCallBack<BaseRequest<List<Album>>> callBack);
        Subscription loadAlbum(Map<String, String> map, BaseCallBack<BaseRequest<List<Album>>> callBack);
    }

    interface View {
        void setTabLayout(List<Tag> tag);
        void setAdapter(List<Album> albums);
        void showInfo(String str);
        void loadAlbum(List<Album> loadAlbums);
    }

    interface Presenter {
        void getTag();
        void getAlbum(Map<String, String> map);
        void loadAlbum(Map<String, String> map);
    }
}
