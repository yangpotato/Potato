package com.yang.potato.potato.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.potato.potato.R;

import butterknife.ButterKnife;

/**
 * Created by potato on 2018/1/16.
 */

public abstract  class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTitle, tvRight;
    private ImageView imgRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        if(isFullScreen()){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        if(setStatusBarTransparent()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else{
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }

        if(setNavigationBarTran()){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tv_title);
        tvRight = findViewById(R.id.tv_right);
        imgRight = findViewById(R.id.img_right);

        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            tvTitle.setPaintFlags(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
//            tvTitle.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
//            tvTitle.getPaint().setFakeBoldText(true);
        }
        if(isShowBack() && toolbar != null){
            showBack();
        }
        initData();
        initView();
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();

    public boolean isFullScreen(){
        return false;
    }

    public boolean setStatusBarTransparent(){
        return false;
    }

    public boolean setNavigationBarTran(){
        return  false;
    }

    protected void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
    protected void startToActivity(Class<?> cls){
        startActivity(new Intent(this, cls));
    }

    protected void startToActivity(Class<?> cls, Bundle bundle){
        startActivity(new Intent(this, cls).putExtra("bundle", bundle));
    }
    protected void startToActivity(Class<?> cls, String str){
        startActivity(new Intent(this, cls).putExtra("string", str));
    }

    protected boolean isShowBack(){
        return true;
    }

    protected boolean isTransparent(){
        return false;
    }

    private void showBack(){
        toolbar.setNavigationIcon(R.mipmap.nav_back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public TextView getTitleTextView(){
        return tvTitle;
    }

    public TextView getRightTextView(){
        return tvRight;
    }

    public ImageView getRightImageView(){
        return imgRight;
    }

    public void setToolTitle(CharSequence  title){
        tvTitle.setText(title);
        //tvTitle.setText(new Spanny().append(title,new BoldSpan()));
    }

    public void setToolRight(CharSequence right){
        tvRight.setText(right);
    }

    public void setToolRightImg(int resId){
        imgRight.setImageResource(resId);
    }

}
