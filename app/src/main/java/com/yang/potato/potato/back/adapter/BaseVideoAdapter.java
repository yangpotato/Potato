package com.yang.potato.potato.back.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.Video;

import java.util.List;

/**
 * Created by potato on 2018/5/4.
 */

public class BaseVideoAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {

    public BaseVideoAdapter(@Nullable List<Video> data) {
        super(R.layout.back_item_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {
        helper.setText(R.id.tv_id, item.getId());
        helper.setText(R.id.tv_nickname, item.getTitle());
//        helper.setText(R.id.tv_time, item.getStatus().toString());

        if("0".equals(item.getStatus().toString())){
            helper.setText(R.id.tv_time, "正常");
        }else{
            helper.setText(R.id.tv_time, "已删除");
        }
    }
}
