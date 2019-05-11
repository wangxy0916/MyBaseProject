package com.smartwang.charge.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.smartwang.project.R;
import com.smartwang.charge.base.BaseApplication;


/**
 * Toast统一管理类
 */
public class ToastUtil {
    private static Toast toast;
    private static Toast toast2;
    private static TextView mTvToast;
    private static void initToast(CharSequence message, int duration) {
        if (StringUtil.isNotBlank(message)){
            if (toast == null) {
                toast = Toast.makeText(BaseApplication.getApplication(), message, duration);
                View view = LayoutInflater.from(BaseApplication.getApplication()).inflate(R.layout.view_toast_text, null);
                mTvToast = (TextView) view.findViewById(R.id.tv_toast);
                toast.setView(view);
                toast.setGravity(Gravity.BOTTOM, 0, 350);
                mTvToast.setText(message);
                toast.setDuration(duration);
            } else {
                mTvToast.setText(message);
                toast.setDuration(duration);
            }
            toast.show();
        }else{
            Log.e("--->jason ToatUtil", "Toast内容是空的");
        }

    }


    /**
     * 短时间显示Toast
     * @param message
     */
    public static void showShort(CharSequence message) {
        initToast(message, Toast.LENGTH_SHORT);
    }


    /**
     * 短时间显示Toast
     * @param strResId
     */
    public static void showShort(int strResId) {
        //		Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
        initToast(BaseApplication.getApplication().getResources().getText(strResId), Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     * @param message
     */
    public static void showLong(CharSequence message) {
        initToast(message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     * @param strResId
     */
    public static void showLong(int strResId) {
        initToast(BaseApplication.getApplication().getResources().getText(strResId), Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        initToast(message, duration);

    }

    /**
     * 自定义显示Toast时间
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(Context context, int strResId, int duration) {
        initToast(context.getResources().getText(strResId), duration);
    }

    /**
     * 显示有image的toast
     * @param tvStr
     * @param imageResource
     * @return
     */
    public static Toast showToastWithImg(final String tvStr, final int imageResource) {
        if (toast2 == null) {
            toast2 = new Toast(BaseApplication.getApplication());
        }
        View view = LayoutInflater.from(BaseApplication.getApplication()).inflate(R.layout.view_toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_toast_custom);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_toast_custom);
        if (imageResource > 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageResource);
        } else {
            iv.setVisibility(View.GONE);
        }
        toast2.setView(view);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
        return toast2;

    }

}
