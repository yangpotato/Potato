package com.yang.potato.potato.utils;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by potato on 2018/4/30.
 */

public class ImageLoder {

    public static void loadImg(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }
}
