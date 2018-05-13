package com.yang.potato.potato.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yang.potato.potato.R;
import com.yang.potato.potato.activitys.AlbumInfoActivity;
import com.yang.potato.potato.adapter.HomeAdapter;
import com.yang.potato.potato.adapter.VideoAdapter;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.MineContract;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.Video;
import com.yang.potato.potato.presenter.MinePresenter;
import com.yang.potato.potato.retrofit.RetrofitManage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineScollFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineScollFragment extends Fragment implements MineContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycle)
    RecyclerView recycle;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MinePresenter presenter;
    private HomeAdapter adapter;
    private List<Album> albums = new ArrayList<>();
    private List<Video> videos = new ArrayList<>();
    private VideoAdapter videoAdapter;


    public MineScollFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineScollFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineScollFragment newInstance(String param1, String param2) {
        MineScollFragment fragment = new MineScollFragment();
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
        View view = inflater.inflate(R.layout.fragment_mine_scoll, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new MinePresenter(this);

        if("0".equals(mParam1)) {
            presenter.getMyAlbum();
        }else{
            request();
        }
        return view;
    }

    private void request(){
        RetrofitManage.getVideo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<Video>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final BaseRequest<List<Video>> listBaseRequest) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videos = listBaseRequest.getData();
                                videoAdapter = new VideoAdapter(getActivity(), videos);
                                recycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recycle.setAdapter(videoAdapter);

                            }
                        });

                    }
                });
    }

    @Override
    public void setAdapter(final List<Album> albums) {

        adapter = new HomeAdapter(getActivity(), albums);
        recycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycle.setAdapter(adapter);

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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
