package com.example.administrator.sharenebulaproject.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.ToastUtil;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Administrator on 2018/8/24.
 */

public class WebViewBuilder {

    public WebView web_view;
    public ProgressBar progressBar;
    public ToastUtil toastUtil;
    private String TAG = "WebViewBuilder";
    private ProgressDialog progressDialog;
    private Context context;
    private Handler handler;

    public WebViewBuilder(WebView web_view, ProgressDialog progressDialog, ToastUtil toastUtil, Context context, Handler handler) {
        this.web_view = web_view;
        this.progressDialog = progressDialog;
        this.toastUtil = toastUtil;
        this.context = context;
        this.handler = handler;
    }

    public void initWebView() {
        web_view.setWebChromeClient(webChromeClient);
        web_view.setWebViewClient(webViewClient);
        web_view.getSettings().setJavaScriptEnabled(true);//设置js可用
        web_view.addJavascriptInterface(new JSInterface(), "share");
        WebSettings webSettings = web_view.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        int fontSize = (int) context.getResources().getDimension(R.dimen.dp14);
//        webSettings.setDefaultFontSize(fontSize);

    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();
            //注意:必须要这一句代码:result.confirm()表示: 处理结果为确定状态同时唤醒WebCore线程 否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (progressBar != null)
                progressBar.setProgress(newProgress);
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
            if (progressDialog != null)
                progressDialog.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
            if (progressDialog != null)
                progressDialog.show();
        }

        @SuppressLint("WrongConstant")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.equals("http://www.google.com/")) {
                toastUtil.showToast("国内不能访问google,拦截该url");
                return true;//表示我已经处理过了
            }
//            return super.shouldOverrideUrlLoading(view, url);
            Intent intent = new Intent(context, PublicWebActivity.class);
            intent.setFlags(EventCode.EXTERNAL_LINKS);
            intent.putExtra("url", url);
            context.startActivity(intent);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();  // 接受所有网站的证书
        }
    };

    public void loadWebView(String html, boolean status) {
        if (status) {
            web_view.loadUrl(html);
            LogUtil.e(TAG, "loadUrl");
        } else {
//            web_view.loadData( html , "text/html; charset=UTF-8", null);//这种写法可以正确解码
            web_view.loadDataWithBaseURL(null, "<html><body>" + html + "</body></html>", "text/html", "utf-8", null);
            LogUtil.e(TAG, "loadDataWithBaseURL : " + "<html><body>" + html + "</body></html>");
        }
//        web_view.loadUrl("http://localhost:63343/untitled1/word_sign/wordSign.html");

//        web_view.loadDataWithBaseURL("about:blank","<!doctype html>\n" +
//                " <html lang=\"en\">\n" +
//                " <head>\n" +
//                " <style type=\"text/css\">\n" +
//                " img{\n" +
//                "     width:100% !important;\n" +
//                "     height:100% !important;\n" +
//                "  }\n" +
//                " </style>" +
//                " <meta charset=\"UTF-8\" name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
//                " <title>Document</title>\n" +
//                " </head>\n" +
//                " <body>"+"" +
//                StringEscapeUtils.unescapeHtml4(html)+
//                " </body>\n" +
//                "</html>","texe/html","utf-8", null);

    }

    private final class JSInterface {
        /**
         * 注意这里的@JavascriptInterface注解， target是4.2以上都需要添加这个注解，否则无法调用
         *
         * @param text
         */
        @JavascriptInterface
        public void newsshare(String text) {
            handler.sendEmptyMessage(0);
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void showJsText(String text) {
            web_view.loadUrl("javascript:jsText('" + text + "')");
        }
    }

    public void shareRefeshAdShow() {
        web_view.evaluateJavascript("javascript:showad()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                LogUtil.e(TAG,"调用成功");
            }
        });
    }


}
