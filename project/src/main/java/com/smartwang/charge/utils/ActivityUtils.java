package com.smartwang.charge.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Stack;


/**
 * Created by ZHT on 2017/4/17.
 * Activity管理工具类(包含对Fragment的添加切换)
 */
public class ActivityUtils {
    private static Stack<Activity> mActivityStack;

    /**
     * 添加一个Activity到堆栈中
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (null == mActivityStack) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 从堆栈中移除指定的Activity
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取顶部的Activity
     * @return
     */
    public static Activity getTopActivity() {
        if (mActivityStack.isEmpty()) {
            return null;
        } else {
            return mActivityStack.get(mActivityStack.size() - 1);
        }
    }

    /**
     * 结束所有的Activity
     */
    public static void removeAllActivity() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (Activity activity : mActivityStack) {
                activity.finish();
            }
            mActivityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
//    public void AppExit(Context context) {
//        try {
//            removeAllActivity();
//            ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            activityMgr.restartPackage(context.getPackageName());
//            System.exit(0);
//        } catch (Exception e) { }
//    }

    /**
     * 退出应用程序
     * 这里关闭的是所有的Activity，没有关闭Activity之外的其他组件;
     * android.os.Process.killProcess(android.os.Process.myPid())
     * 杀死进程关闭了整个应用的所有资源，有时候是不合理的，通常是用
     * 堆栈管理Activity;System.exit(0)杀死了整个进程，这时候活动所占的
     * 资源也会被释放,它会执行所有通过Runtime.addShutdownHook注册的shutdown hooks.
     * 它能有效的释放JVM之外的资源,执行清除任务，运行相关的finalizer方法终结对象，
     * 而finish只是退出了Activity。
     */
    public static void AppExit(Context context) {
        try {
            removeAllActivity();
            //DalvikVM的本地方法
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            //这些方法如果是放到主Activity就可以退出应用，如果不是主Activity
            //就是退出当前的Activity
        } catch (Exception e) {
        }
    }

    /**
     * 将一个Fragment添加到Activity中
     * @param fragmentManager fragment管理器
     * @param fragment  需要添加的fragment
     * @param frameId  布局FrameLayout的Id
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        if (null != fragmentManager && null != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }
    }

    /**
     * 将一个Fragment添加到Activity中,并添加tag标识
     * @param fragmentManager  fragment管理器
     * @param fragment  需要添加的fragment
     * @param frameId 布局FrameLayout的Id
     * @param tag  fragment的唯一tag标识
     * @param addToBackStack  是否添加到栈中，可通过返回键进行切换fragment
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, String tag, boolean addToBackStack) {
        if (null != fragmentManager && null != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
        }
    }

    /**
     * 对Fragment进行显示隐藏的切换，减少fragment的重复创建
     * @param fragmentManager fragment管理器
     * @param hideFragment  需要隐藏的Fragment
     * @param showFragment  需要显示的Fragment
     * @param frameId   布局FrameLayout的Id
     * @param tag  fragment的唯一tag标识
     */
    public static void switchFragment(FragmentManager fragmentManager, Fragment hideFragment, Fragment showFragment, int frameId, String tag) {
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!showFragment.isAdded()) {
                transaction.hide(hideFragment)
                        .add(frameId, showFragment, tag)
                        .commit();
            } else {
                transaction.hide(hideFragment)
                        .show(showFragment)
                        .commit();
            }
        }
    }

    /**
     * 替换Activity中的Fragment
     * @param fragmentManager fragment管理器
     * @param fragment  需要替换到Activity的Fragment
     * @param frameId  布局FrameLayout的Id
     */
    public static void replaceFragmentFromActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        if (null != fragmentManager && null != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment);
            transaction.commit();
        }
    }
}
