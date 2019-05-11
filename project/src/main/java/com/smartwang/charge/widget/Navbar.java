package com.smartwang.charge.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartwang.project.R;

/**
 * Created by smartwang on 2018/3/15.
 */
public class Navbar extends FrameLayout {
    private RelativeLayout relativeNabarView = null;//标题栏布局文件
    private RelativeLayout navbarBackGround = null;//标题栏在baseActivity的id

    private RelativeLayout mNavbarLeftRl = null;//左侧父
    private RelativeLayout mNavbarRightRl = null;//右侧父
    private RelativeLayout mNavbarCentreRl = null;//中间父

    private TextView mNavbarLeftTv = null;//左侧tv
    private TextView mNavbarRightTv = null;//右侧tv
    private ImageView mNavbarRightIv = null;//右侧iv
    private TextView mNavbarCentreTv = null;//标题tv
    private ImageView mNavbarCentreIv = null;//标题iv
    private View mNavbarBottomLineView = null;//分界线
    public Navbar(Context context) {
        super(context);
        initUI();
    }
    public Navbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
        getNavbarBackGround();
    }
    public Navbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI();
    }
    private void initUI() {
        addView(getNavbar());
    }
    public RelativeLayout getNavbar(){
        if (relativeNabarView == null) {
            relativeNabarView = (RelativeLayout) LayoutInflater.from(this.getContext())
                    .inflate(R.layout.widget_navbar, null);
        }
        return relativeNabarView;
    }
    public RelativeLayout getNavbarBackGround() {
        if (navbarBackGround == null) {
            navbarBackGround = (RelativeLayout) getNavbar().findViewById(R.id.rl_navbar);
        }
        return navbarBackGround;
    }
    public RelativeLayout getNavbarLeftRl() {
        if (mNavbarLeftRl == null) {
            mNavbarLeftRl = (RelativeLayout) getNavbar().findViewById(R.id.rl_navbar_left);
        }
        return mNavbarLeftRl;
    }
    public RelativeLayout getNavbarRightRl() {
        if (mNavbarRightRl == null) {
            mNavbarRightRl = (RelativeLayout) getNavbar().findViewById(R.id.rl_navbar_right);
        }
        return mNavbarRightRl;
    }
    public RelativeLayout getNavbarCentreRl(){
        if (mNavbarCentreRl == null){
            mNavbarCentreRl = getNavbar().findViewById(R.id.rl_navbar_centre);
        }
        return mNavbarCentreRl;
    }


    public ImageView getNavbarCentreIv(){
        if (mNavbarCentreIv == null){
            mNavbarCentreIv = getNavbar().findViewById(R.id.iv_navbar_centre);
        }
        return mNavbarCentreIv;
    }
    public TextView getNavbarCentreTv() {
        if (mNavbarCentreTv == null) {
            mNavbarCentreTv = (TextView) getNavbar().findViewById(R.id.tv_navbar_centre);
        }
        return mNavbarCentreTv;
    }

    public TextView getNavbarLeftTv() {
        if (mNavbarLeftTv == null) {
            mNavbarLeftTv = (TextView) getNavbar().findViewById(R.id.tv_navbar_left);
        }
        return mNavbarLeftTv;
    }


    public TextView getNavbarRightTv() {
        if (mNavbarRightTv == null) {
            mNavbarRightTv = (TextView) getNavbar().findViewById(R.id.tv_navbar_right);
        }
        return mNavbarRightTv;
    }
    public ImageView getNavbarRightIv() {
        if (mNavbarRightIv == null) {
            mNavbarRightIv = (ImageView) getNavbar().findViewById(R.id.iv_navbar_right);
        }
        return mNavbarRightIv;
    }


    public View getNavbarBottomLine() {
        if (mNavbarBottomLineView == null) {
            mNavbarBottomLineView = getNavbar().findViewById(R.id.view_navbar_line);
        }
        return mNavbarBottomLineView;
    }

    public void setNavbarBackGround(int res) {
        if(navbarBackGround != null){
            navbarBackGround.setBackgroundResource(res);
        }
    }
}
