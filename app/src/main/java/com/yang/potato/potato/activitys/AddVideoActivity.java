package com.yang.potato.potato.activitys;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.qiniuutils.Auth;
import com.yang.potato.potato.retrofit.RetrofitManage;
import com.yang.potato.potato.utils.Utils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddVideoActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_video_info)
    EditText edtVideoInfo;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.video)
    JZVideoPlayerStandard video;
    private Uri uri;

    private Configuration config;
    private UploadManager uploadManager;

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_video;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
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

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEvent(Uri uri) {
        this.uri = uri;
        File file = new File(getPath(uri));
        upload(file);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_create)
    public void onClick() {
        if(TextUtils.isEmpty(edtTitle.getText().toString())||
                TextUtils.isEmpty(edtVideoInfo.getText().toString())){
            Toast.makeText(this, "请输入完整的信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(url)){
            Toast.makeText(this, "请等待视频加载完成!", Toast.LENGTH_SHORT).show();
            return;
        }
        request();
    }

    private void request() {
        Map<String, String> map = new HashMap<>();
        map.put("title", edtTitle.getText().toString());
        map.put("info", edtVideoInfo.getText().toString());
        map.put("url", url);
        RetrofitManage.uploadVideo(map)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(AddVideoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        Log.i("ygy", e.toString());
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.isOk()){
                            Toast.makeText(AddVideoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    public String getToken() {
        return Auth.create(Utils.AK, Utils.SK).uploadToken("mvdemo");
    }

    private void upload(File file) {
        String str = System.currentTimeMillis() + ".mp4";
        uploadManager.put(file, str, getToken(), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                if (info.isOK()) {
                    Log.i("qiniu", "Upload Success");
                    url = "http://owf1tbdp7.bkt.clouddn.com/" + key;
                    video.setUp("http://owf1tbdp7.bkt.clouddn.com/" + key, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                    //updatePhoto("http://owf1tbdp7.bkt.clouddn.com/" + key);
                } else {
                    Log.i("qiniu", "Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    Toast.makeText(AddVideoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
