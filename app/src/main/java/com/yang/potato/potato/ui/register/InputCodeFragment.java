package com.yang.potato.potato.ui.register;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.potato.potato.MyApplication;
import com.yang.potato.potato.R;
import com.yang.potato.potato.utils.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class InputCodeFragment extends Fragment {
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
    @BindView(R.id.tv_login_phone)
    TextView tvLoginPhone;
    @BindView(R.id.btn_login_sure)
    Button btnLoginSure;
    @BindView(R.id.tv_login_bottom)
    TextView tvLoginBottom;
    Unbinder unbinder;
    @BindView(R.id.edtCode)
    EditText edtCode;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String code;

    public static InputCodeFragment newInstance(String param1, String param2) {
        InputCodeFragment fragment = new InputCodeFragment();
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
        View view = inflater.inflate(R.layout.fragment_input_code, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvTitle.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.mipmap.nav_back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        tvLoginPhone.setText("+86 " + mParam2);
        initButtonChange();
        initSoftInput();
        return view;
    }

    private void initButtonChange() {
        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtCode.getText().toString().length() == 4) {
                    btnLoginSure.setSelected(true);
                } else {
                    btnLoginSure.setSelected(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_login_sure, R.id.tv_login_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_sure:
                if(!btnLoginSure.isSelected()){
                    return;
                }
                code = edtCode.getText().toString();
                submitCode("86", mParam2, code);

                break;
            case R.id.tv_login_bottom:
                break;
        }
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
            edtCode.setFocusable(true);
            edtCode.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) edtCode.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(edtCode, 0);
        }
    };

    public void submitCode(String country, String phone, String code1) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    Toast.makeText(MyApplication.getContext(), "验证成功", Toast.LENGTH_SHORT).show();
                    LogUtils.i("验证成功");
                    InputPwdFragment fa1 = InputPwdFragment.newInstance(mParam2, code);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction tr = fm.beginTransaction();
                    tr.replace(R.id.fragment, fa1);
                    tr.addToBackStack(null);
                    tr.commit();
                } else{
                    // TODO 处理错误的结果
                    LogUtils.i("验证失败");
                    LogUtils.i(result +"");

                    Toast.makeText(getActivity(), "验证失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
