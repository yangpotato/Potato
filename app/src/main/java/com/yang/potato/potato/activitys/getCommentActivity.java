package com.yang.potato.potato.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.yang.potato.potato.R;
import com.yang.potato.potato.adapter.GetCommentAdapter;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.GetComment;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class getCommentActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle)
    RecyclerView recycle;

    private GetCommentAdapter adapter;
    private List<GetComment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_comment;
    }

    @Override
    protected void initView() {
        setToolTitle("收到的评论");
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new GetCommentAdapter(list, this);
        recycle.setAdapter(adapter);

        request();

    }

    @Override
    protected void initData() {

    }

    private void request(){
        RetrofitManage.getComment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<GetComment>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<GetComment>> listBaseRequest) {
                        if(listBaseRequest.isOk()){
                            list = listBaseRequest.getData();
                            adapter.setNewData(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
