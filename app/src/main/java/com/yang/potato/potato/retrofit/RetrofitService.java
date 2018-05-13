package com.yang.potato.potato.retrofit;

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

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by potato on 2018/4/28.
 */

public interface RetrofitService {

    @POST("user/login")
    Observable<BaseRequest<User>> login(@QueryMap Map<String, String> map);

    @GET("album/getTag")
    Observable<BaseRequest<List<Tag>>> getTag();

    @GET("album/recommend")
    Observable<BaseRequest<List<Album>>> getAlbum(@QueryMap Map<String, String> map);

    @GET("album/recommend")
    Observable<AlbumAll> getAlbumA(@QueryMap Map<String, String> map);

    @GET("album/getAlbum")
    Observable<BaseRequest<List<Album>>> getAlbumById();

    @POST("user/register")
    Observable<BaseRequest> register(@QueryMap Map<String, String> map);

    @GET("album/getAlbumInfo")
    Observable<BaseRequest<AlbumInfo>> getAlbumInfo(@Query("albumId") String albumId);

    @GET("album/getZanStatus")
    Observable<BaseRequest<Status>> getZanStatus(@QueryMap Map<String, String> map);

    @POST("interaction/collection")
    Observable<BaseRequest> collection(@QueryMap Map<String, String> map);

    //获取关注内容
    @GET("album/getFollowAlbum")
    Observable<BaseRequest<List<Album>>> getFollowAlbum();

    //获取视频
    @GET("album/getVideoAll")
    Observable<BaseRequest<List<Video>>> getVideoAll();

    @GET("interaction/getCollection")
    Observable<BaseRequest<List<CollectionZan>>> getCollection();

    @GET("back/getUser")
    Observable<BaseRequest<List<User>>> getUser();

    @POST("back/updateUser")
    Observable<BaseRequest> updateUser(@QueryMap Map<String, String> map);

    @POST("back/delectUser")
    Observable<BaseRequest> delectUser(@QueryMap Map<String, String> map);

    @GET("back/getAlbum")
    Observable<BaseRequest<List<Album>>> getAlbum();

    @POST("back/delectAlbum")
    Observable<BaseRequest> delectAlbum(@QueryMap Map<String, String> map);

    @GET("back/getVideo")
    Observable<BaseRequest<List<Video>>> getVideo();

    @POST("back/delectVideo")
    Observable<BaseRequest> delectVideo(@QueryMap Map<String, String> map);

    @POST("back/login")
    Observable<BaseRequest<Admin>> backLogin(@QueryMap Map<String, String> map);

    @POST("interaction/comment")
    Observable<BaseRequest> addComment(@QueryMap Map<String, String> map);

    @POST("album/uploadAlbum")
    Observable<BaseRequest<String>> uploadAlbum(@QueryMap Map<String, String> map);

    @POST("album/uploadPhoto")
    Observable<BaseRequest> uploadPhoto(@QueryMap Map<String, String> map);

    @POST("album/uploadTag")
    Observable<BaseRequest> uploadTag(@QueryMap Map<String, String> map);

    @POST("user/modify")
    Observable<BaseRequest> modify(@QueryMap Map<String, String> map);

    @GET("user/changePwd")
    Observable<BaseRequest> changePwd(@QueryMap Map<String, String> map);

    @POST("album/uploadVideo")
    Observable<BaseRequest> uploadVideo(@QueryMap Map<String, String> map);

    @GET("interaction/getFollow")
    Observable<BaseRequest<List<User>>> getFollow();

    @GET("album/searchAlbum")
    Observable<BaseRequest<List<Album>>> searchAlbum(@QueryMap Map<String, String> map);

    @GET("album/searchAlbum")
    Observable<BaseRequest<List<Video>>> searchVideo(@QueryMap Map<String, String> map);

    @GET("album/searchAlbum")
    Observable<BaseRequest<List<User>>> searchUser(@QueryMap Map<String, String> map);

    @GET("album/selectZanByUserIds")
    Observable<BaseRequest<List<SelectZanByUserId>>> selectZanByUserIds();

    @GET("album/getComment")
    Observable<BaseRequest<List<GetComment>>> getComment();

    @GET("album/findFollowStatus")
    Observable<BaseRequest> findFollowStatus(@QueryMap Map<String, String> map);

    @POST("user/attention")
    Observable<BaseRequest> attention(@QueryMap Map<String, String> map);

    @POST("user/cancelAttention")
    Observable<BaseRequest> cancelAttention(@QueryMap Map<String, String> map);

    @GET("user/addUserTag")
    Observable<BaseRequest> addUserTag(@QueryMap Map<String, String> map);
}
