package com.smartwang.charge.utils;

import android.Manifest;
import android.app.Activity;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionManager {

    /**
     * 写入权限的请求code,提示语，和权限码
     */
    public final static  int WRITE_PERMISSION_CODE=110;
    public final static  String WRITE_PERMISSION_TIP ="为了正常使用，请允许读写权限!";
    public final  static String[] PERMS_WRITE ={Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     *
     * @param context
     * return true:已经获取权限
     * return false: 未获取权限，主动请求权限
     */
    // @AfterPermissionGranted 是可选的
    public static boolean checkPermission(Activity context, String[] perms) {
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 检查添加权限
     */
    public void checkBasePermission(Activity context,String[] perms) {
        boolean result = PermissionManager.checkPermission(context,perms);
        if (!result) {
            PermissionManager.requestPermission(context, PermissionManager.WRITE_PERMISSION_TIP, PermissionManager.WRITE_PERMISSION_CODE, perms);
        }
    }
    /**
     * 检查基础权限
     */
    public void checkBasePermission(Activity context) {
        boolean result = PermissionManager.checkPermission(context,PERMS_WRITE);
        if (!result) {
            PermissionManager.requestPermission(context, PermissionManager.WRITE_PERMISSION_TIP, PermissionManager.WRITE_PERMISSION_CODE, PermissionManager.PERMS_WRITE);
        }
    }
    /**
     * 请求权限
     * @param context
     */
    public static void requestPermission(Activity context, String tip, int requestCode, String[] perms) {
        EasyPermissions.requestPermissions(context, tip,requestCode,perms);

    }

}
/*
//调用：
        PermissionManager permissionManager = new PermissionManager();
        permissionManager.checkBasePermission(LoadingActivity.this);

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    public final static String[] PERMS_WRITE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        recoverState(savedInstanceState);
        PermissionManager permissionManager = new PermissionManager();
        permissionManager.checkBasePermission(MainActivity.this,PERMS_WRITE);

    }

    *//**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     *//*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    *//**
     * 请求权限成功
     *
     * @param requestCode
     * @param perms
     *//*
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //        ToastUtils.showToast(getApplicationContext(), "用户授权成功");

    }
    *//**
     * 请求权限失败
     *
     * @param requestCode
     * @param perms
     *//*
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //        ToastUtils.showToast(getApplicationContext(), "用户授权失败");
        *//**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         *//*
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}*/
