package com.yang.potato.potato.base;

import android.content.Intent;
import android.widget.Toast;

import com.yang.potato.potato.MyApplication;
import com.yang.potato.potato.activitys.LoginActivity;

/**
 * Created by potato on 2018/4/28.
 */

public class BaseRequest<T> {
    private String status;
    private String msg;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk(){
        if("0".equals(this.status)){
            return true;
        }else if("10".equals(this.status)){
            MyApplication.getContext().startActivity(new Intent(MyApplication.getContext(), LoginActivity.class));
        }else{
            //Toast.makeText(MyApplication.getContext(), this.getMsg(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
