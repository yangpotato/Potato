package com.yang.potato.potato.presenter;

import android.util.Log;

import com.yang.potato.potato.base.BaseCallBack;
import com.yang.potato.potato.base.BaseRequest;
import com.yang.potato.potato.contract.BasePresenter;
import com.yang.potato.potato.contract.RegisterContract;
import com.yang.potato.potato.model.RegisterModel;
import com.yang.potato.potato.utils.LogUtils;

import java.util.Map;

/**
 * Created by potato on 2018/1/25.
 */

public class RegisterPresenter extends BasePresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private RegisterContract.Model model;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        model = new RegisterModel();
    }

    @Override
    public void register(Map<String, String> map) {
        addSubscription(model.register(map, new BaseCallBack<BaseRequest>() {
            @Override
            public void onSuccess(BaseRequest baseRequest) {
                LogUtils.i("success");
                if("2".equals(baseRequest.getStatus())){
                    view.showInfo("该用户已被注册");
                }
                if(baseRequest.isOk()){
                    view.showInfo("注册成功");
                    view.startToLogin();
                    view.finish();
                }
            }

            @Override
            public void OnFailed(String error) {
                view.showInfo("连接失败");
            }
        }));
    }
}
