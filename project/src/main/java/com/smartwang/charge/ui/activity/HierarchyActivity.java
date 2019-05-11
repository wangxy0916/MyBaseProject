//package com.smartwang.charge.ui.activity;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.smartwang.charge.base.BaseActivity;
//import com.smartwang.charge.entity.EmptyBean;
//import com.smartwang.charge.entity.HierarchyBean;
//import com.smartwang.charge.ui.adapter.QuickAdapter;
//import com.smartwang.charge.widget.GridSectionAverageGapItemDecoration;
//import com.smartwang.project.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class HierarchyActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
//
//    private List<HierarchyBean.DataBeanX> alldata = new ArrayList<>();
//    private List<HierarchyBean.DataBeanX> data = new ArrayList<>();
//    private QuickAdapter mQuickAdapter;
//
//    private View notDataView;
//    private View errorView;
//
//
//    @BindView(R.id.rv_recycler)
//    RecyclerView rvRecycler;
//    @BindView(R.id.srl_recycler)
//    SmartRefreshLayout srlRecycler;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recycler);
//        ButterKnife.bind(this);
//
//        initView();
//        initAdapter();
//        initData(2);
//
//    }
//
//
//
//    private void initView() {
//        srlRecycler.setOnRefreshListener(this);
//        srlRecycler.setOnLoadMoreListener(this);
//        //当我们确定Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)
//        // ，并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()
//        rvRecycler.setLayoutManager(new LinearLayoutManager(this));
//        rvRecycler.addItemDecoration(new GridSectionAverageGapItemDecoration(50, 20, 20, 20));
//
//
//
//        notDataView = getLayoutInflater().inflate(R.layout.state_empty_view, (ViewGroup) rvRecycler.getParent(), false);
//        notDataView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initData(4);
//            }
//        });
//        errorView = getLayoutInflater().inflate(R.layout.state_error_view, (ViewGroup) rvRecycler.getParent(), false);
//        errorView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initData(4);
//            }
//        });
//
//    }
//
//    private void initAdapter() {
//        mQuickAdapter = new QuickAdapter(R.layout.item_quick, alldata);
//        rvRecycler.setAdapter(mQuickAdapter);
//    }
//    private void initData(int num) {
//        mQuickAdapter.setEmptyView(R.layout.state_loading_view, (ViewGroup) rvRecycler.getParent());
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (num == 0){
//                    alldata.clear();
//                }
//                data.clear();
//                for (int i = 0; i < num; i++) {
//                    EmptyBean emptyBean = new EmptyBean();
//                    emptyBean.setName("我是：" + i);
//                    data.add(emptyBean);
//                }
//                mQuickAdapter.addData(alldata.size(), data);
//
//                srlRecycler.finishRefresh();//结束刷新
//                srlRecycler.finishLoadMore();//结束加载
//
//
//                if (alldata.size() == 0){
//                    mQuickAdapter.setEmptyView(notDataView);
////                    mQuickAdapter.setEmptyView(errorView);
//                }
//            }
//        }, 1000);
//    }
//
//
//    @Override
//    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//        initData(3);
//    }
//
//    @Override
//    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//        initData(0);
//    }
//
//
//}
