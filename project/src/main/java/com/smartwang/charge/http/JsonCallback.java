package com.smartwang.charge.http;

import android.app.Activity;

import com.alibaba.fastjson.JSONReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.smartwang.charge.utils.ToastUtil;
import com.smartwang.charge.widget.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Response;
import okhttp3.ResponseBody;


public abstract class JsonCallback<T> extends AbsCallback<T> {
    private Type type;
    private Class<T> clazz;

    private LoadingDialog loadingDialog;

    public JsonCallback(Type type, Activity activity) {
        this.type = type;
        loadingDialog = new LoadingDialog(activity);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
    }

    public JsonCallback(Class<T> clazz, Activity activity) {
        this.clazz = clazz;
        loadingDialog = new LoadingDialog(activity);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
    }


    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        request.headers("header1", "HeaderValue1")//
                .params("params1", "654321")//
                .params("token", "123456");
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalStateException("没有填写泛型参数");
        }
        Type rawType = ((ParameterizedType) type).getRawType();
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

        ResponseBody body = response.body();
        if (body == null) return null;
        JSONReader jsonReader = new JSONReader(body.charStream());
        if (rawType != MyResponse.class) {
            T data = jsonReader.readObject(type);
            response.close();
            return data;
        } else {
            if (typeArgument == Void.class) {
                SimpleResponse simple = jsonReader.readObject(SimpleResponse.class);
                response.close();
                return (T) simple.toMyResponse();
            } else {
                //有数据类型，表示有data
                MyResponse mResponse = jsonReader.readObject(type);
                response.close();
                int code = mResponse.code;
                if (code == 1) {
                    return (T) mResponse;
                } else if (code == 2) {
                    //如果不仅需要Toast还需要做其他的操作，就在这里做其他操作。比如被挤下线，不仅toast警告，而且要做本地退出登录操作.....
                    throw new IllegalStateException(mResponse.msg);
                } else if (code == 3) {
                    throw new IllegalStateException(mResponse.msg);
                } else if (code == 4) {
                    throw new IllegalStateException(mResponse.msg);
                } else if (code == 5) {
                    throw new IllegalStateException(mResponse.msg);
                } else if (code == 6) {
                    throw new IllegalStateException(mResponse.msg);
                } else {
                    throw new IllegalStateException("未知错误代码：" + code + ",错误信息：" + mResponse.msg);
                }
            }
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Throwable exception = response.getException();
        if (exception != null) {
            //这里获取到异常后，使用printStackTrace()打印异常详情
            exception.printStackTrace();
        }
        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            ToastUtil.showShort("网络连接失败，请检查网络");
        } else if (exception instanceof SocketTimeoutException) {
            ToastUtil.showShort("请求超时");
        } else if (exception instanceof HttpException) {
            ToastUtil.showShort("服务器404或者5xx");
        } else if (exception instanceof StorageException) {
            ToastUtil.showShort("sd卡不存在，或者没有权限");
        } else if (exception instanceof IllegalStateException) {
            String message = exception.getMessage();
            ToastUtil.showShort(message);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
