package com.smartwang.charge.ui.module_my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smartwang.charge.base.BaseFragment;
import com.smartwang.charge.config.Constant;
import com.smartwang.charge.ui.module_login.LoginActivity;
import com.smartwang.charge.utils.SharedPreferencesUtil;
import com.smartwang.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyFragment extends BaseFragment {
    @BindView(R.id.btn_my_exit)
    Button btnMyExit;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_my_exit)
    public void onViewClicked() {
        SharedPreferencesUtil.putBoolean(Constant.SHARE_ISLOGIN, false);
        startActivity(LoginActivity.class);
        getActivity().finish();
    }
}
