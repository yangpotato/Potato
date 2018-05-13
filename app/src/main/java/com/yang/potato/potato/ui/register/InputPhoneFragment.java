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
import com.yang.potato.potato.utils.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputPhoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputPhoneFragment extends Fragment {
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
    @BindView(R.id.edt_login_phone)
    EditText edtLoginPhone;
    @BindView(R.id.btn_login_sure)
    Button btnLoginSure;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String phone;


    public InputPhoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputPhoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputPhoneFragment newInstance(String param1, String param2) {
        InputPhoneFragment fragment = new InputPhoneFragment();
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
        View view = inflater.inflate(R.layout.fragment_input_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvTitle.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.mipmap.nav_back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        initSoftInput();
        initEditTextButtonChange();
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //根据输入框字数来判断按钮的颜色
    private void initEditTextButtonChange() {
        edtLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtLoginPhone.getText().toString().length() == 11) {
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
            edtLoginPhone.setFocusable(true);
            edtLoginPhone.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) edtLoginPhone.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(edtLoginPhone, 0);
        }
    };

    @OnClick(R.id.btn_login_sure)
    public void onClick() {
        if(!btnLoginSure.isSelected()){
            return;
        }
        phone = edtLoginPhone.getText().toString();

        sendCode("86", phone);
        InputCodeFragment fa = InputCodeFragment.newInstance(mParam1, phone);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(R.id.fragment, fa);
        tr.addToBackStack(null);
        tr.commit();

    }

    public void sendCode(String country, String phone1) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    LogUtils.i("成功");
                    Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();


                } else{
                    // TODO 处理错误的结果
                    Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
