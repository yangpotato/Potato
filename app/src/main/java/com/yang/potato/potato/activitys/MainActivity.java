package com.yang.potato.potato.activitys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yang.potato.potato.R;
import com.yang.potato.potato.base.BaseActivity;
import com.yang.potato.potato.fragment.MainFragment;
import com.yang.potato.potato.fragment.MessageFragment;
import com.yang.potato.potato.fragment.MineFragment;
import com.yang.potato.potato.fragment.VideoFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment)
    FrameLayout fragment;
    @BindView(R.id.rbtn_main_index)
    RadioButton rbtnMainIndex;
    @BindView(R.id.rbtn_main_shop)
    RadioButton rbtnMainShop;
    @BindView(R.id.btn_main_add)
    Button btnMainAdd;
    @BindView(R.id.rbtn_main_msg)
    RadioButton rbtnMainMsg;
    @BindView(R.id.rbtn_main_me)
    RadioButton rbtnMainMe;
    @BindView(R.id.radio)
    RadioGroup radio;

    private Fragment mFragment, mainFragment, videoFragment, messageFragment, mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, mainFragment).commit();
        mFragment = mainFragment;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1002);
        }else{
            //申请成功将执行的操作
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 1002:
                if(grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //申请成功将执行的操作
                }else{
                    Toast.makeText(this, "需要申请存储权限", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void initData() {
        mainFragment = new MainFragment();
        videoFragment = new VideoFragment();
        messageFragment = new MessageFragment();
        mineFragment = new MineFragment();
    }

    @OnClick({R.id.rbtn_main_index, R.id.rbtn_main_shop, R.id.btn_main_add, R.id.rbtn_main_msg, R.id.rbtn_main_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbtn_main_index:
                showFragmnet(mainFragment);
                break;
            case R.id.rbtn_main_shop:
                showFragmnet(videoFragment);
                break;
            case R.id.btn_main_add:
                startToActivity(SelectActivity.class);
                break;
            case R.id.rbtn_main_msg:
                showFragmnet(messageFragment);
                break;
            case R.id.rbtn_main_me:
                showFragmnet(mineFragment);
                break;
        }
    }

    private void showFragmnet(Fragment fragment){
        if (mFragment != fragment){
            if(!fragment.isAdded()){
                getSupportFragmentManager().beginTransaction().hide(mFragment).add(R.id.fragment, fragment).commit();
            }else{
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
        }
        mFragment = fragment;
    }

}
