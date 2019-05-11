package com.smartwang.charge.ui.module_home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fastgo.sydialoglib.IDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.smartwang.charge.base.BaseFragment;
import com.smartwang.charge.config.Constant;
import com.smartwang.charge.entity.HomeBean;
import com.smartwang.charge.http.JsonCallback;
import com.smartwang.charge.http.MyResponse;
import com.smartwang.charge.impl.OnPermissionResultListener;
import com.smartwang.charge.ui.activity.HtmlActivity;
import com.smartwang.charge.ui.activity.ItemActivity;
import com.smartwang.charge.ui.activity.RecyclerActivity;
import com.smartwang.charge.ui.module_dialog.activity.DialogActivity;
import com.smartwang.charge.utils.ToastUtil;
import com.smartwang.project.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment implements OnPermissionResultListener {

    @BindView(R.id.ed1)
    EditText ed1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.btn10)
    Button btn10;
    @BindView(R.id.btn12)
    Button btn12;

    Unbinder unbinder;
    @BindView(R.id.btn_home_dialog)
    Button btnHomeDialog;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.tv2)
    TextView tv2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initData();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initData() {
        requestPermissions(this, Constant.PERMISSIONS_BASE_CODE, Constant.PERMISSIONS_BASE_RATIONALE, Constant.PERMISSIONS_BASE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @OnClick(R.id.btn)
//    public void onClick() {
////        这是请求权限的写法
//
////
////
//
//    }

    @Override
    public void succeed(int code) {
        ToastUtil.showShort("Home权限获取成功");
    }

    @Override
    public void failed(int code) {
        ToastUtil.showShort("Home权限获取失败");
    }

    @OnClick({R.id.btn_home_dialog, R.id.btn, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn12})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_home_dialog:
                startActivity(DialogActivity.class);
                break;
            case R.id.btn:
                /*这是http请求的用法*/
                OkGo.<MyResponse<HomeBean>>post("http://kf.outoken.com/blog/api/about")
                        .isSpliceUrl(true).tag(this).execute(new JsonCallback<MyResponse<HomeBean>>(HomeBean.class, getActivity()) {
                    @Override
                    public void onSuccess(Response<MyResponse<HomeBean>> response) {
                        ToastUtil.showShort("网络请求成功");
                    }

                    @Override
                    public void onError(Response<MyResponse<HomeBean>> response) {
                        super.onError(response);
                    }
                });
                break;
            case R.id.btn6:
                requestPermissions(this, Constant.PERMISSIONS_CALLPHONE_CODE, Constant.PERMISSIONS_CALLPHONE_RATIONALE, Constant.PERMISSIONS_CALLPHONE);
                break;
            case R.id.btn7:
                startActivity(RecyclerActivity.class);
                break;
            case R.id.btn8:
                startActivity(ItemActivity.class);
                break;
            case R.id.btn9:
                break;
            case R.id.btn10:
                /*这是打开webview的方法*/
                Bundle bundle = new Bundle();
                bundle.putString(HtmlActivity.PARAM_URL, "https://www.baidu.com/");
                bundle.putString(HtmlActivity.PARAM_TITLE, "标题");
                bundle.putInt(HtmlActivity.PARAM_MODE, 1);
                startActivity(HtmlActivity.class, bundle);
                break;
            case R.id.btn12:
                break;
        }
    }
}
