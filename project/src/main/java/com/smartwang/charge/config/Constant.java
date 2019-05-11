package com.smartwang.charge.config;

import android.Manifest;

public class Constant {

    /**
     * 文件
     */
    public static final String File_ROOT = "com.smartwang.baseproject";
    public static final String File_LOG = "log";
    public static final String File_HEAD = "head";



    /**
     * 共享参数字段
     */
    public static final String SHARE_LANGUAGE = "language";
    public static final String SHARE_ISFIRST = "isfirst";
    public static final String SHARE_ISLOGIN = "islogin";


    /**
     * 语言环境
     */
    public static final int LANGUAGE_CHINESE_SIMPLIFIED = 0; //简体
    public static final int LANGUAGE_EN = 1;    //英文
    public static final int LANGUAGE_CHINESE_TRADITIONAL= 2; //繁体
    public static final int LANGUAGE_CHINESE_SYSTEM= 3; //跟随系统


    /*
    权限请求分类
     */
    /**
     * app基础权限，必须允许
     */
    public final static int PERMISSIONS_BASE_CODE = 100;
    public final static String PERMISSIONS_BASE_RATIONALE= "最基础的权限，如不允许将无法使用app";
    public final static String[] PERMISSIONS_BASE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};
    /**
     * 相机权限
     */
    public final static int PERMISSIONS_CAMERA_CODE = 101;
    public final static String PERMISSIONS_CAMERA_RATIONALE= "我们将使用您的摄像头，如不允许该功能将无法使用";
    public final static String[] PERMISSIONS_CAMERA = {Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.CAMERA};
    /**
     * 存储权限
     */
    public final static int PERMISSIONS_STORAGE_CODE = 103;
    public final static String PERMISSIONS_STORAGE_RATIONALE= "我们将使用您的内存卡，如不允许该功能将无法使用";
    public final static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 拨打电话权限
     */
    public final static int PERMISSIONS_CALLPHONE_CODE = 104;
    public final static String PERMISSIONS_CALLPHONE_RATIONALE= "我们将使用您的手机拨打电话，如不允许该功能将无法使用";
    public final static String[] PERMISSIONS_CALLPHONE = {Manifest.permission.CALL_PHONE};

}
