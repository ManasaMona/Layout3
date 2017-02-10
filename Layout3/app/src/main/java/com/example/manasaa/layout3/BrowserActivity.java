package com.example.manasaa.layout3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class BrowserActivity extends AppCompatActivity {
    private  String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        WebView webview = new WebView(this);
        setContentView(webview);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }else{
            url = getIntent().getStringExtra("url");
        }
        webview.loadUrl(url);
    }
}
