package com.yang.potato.potato.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class CreateALbumActivity extends BaseActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_album;
    }

    @Override
    protected void initView() {
        setToolTitle("上传相册");
        getRightTextView().setVisibility(View.VISIBLE);
        getRightTextView().setText("提交");
        getRightTextView().setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        Map<String, String> map = new HashMap<>();
       // map.put("title", edtTitle.to)
    }
}
