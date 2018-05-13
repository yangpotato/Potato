package com.yang.potato.potato.back.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.User;
import com.yang.potato.potato.utils.Utils;

import java.util.List;

/**
 * Created by potato on 2018/5/4.
 */

public class BackUserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    public BackUserAdapter(@Nullable List<User> data) {
        super(R.layout.back_item_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        helper.setText(R.id.tv_id, item.getUserId());
        helper.setText(R.id.tv_nickname, item.getNickName());
        //helper.setText(R.id.tv_time, Utils.getTime(item.getRegTime(), "MM-dd"));
        if("0".equals(item.getStatus())){
            helper.setText(R.id.tv_time, "正常");
        }else{
            helper.setText(R.id.tv_time, "已注销");
        }
    }
}
