package com.yang.potato.potato.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.yang.potato.potato.MyApplication;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by potato on 2017/12/10.
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            Log.d("asd","if");
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SharedPreferences.Editor config = MyApplication.getContext()
                    .getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE)
                    .edit();
            config.putStringSet("cookie", cookies);
            config.commit();
        }else{
            Log.d("asd","else");
        }

        return originalResponse;
    }

}

