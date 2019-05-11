package com.smartwang.charge.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.smartwang.charge.config.Constant;

import java.util.Locale;

public class LocalManageUtil {


    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
//    public static Locale getSystemLocale(Context context) {
//        return SPUtil.getInstance(context).getSystemCurrentLocal();
//    }
//
//    public static String getSelectLanguage(Context context) {
//        switch (SPUtil.getInstance(context).getSelectLanguage()) {
//            case 0:
//                return context.getString(R.string.language_auto);
//            case 1:
//                return context.getString(R.string.language_cn);
//            case 2:
//                return context.getString(R.string.language_traditional);
//            case 3:
//            default:
//                return context.getString(R.string.language_en);
//        }
//    }

    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    public static Locale getSetLanguageLocale(Context context) {
        switch (ProjectUtil.getLanguage()) {
            case Constant.LANGUAGE_CHINESE_SIMPLIFIED:
                return Locale.CHINA;
            case Constant.LANGUAGE_EN:
                return Locale.ENGLISH;
            case Constant.LANGUAGE_CHINESE_TRADITIONAL:
                return Locale.CHINA;
            default:
                return Locale.CHINA;
        }
    }

    public static void saveSelectLanguage(Context context, int select) {
        ProjectUtil.setLanguage(select);
        setApplicationLanguage(context);
    }

    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale(context);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

//    public static void saveSystemCurrentLanguage(Context context) {
//        Locale locale;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            locale = LocaleList.getDefault().get(0);
//        } else {
//            locale = Locale.getDefault();
//        }
//        Log.d(TAG, locale.getLanguage());
//        SPUtil.getInstance(context).setSystemCurrentLocal(locale);
//        WalletUtil.setSystemLanguage(locale);
//    }

    public static void onConfigurationChanged(Context context){
//        saveSystemCurrentLanguage(context);
        setLocal(context);
        setApplicationLanguage(context);
    }
}
