package com.yang.potato.potato.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yang.potato.potato.R;
import com.yang.potato.potato.activitys.AlbumInfoActivity;
import com.yang.potato.potato.adapter.FollowAdapter;
import com.yang.potato.potato.contract.FollowContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.presenter.FollowPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FollowFragment extends Fragment implements FollowContract.View {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    @BindView(R.id.recycle_follow)
    RecyclerView recycleFollow;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    private FollowAdapter adapter;
    private List<Album> albums = new ArrayList<>();
    private FollowPresenter presenter;

    public static FollowFragment newInstance(String param1, String param2) {
        FollowFragment fragment = new FollowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new FollowPresenter(this);
        presenter.getFolloAlbum();
        initAdapter();
       // initRefreshLayout();
        return view;
    }

    private void initAdapter() {

//        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
//        swipeLayout.setRefreshing(true);

        adapter = new FollowAdapter(getActivity(), albums);
        recycleFollow.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleFollow.setAdapter(adapter);
    }
//    private void initRefreshLayout() {
//        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refresh();
//            }
//        });
//    }

//    private void refresh() {
//        //pager = 0;
//        adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
////        Map<String, String> map = new HashMap<>();
////        if(!"0".equals(mParam1)) {
////            map.put("tagId", mParam1);
////        }
////        map.put("pager", pager+"");
////        map.put("number", number+"");
////        presenter.getAlbum(map);
//        presenter.getFolloAlbum();
//        //成功加载后
//        adapter.setEnableLoadMore(true);
//        swipeLayout.setRefreshing(false);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initRecycle(final List<Album> albums) {
        //Toast.makeText(getActivity(), "232", Toast.LENGTH_SHORT).show();
        if (albums.size() == 0) {
            recycleFollow.setVisibility(View.INVISIBLE);
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            recycleFollow.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.INVISIBLE);
        }
        adapter.setNewData(albums);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), AlbumInfoActivity.class)
                        .putExtra("albumId", albums.get(position).getId())
                        .putExtra("otherId", albums.get(position).getUserId()));
            }
        });
    }

    @Override
    public void showInfo(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
