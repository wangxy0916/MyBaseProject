package com.smartwang.charge.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smartwang.charge.base.BaseActivity;
import com.smartwang.charge.entity.EmptyBean;
import com.smartwang.charge.entity.MultipleItem;
import com.smartwang.charge.ui.adapter.MultipleItemQuickAdapter;
import com.smartwang.project.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    private List<MultipleItem> alldata = new ArrayList<>();
    private List<MultipleItem> data = new ArrayList<>();
    private MultipleItemQuickAdapter multipleItemAdapter;
    @BindView(R.id.rv_recycler)
    RecyclerView rvRecycler;
    @BindView(R.id.srl_recycler)
    SmartRefreshLayout srlRecycler;

    private View notDataView;
    private View errorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);
        initView();
        initData(2);
    }

    private void initView() {
        multipleItemAdapter = new MultipleItemQuickAdapter(this, alldata);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rvRecycler.setLayoutManager(manager);
        rvRecycler.setAdapter(multipleItemAdapter);
        srlRecycler.setOnRefreshListener(this);
        srlRecycler.setOnLoadMoreListener(this);

        notDataView = getLayoutInflater().inflate(R.layout.state_empty_view, (ViewGroup) rvRecycler.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(2);
            }
        });
        errorView = getLayoutInflater().inflate(R.layout.state_error_view, (ViewGroup) rvRecycler.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(2);
            }
        });
    }

    private void initData(int num) {
        multipleItemAdapter.setEmptyView(R.layout.state_loading_view, (ViewGroup) rvRecycler.getParent());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                data.clear();
                data.add(new MultipleItem(MultipleItem.IMG));
                for (int i = 0; i < num; i++) {
                    data.add(new MultipleItem(MultipleItem.TEXT, "aaaaaa"));
                    data.add(new MultipleItem(MultipleItem.TEXT, "bbbbbb"));
                }
                if (num == 0) {
                    alldata.clear();
                    data.clear();
                }
                multipleItemAdapter.addData(alldata.size(), data);
                srlRecycler.finishRefresh();//结束刷新
                srlRecycler.finishLoadMore();//结束加载

                if (alldata.size() == 0){
                    multipleItemAdapter.setEmptyView(notDataView);
//                    mQuickAdapter.setEmptyView(errorView);
                }
            }
        }, 1000);

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        initData(2);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        initData(0);
    }
}
