package com.yang.potato.potato.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by potato on 2018/5/1.
 */

public class AlbumInfo implements Serializable {

    /**
     * album_id : 1
     * userId : 1
     * title : title1
     * info : info1
     * zan : 3
     * collecti : 1
     * comment : 4
     * headImg : http://owf1tbdp7.bkt.clouddn.com/icon.jpg
     * nickName : YangGuiyu
     * photos : [{"id":"1","albumId":null,"imgUrl":"tyhrtuyrtyu","createTime":null},{"id":"2","albumId":null,"imgUrl":"豆腐干地方和","createTime":null}]
     * tags : [{"id":"1","albumId":null,"tag":"动漫"},{"id":"2","albumId":null,"tag":"风景"}]
     * comments : [{"id":"1","userId":"1","nickName":"YangGuiyu","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","albumId":null,"videoId":null,"info":"评论131234124234235","createTime":"1524302132613","status":null},{"id":"2","userId":"3","nickName":"5455","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","albumId":null,"videoId":null,"info":"评论131234124234235","createTime":"1524302151384","status":null},{"id":"3","userId":"9","nickName":"potato9","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","albumId":null,"videoId":null,"info":"wdfsfdsfsdfsdf2222","createTime":"1524809667654","status":null},{"id":"4","userId":"12","nickName":"potato12","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","albumId":null,"videoId":null,"info":"wdfsfdsfsdfsdf333","createTime":"1524809678507","status":null},{"id":"5","userId":"26","nickName":"potato26","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","albumId":null,"videoId":null,"info":"wdfsfdsfsdfsdf11111","createTime":"1524809679761","status":null},{"id":"6","userId":"9","nickName":"potato9","headImg":"http://owf1tbdp7.bkt.clouddn.com/icon.jpg","albumId":null,"videoId":null,"info":"wdfsfdsfsdfsdfasdas","createTime":"1524809680910","status":null}]
     * creatTime : 1524063554297
     */

    private String album_id;
    private String userId;
    private String title;
    private String info;
    private int zan;
    private int collecti;
    private int comment;
    private String headImg;
    private String nickName;
    private String creatTime;
    private List<PhotosBean> photos;
    private List<TagsBean> tags;
    private List<CommentsBean> comments;

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
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

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class PhotosBean implements Serializable {
        /**
         * id : 1
         * albumId : null
         * imgUrl : tyhrtuyrtyu
         * createTime : null
         */

        private String id;
        private Object albumId;
        private String imgUrl;
        private Object createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getAlbumId() {
            return albumId;
        }

        public void setAlbumId(Object albumId) {
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

    public static class TagsBean implements Serializable {
        /**
         * id : 1
         * albumId : null
         * tag : 动漫
         */

        private String id;
        private Object albumId;
        private String tag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getAlbumId() {
            return albumId;
        }

        public void setAlbumId(Object albumId) {
            this.albumId = albumId;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }

    public static class CommentsBean implements Serializable {
        /**
         * id : 1
         * userId : 1
         * nickName : YangGuiyu
         * headImg : http://owf1tbdp7.bkt.clouddn.com/icon.jpg
         * albumId : null
         * videoId : null
         * info : 评论131234124234235
         * createTime : 1524302132613
         * status : null
         */

        private String id;
        private String userId;
        private String nickName;
        private String headImg;
        private Object albumId;
        private Object videoId;
        private String info;
        private String createTime;
        private Object status;

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

        public Object getAlbumId() {
            return albumId;
        }

        public void setAlbumId(Object albumId) {
            this.albumId = albumId;
        }

        public Object getVideoId() {
            return videoId;
        }

        public void setVideoId(Object videoId) {
            this.videoId = videoId;
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

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }
    }
}
