package com.yang.potato.potato.entity;

/**
 * Created by potato on 2018/4/30.
 */

public class Tag {

    /**
     * id : 1
     * albumId : null
     * tag : 动漫
     */

    private String id;
    private Object albumId;
    private String tag;

    public Tag(String id, Object albumId, String tag) {
        this.id = id;
        this.albumId = albumId;
        this.tag = tag;
    }

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
