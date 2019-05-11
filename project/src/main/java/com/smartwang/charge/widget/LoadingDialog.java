package com.smartwang.charge.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.smartwang.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoadingDialog extends Dialog {
    @BindView(R.id.sk_loading)
    SpinKitView skLoading;
    @BindView(R.id.tv_loading_msg)
    TextView tvLoadingMsg;
    @BindView(R.id.rl_loading_bg)
    RelativeLayout rlLoadingBg;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.loading_layout);
        ButterKnife.bind(this);
    }


    /**
     * 自定义主题及布局的构造方法 * @param context * @param theme// 去除顶部蓝色线条
     */
    /**
     * 为加载进度个对话框设置不同的提示消息 * * @param message 给用户展示的提示信息 * @return build模式设计，可以链式调用
     */
    public LoadingDialog setMessage(String message) {
        tvLoadingMsg.setText(message);
        return this;
    }

    /*** * 设置loading背景色 * @param Colorbg * @return */
    public LoadingDialog setLoadingBg(int Colorbg) {
        rlLoadingBg.setBackgroundColor(Colorbg);
        return this;
    }

}


