package com.yang.potato.potato.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yang.potato.potato.R;
import com.yang.potato.potato.adapter.FollowListAdapter;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.User;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FollowListActivity extends BaseActivity {

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

    private List<User> list = new ArrayList<>();
    private FollowListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_list;
    }

    @Override
    protected void initView() {
        adapter = new FollowListAdapter(list, this);
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycle.setAdapter(adapter);
        request();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    private void request(){
        RetrofitManage.getFollow()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<User>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<User>> listBaseRequest) {
                        if(listBaseRequest.isOk()){
                            list = listBaseRequest.getData();
                            adapter.setNewData(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
