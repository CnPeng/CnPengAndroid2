package com.cnpeng.a_01_WebView01;

import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/5. WebView 测试页面1
 * <p>
 * 当网络出错时，给出一个自定义的错误提示页面 华为荣耀 H30_L02  4.4.2 系统上不好使，为啥？
 */

public class WebViewTestActivity01 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
                setContentView(R.layout.activity_webviewtest01);

                init();
    }

    private void init() {
        WebView webView = (WebView) findViewById(R.id.webviewTest1);
        final String url_ = "http://blog.csdn.net/north1989/article/details/53471439";


        webView.setWebViewClient(new WebViewClient() {  //监测加载状态
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);      //指明要加载的页面
                return true;        //返回true表示强制使用当前webView 显示网页内容，而不跳转到默认浏览器
            }

            @Override       //当发生错误时调用此方法
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                // 自定义错误提示页面，灰色背景色，带有文字，文字不要输汉字，由于编码问题汉字会变乱码              
                String errorHtml = "<html><body style='background-color:#e5e5e5;'><h1>Page Not Found " + 
                        "!</h1></body></html>";
                view.loadData(errorHtml, "text/html", "UTF-8");

                //如何加载本地已有的错误页面呢？？？？
                //                view.loadUrl(" file:///android_raw/error.html");
            }
        });

        webView.loadUrl(url_);
    }
}
