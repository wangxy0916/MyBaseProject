package com.smartwang.charge.utils;

import com.smartwang.charge.config.Constant;

public class ProjectUtil {



    /**
     * 获取当前语言环境
     *
     * @return
     */
    public static int getLanguage() {
        return SharedPreferencesUtil.getInteger(Constant.SHARE_LANGUAGE, Constant.LANGUAGE_CHINESE_SIMPLIFIED);
    }
    /**
     * 设置语言环境
     * @param checked
     */
    public static void setLanguage(int checked) {
        SharedPreferencesUtil.putInteger(Constant.SHARE_LANGUAGE, checked);
    }


}
