package com.yang.potato.potato.entity;

import android.content.Intent;
import android.widget.Toast;

import com.yang.potato.potato.MyApplication;
import com.yang.potato.potato.activitys.LoginActivity;

import java.util.List;

/**
 * Created by potato on 2018/5/4.
 */

public class AlbumAll {

    /**
     * status : 0
     * msg : 成功
     * data : [{"id":"1","userId":"1","nickName":"YangGuiyu","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","title":"今天天气真好","img":"http://img1.3lian.com/2015/a1/105/d/40.jpg","info":"今天天气真好，适合旅游，出门","createTime":"1524063554297","zan":7,"collecti":1,"comment":10,"status":null,"photos":null,"tags":null},{"id":"2","userId":"1","nickName":"YangGuiyu","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","title":"万里无云","img":"http://attach.bbs.miui.com/forum/201804/30/174358wz44yk6yfrkov9uc.jpg","info":"info2","createTime":"1524063564297","zan":3,"collecti":1,"comment":0,"status":null,"photos":null,"tags":null},{"id":"5","userId":"1","nickName":"YangGuiyu","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","title":"推荐一张壁纸","img":"http://img.zcool.cn/community/038c0ee5744f9a500000025ae5acd2a.jpg","info":"电脑壁纸","createTime":"1524063574297","zan":2,"collecti":1,"comment":0,"status":null,"photos":null,"tags":null},{"id":"6","userId":"1","nickName":"YangGuiyu","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","title":"农夫山泉","img":"http://cdn.duitang.com/uploads/item/201610/24/20161024144503_nwTEH.jpeg","info":"壁纸","createTime":"1524064574297","zan":1,"collecti":1,"comment":0,"status":null,"photos":null,"tags":null},{"id":"7","userId":"1","nickName":"YangGuiyu","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","title":"壁纸","img":"http://i1.hdslb.com/bfs/archive/763293ce06bf1e684ef0ea3da43ae5008d8564b8.jpg","info":"壁纸","createTime":"1524064574397","zan":1,"collecti":1,"comment":0,"status":null,"photos":null,"tags":null}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * userId : 1
         * nickName : YangGuiyu
         * headImg : http://owf1tbdp7.bkt.clouddn.com/icon.jpg
         * title : 今天天气真好
         * img : http://img1.3lian.com/2015/a1/105/d/40.jpg
         * info : 今天天气真好，适合旅游，出门
         * createTime : 1524063554297
         * zan : 7
         * collecti : 1
         * comment : 10
         * status : null
         * photos : null
         * tags : null
         */

        private String id;
        private String userId;
        private String nickName;
        private String headImg;
        private String title;
        private String img;
        private String info;
        private String createTime;
        private int zan;
        private int collecti;
        private int comment;
        private Object status;
        private Object photos;
        private Object tags;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public int getCollecti() {
            return collecti;
        }

        public void setCollecti(int collecti) {
            this.collecti = collecti;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getPhotos() {
            return photos;
        }

        public void setPhotos(Object photos) {
            this.photos = photos;
        }

        public Object getTags() {
            return tags;
        }

        public void setTags(Object tags) {
            this.tags = tags;
        }
    }

    public boolean isOk(){
        if("0".equals(this.status)){
            return true;
        }else if("10".equals(this.status)){
            MyApplication.getContext().startActivity(new Intent(MyApplication.getContext(), LoginActivity.class));
        }else{
            Toast.makeText(MyApplication.getContext(), this.getMsg(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
