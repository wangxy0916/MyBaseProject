package com.smartwang.charge.impl;

public interface OnPermissionResultListener {

    public void succeed(int code);
    public void failed(int code);
}
