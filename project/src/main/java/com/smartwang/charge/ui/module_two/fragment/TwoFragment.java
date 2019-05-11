package com.smartwang.charge.ui.module_two.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.smartwang.charge.base.BaseFragment;
import com.smartwang.charge.utils.ToastUtil;
import com.smartwang.project.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TwoFragment extends BaseFragment {

    @BindView(R.id.btn_two_1)
    Button btnTwo1;
    @BindView(R.id.btn_two_2)
    Button btnTwo2;
    Unbinder unbinder;
    @BindView(R.id.btn_two_3)
    Button btnTwo3;
    @BindView(R.id.btn_two_4)
    Button btnTwo4;
    @BindView(R.id.btn_two_5)
    Button btnTwo5;
    @BindView(R.id.btn_two_6)
    Button btnTwo6;
    @BindView(R.id.btn_two_7)
    Button btnTwo7;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        initView();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        List<String> list = new ArrayList<>();
        list.add("https://pic1.zhimg.com/80/b70efa8338df4ade519363d639256898_hd.jpg");
        list.add("https://pic1.zhimg.com/v2-2bfa84b56d21d07e777104fc1a7e453b_1200x500.jpg");
        list.add("https://pic3.zhimg.com/80/v2-83dfed5f0f33f14f8cb5ea64ca75ed91_hd.jpg");


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_two_1, R.id.btn_two_2,R.id.btn_two_3, R.id.btn_two_4,R.id.btn_two_5, R.id.btn_two_6,R.id.btn_two_7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_two_1:
                new XPopup.Builder(getContext())
//                         .dismissOnTouchOutside(false)
                        // 设置弹窗显示和隐藏的回调监听
//                         .autoDismiss(false)
                        .setPopupCallback(new XPopupCallback() {
                            @Override
                            public void onShow() {
                                Log.e("tag", "onShow");
                            }

                            @Override
                            public void onDismiss() {
                                Log.e("tag", "onDismiss");
                            }
                        }).asConfirm("我是标题", "床前明月光，疑是地上霜；举头望明月，低头思故乡。",
                        "取消", "确定",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                ToastUtil.showShort("点击确定");
                            }
                        }, null, true)
                        .show();
                break;
            case R.id.btn_two_2:
                new XPopup.Builder(getContext())
                        //.dismissOnBackPressed(false)
                        .autoOpenSoftInput(true)
                        //.moveUpToKeyboard(false) //是否移动到软键盘上面，默认为true
                        .asInputConfirm("我是标题", "请输入内容。", "我是默认Hint文字",
                                new OnInputConfirmListener() {
                                    @Override
                                    public void onConfirm(String text) {
                                        ToastUtil.showShort("点击确定" + text);
//                                new XPopup.Builder(getContext()).asLoading().show();
                                    }
                                })
                        .show();
                break;
            case R.id.btn_two_3:
                new XPopup.Builder(getContext())
//                        .maxWidth(600)
                        .asCenterList("请选择一项", new String[]{"条目a", "条目b", "条目c", "条目d"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        ToastUtil.showShort("点击" + position+text);
                                    }
                                })
                        .show();
                    break;
            case R.id.btn_two_4:
                    break;
            case R.id.btn_two_5:
                    break;
            case R.id.btn_two_6:
                    break;
            case R.id.btn_two_7:
                    break;
        }
    }
}
