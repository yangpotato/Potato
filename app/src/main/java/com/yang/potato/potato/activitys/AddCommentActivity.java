package com.yang.potato.potato.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.AlbumInfo;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddCommentActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt)
    EditText edt;

    private AlbumInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_comment;
    }

    @Override
    protected void initView() {
        setToolTitle("添加评论");
        getRightTextView().setVisibility(View.VISIBLE);
        getRightTextView().setText("提交");
        getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edt.getText().toString())){
                    Toast.makeText(AddCommentActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                addComment();
            }
        });
    }

    @Override
    protected void initData() {
        if(getIntent() != null){
            info = (AlbumInfo) getIntent().getSerializableExtra("info");
        }
    }

    private void addComment(){
        Map<String, String> map = new HashMap<>();
        map.put("type", "0");
        map.put("id", info.getAlbum_id());
        map.put("otherId", info.getUserId());
        map.put("info", edt.getText().toString());

        RetrofitManage.addComment(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("请求失败");
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.isOk()){
                            Toast.makeText(AddCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

    }
}
