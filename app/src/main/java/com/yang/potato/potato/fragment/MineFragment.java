package com.yang.potato.potato.fragment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yang.potato.potato.R;
import com.yang.potato.potato.activitys.SettingActivity;
import com.yang.potato.potato.activitys.SettingInfoActivity;
import com.yang.potato.potato.adapter.MineFragmentAdapter;
import com.yang.potato.potato.back.BackLoginActivity;
import com.yang.potato.potato.utils.ImageLoder;
import com.yang.potato.potato.utils.SPUtils;
import com.yang.potato.potato.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MineFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_userinfo_setting)
    ImageView imgUserinfoSetting;
    @BindView(R.id.img_userinfo_share)
    ImageView imgUserinfoShare;
    @BindView(R.id.img_userinfo_icon)
    RoundedImageView imgUserinfoIcon;
    @BindView(R.id.tv_userinfo_name)
    TextView tvUserinfoName;
    @BindView(R.id.linn)
    LinearLayout linn;
    @BindView(R.id.tv_userinfo_follow_number)
    TextView tvUserinfoFollowNumber;
    @BindView(R.id.tv_userinfo_fans_number)
    TextView tvUserinfoFansNumber;
    @BindView(R.id.tv_userinfo_follow)
    TextView tvUserinfoFollow;
    @BindView(R.id.tv_userinfo_info)
    TextView tvUserinfoInfo;
    @BindView(R.id.lin_show)
    LinearLayout linShow;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tab_userinfo)
    TabLayout tabUserinfo;
    @BindView(R.id.viewpager_mine)
    ViewPager viewpagerMine;
    @BindView(R.id.lin)
    LinearLayout lin;
    @BindView(R.id.linAll)
    CoordinatorLayout linAll;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    private MineFragmentAdapter adapter;

    private List<String> titles;
    private boolean isStart = true;


    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add("相册");
        titles.add("视频");
    }

    private void initView() {
        adapter = new MineFragmentAdapter(getChildFragmentManager(), titles);
        viewpagerMine.setAdapter(adapter);
        tabUserinfo.setupWithViewPager(viewpagerMine);

        setSlide();

        ImageLoder.loadImg(getActivity(), (String) SPUtils.get(getActivity(), "headImg", ""), imgUserinfoIcon);
        tvUserinfoName.setText((String) SPUtils.get(getActivity(), "nickName", ""));
        if (!TextUtils.isEmpty((String) SPUtils.get(getActivity(), "background", ""))) {
            tvUserinfoInfo.setText((String) SPUtils.get(getActivity(), "background", ""));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 根据滑动来计算tabLayout的上间距
     * 使用Value动画
     */
    private void setSlide() {
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("ygy", "-->" + verticalOffset);
                if (verticalOffset < -Utils.dp2px(getActivity(), 245)) {
                    if (isStart) {
                        isStart = false;
                        ValueAnimator animator = ValueAnimator.ofInt(0, Utils.getStatusBarHeight(getActivity()));
                        animator.setDuration(100);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int curValue = (int) valueAnimator.getAnimatedValue();
                                Log.d("ygy", "===>" + curValue);
                                lin.setPadding(0, curValue, 0, 0);
                            }
                        });
                        animator.start();
                    }
                } else {
                    if (!isStart) {
                        isStart = true;
                        ValueAnimator animator = ValueAnimator.ofInt(Utils.getStatusBarHeight(getActivity()), 0);
                        animator.setDuration(100);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int curValue = (int) valueAnimator.getAnimatedValue();
                                Log.d("ygy", "===>" + curValue);
                                lin.setPadding(0, curValue, 0, 0);
                            }
                        });
                        animator.start();
                    }
                }
            }
        });
    }

    @OnClick({R.id.img_userinfo_setting, R.id.img_userinfo_share, R.id.tv_userinfo_follow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_userinfo_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.img_userinfo_share:
                startActivity(new Intent(getActivity(), BackLoginActivity.class));
                break;
            case R.id.tv_userinfo_follow:
                startActivity(new Intent(getActivity(), SettingInfoActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tvUserinfoInfo.setText((String) SPUtils.get(getActivity(), "background", ""));
        tvUserinfoName.setText((String) SPUtils.get(getActivity(), "nickName", ""));
        ImageLoder.loadImg(getActivity(), (String) SPUtils.get(getActivity(), "headImg", ""), imgUserinfoIcon);
    }
}
