package com.yang.potato.potato.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.yang.potato.potato.adapter.UploadImgAdapter;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.qiniuutils.Auth;
import com.yang.potato.potato.retrofit.RetrofitManage;
import com.yang.potato.potato.utils.LogUtils;
import com.yang.potato.potato.utils.Utils;
import com.yang.potato.potato.utils.Yasuo;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UploadPhotoActivity extends BaseActivity implements View.OnClickListener {

    private static final int TYPE_TAKE_PHOTO = 1;
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
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.recycleUpload)
    RecyclerView recycleUpload;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.tv_tag2)
    TextView tvTag2;
    @BindView(R.id.tv_tag3)
    TextView tvTag3;
    @BindView(R.id.btn_create)
    Button btnCreate;

    private List<String> urls = new ArrayList<>();
    private UploadImgAdapter adapter;

    private String name = "";
    private String city = "";
    private int height = 0;
    private int weight = 0;
    private String BWHString = "";
    private String mCupString = "";
    private int currentDegree = 0;
    private int currMarryStatus = 0;
    private int currIncome = 0;
    private Bitmap bitmap;
    private int age = 0;
    private String currentXingzuo = "";

    private static final int ACTION_CAMERA = 1;
    private static final int ACTION_ALBUM = 2;
    private static final int CROP_RESULT = 3;

    String[] s = {"", "相册"};
    private AlertDialog.Builder builder;

    private Configuration config;
    private UploadManager uploadManager;

    private boolean isCreate = false;
    private String albumId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload_photo;
    }

    @Override
    protected void initView() {
        setToolTitle("上传相册");
        getRightTextView().setVisibility(View.VISIBLE);
        getRightTextView().setText("提交");
        getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(TagActivity.tag)){
                    Toast.makeText(UploadPhotoActivity.this, "你还未选择标签", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateTag();
            }
        });

        adapter = new UploadImgAdapter(this, urls);
        recycleUpload.setLayoutManager(new GridLayoutManager(this, 3));
        recycleUpload.setAdapter(adapter);

        adapter.addFooterView(getFootView());
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

    private View getFootView() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_img, null);
        ImageView img = view.findViewById(R.id.img_item);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(albumId)){
                    Toast.makeText(UploadPhotoActivity.this, "请先创建相册", Toast.LENGTH_SHORT).show();
                    return;
                }
                builder = new AlertDialog.Builder(UploadPhotoActivity.this);
                builder.setItems(s, new DialogInterface.OnClickListener()

                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
//                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                        if (Utils.hasSDCard()) {
//                                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "face.jpg")));
//                                        }
//                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                                        Uri photoUrid= getMediaFileUri(TYPE_TAKE_PHOTO);
////                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                                        startActivityForResult(intent, ACTION_CAMERA);
                                        break;
                                    case 1:
//                                        Intent photoIntent = new Intent();
//                                        photoIntent.setType("image/*");
//                                        photoIntent.setAction(Intent.ACTION_GET_CONTENT);
//                                        startActivityForResult(photoIntent, ACTION_ALBUM);
                                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                                        intent1.setType("image/*");
                                        startActivityForResult(intent1, ACTION_ALBUM);
                                        break;
                                }
                            }
                        }
                );
                builder.show();
            }
        });
        return view;
    }

    public Uri getMediaFileUri(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "相册名字");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return Uri.fromFile(mediaFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case ACTION_CAMERA:
//                    if (Utils.hasSDCard()) {
//                        File tempFile = new File(Environment.getExternalStorageDirectory() + "/face.jpg");
////                        startPhotZoom(data.getData());
//                        //startPhotZoom(Uri.fromFile(tempFile));
//                        upload(uri2File(data.getData()));
//                    }
                    Uri uri = data.getData();
                    //Glide.with(this).load(uri).into(img);
                    //File file = uri2File(uri);
                    //upload(file);
                    startActivity(new Intent(UploadPhotoActivity.this, PhotoActivity.class)
                            .putExtra("uri", uri)
                            .putExtra("albumId", albumId));
                    break;
                case ACTION_ALBUM:
                    if (data != null && data.getData() != null){
                        startActivity(new Intent(UploadPhotoActivity.this, PhotoActivity.class)
                                .putExtra("uri", data.getData())
                                .putExtra("albumId", albumId));
                    }
                        //startPhotZoom(data.getData());
                        //upload(uri2File(data.getData()));
                    break;
                case CROP_RESULT://裁剪后的图片
                    if (data != null)
                        setImage(data);
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(String str){
        urls.add(str);
        adapter.notifyDataSetChanged();
    }

    private void setImage(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                bitmap = bundle.getParcelable("data");
                bitmap = Yasuo.comp(bitmap);
                upload(compressImage(bitmap));
            }
        }
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

    /**
     * 裁剪tde
     *
     * @param uri
     */
    private void startPhotZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_RESULT);
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
                    urls.add("http://owf1tbdp7.bkt.clouddn.com/" + key);
                    adapter.notifyDataSetChanged();

                    updatePhoto("http://owf1tbdp7.bkt.clouddn.com/" + key);
                } else {
                    Log.i("qiniu", "Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    Toast.makeText(UploadPhotoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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

    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                //BAFLogger.e(TAG,e.getMessage());
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            //BAFLogger.e(TAG,e.getMessage());
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(TagActivity.tagName)) {
            String[] s = TagActivity.tagName.split(",");
            for (int i = 0; i < s.length; i++) {
                if (i == 0) {
                    tvTag1.setText("#" + s[0]);
                    tvTag1.setVisibility(View.VISIBLE);
                }
                if (i == 1) {
                    tvTag2.setText("#" + s[1]);
                    tvTag2.setVisibility(View.VISIBLE);
                }
                if (i == 2) {
                    tvTag3.setText("#" + s[2]);
                    tvTag3.setVisibility(View.VISIBLE);
                }
            }
        }
    }



    @OnClick({R.id.btn_create, R.id.tv_tag})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                if(TextUtils.isEmpty(edtVideoInfo.getText().toString()) ||
                        TextUtils.isEmpty(edtTitle.getText().toString())){
                    Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                craeteAlbum();
                break;
            case R.id.tv_tag:
                if(TextUtils.isEmpty(albumId)){
                    Toast.makeText(this, "请先创建相册", Toast.LENGTH_SHORT).show();
                    return;
                }
                startToActivity(TagActivity.class);
                break;
        }
    }

    private void craeteAlbum() {
        Map<String, String> map = new HashMap<>();
        map.put("title", edtTitle.getText().toString());
        map.put("info", edtVideoInfo.getText().toString());
        map.put("img",  "");
        RetrofitManage.uploadAlbum(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<String>>() {
                    @Override
                    public void onCompleted() {
                        isCreate = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e.toString());
                        Toast.makeText(UploadPhotoActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest<String> baseRequest) {
                        if(baseRequest.isOk()){
                            Toast.makeText(UploadPhotoActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                            isCreate = true;
                            albumId = String.valueOf(baseRequest.getData());
                        }
                    }
                });

    }

    private void updatePhoto(String url){
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
                        Toast.makeText(UploadPhotoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.isOk()){
                            Toast.makeText(UploadPhotoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateTag(){
        Map<String, String> map = new HashMap<>();
        map.put("albumId", albumId);
        map.put("tag", TagActivity.tag);
        RetrofitManage.uploadTag(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(UploadPhotoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.isOk()){
                            Toast.makeText(UploadPhotoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
