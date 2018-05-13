package com.yang.potato.potato.ui.register;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.potato.potato.R;
import com.yang.potato.potato.activitys.LoginActivity;
import com.yang.potato.potato.contract.RegisterContract;
import com.yang.potato.potato.presenter.RegisterPresenter;
import com.yang.potato.potato.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputPwdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputPwdFragment extends Fragment implements RegisterContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_top)
    TextView tvTop;
    @BindView(R.id.edt_login_pwd)
    EditText edtLoginPwd;
    @BindView(R.id.edt_login_pwd2)
    EditText edtLoginPwd2;
    @BindView(R.id.btn_login_sure)
    Button btnLoginSure;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RegisterPresenter presenter;


    public InputPwdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputPwdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputPwdFragment newInstance(String param1, String param2) {
        InputPwdFragment fragment = new InputPwdFragment();
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
        View view = inflater.inflate(R.layout.fragment_input_pwd, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvTitle.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.mipmap.nav_back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        presenter = new RegisterPresenter(this);
        initSoftInput();
        btnLoginSure.setSelected(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_login_sure)
    public void onClick() {
        String pw = edtLoginPwd.getText().toString();
        String pw2 = edtLoginPwd2.getText().toString();

        if(pw.length() < 6 || pw2.length() < 6){
            Toast.makeText(getActivity(), "请输入正确格式的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pw.equals(pw2)){
            Toast.makeText(getActivity(), "两次密码不匹配", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("code", mParam1);
        map.put("pwd", pw);
        LogUtils.i("code:"+mParam1+"->pwd:"+pw);
        presenter.register(map);
    }


    private void initSoftInput() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        }, 500);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            edtLoginPwd.setFocusable(true);
            edtLoginPwd.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) edtLoginPwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(edtLoginPwd, 0);
        }
    };

    @Override
    public void showInfo(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startToLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("phone", mParam1));
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }
}
