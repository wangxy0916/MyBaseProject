package com.smartwang.charge.base;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.noober.background.BackgroundLibrary;
import com.orhanobut.logger.Logger;
import com.smartwang.project.R;
import com.smartwang.charge.config.Constant;
import com.smartwang.charge.impl.OnPermissionResultListener;
import com.smartwang.charge.utils.ActivityUtils;
import com.smartwang.charge.widget.Navbar;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
//

public class BaseActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private long lastClickTime = 0;

    public Navbar navbarBase;
    public View topView;
    public FrameLayout flContentBase;

    public RelativeLayout mNavbarLeftRl;//左侧父
    public RelativeLayout mNavbarRightRl;//右侧父
    public RelativeLayout mNavbarCentreRl;//中间父
    public TextView mNavbarLeftTv;//左侧文本
    public TextView mNavbarRightTv;//右侧文本
    public ImageView mNavbarRightIv;//右侧图片
    public TextView mCentreTv;//中间文本
    public ImageView mCentreIv;//中间图片
    public View lineBarView;//Navbar底部分割线

    private String[] permss;//当前请求的权限
    private int permCode;//当前请求的权限Code
    private OnPermissionResultListener mPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        Logger.v("创建:" + getClass().getSimpleName());
        ActivityUtils.addActivity(this);
        initImmersionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        //设置填充activity_base布局
        super.setContentView(view);
        initView();
        //加载子类Activity的布局
        setContentChildView(layoutResID);
        initTitleView();
    }

    private void initView() {
        navbarBase = (Navbar) findViewById(R.id.navbar_base);
        topView = (View) findViewById(R.id.top_view);
        flContentBase = (FrameLayout) findViewById(R.id.fl_content_base);
    }

    /**
     * 加载子内容布局
     *
     * @param LayoutResID
     */
    private void setContentChildView(int LayoutResID) {
        View childView = LayoutInflater.from(this).inflate(LayoutResID, null);
        flContentBase.addView(childView, 0);
    }

    /**
     * 初始化TitleBar
     */
    private void initTitleView() {
        mNavbarCentreRl = navbarBase.getNavbarCentreRl();
        mCentreTv = navbarBase.getNavbarCentreTv();
        mCentreIv = navbarBase.getNavbarCentreIv();
        mNavbarLeftRl = navbarBase.getNavbarLeftRl();
        mNavbarLeftTv = navbarBase.getNavbarLeftTv();
        mNavbarRightRl = navbarBase.getNavbarRightRl();
        mNavbarRightIv = navbarBase.getNavbarRightIv();
        mNavbarRightTv = navbarBase.getNavbarRightTv();
        lineBarView = navbarBase.getNavbarBottomLine();
        mNavbarLeftRl.setOnClickListener(this);
    }

    /**
     * @param listener  请求权限结果的回调
     * @param code      这次请求权限的code
     * @param rationale 当第一次请求被拒绝，第二次请求前会展示这次请求权限的作用
     * @param perms     请求的权限
     */
    public void requestPermissions(OnPermissionResultListener listener, int code, String rationale, String... perms) {
        Logger.d("BaseActivity, requestPermissions, 开始请求权限 requestCode:" + code);
        mPermissionListener = listener;
        this.permss = perms;
        permCode = code;
        if (hasPermission(permss)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(permCode);
            }
        } else {
            EasyPermissions.requestPermissions(this, rationale, permCode, permss);
        }
    }

    private boolean hasPermission(String... perms) {
        return EasyPermissions.hasPermissions(this, perms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.d("BaseActivity, onRequestPermissionsResult, 请求有返回值了 requestCode:" + requestCode);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("BaseActivity, onActivityResult, requestCode:" + requestCode + ",resultCode:" + resultCode);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (hasPermission(permss)) {
                mPermissionListener.succeed(permCode);
            } else {
                mPermissionListener.failed(permCode);
            }
        }
    }

    @AfterPermissionGranted(Constant.PERMISSIONS_BASE_CODE)
    public void perBase() {
        if (hasPermission(Constant.PERMISSIONS_BASE)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(Constant.PERMISSIONS_BASE_CODE);
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    Constant.PERMISSIONS_BASE_RATIONALE,
                    Constant.PERMISSIONS_BASE_CODE,
                    Constant.PERMISSIONS_BASE);
        }
    }

    @AfterPermissionGranted(Constant.PERMISSIONS_CALLPHONE_CODE)
    public void perCallPhone() {
        if (hasPermission(Constant.PERMISSIONS_CALLPHONE)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(Constant.PERMISSIONS_CALLPHONE_CODE);
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    Constant.PERMISSIONS_CALLPHONE_RATIONALE,
                    Constant.PERMISSIONS_CALLPHONE_CODE,
                    Constant.PERMISSIONS_CALLPHONE);
        }
    }

    @AfterPermissionGranted(Constant.PERMISSIONS_CAMERA_CODE)
    public void perCamera() {
        if (hasPermission(Constant.PERMISSIONS_CAMERA)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(Constant.PERMISSIONS_CAMERA_CODE);
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    Constant.PERMISSIONS_CAMERA_RATIONALE,
                    Constant.PERMISSIONS_CAMERA_CODE,
                    Constant.PERMISSIONS_CAMERA);
        }
    }

    @AfterPermissionGranted(Constant.PERMISSIONS_STORAGE_CODE)
    public void perStorage() {
        if (hasPermission(Constant.PERMISSIONS_STORAGE)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(Constant.PERMISSIONS_STORAGE_CODE);
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    Constant.PERMISSIONS_STORAGE_RATIONALE,
                    Constant.PERMISSIONS_STORAGE_CODE,
                    Constant.PERMISSIONS_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Logger.d("BaseActivity onPermissionsGranted, requestCode:" + requestCode + ",perms:" + perms);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Logger.d("BaseActivity onPermissionsDenied, requestCode:" + requestCode + ",perms:" + perms);
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale(R.string.text_perm_reject)
                    .setTitle(R.string.text_perm_must)
                    .build().show();
        } else {
            if (mPermissionListener != null) {
                mPermissionListener.failed(requestCode);
            }
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        Logger.d("BaseActivity, onRationaleAccepted, 基本原理接受 requestCode:" + requestCode);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Logger.d("BaseActivity, onRationaleAccepted, 基本原理拒绝 requestCode:" + requestCode);
    }


    /**
     * 沉浸式, 所有子类都将继承这些相同的属性,请在设置界面之后设置
     */
    public void initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(true).navigationBarColor(R.color.bgcolor_virtual_white).statusBarColor(R.color.color_theme)
                .init();
    }

    /*
   app字体不跟随系统变化大小
    */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        res.getConfiguration().fontScale = 1;
        res.updateConfiguration(null, null);
        return res;
    }

    /**
     * 获取TitleBar
     *
     * @return
     */
    public Navbar getRelativeNavbar() {
        return navbarBase;
    }

    public void setCentreTv(int strId) {
        mCentreTv.setText(getString(strId));
    }

    public void setCentreTv(String str) {
        mCentreTv.setText(str);
    }


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
        overridePendingTransition(R.anim.activity_from_right_in, R.anim.activity_to_left_out);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.activity_from_right_in, R.anim.activity_to_left_out);
    }

    /**
     * 通过Class跳转界面有返回值
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
        overridePendingTransition(R.anim.activity_from_right_in, R.anim.activity_to_left_out);
    }

    /**
     * 含有Bundle通过Class跳转界面有返回值
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_from_right_in, R.anim.activity_to_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_from_left_in, R.anim.activity_to_right_out);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_navbar_left:
                finish();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 点击频率过快则忽略
     *
     * @return
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD <= 300;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);//不知道会不会取消所有正在进行的请求先写着试试
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        ActivityUtils.removeActivity(this);
    }
}
