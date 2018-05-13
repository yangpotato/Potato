package com.yang.potato.potato.entity;

import java.io.Serializable;

/**
 * Created by potato on 2018/4/30.
 */

public class User implements Serializable {

    /**
     * userId : 1
     * code : 18757999495
     * nickName : 1
     * headImg : http://owf1tbdp7.bkt.clouddn.com/icon.jpg
     * background :
     * pwd : null
     * qq : null
     * sex : 8
     * regTime : null
     * loginTime : 1525068115661
     * status : null
     */

    private String userId;
    private String code;
    private String nickName;
    private String headImg;
    private String background;
    private String pwd;
    private String qq;
    private int sex;
    private String regTime;
    private String loginTime;
    private String status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
