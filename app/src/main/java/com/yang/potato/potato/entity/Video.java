package com.yang.potato.potato.entity;

/**
 * Created by potato on 2018/5/3.
 */

public class Video {

    /**
     * id : 1
     * userId : 1
     * title : 动画
     * nickName : YangGuiyu
     * headImg : http://owf1tbdp7.bkt.clouddn.com/icon.jpg
     * info : 这是一部动画
     * videoUrl : https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo-transcode/1569110_98d3368fd3b984acd19a286e71e80847_0.mp4
     * createTime : 1524064704614
     * zan : null
     * collecti : null
     * comment : null
     * status : null
     */

    private String id;
    private String userId;
    private String title;
    private String nickName;
    private String headImg;
    private String info;
    private String videoUrl;
    private String createTime;
    private Object zan;
    private Object collecti;
    private Object comment;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getZan() {
        return zan;
    }

    public void setZan(Object zan) {
        this.zan = zan;
    }

    public Object getCollecti() {
        return collecti;
    }

    public void setCollecti(Object collecti) {
        this.collecti = collecti;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
