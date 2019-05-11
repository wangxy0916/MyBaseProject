package com.smartwang.charge.ui.module_splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.smartwang.charge.base.BaseActivity;
import com.smartwang.charge.ui.activity.MainActivity;
import com.smartwang.charge.ui.module_login.LoginActivity;
import com.smartwang.charge.utils.SharedPreferencesUtil;
import com.smartwang.project.R;
import com.smartwang.charge.base.BaseApplication;
import com.smartwang.charge.config.Constant;
import com.smartwang.charge.impl.OnPermissionResultListener;
import com.smartwang.charge.utils.ActivityUtils;
import com.smartwang.charge.utils.CountDownUtil;
import com.smartwang.charge.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity implements OnPermissionResultListener, CountDownUtil.OnCountEvent {

    private final int ISFIRST = 0;
    private final int NOFIRST = 1;

    private boolean isFirst = true;
    private boolean isLogin = false;

    @BindView(R.id.tv_splash_count)
    TextView tvSplashCount;

    private CountDownUtil mCountUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
    }

    private void initView() {
        getRelativeNavbar().setVisibility(View.GONE);
        mCountUtil = new CountDownUtil(2L, this);
        isFirst = SharedPreferencesUtil.getBoolean(Constant.SHARE_ISFIRST, true);
        isLogin = SharedPreferencesUtil.getBoolean(Constant.SHARE_ISLOGIN, false);

        requestPermissions(this, Constant.PERMISSIONS_BASE_CODE, Constant.PERMISSIONS_BASE_RATIONALE, Constant.PERMISSIONS_BASE);
    }

    private void toMain() {
        if (isLogin) {
            startActivity(MainActivity.class);
        } else {
            startActivity(LoginActivity.class);
        }
        finish();
    }

    private void toGuide() {
        startActivity(GuideActivity.class);
//        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void succeed(int code) {
        ToastUtil.showShort("Splash权限获取成功");
        mCountUtil.startTimer();
    }

    @Override
    public void failed(int code) {
        ActivityUtils.AppExit(BaseApplication.getApplication());
        ToastUtil.showShort("Splash权限获取失败");
    }

    @OnClick(R.id.tv_splash_count)
    public void onClick() {
        mCountUtil.finishTimer();
    }

    @Override
    public void onTimerFinish() {
        if (isFirst) {
            toGuide();
        } else {
            toMain();
        }
    }

    @Override
    public void onTickEvent(long millisUntilFinished) {
        tvSplashCount.setText(millisUntilFinished / 1000 + "s");
    }
}
