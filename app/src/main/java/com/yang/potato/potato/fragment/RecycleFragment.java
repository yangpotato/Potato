package com.yang.potato.potato.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yang.potato.potato.R;
import com.yang.potato.potato.activitys.AlbumInfoActivity;
import com.yang.potato.potato.adapter.HomeAdapter;
import com.yang.potato.potato.adapter.HomeTagAdapter;
import com.yang.potato.potato.contract.HomeContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.Tag;
import com.yang.potato.potato.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecycleFragment extends Fragment implements HomeContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycle)
    RecyclerView recycle;
    Unbinder unbinder;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private HomePresenter presenter;

    private HomeTagAdapter tagAdapter;
    private HomeAdapter adapter;
    private List<Tag> tags = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();

    private Integer pager = 0, number = 3;


    public static RecycleFragment newInstance(String param1, String param2) {
        RecycleFragment fragment = new RecycleFragment();
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
        View view = inflater.inflate(R.layout.fragment_recycle, container, false);
        presenter = new HomePresenter(this);
        unbinder = ButterKnife.bind(this, view);
        Map<String, String> map = new HashMap<>();
        if(!"0".equals(mParam1)) {
            map.put("tagId", mParam1);
            //Toast.makeText(getActivity(), "456", Toast.LENGTH_SHORT).show();
        }
        map.put("pager", pager+"");
        map.put("number", number+"");
        presenter.getAlbum(map);
        initView();
        return view;
    }

    private void initView() {
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setRefreshing(true);

        adapter = new HomeAdapter(getActivity(), albums);
        recycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycle.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LoadMore();
            }
        });

        initRefreshLayout();

    }

    private void LoadMore() {
        Map<String, String> map = new HashMap<>();
        if(!"0".equals(mParam1)) {
            map.put("tagId", mParam1);
        }
        pager = pager +1;
        map.put("pager", pager +"");
        map.put("number", number+"");
        presenter.loadAlbum(map);


    }

    private void initRefreshLayout() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        pager = 0;
        adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        Map<String, String> map = new HashMap<>();
        if(!"0".equals(mParam1)) {
            map.put("tagId", mParam1);
        }
        map.put("pager", pager+"");
        map.put("number", number+"");
        presenter.getAlbum(map);
        //成功加载后
        adapter.setEnableLoadMore(true);
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void setTabLayout(List<Tag> tag) {

    }

    @Override
    public void setAdapter(final List<Album> albums) {
        adapter.setEnableLoadMore(true);
        swipeLayout.setRefreshing(false);

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

    @Override
    public void loadAlbum(List<Album> loadAlbums) {
        adapter.addData(loadAlbums);
        adapter.notifyDataSetChanged();

        adapter.setEnableLoadMore(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
