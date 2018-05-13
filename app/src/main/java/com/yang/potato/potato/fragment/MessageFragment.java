package com.yang.potato.potato.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yang.potato.potato.R;
import com.yang.potato.potato.activitys.FollowListActivity;
import com.yang.potato.potato.activitys.SelectZanByUserIdsActivity;
import com.yang.potato.potato.activitys.getCommentActivity;
import com.yang.potato.potato.entity.GetComment;
import com.yang.potato.potato.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.linAll)
    LinearLayout linAll;
    Unbinder unbinder;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.lin_fmessage_zan)
    RelativeLayout linFmessageZan;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.lin_fmessage_comment)
    RelativeLayout linFmessageComment;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.lin_fmessage_follow)
    RelativeLayout linFmessageFollow;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.lin_fmessage_notice)
    RelativeLayout linFmessageNotice;
    @BindView(R.id.img5)
    ImageView img5;
    @BindView(R.id.lin_fmessage_letter)
    RelativeLayout linFmessageLetter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        linAll.setPadding(0, Utils.getStatusBarHeight(getActivity()), 0, 0);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.lin_fmessage_zan, R.id.lin_fmessage_comment, R.id.lin_fmessage_follow, R.id.lin_fmessage_notice, R.id.lin_fmessage_letter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_fmessage_zan:
                startActivity(new Intent(getActivity(), SelectZanByUserIdsActivity.class));
                break;
            case R.id.lin_fmessage_comment:
                startActivity(new Intent(getActivity(), getCommentActivity.class));
                break;
            case R.id.lin_fmessage_follow:
                startActivity(new Intent(getActivity(), FollowListActivity.class));
                break;
            case R.id.lin_fmessage_notice:
                break;
            case R.id.lin_fmessage_letter:
                break;
        }
    }
}
