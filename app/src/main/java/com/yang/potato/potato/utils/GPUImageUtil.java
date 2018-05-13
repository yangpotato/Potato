package com.yang.potato.potato.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;

/**
 * Created by potato on 2018/5/4.
 */

public class GPUImageUtil {

    private static GPUImageFilter filter;

    //饱和度、亮度等参数指数
    private static int count;

    /**
     * 获取过滤器
     * @param GPUFlag
     * @return 滤镜类型
     */
    public static GPUImageFilter getFilter(int GPUFlag){
        switch (GPUFlag){
            case 1:
                filter = new GPUImageGrayscaleFilter();//灰度
                break;
            case 2:
                filter = new GPUImageSharpenFilter();//锐化
                break;
            case 3:
                filter = new GPUImageColorInvertFilter();//反色
                break;
            case 4:
                filter = new GPUImageSketchFilter();//素描
                break;
            case 5:
                filter = new GPUImageGaussianBlurFilter();//模糊
                break;
            case 6:
                filter = new GPUImageBrightnessFilter();//亮度
                break;
            case 7:
                filter = new GPUImageBulgeDistortionFilter();//凸起失真，鱼眼效果
                break;
            case 8:
                filter = new GPUImageToonFilter();//卡通
                break;
            case 9:
                filter = new GPUImageChromaKeyBlendFilter();//色度键混合
                break;
            case 10:
                filter = new GPUImageGlassSphereFilter();
                break;
            case 11:
                filter = new GPUImageSaturationFilter(count);
                break;
        }
        return filter;
    }



    public static Bitmap getGPUImageFromURL(String url) {
        Bitmap bitmap = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //调整饱和度、亮度等
    public static void changeSaturation(int curCount){
        GPUImageUtil.count = curCount;
    }
}
