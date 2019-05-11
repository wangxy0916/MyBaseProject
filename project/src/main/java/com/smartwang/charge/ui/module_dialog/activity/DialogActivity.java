package com.smartwang.charge.ui.module_dialog.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.smartwang.charge.base.BaseActivity;
import com.smartwang.charge.utils.ToastUtil;
import com.smartwang.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends BaseActivity {


    @BindView(R.id.btn_dialog_1)
    Button btnDialog1;
    @BindView(R.id.btn_dialog_2)
    Button btnDialog2;
    @BindView(R.id.btn_dialog_3)
    Button btnDialog3;
    @BindView(R.id.btn_dialog_4)
    Button btnDialog4;
    @BindView(R.id.btn_dialog_5)
    Button btnDialog5;
    @BindView(R.id.btn_dialog_6)
    Button btnDialog6;
    @BindView(R.id.btn_dialog_7)
    Button btnDialog7;
    @BindView(R.id.btn_dialog_8)
    Button btnDialog8;
    @BindView(R.id.btn_dialog_9)
    Button btnDialog9;
    @BindView(R.id.btn_dialog_10)
    Button btnDialog10;
    @BindView(R.id.btn_dialog_11)
    Button btnDialog11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setCentreTv("Dialog测试");
    }

    @OnClick({R.id.btn_dialog_1, R.id.btn_dialog_2, R.id.btn_dialog_3, R.id.btn_dialog_4, R.id.btn_dialog_5, R.id.btn_dialog_6, R.id.btn_dialog_7, R.id.btn_dialog_8, R.id.btn_dialog_9,R.id.btn_dialog_10, R.id.btn_dialog_11})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_1:
                new XPopup.Builder(this)
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
                        }, null, false)
                        .show();
                break;
            case R.id.btn_dialog_2:
                new XPopup.Builder(this)
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
            case R.id.btn_dialog_3:
                new XPopup.Builder(this)
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
            case R.id.btn_dialog_4:
                new XPopup.Builder(this)
//                        .maxWidth(600)
                        .asCenterList("请选择一项", new String[]{"条目a", "条目b", "条目c", "条目d"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        ToastUtil.showShort("点击" + position + text);
                                    }
                                })
                        .show();
                break;
            case R.id.btn_dialog_5:
                new XPopup.Builder(this)
//                        .maxWidth(600)
                        .asCenterList("请选择一项", new String[]{"条目a", "条目b", "条目c", "条目d"},
                                new int[]{R.mipmap.ic_home_select, R.mipmap.ic_home_unselect, R.mipmap.ic_two_select, R.mipmap.ic_two_unselect},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        ToastUtil.showShort("点击" + position + text);
                                    }
                                })
                        .show();
                break;
            case R.id.btn_dialog_6:
                break;
            case R.id.btn_dialog_7:
                break;
            case R.id.btn_dialog_8:
                break;
            case R.id.btn_dialog_9:
                break;
            case R.id.btn_dialog_10:
                break;
            case R.id.btn_dialog_11:
                break;
        }
    }

}
