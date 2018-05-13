package com.yang.potato.potato.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.yang.potato.potato.R;
import com.yang.potato.potato.adapter.UploadImgAdapter;
import com.yang.potato.potato.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class SelectActivity extends BaseActivity {

    @BindView(R.id.img_pop_video)
    ImageView imgPopVideo;
    @BindView(R.id.img_pop_notes)
    ImageView imgPopNotes;
    @BindView(R.id.img_pop_close)
    ImageView imgPopClose;

    public static int VIDEO_CAPTURE = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_pop_video, R.id.img_pop_notes, R.id.img_pop_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_pop_video:
                Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);

                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,10485760L);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10);
                startActivityForResult(intent,VIDEO_CAPTURE);
                break;
            case R.id.img_pop_notes:
                startToActivity(UploadPhotoActivity.class);
                break;
            case R.id.img_pop_close:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == VIDEO_CAPTURE){
            Uri uri = data.getData();
            EventBus.getDefault().postSticky(uri);
           startActivity(new Intent(this, AddVideoActivity.class));
        }
    }
}
