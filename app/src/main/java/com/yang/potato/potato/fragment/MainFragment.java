package com.yang.potato.potato.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yang.potato.potato.R;
import com.yang.potato.potato.activitys.SearchActivity;
import com.yang.potato.potato.adapter.MainFragmentAdapter;
import com.yang.potato.potato.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tablayout_main)
    TabLayout tablayoutMain;
    @BindView(R.id.lin_search)
    LinearLayout linSearch;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.linAll)
    LinearLayout linAll;
    Unbinder unbinder;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    private String mParam1;
    private String mParam2;

    private List<Fragment> fragments;
    private List<String> titles;
    private MainFragmentAdapter adapter;

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        linAll.setPadding(0, Utils.getStatusBarHeight(getActivity()), 0, 0);
        initData();
        adapter = new MainFragmentAdapter(getChildFragmentManager(), fragments, titles);
        vpMain.setAdapter(adapter);
        tablayoutMain.setupWithViewPager(vpMain);
        vpMain.setCurrentItem(1);
        return view;
    }

    private void initData() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        fragments.add(new FollowFragment());
        fragments.add(new HomeFragment());
        titles.add("关注");
        titles.add("首页");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_search)
    public void onClick() {
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }
}
