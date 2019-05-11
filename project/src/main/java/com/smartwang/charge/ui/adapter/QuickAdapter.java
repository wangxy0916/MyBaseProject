package com.smartwang.charge.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smartwang.charge.entity.EmptyBean;
import com.smartwang.project.R;

import java.util.List;

public class QuickAdapter extends BaseQuickAdapter<EmptyBean, BaseViewHolder> {



    public QuickAdapter(int layoutResId, @Nullable List<EmptyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmptyBean item) {
        helper.setText(R.id.tv_quick_item, item.getName());
    }
}
