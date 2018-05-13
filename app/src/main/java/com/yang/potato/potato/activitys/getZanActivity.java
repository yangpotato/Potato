package com.yang.potato.potato.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yang.potato.potato.R;
import com.yang.potato.potato.adapter.GetZanAdapter;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.CollectionZan;
import com.yang.potato.potato.retrofit.RetrofitManage;
import com.yang.potato.potato.utils.LogUtils;
import com.yang.potato.potato.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class getZanActivity extends BaseActivity {

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
    private GetZanAdapter adapter;
    private List<CollectionZan> collectionZanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_zan;
    }

    @Override
    protected void initView() {
        setToolTitle("点赞收藏");

        adapter = new GetZanAdapter(this, collectionZanList);
        recycle.setLayoutManager(new GridLayoutManager(this, 3));
        recycle.setAdapter(adapter);

        RetrofitManage.getCollection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<CollectionZan>>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(e.toString());
                        //Toast.makeText(getZanActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getZanActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest<List<CollectionZan>> listBaseRequest) {
                        collectionZanList = listBaseRequest.getData();
                        adapter.setNewData(collectionZanList);
                        adapter.notifyDataSetChanged();
                    }
                });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getZanActivity.this, AlbumInfoActivity.class)
                        .putExtra("albumId", collectionZanList.get(position).getId())
                        .putExtra("otherId", collectionZanList.get(position).getUserId()));
            }
        });
    }

    @Override
    protected void initData() {

    }
}
