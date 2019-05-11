package com.smartwang.charge.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.smartwang.charge.base.BaseActivity;
import com.smartwang.charge.help.webview.SonicJavaScriptInterface;
import com.smartwang.charge.help.webview.SonicRuntimeImpl;
import com.smartwang.charge.help.webview.SonicSessionClientImpl;
import com.smartwang.charge.utils.ToastUtil;
import com.smartwang.project.R;
import com.smartwang.charge.utils.StringUtil;
import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlActivity extends BaseActivity {

//    public static final int MODE_DEFAULT = 0;

    public static final int MODE_SONIC = 1;

//    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

//    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1;


    public final static String PARAM_URL = "param_url";
    public final static String PARAM_TITLE = "param_title";
    public final static String PARAM_MODE = "param_mode";

    private SonicSession sonicSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        Intent intent = getIntent();
        String url = intent.getStringExtra(PARAM_URL);
        int mode = intent.getIntExtra(PARAM_MODE, -1);
        String title = intent.getStringExtra(PARAM_TITLE);
        if (StringUtil.isEmpty(url) || -1 == mode) {
            finish();
            return;
        }
        setCentreTv(title);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }

        SonicSessionClientImpl sonicSessionClient = null;

        // if it's sonic mode , startup sonic session at first time
//        if (MODE_DEFAULT != mode) { // sonic mode
            SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
            sessionConfigBuilder.setSupportLocalServer(true);

            // if it's offline pkg mode, we need to intercept the session connection
//            if (MODE_SONIC_WITH_OFFLINE_CACHE == mode) {
//                sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
//                    @Override
//                    public String getCacheData(SonicSession session) {
//                        return null; // offline pkg does not need cache
//                    }
//                });
//
//                sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
//                    @Override
//                    public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
//                        return new OfflinePkgSessionConnection(HtmlActivity.this, session, intent);
//                    }
//                });
//            }

            // create sonic session and run sonic flow
            sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
            if (null != sonicSession) {
                sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
            } else {
                // this only happen when a same sonic session is already running,
                // u can comment following codes to feedback as a default mode.
                // throw new UnknownError("create session fail!");
                ToastUtil.showShort(R.string.text_html_toast_fail);

            }
//        }

        // start init flow ...
        // in the real world, the init flow may cost a long time as startup
        // runtime、init configs....
//        setContentView(R.layout.activity_html);

//        FloatingActionButton btnFab = (FloatingActionButton) findViewById(R.id.btn_refresh);
//        btnFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (sonicSession != null) {
//                    sonicSession.refresh();
//                }
//            }
//        });

        // init webview
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });

        WebSettings webSettings = webView.getSettings();

        // add java script interface
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.setJavaScriptEnabled(true);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "sonic");

        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(url);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroy();
    }


    private static class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final WeakReference<Context> context;

        public OfflinePkgSessionConnection(Context context, SonicSession session, Intent intent) {
            super(session, intent);
            this.context = new WeakReference<Context>(context);
        }

        @Override
        protected int internalConnect() {
            Context ctx = context.get();
            if (null != ctx) {
                try {
                    InputStream offlineHtmlInputStream = ctx.getAssets().open("sonic-demo-index.html");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }

        @Override
        protected String internalGetCustomHeadFieldEtag() {
            return SonicSessionConnection.CUSTOM_HEAD_FILED_ETAG;
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }
    }
}
