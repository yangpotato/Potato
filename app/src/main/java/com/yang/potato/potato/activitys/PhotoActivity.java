package com.yang.potato.potato.activitys;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.yang.potato.potato.R;
import com.yang.potato.potato.adapter.FilterAdapter;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.qiniuutils.Auth;
import com.yang.potato.potato.retrofit.RetrofitManage;
import com.yang.potato.potato.utils.GPUImageUtil;
import com.yang.potato.potato.utils.Utils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PhotoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_photo)
    ImageView imgPhoto;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.seekbar)
    SeekBar seekbar;

    private Uri uri;
    private String albumId;

    private Bitmap mBitmap;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private GPUImage gpuImage;

    private Configuration config;

    private UploadManager uploadManager;

    private FilterAdapter adapter;
    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initView() {
        setToolTitle("图片处理");
        setToolRight("完成");
        getRightTextView().setVisibility(View.VISIBLE);
        getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = getBitmap(imgPhoto.getDrawable());
                upload(UploadPhotoActivity.compressImage(bitmap));
            }
        });

        adapter = new FilterAdapter(list);
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycle.setAdapter(adapter);

        //tablyaout.setOnClickListener(this);
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        mBitmap = bitmap;
                        mBitmap1 = bitmap;
                        mBitmap2 = bitmap;
                        imgPhoto.setImageBitmap(bitmap);


                    }
                });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //通过进度条的值更改饱和度
                imgPhoto.setImageBitmap(getGPUImageFromAssets(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mBitmap != null) {
                    gpuImage = new GPUImage(PhotoActivity.this);
                    gpuImage.setImage(mBitmap);
                    gpuImage.setFilter(GPUImageUtil.getFilter(position + 1));
                    Bitmap bitmap = gpuImage.getBitmapWithFilterApplied();
                    mBitmap2 = bitmap;
                    imgPhoto.setImageBitmap(bitmap);
                }
            }
        });
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            uri = getIntent().getParcelableExtra("uri");
            albumId = getIntent().getStringExtra("albumId");
        }
        list.add("灰化");
        list.add("锐化");
        list.add("反色");
        list.add("素描");
        list.add("模糊");
        list.add("亮度");
        list.add("凸起");
        list.add("色彩");
        list.add("卡通");
        list.add("水晶球");

        config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
//                .useHttps(true)               // 是否使用https上传域名
//                .responseTimeout(60)          // 服务器响应超时。默认60秒
//                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone2)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        uploadManager = new UploadManager(config);
    }

    private void getData() {

        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }

    public Bitmap getGPUImageFromAssets(int progress){

        // 使用GPUImage处理图像
        gpuImage = new GPUImage(this);
        gpuImage.setImage(mBitmap);
        gpuImage.setFilter(new GPUImageSaturationFilter(progress));
        Bitmap  bitmap= gpuImage.getBitmapWithFilterApplied();
        mBitmap1 = bitmap;
        return bitmap;
    }
    public String getToken() {
        return Auth.create(Utils.AK, Utils.SK).uploadToken("mvdemo");
    }

    private void upload(File file) {
        String str = System.currentTimeMillis() + ".jpg";
        uploadManager.put(file, str, getToken(), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                if (info.isOK()) {
                    Log.i("qiniu", "Upload Success");
//                    urls.add("http://owf1tbdp7.bkt.clouddn.com/" + key);
//                    adapter.notifyDataSetChanged();

                    updatePhoto("http://owf1tbdp7.bkt.clouddn.com/" + key);
                } else {
                    Log.i("qiniu", "Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    Toast.makeText(PhotoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                }
//                Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
//                Toast.makeText(UploadPhotoActivity.this, ""+key, Toast.LENGTH_SHORT).show();
            }
        }, new UploadOptions(null, null, false, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                Log.i("qiniu", key + ": +" + percent);

            }
        }, null));
    }

    private void updatePhoto(final String url){
        Map<String, String> map = new HashMap<>();
        map.put("albumId", albumId);
        map.put("img", url);
        RetrofitManage.uploadPhoto(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(PhotoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.isOk()){
                            Toast.makeText(PhotoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(url);
                            finish();
                        }
                    }
                });
    }

    private Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
