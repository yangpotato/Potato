package com.yang.potato.potato.base;

/**
 * Created by potato on 2018/4/28.
 */

public interface BaseCallBack<T> {
    void onSuccess(T t);
    void OnFailed(String error);
}
