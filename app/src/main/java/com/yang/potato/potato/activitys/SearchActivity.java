package com.yang.potato.potato.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yang.potato.potato.R;
import com.yang.potato.potato.adapter.FollowAdapter;
import com.yang.potato.potato.adapter.FollowListAdapter;
import com.yang.potato.potato.adapter.SearchAlbumAdapter;
import com.yang.potato.potato.adapter.VideoAdapter;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.User;
import com.yang.potato.potato.entity.Video;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.lin_search)
    LinearLayout linSearch;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.recycle)
    RecyclerView recycle;

    private List<Album> albums = new ArrayList<>();
    private List<Video> videos = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    private SearchAlbumAdapter albumAdapter;
    private VideoAdapter videoAdapter;
    private FollowListAdapter userAdapter;

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        setToolTitle("搜索");


        tablayout.addTab(tablayout.newTab().setText("相册"));
        tablayout.addTab(tablayout.newTab().setText("视频"));
        tablayout.addTab(tablayout.newTab().setText("用户"));

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                if(!TextUtils.isEmpty(search.getText().toString())) {
                    if (position == 0) {
                        requestAlbum(0 + "");
                    } else if (position == 1) {
                        requestVideo(1 + "");
                    } else if (position == 2) {
                        requestUser(2 + "");
                    }
                }else{
                    Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive()){
                        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0 );
                        //响应的事件
                        if(!TextUtils.isEmpty(search.getText().toString())){
                            requestAlbum(position+"");

                        }else{
                            Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                        }

                    }
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void initData() {

    }

    public void requestAlbum(final String type){
        Map<String, String> map = new HashMap<>();
        map.put("keyword", search.getText().toString());
        map.put("type", type);
        RetrofitManage.searchAlbum(map)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<Album>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<Album>> listBaseRequest) {
                        if(listBaseRequest.isOk()){
                            albums = listBaseRequest.getData();

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                    recycle.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
                                    albumAdapter = new SearchAlbumAdapter( SearchActivity.this, albums);

                                    recycle.setAdapter(albumAdapter);

                                    albumAdapter.setNewData(albums);
                                    albumAdapter.notifyDataSetChanged();

                                    albumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            startActivity(new Intent(SearchActivity.this, AlbumInfoActivity.class)
                                                    .putExtra("albumId", albums.get(position).getId())
                                                    .putExtra("otherId", albums.get(position).getUserId()));
                                        }
                                    });
                                }
                            });

                        }
                    }
                });
    }
    private void requestVideo(final String type){
        Map<String, String> map = new HashMap<>();
        map.put("keyword", search.getText().toString());
        map.put("type", type);
        RetrofitManage.searchVideo(map)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<Video>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRequest<List<Video>> listBaseRequest) {
                        if(listBaseRequest.isOk()){
                            videos = listBaseRequest.getData();

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                    videoAdapter = new VideoAdapter( SearchActivity.this, videos);
                                    recycle.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
                                    recycle.setAdapter(videoAdapter);
                                    videoAdapter.setNewData(videos);
                                    videoAdapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                });
    }
    private void requestUser(final String type){
        Map<String, String> map = new HashMap<>();
        map.put("keyword", search.getText().toString());
        map.put("type", type);
        RetrofitManage.searchUser(map)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
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
                            users = listBaseRequest.getData();

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                    userAdapter = new FollowListAdapter(users, SearchActivity.this);
                                    recycle.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
                                    recycle.setAdapter(userAdapter);
                                    userAdapter.setNewData(users);
                                    userAdapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                });
    }
}
