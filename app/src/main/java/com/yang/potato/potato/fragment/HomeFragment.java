package com.yang.potato.potato.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yang.potato.potato.R;
import com.yang.potato.potato.adapter.HomeAdapter;
import com.yang.potato.potato.adapter.HomeTagAdapter;
import com.yang.potato.potato.contract.HomeContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.Tag;
import com.yang.potato.potato.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends Fragment implements HomeContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    Unbinder unbinder;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private HomeTagAdapter tagAdapter;
    private HomeAdapter adapter;
    private List<Tag> tags = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();

    private HomePresenter presenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new HomePresenter(this);
        initView();
        return view;
    }

    private void initView() {
        adapter = new HomeAdapter(getActivity(), albums);
//        recycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recycle.setAdapter(adapter);
        presenter.getTag();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setTabLayout(List<Tag> tag) {
        tag.add(0, new Tag("0", "", "推荐"));
        tagAdapter = new HomeTagAdapter(getChildFragmentManager(), tag);
        viewpager.setAdapter(tagAdapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public void setAdapter(List<Album> album) {
//        adapter.setNewData(album);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void showInfo(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadAlbum(List<Album> loadAlbums) {

    }
}
