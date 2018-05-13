package com.yang.potato.potato.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
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
import com.yang.potato.potato.utils.ImageLoder;
import com.yang.potato.potato.utils.SPUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yang.potato.potato.utils.Utils.AK;
import static com.yang.potato.potato.utils.Utils.SK;

public class SettingInfoActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img_icon)
    RoundedImageView imgIcon;
    @BindView(R.id.lin_setting_info_icon)
    RelativeLayout linSettingInfoIcon;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.lin_setting_info_name)
    RelativeLayout linSettingInfoName;
    @BindView(R.id.tvv)
    TextView tvv;
    @BindView(R.id.lin_setting_info_sign)
    RelativeLayout linSettingInfoSign;
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edt_seting_info)
    EditText edtSetingInfo;

    private File file;
    private UploadManager uploadManager;

    private String imgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_info;
    }

    @Override
    protected void initView() {
        setToolTitle("修改个人信息");
        setToolRight("提交");
        getRightTextView().setVisibility(View.VISIBLE);
        getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });

        ImageLoder.loadImg(this, (String) SPUtils.get(this, "headImg", ""), imgIcon);
        edtName.setText((String) SPUtils.get(this, "nickName", ""));
        edtSetingInfo.setText((String) SPUtils.get(this, "background", ""));
    }

    @Override
    protected void initData() {

        Configuration config = new Configuration.Builder()
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

    private void request() {
        Map<String, String> map = new HashMap<>();
        map.put("nickName", edtName.getText().toString());
        map.put("sex", "0");
        map.put("background", edtSetingInfo.getText().toString());
        if(TextUtils.isEmpty(imgUrl)) {
            map.put("headImg", (String) SPUtils.get(this, "headImg", ""));
        }else{
            map.put("headImg", imgUrl);
        }
        RetrofitManage.modify(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SettingInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if (baseRequest.isOk()) {
                            Toast.makeText(SettingInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            SPUtils.put(SettingInfoActivity.this, "nickName", edtName.getText().toString());
                            if(!TextUtils.isEmpty(imgUrl)){
                                SPUtils.put(SettingInfoActivity.this, "headImg", imgUrl);
                            }
                            SPUtils.put(SettingInfoActivity.this, "background", edtSetingInfo.getText().toString());
                        }
                    }
                });
    }

    public String getToken(){
        return Auth.create(AK, SK).uploadToken("mvdemo");
    }

    private void upload(){
        String str = System.currentTimeMillis() + ".jpg";
        uploadManager.put(file, str, getToken(), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                if(info.isOK()) {
                    Log.i("qiniu", "Upload Success");
                    imgUrl = "http://owf1tbdp7.bkt.clouddn.com/" + key;
                } else {
                    Log.i("qiniu", "Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
                Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                Toast.makeText(SettingInfoActivity.this, ""+key, Toast.LENGTH_SHORT).show();
            }
        }, new UploadOptions(null, null, false, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                Log.i("qiniu", key + ": +" + percent);
               //progressBar.setProgress((int)percent*100);
            }
        }, null));
    }



    @OnClick(R.id.img_icon)
    public void onClick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        Glide.with(this).load(uri).into(imgIcon);
        file = uri2File(uri);
    }

    private File uri2File(Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }
}
