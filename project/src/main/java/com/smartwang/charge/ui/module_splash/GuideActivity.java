package com.smartwang.charge.ui.module_splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.smartwang.charge.base.BaseActivity;
import com.smartwang.charge.ui.module_login.LoginActivity;
import com.smartwang.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity {


    @BindView(R.id.btn_guide_into)
    Button btnGuideInto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_guide_into)
    public void onViewClicked() {
        startActivity(LoginActivity.class);
        finish();
    }
}
