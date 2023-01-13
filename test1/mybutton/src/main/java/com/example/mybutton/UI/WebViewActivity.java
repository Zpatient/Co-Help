package com.example.mybutton.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mybutton.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWv = findViewById(R.id.Wv);
        //加载本地html
        mWv.loadUrl("file:///android_asset/test_as.html");
        //加载网络URL
        mWv.getSettings().setJavaScriptEnabled(true);//打开不了再写这2行，
        mWv.setWebViewClient(new MyWebViewClient());
        mWv.loadUrl("https://m.baidu.com");//m-mobile

    }
    class MyWebViewClient extends WebViewClient{
        //在内部加载url不是在外部加载
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("WebView","onPageStarted");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("WebView","onPageFinished");
        }
    }
    //点击返回键回到上一级
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && mWv.canGoBack()){
            mWv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}