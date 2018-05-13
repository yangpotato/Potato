package com.yang.potato.potato.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yang.potato.potato.R;
import com.yang.potato.potato.adapter.AlbumInfoAdapter;
import com.yang.potato.potato.adapter.CommentAdapter;
import com.yang.potato.potato.adapter.PhotoAdapter;
import com.yang.potato.potato.adapter.TagAdapter;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.AlbumInfoContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.AlbumAll;
import com.yang.potato.potato.entity.AlbumInfo;
import com.yang.potato.potato.entity.Status;
import com.yang.potato.potato.presenter.AlbumInfoPresenter;
import com.yang.potato.potato.retrofit.RetrofitManage;
import com.yang.potato.potato.utils.ImageLoder;
import com.yang.potato.potato.utils.SPUtils;
import com.yang.potato.potato.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AlbumInfoActivity extends BaseActivity implements AlbumInfoContract.View {

    @BindView(R.id.recycle_info)
    RecyclerView recycleInfo;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    private AlbumInfoAdapter adapter;
    private List<Album> albums = new ArrayList<>();

    private AlbumInfoPresenter presenter;

    private RecyclerView recycleBinner, tagRecycle, commentRecycle;
    private RoundedImageView imgIcon, imgComment;
    private TextView tvName, tvFollow, tvTitle, tvInfo, tvTime, tvCommentNumber;

    private String albumId, otherId;
    private PhotoAdapter photoAdapter;
    private TagAdapter tagAdapter;
    private CommentAdapter commentAdapter;
    private List<AlbumInfo.CommentsBean> comments = new ArrayList<>();
    private List<AlbumInfo.PhotosBean> photos = new ArrayList<>();
    private List<AlbumInfo.TagsBean> tags = new ArrayList<>();
    private Integer type = -1;

    private AlbumInfo info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_album_info;
    }

    @Override
    protected void initView() {

        recycleInfo.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new AlbumInfoAdapter(albums);
        recycleInfo.setAdapter(adapter);

        adapter.addHeaderView(setHeaderFirstView());
        adapter.addHeaderView(setHeaderCommentView());
        //tvFollow.setVisibility(View.GONE);
        presenter = new AlbumInfoPresenter(this);
        Map<String, String> map = new HashMap<>();
        map.put("pager", 0+"");
        map.put("number", 10+"");
        presenter.getAlbum(map);
        presenter.getALbumInfo(albumId);


    }

    private void request() {
        Map<String, String> map = new HashMap<>();
        map.put("otherId", info.getUserId());

        RetrofitManage.findFollowStatus(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if("0".equals(baseRequest.getStatus())){
                            tvFollow.setSelected(true);
                            tvFollow.setText("已关注");
                        }else{
                            tvFollow.setSelected(false);
                            tvFollow.setText("关注");
                        }
                    }
                });
    }

    private void attention(){
        Map<String, String> map = new HashMap<>();
        map.put("userId", info.getUserId());
        RetrofitManage.attention(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.isOk()){
                            Toast.makeText(AlbumInfoActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                            tvFollow.setSelected(true);
                            tvFollow.setText("已关注");
                        }
                    }
                });
    }

    private void cancelAttention(){
        Map<String, String> map = new HashMap<>();
        map.put("userId", info.getUserId());
        RetrofitManage.cancelAttention(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.isOk()){
                            Toast.makeText(AlbumInfoActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                            tvFollow.setSelected(false);
                            tvFollow.setText("关注");
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            albumId = getIntent().getStringExtra("albumId");
            otherId = getIntent().getStringExtra("otherId");
        }
    }

    private void getAlbum(){
        Map<String, String> map = new HashMap<>();
        map.put("pager", 0+"");
        map.put("number", 10+"");
        RetrofitManage.getAlbumA(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AlbumAll>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(AlbumInfoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AlbumAll listBaseRequest) {
                        if(listBaseRequest.isOk()){
                            //adapter.setNewData(listBaseRequest.getData());
                            adapter.notifyDataSetChanged();

                            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    startActivity(new Intent(AlbumInfoActivity.this, AlbumInfoActivity.class)
                                            .putExtra("albumId", albums.get(position).getId())
                                            .putExtra("otherId", albums.get(position).getUserId()));
                                }
                            });
                        }
                    }
                });
    }

    private void getAlbumInfo(){

    }

    @Override
    public void initRecycle(final List<Album> albums) {
        adapter.setNewData(albums);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(AlbumInfoActivity.this, AlbumInfoActivity.class)
                        .putExtra("albumId", albums.get(position).getId())
                        .putExtra("otherId", albums.get(position).getUserId()));
            }
        });
    }

    @Override
    public void initView(AlbumInfo info) {

        this.info = info;
        String id = (String) SPUtils.get(this, "userId", "");
        if(id.equals(info.getUserId())){
            tvFollow.setVisibility(View.GONE);
        }
        request();
        Map<String, String> map = new HashMap<>();
        map.put("albumId", info.getAlbum_id());
        map.put("otherId", info.getUserId());
        presenter.getStatus(map);

        ImageLoder.loadImg(this, info.getHeadImg(), imgIcon);
        ImageLoder.loadImg(this, info.getHeadImg(), imgComment);
        tvName.setText(info.getNickName());
        tvTitle.setText(info.getTitle());
        tvInfo.setText(info.getInfo());
        tvZan.setText("赞·"+ info.getZan());
        tvComment.setText("评论·"+ info.getComment());
        tvCollect.setText("收藏·"+ info.getCollecti());
        tvTime.setText(Utils.getTime(info.getCreatTime(), null));
        tvCommentNumber.setText("共"+info.getComments().size()+"条评论");

        photoAdapter.setNewData(info.getPhotos());
        photoAdapter.notifyDataSetChanged();

        tagAdapter.setNewData(info.getTags());
        tagAdapter.notifyDataSetChanged();

        commentAdapter.setNewData(info.getComments());
        commentAdapter.notifyDataSetChanged();

    }

    @Override
    public void showInfo(String string) {
       // Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Map<String, String> map = new HashMap<>();
        map.put("pager", 0+"");
        map.put("number", 10+"");
        presenter.getAlbum(map);
        presenter.getALbumInfo(albumId);
    }

    @Override
    public void initZanStatus(Status status) {
        if("1".equals(status.getZan())){
            tvZan.setSelected(true);
        }else{
            tvZan.setSelected(false);
        }

        if("1".equals(status.getCollection())){
            tvCollect.setSelected(true);
        }else{
            tvCollect.setSelected(false);
        }
//
//        if("1".equals(status.getFollow())){
//            tvFollow.setVisibility(View.GONE);
//        }else{
//            tvFollow.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void setViewSelect() {
        if(type == 0){
            tvZan.setSelected(true);
            String str = tvZan.getText().toString().substring(2, tvZan.getText().toString().length());
            int a =Integer.parseInt(str) +1;
            tvZan.setText("赞·"+ a);
        }
        if(type == 1){
            tvCollect.setSelected(true);
            String str = tvCollect.getText().toString().substring(3, tvCollect.getText().toString().length());
            int a =Integer.parseInt(str) +1;
            tvCollect.setText("收藏·"+ a);
        }
    }

    @Override
    public void setViewUnSelect() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    public View setHeaderFirstView() {
        View view = LayoutInflater.from(this).inflate(R.layout.info_header_first, null);
        recycleBinner = view.findViewById(R.id.recycle);
        tagRecycle = view.findViewById(R.id.tagRecycle);
        tvName = view.findViewById(R.id.tv_info_header_name);
        tvFollow = view.findViewById(R.id.tv_info_header_follow);
        imgIcon = view.findViewById(R.id.img_info_header_icon);
        tvInfo = view.findViewById(R.id.tv_info);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTime = view.findViewById(R.id.tvTime);
        //recycleBinner.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tvFollow.isSelected()) {
                    attention();
                }else{
                    cancelAttention();
                }
            }
        });
        recycleBinner.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        photoAdapter = new PhotoAdapter(this, photos);
        recycleBinner.setAdapter(photoAdapter);

        tagRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tagAdapter = new TagAdapter(tags);
        tagRecycle.setAdapter(tagAdapter);
        return view;
    }

    @OnClick({R.id.tv_zan, R.id.tv_comment, R.id.tv_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_zan:
                type = 0;
                if(!tvZan.isSelected()){
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "0");
                    map.put("aType", "0");
                    map.put("id", albumId);
                    map.put("otherId", otherId);
                    presenter.collection(map);
                }
                break;
            case R.id.tv_comment:
                break;
            case R.id.tv_collect:
                type = 1;
                if(!tvCollect.isSelected()){
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "0");
                    map.put("aType", "1");
                    map.put("id", albumId);
                    map.put("otherId", otherId);
                    presenter.collection(map);
                }
                break;
        }
    }

    public View setHeaderCommentView(){
        View view = LayoutInflater.from(this).inflate(R.layout.info_header_comment, null);
        commentRecycle = view.findViewById(R.id.recycle_comments);
        tvCommentNumber = view.findViewById(R.id.tv_comment_number);
        imgComment = view.findViewById(R.id.img_comment);
        TextView tvvv = view.findViewById(R.id.tvvv);
        commentRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        commentAdapter = new CommentAdapter(this, comments);
        commentRecycle.setAdapter(commentAdapter);
        tvvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AlbumInfoActivity.this, AddCommentActivity.class).putExtra("info", info));
            }
        });
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new HashMap<>();
        map.put("pager", 0+"");
        map.put("number", 10+"");
        presenter.getAlbum(map);
        presenter.getALbumInfo(albumId);
    }
}
