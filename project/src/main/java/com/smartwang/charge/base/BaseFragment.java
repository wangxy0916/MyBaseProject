package com.smartwang.charge.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.orhanobut.logger.Logger;
import com.smartwang.project.R;
import com.smartwang.charge.config.Constant;
import com.smartwang.charge.impl.OnPermissionResultListener;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private String[] permss;//当前请求的权限
    private int permCode;//当前请求的权限Code
    private OnPermissionResultListener mPermissionListener;


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void requestPermissions(OnPermissionResultListener listener, int code, String rationale, String... perms) {
        Logger.d("BaseFragment, requestPermissions, 开始请求权限 requestCode:" + code);
        mPermissionListener = listener;
        this.permss = perms;
        permCode = code;
        if (hasPermission(permss)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(permCode);
            }
        } else {
            EasyPermissions.requestPermissions(BaseFragment.this, rationale, permCode, permss);
        }
    }

    private boolean hasPermission(String... perms) {
        return EasyPermissions.hasPermissions(getContext(), perms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.d("BaseFragment, onRequestPermissionsResult, 请求有返回值了 requestCode:" + requestCode);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, BaseFragment.this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("BaseFragment, onActivityResult, requestCode:" + requestCode + ",resultCode:" + resultCode);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (hasPermission(permss)) {
                mPermissionListener.succeed(permCode);
            } else {
                mPermissionListener.failed(permCode);
            }
        }
    }

    @AfterPermissionGranted(Constant.PERMISSIONS_BASE_CODE)
    public void perBase() {
        if (hasPermission(Constant.PERMISSIONS_BASE)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(Constant.PERMISSIONS_BASE_CODE);
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    Constant.PERMISSIONS_BASE_RATIONALE,
                    Constant.PERMISSIONS_BASE_CODE,
                    Constant.PERMISSIONS_BASE);
        }
    }

    @AfterPermissionGranted(Constant.PERMISSIONS_CALLPHONE_CODE)
    public void perCallPhone() {
        if (hasPermission(Constant.PERMISSIONS_CALLPHONE)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(Constant.PERMISSIONS_CALLPHONE_CODE);
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    Constant.PERMISSIONS_CALLPHONE_RATIONALE,
                    Constant.PERMISSIONS_CALLPHONE_CODE,
                    Constant.PERMISSIONS_CALLPHONE);
        }
    }

    @AfterPermissionGranted(Constant.PERMISSIONS_CAMERA_CODE)
    public void perCamera() {
        if (hasPermission(Constant.PERMISSIONS_CAMERA)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(Constant.PERMISSIONS_CAMERA_CODE);
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    Constant.PERMISSIONS_CAMERA_RATIONALE,
                    Constant.PERMISSIONS_CAMERA_CODE,
                    Constant.PERMISSIONS_CAMERA);
        }
    }

    @AfterPermissionGranted(Constant.PERMISSIONS_STORAGE_CODE)
    public void perStorage() {
        if (hasPermission(Constant.PERMISSIONS_STORAGE)) {
            if (mPermissionListener != null) {
                mPermissionListener.succeed(Constant.PERMISSIONS_STORAGE_CODE);
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    Constant.PERMISSIONS_STORAGE_RATIONALE,
                    Constant.PERMISSIONS_STORAGE_CODE,
                    Constant.PERMISSIONS_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Logger.d("BaseFragment onPermissionsGranted, requestCode:" + requestCode);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Logger.d("BaseFragment onPermissionsDenied, requestCode:" + requestCode);
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale(R.string.text_perm_reject)
                    .setTitle(R.string.text_perm_must)
                    .build().show();
        } else {
            if (mPermissionListener != null) {
                mPermissionListener.failed(requestCode);
            }
        }
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
        getActivity().overridePendingTransition(R.anim.activity_from_right_in, R.anim.activity_to_left_out);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_from_right_in, R.anim.activity_to_left_out);
    }

    /**
     * 通过Class跳转界面有返回值
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
        getActivity().overridePendingTransition(R.anim.activity_from_right_in, R.anim.activity_to_left_out);
    }

    /**
     * 含有Bundle通过Class跳转界面有返回值
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.activity_from_right_in, R.anim.activity_to_left_out);
    }


}
