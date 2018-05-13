package com.yang.potato.potato.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yang.potato.potato.entity.User;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by potato on 2018/4/30.
 */

public class Utils {

    public static String AK = "NY8bNE2EUR7fz-hMDLQjfM-mpgtv3qyeRM5EoDeF";
    public static String SK = "eRpDnnNA8PFJ95epV8ywgFW0luXm_2-5cd1he_l_";

    public static void saveUser(Context context, User user){
        SPUtils.put(context, "userId", user.getUserId());
        SPUtils.put(context, "code", TextUtils.isEmpty(user.getCode())? "" : user.getCode());
        SPUtils.put(context, "nickName", user.getNickName());
        SPUtils.put(context, "headImg", user.getHeadImg());
        SPUtils.put(context, "background", TextUtils.isEmpty(user.getBackground())? "" : user.getBackground());
        SPUtils.put(context, "qq", TextUtils.isEmpty(user.getQq())?"":user.getQq());
    }


    /**
     * 修改TabLayout下划线的长度
     *
     * @param tabs
     * @param leftDip  左间距
     * @param rightDip  右间距
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 获取状态栏高度
     * @param mContext
     * @return  状态栏高度
     */
    public static int getStatusBarHeight(Context mContext){

        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("WangJ", "状态栏-方法1:" + statusBarHeight1);
        return statusBarHeight1;
    }

    /**
     * 倒计时
     * @param tv 需要显示倒计时内容的TextView
     */
    public static void startCountDown(final TextView tv){
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                String value = String .valueOf((int)l/1000);
                tv.setText(value);
                tv.setEnabled(false);
            }

            @Override
            public void onFinish() {
                tv.setEnabled(true);
                tv.setText("发送验证码");
            }
        };
        countDownTimer.start();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String getTime(String seconds, String format){
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }


}
