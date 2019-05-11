package com.smartwang.charge.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.smartwang.charge.base.BaseActivity;
import com.smartwang.charge.ui.module_home.fragment.HomeFragment;
import com.smartwang.charge.ui.module_my.fragment.MyFragment;
import com.smartwang.charge.ui.module_three.fragment.ThreeFragment;
import com.smartwang.charge.ui.module_two.fragment.TwoFragment;
import com.smartwang.project.R;
import com.smartwang.charge.base.BaseApplication;
import com.smartwang.charge.impl.OnPermissionResultListener;
import com.smartwang.charge.utils.ActivityUtils;
import com.smartwang.charge.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements OnPermissionResultListener {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.fragment_main)
    FrameLayout fragmentMain;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Fragment fragmentHome;
    private Fragment fragmentTwo;
    private Fragment fragmentThree;
    private Fragment fragmentMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        //如果不需要恢复之前的状态
        if (savedInstanceState == null) {
            if (fragmentHome == null) {
                fragmentHome = new HomeFragment();
            }
            ImmersionBar.with(MainActivity.this).reset().navigationBarColor(R.color.bgcolor_virtual_white).init();
            fragmentManager.beginTransaction().add(R.id.fragment_main, fragmentHome, "home").commit();
        } else {
            fragmentHome = fragmentManager.findFragmentByTag("home");
            fragmentTwo = fragmentManager.findFragmentByTag("two");
            fragmentThree = fragmentManager.findFragmentByTag("three");
            fragmentMy = fragmentManager.findFragmentByTag("my");
        }
        initView();
    }

    private void initView() {
        getRelativeNavbar().setVisibility(View.GONE);
        navigation.setItemIconTintList(null);//xml中设置无效
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            transaction = fragmentManager.beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.tab_home:
                    if (fragmentHome == null) {
                        fragmentHome = new HomeFragment();
                    }
                    if (fragmentHome.isAdded()) {
                        transaction.show(fragmentHome);
                    } else {
                        transaction.add(R.id.fragment_main, fragmentHome, "home");
                    }
                    //隐藏掉其他两个Fragment
                    if (fragmentTwo != null && fragmentTwo.isAdded()) {
                        transaction.hide(fragmentTwo);
                    }
                    if (fragmentThree != null && fragmentThree.isAdded()) {
                        transaction.hide(fragmentThree);
                    }
                    if (fragmentMy != null && fragmentMy.isAdded()) {
                        transaction.hide(fragmentMy);
                    }
                    ImmersionBar.with(MainActivity.this).reset().navigationBarColor(R.color.bgcolor_virtual_white)
                            .init();
                    transaction.commitAllowingStateLoss();
                    return true;
                case R.id.tab_two:
                    if (fragmentTwo == null) {
                        fragmentTwo = new TwoFragment();
                    }
                    if (fragmentTwo.isAdded()) {
                        transaction.show(fragmentTwo);
                    } else {
                        transaction.add(R.id.fragment_main, fragmentTwo, "two");
                    }
                    //隐藏掉其他Fragment
                    if (fragmentHome != null && fragmentHome.isAdded()) {
                        transaction.hide(fragmentHome);
                    }
                    if (fragmentThree != null && fragmentThree.isAdded()) {
                        transaction.hide(fragmentThree);
                    }
                    if (fragmentMy != null && fragmentMy.isAdded()) {
                        transaction.hide(fragmentMy);
                    }
                    transaction.commitAllowingStateLoss();
                    ImmersionBar.with(MainActivity.this).reset().fitsSystemWindows(true).keyboardEnable(false).statusBarDarkFont(true, 0.2f).barColor(R.color.color_red).init();
                    return true;
                case R.id.tab_three:
                    if (fragmentThree == null) {
                        fragmentThree = new ThreeFragment();
                    }
                    if (fragmentThree.isAdded()) {
                        transaction.show(fragmentThree);
                    } else {
                        transaction.add(R.id.fragment_main, fragmentThree, "three");
                    }
                    //隐藏掉其他Fragment
                    if (fragmentHome != null && fragmentHome.isAdded()) {
                        transaction.hide(fragmentHome);
                    }
                    if (fragmentTwo != null && fragmentTwo.isAdded()) {
                        transaction.hide(fragmentTwo);
                    }
                    if (fragmentMy != null && fragmentMy.isAdded()) {
                        transaction.hide(fragmentMy);
                    }
                    ImmersionBar.with(MainActivity.this).reset().fitsSystemWindows(true).navigationBarColor(R.color.color_green).statusBarDarkFont(true, 0.2f).barColor(R.color.color_green).init();
                    transaction.commitAllowingStateLoss();
                    return true;
                case R.id.tab_my:
                    if (fragmentMy == null) {
                        fragmentMy = new MyFragment();
                    }
                    if (fragmentMy.isAdded()) {
                        transaction.show(fragmentMy);
                    } else {
                        transaction.add(R.id.fragment_main, fragmentMy, "my");
                    }
                    //隐藏掉其他Fragment
                    if (fragmentHome != null && fragmentHome.isAdded()) {
                        transaction.hide(fragmentHome);
                    }
                    if (fragmentTwo != null && fragmentTwo.isAdded()) {
                        transaction.hide(fragmentTwo);
                    }
                    if (fragmentThree != null && fragmentThree.isAdded()) {
                        transaction.hide(fragmentThree);
                    }
                    transaction.commitAllowingStateLoss();
                    ImmersionBar.with(MainActivity.this).reset().fitsSystemWindows(true).keyboardEnable(false).statusBarDarkFont(false).barColor(R.color.color_blue).init();
                    return true;
            }
            return false;
        }
    };


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //首先判断是否按了返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //如果大于2000毫秒,说明误操作
                ToastUtil.showShort(R.string.text_main_toast_outlogin_remind);
                exitTime = System.currentTimeMillis();
            } else {
                ActivityUtils.AppExit(BaseApplication.getApplication());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void succeed(int code) {
        ToastUtil.showShort("权限获取成功");
        Logger.d("MainFragment, succeed, 成功了, code:" + code);
    }

    @Override
    public void failed(int code) {
        ToastUtil.showShort("权限获取失败");
        Logger.d("MainFragment, failed, 失败了, code:" + code);
    }
}
