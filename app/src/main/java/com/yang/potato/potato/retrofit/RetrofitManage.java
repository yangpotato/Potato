package com.yang.potato.potato.retrofit;

import com.google.gson.GsonBuilder;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.Admin;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.AlbumAll;
import com.yang.potato.potato.entity.AlbumInfo;
import com.yang.potato.potato.entity.CollectionZan;
import com.yang.potato.potato.entity.GetComment;
import com.yang.potato.potato.entity.SelectZanByUserId;
import com.yang.potato.potato.entity.Status;
import com.yang.potato.potato.entity.Tag;
import com.yang.potato.potato.entity.User;
import com.yang.potato.potato.entity.Video;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by potato on 2018/4/28.
 */

public class RetrofitManage {

//    public static String BASE_URL = "http://192.168.1.110:8080/";
    public static String BASE_URL = "http://192.168.43.205:8080/";

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AddCookiesInterceptor())
            .addInterceptor(new ReceivedCookiesInterceptor())
            .build();

    private static Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build();

    private static RetrofitService service = mRetrofit.create(RetrofitService.class);

    public static Observable<BaseRequest<User>> login(Map<String, String> map) {
        return service.login(map);
    }

    public static Observable<BaseRequest<List<Tag>>> getTag() {
        return service.getTag();
    }

    public static Observable<BaseRequest<List<Album>>> getAlbum(Map<String, String> map) {
        return service.getAlbum(map);
    }

    public static Observable<AlbumAll> getAlbumA(Map<String, String> map) {
        return service.getAlbumA(map);
    }

    public static Observable<BaseRequest<List<Album>>> getAlbumById() {
        return service.getAlbumById();
    }

    public static Observable<BaseRequest> register(Map<String, String> map) {
        return service.register(map);
    }

    public static Observable<BaseRequest<AlbumInfo>> getAlbumInfo(String albumId) {
        return service.getAlbumInfo(albumId);
    }

    public static Observable<BaseRequest<Status>> getZanStatus(Map<String, String> map) {
        return service.getZanStatus(map);
    }

    public static Observable<BaseRequest> collection(Map<String, String> map) {
        return service.collection(map);
    }

    public static Observable<BaseRequest<List<Album>>> getFollowAlbum() {
        return service.getFollowAlbum();
    }

    public static Observable<BaseRequest<List<Video>>> getVideoALl() {
        return service.getVideoAll();
    }

    public static Observable<BaseRequest<List<CollectionZan>>> getCollection() {
        return service.getCollection();
    }

    public static Observable<BaseRequest<List<User>>> getUser() {
        return service.getUser();
    }

    public static Observable<BaseRequest> updateUser(Map<String, String> map) {
        return service.updateUser(map);
    }

    public static Observable<BaseRequest> delectUser(Map<String, String> map) {
        return service.delectUser(map);
    }

    public static Observable<BaseRequest> delectAlbum(Map<String, String> map) {
        return service.delectAlbum(map);
    }

    public static Observable<BaseRequest<List<Album>>> getAlbum() {
        return service.getAlbum();
    }

    public static Observable<BaseRequest> delectVideo(Map<String, String> map) {
        return service.delectVideo(map);
    }

    public static Observable<BaseRequest<List<Video>>> getVideo() {
        return service.getVideo();
    }

    public static Observable<BaseRequest<Admin>> backLogin(Map<String, String> map) {
        return service.backLogin(map);
    }

    public static Observable<BaseRequest> addComment(Map<String, String> map) {
        return service.addComment(map);
    }

    public static Observable<BaseRequest<String>> uploadAlbum(Map<String, String> map) {
        return service.uploadAlbum(map);
    }

    public static Observable<BaseRequest> uploadPhoto(Map<String, String> map) {
        return service.uploadPhoto(map);
    }

    public static Observable<BaseRequest> uploadTag(Map<String, String> map) {
        return service.uploadTag(map);
    }

    public static Observable<BaseRequest> modify(Map<String, String> map) {
        return service.modify(map);
    }

    public static Observable<BaseRequest> changePwd(Map<String, String> map) {
        return service.changePwd(map);
    }

    public static Observable<BaseRequest> uploadVideo(Map<String, String> map) {
        return service.uploadVideo(map);
    }

    public static Observable<BaseRequest<List<User>>> getFollow() {
        return service.getFollow();
    }

    public static Observable<BaseRequest<List<Album>>> searchAlbum(Map<String, String> map) {
        return service.searchAlbum(map);
    }


    public static Observable<BaseRequest<List<Video>>> searchVideo(Map<String, String> map) {
        return service.searchVideo(map);
    }


    public static Observable<BaseRequest<List<User>>> searchUser(Map<String, String> map) {
        return service.searchUser(map);
    }

    public static Observable<BaseRequest<List<SelectZanByUserId>>> selectZanByUserIds() {
        return service.selectZanByUserIds();
    }

    public static Observable<BaseRequest<List<GetComment>>> getComment() {
        return service.getComment();
    }

    public static Observable<BaseRequest> findFollowStatus(Map<String, String> map) {
        return service.findFollowStatus(map);
    }

    public static Observable<BaseRequest> attention(Map<String, String> map) {
        return service.attention(map);
    }

    public static Observable<BaseRequest> cancelAttention(Map<String, String> map) {
        return service.cancelAttention(map);
    }

    public static Observable<BaseRequest> addUserTag(Map<String, String> map) {
        return service.addUserTag(map);
    }
}
