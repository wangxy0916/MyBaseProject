package com.smartwang.charge.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SharedPreferencesUtil {
    private static SharedPreferencesUtil util;
    private static SharedPreferences sp;

    private SharedPreferencesUtil(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 初始化SharedPreferencesUtil,只需要初始化一次，建议在Application中初始化
     *
     * @param context 上下文对象
     * @param name    SharedPreferences Name
     */
    public static void getInstance(Context context, String name) {
        if (util == null) {
            util = new SharedPreferencesUtil(context, name);
        }
    }

    /**
     * 保存数据到SharedPreferences
     *
     * @param key   键
     * @param value 需要保存的数据
     * @return 保存结果
     */
    public static boolean putString(String key, String value) {
        boolean result;
        SharedPreferences.Editor editor = sp.edit();
        try {
            editor.putString(key, value);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    public static boolean putBoolean(String key, boolean value) {
        boolean result;
        SharedPreferences.Editor editor = sp.edit();
        try {
            editor.putBoolean(key, value);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    public static boolean putInteger(String key, int value) {
        boolean result;
        SharedPreferences.Editor editor = sp.edit();
        try {
            editor.putInt(key, value);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    public static boolean putObj(String key, Object value) {
        boolean result;
        SharedPreferences.Editor editor = sp.edit();
        try {
            result = true;
            if (value == null){
              editor.putString(key, "");
            }else {
                String s = JSON.toJSONString(value);
                editor.putString(key, s);
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }


    /**
     * 获取SharedPreferences中保存的数据
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        boolean result = sp.getBoolean(key, defaultValue);
        return result;
    }

    public static String getString(String key, String defaultValue) {
        String result = sp.getString(key, defaultValue);
        return result;
    }

    public static long getLong(String key, long defaultValue) {
        long result = sp.getLong(key, defaultValue);
        return result;
    }

    public static float getFloat(String key, float defaultValue) {
        Float result = sp.getFloat(key, defaultValue);
        return result;
    }

    public static int getInteger(String key, int defaultValue) {
        int result = sp.getInt(key, defaultValue);
        return result;
    }

    public static Object getObj(String key, Class defaultValue) {
        Object result;
        String json = sp.getString(key, "");
        if (StringUtil.isNotBlank(json)) {
            result= JSONObject.parseObject(json, defaultValue);
            return result;
        } else {
            return null;
        }
    }



}
