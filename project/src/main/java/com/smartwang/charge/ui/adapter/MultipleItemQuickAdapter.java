package com.smartwang.charge.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smartwang.charge.entity.MultipleItem;
import com.smartwang.project.R;

import java.util.List;

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(Context context, List data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.item_text_view);
        addItemType(MultipleItem.IMG, R.layout.item_image_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                helper.setText(R.id.tv33, item.getContent());
                break;
            case MultipleItem.IMG:
                helper.setImageResource(R.id.iv, R.mipmap.ic_banner_default);
                break;
            default:
                break;
        }
    }

}
