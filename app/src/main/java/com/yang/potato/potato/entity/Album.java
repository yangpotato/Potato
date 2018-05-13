package com.yang.potato.potato.entity;

import java.util.List;

/**
 * Created by potato on 2018/4/30.
 */

public class Album {

    /**
     * id : 1
     * userId : 1
     * title : title1
     * img : img1
     * info : info1
     * createTime : 1524063554297
     * zan : 3
     * collecti : 1
     * comment : 4
     * status : null
     * photos : [{"id":"1","albumId":"1","imgUrl":"tyhrtuyrtyu","createTime":null},{"id":"2","albumId":"1","imgUrl":"豆腐干地方和","createTime":null}]
     * tags : [{"id":"2","albumId":"1","tag":"风景"},{"id":"1","albumId":"1","tag":"动漫"}]
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
    private String status;
    private List<PhotosBean> photos;
    private List<TagsBean> tags;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class PhotosBean {
        /**
         * id : 1
         * albumId : 1
         * imgUrl : tyhrtuyrtyu
         * createTime : null
         */

        private String id;
        private String albumId;
        private String imgUrl;
        private Object createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }
    }

    public static class TagsBean {
        /**
         * id : 2
         * albumId : 1
         * tag : 风景
         */

        private String id;
        private String albumId;
        private String tag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
