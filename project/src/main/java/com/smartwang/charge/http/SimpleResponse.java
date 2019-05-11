package com.smartwang.charge.http;

import com.smartwang.charge.entity.BaseBean;

public class SimpleResponse extends BaseBean {

    private static final long serialVersionUID = -1477609349345966116L;

    public int code;
    public String msg;

    public  MyResponse toMyResponse() {
        MyResponse mResponse = new MyResponse();
        mResponse.code = code;
        mResponse.msg = msg;
        return mResponse;
    }
}
