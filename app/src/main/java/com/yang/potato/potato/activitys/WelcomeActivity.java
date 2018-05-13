package com.yang.potato.potato.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.yang.potato.potato.R;
import com.yang.potato.potato.utils.SPUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        if(TextUtils.isEmpty((String)SPUtils.get(this, "userId", ""))){
//
//        }
        String id = (String) SPUtils.get(this, "userId", "");
        if(!TextUtils.isEmpty(id)) {
            JPushInterface.setAlias(WelcomeActivity.this, id, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    Log.d("Debug:", "I+"+i+"别名:"+s);
                }
            });
        }

        startActivity(new Intent(this, MainActivity.class));
    }
}
