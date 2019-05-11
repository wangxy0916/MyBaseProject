package com.smartwang.charge.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.StrictMode;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.smartwang.charge.utils.LocalManageUtil;
import com.smartwang.charge.utils.SharedPreferencesUtil;
import com.smartwang.project.R;

import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    private static BaseApplication baseApplication = null;
//    private BoxStore boxStore; //数据库表的管理者


    public static BaseApplication getApplication() {
        return baseApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
//        LocalManageUtil.saveSystemCurrentLanguage(base);
        SharedPreferencesUtil.getInstance(base, "wallet_data");
        super.attachBaseContext(LocalManageUtil.setLocal(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil.onConfigurationChanged(getApplicationContext());
    }


    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;

        init();
        initTriple();
    }

    private void init() {
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        SharedPreferencesUtil.getInstance(baseApplication, "wallet_data");
        //初始化语言环境
        LocalManageUtil.setApplicationLanguage(this);
    }

    private void initTriple() {
        initLogger();
        initObjectBox();
        initOkGo();
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) 是否显示线程信息。默认 true
                .methodCount(0)         // (Optional) 要显示多少方法行。默认值2
                .methodOffset(7)        // (Optional) 隐藏内部方法调用，直到偏移量。默认 5
//                .logStrategy(customLog) // (Optional)更改日志策略以打印输出。默认LogCat
                .tag("-->smartwang log")   // (Optional)每个日志的全局标记。默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    private void initObjectBox() {
//        boxStore = MyObjectBox.builder().androidContext(this).build();
//        if (BuildConfig.DEBUG) {
//            new AndroidObjectBrowser(boxStore).start(this);
//        }
    }

    private void initOkGo(){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
//log打印级别，决定了log显示的详细程度
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
//log颜色级别，决定了log在控制台显示的颜色
            loggingInterceptor.setColorLevel(Level.INFO);
            builder.addInterceptor(loggingInterceptor);

            //使用sp保持cookie，如果cookie不过期，则一直有效
            builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));

            HttpHeaders headers = new HttpHeaders();
            headers.put("commonHeaderKey1", "commonHeaderValue1");

            OkGo.getInstance().init(this).setOkHttpClient(builder.build())
                    .addCommonHeaders(headers);
    }

//    public BoxStore getBoxStore(){
//        return boxStore;
//    }


    //static 代码段可以防止内存泄露
    static {//static 代码段可以防止内存泄露
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.bgcolor_refresh, R.color.fontcolor_refresh);//全局设置主题颜色
//                new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));////指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

}
