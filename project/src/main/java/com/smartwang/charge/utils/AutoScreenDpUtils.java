package com.smartwang.charge.utils;//package com.bihang.www.baseproject.utils;
//
//import android.app.Activity;
//import android.app.Application;
//import android.content.ComponentCallbacks;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageManager;
//import android.content.res.Configuration;
//import android.support.annotation.NonNull;
//import android.util.DisplayMetrics;
//
//import com.orhanobut.logger.Logger;
//
//public class AutoScreenDpUtils {
//
//    private final static String WIDTH_DP = "set_width_dp";
//
//    public static boolean isDebug = false;
//
//    private static Application mApplication;
//
//    private static float w = 0;
//
//    private static float oldDensity = -1;
//    private static int oldDensityDpi = -1;
//    private static float oldScaledDensity = -1;
//
//    private static float newDensity = -1;
//    private static int newDensityDpi = -1;
//    private static float newScaledDensity = -1;
//
//    public static void init(@NonNull Application application) {
//        init(application, 0);
//    }
//
//    public static void init(@NonNull Application application, boolean debug) {
//        init(application, 0, debug);
//    }
//
//    public static void init(@NonNull Application application, float widthDp) {
//        init(application, widthDp, false);
//    }
//
//    public static void init(@NonNull Application application, float widthDp, boolean debug) {
//        isDebug = debug;
//        if (widthDp != 0) {
//            w = widthDp;
//        } else {
//            try {
//                ApplicationInfo info = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA);
//                w = info.metaData.getInt(WIDTH_DP, 0);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        if (w == 0) {
//            Logger.e("w is null");
//            return;
//        }
//        if (mApplication == null) {
//            mApplication = application;
//        }
//        Logger.d("默认AutoScreenDpUtils.isDebug为false");
//        initApplication();
//    }
//
//    private static void initApplication() {
//        final DisplayMetrics metrics = mApplication.getResources().getDisplayMetrics();
//        if (oldDensity == -1) {
//            oldDensity = metrics.density;
//            newDensity = metrics.widthPixels / w;
//        }
//        if (oldDensityDpi == -1) {
//            oldDensityDpi = metrics.densityDpi;
//            newDensityDpi = (int) (newDensity * 160);
//        }
//        if (oldScaledDensity == -1) {
//            oldScaledDensity = metrics.scaledDensity;
//            newScaledDensity = newDensity * (oldScaledDensity / oldDensity);
//        }
//        mApplication.registerComponentCallbacks(new ComponentCallbacks() {
//            @Override
//            public void onConfigurationChanged(Configuration newConfig) {
//                if (newConfig != null && newConfig.fontScale > 0) {
//                    Logger.d("fontScale is change.");
//                    newScaledDensity = newDensity * newConfig.fontScale;
//                    oldScaledDensity = oldDensity * newConfig.fontScale;
//                }
//            }
//
//            @Override
//            public void onLowMemory() {
//            }
//        });
//        Logger.d("oldDensity = " + oldDensity);
//        Logger.d("oldDensityDpi = " + oldDensityDpi);
//        Logger.d("oldScaledDensity = " + oldScaledDensity);
//        Logger.d("newDensity => " + metrics.widthPixels + " / " + w + " = " + newDensity);
//        Logger.d("newDensityDpi => " + newDensity + " * 160 = " + newDensityDpi);
//        Logger.d("newScaledDensity => " + newDensity + " * (" + oldScaledDensity + " / " + oldDensity + ") = " + newScaledDensity);
//    }
//
//    public static void setCustomDensity(@NonNull final Activity activity) {
//        if (mApplication == null) {
//            Logger.e("application is null.");
//            return;
//        }
//        changeDensity(activity);
//    }
//
//    private static void changeDensity(@NonNull Activity activity) {
//        float density;
//        int densityDpi;
//        float scaledDensity;
//        final DisplayMetrics activityMetrics = activity.getResources().getDisplayMetrics();
//        if (activity instanceof IAutoCancel) {
//            density = oldDensity;
//            densityDpi = oldDensityDpi;
//            scaledDensity = oldScaledDensity;
//            Logger.d("default Density");
//        } else if (activity instanceof IAutoChange) {
//            density = activityMetrics.widthPixels / ((IAutoChange) activity).newWidth();
//            densityDpi = (int) (density * 160);
//            scaledDensity = densityDpi * (oldScaledDensity / oldDensity);
//            Logger.d("new dp width = " + ((IAutoChange) activity).newWidth());
//            Logger.d("new Density = " + density);
//        } else {
//            density = newDensity;
//            densityDpi = newDensityDpi;
//            scaledDensity = newScaledDensity;
//            Logger.d("new dp width = " + w);
//            Logger.d("new Density = " + density);
//        }
//        activityMetrics.density = density;
//        activityMetrics.densityDpi = densityDpi;
//        activityMetrics.scaledDensity = scaledDensity;
//    }
//}
