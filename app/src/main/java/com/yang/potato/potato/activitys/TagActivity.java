package com.yang.potato.potato.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yang.potato.potato.R;
import com.yang.potato.potato.adapter.SelectTagAdapter;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.Tag;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TagActivity extends BaseActivity {

    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private List<Tag> tags = new ArrayList<>();
    private SelectTagAdapter adapter;
    public static String tag = "";
    public static String tagName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag;
    }

    @Override
    protected void initView() {
        adapter = new SelectTagAdapter(tags);
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycle.setAdapter(adapter);

        RetrofitManage.getTag()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<Tag>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<Tag>> listBaseRequest) {
                        tags = listBaseRequest.getData();
                        adapter.setNewData(listBaseRequest.getData());
                        adapter.notifyDataSetChanged();
                    }
                });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.isSelected()){
                    view.setSelected(false);
                }else{
                    view.setSelected(true);
                }
                if(TextUtils.isEmpty(tag)){
                    tag = tags.get(position).getId();
                    tagName = tags.get(position).getTag();
                }else{
                    tag = tag + "," +tags.get(position).getId();
                    tagName = tagName +  "," + tags.get(position).getTag();
                }

            }
        });

    }

    @Override
    protected void initData() {
        tag = "";
        tagName = "";
    }

    @OnClick({R.id.tv_cancel, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                tag = "";
                tagName = "";
                finish();
                break;
            case R.id.tv_sure:
                finish();
                break;
        }
    }
}
