package com.yang.potato.potato.back;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yang.potato.potato.R;
import com.yang.potato.potato.back.adapter.BackAlbumAdapter;
import com.yang.potato.potato.back.adapter.BaseVideoAdapter;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.Video;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VideoListActivity extends BaseActivity {

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
    private List<Video> videos = new ArrayList<>();
    private BaseVideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void initView() {
        setToolTitle("视频管理");

        adapter = new BaseVideoAdapter(videos);
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycle.setAdapter(adapter);

        request1();

        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                delete(position);
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void delete(int position) {
        Map<String, String> map = new HashMap<>();
        map.put("id", videos.get(position).getId());
        RetrofitManage.delectVideo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(VideoListActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest listBaseRequest) {
//                        aLbums = listBaseRequest.getData();
//                        adapter.setNewData(aLbums);
//                        adapter.notifyDataSetChanged();
                        Toast.makeText(VideoListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        request1();
                    }
                });
    }

    private void request1() {
        RetrofitManage.getVideo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<Video>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(VideoListActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseRequest<List<Video>> listBaseRequest) {
                        videos = listBaseRequest.getData();
                        adapter.setNewData(videos);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        request1();
    }
}
