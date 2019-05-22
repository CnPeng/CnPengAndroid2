package com.cnpeng.b_13_customUserAgent;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.utils.LogUtils;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/6/2:下午5:08
 * <p>
 * 说明：自定义 WebView 的 UserAgent
 */

public class CustomUserAgentActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_useragent);

        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.wv_customUserAgent);
        WebSettings webSettings = webView.getSettings();
        String defUA = webSettings.getUserAgentString();    //获取默认的UA 
        //        webSettings.setUserAgentString("CnPeng_Android_CustomUserAgent");   //设置自定义UA
        String newUA = webSettings.getUserAgentString();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://www.baidu.com");    //此处必须设置，如果不设置，下面的shouldOverrideUrlLoading 中的 loadUrl() 
        // 将无法跳转，即便强制指定了跳转链接

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;    //返回true 不跳转默认浏览器，如果false 则跳转到默认浏览器
            }
        });

        LogUtils.e("自定义UA", "默认UA--" + defUA + "/自定义UA--" + newUA);
    }
}
