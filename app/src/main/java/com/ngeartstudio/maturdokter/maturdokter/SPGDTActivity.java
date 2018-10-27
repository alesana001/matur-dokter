package com.ngeartstudio.maturdokter.maturdokter;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class SPGDTActivity extends AppCompatActivity {
    private ProgressBar spinner;
    private WebView webview;
    private SwipeRefreshLayout swipe;
    private String currentURL = "http://spgdt.klatenkab.go.id/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spgdt);

        webview =(WebView)findViewById(R.id.web_spgdt);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl(currentURL);
        webview.setWebViewClient(new MyWebViewClient());

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webview.loadUrl(currentURL);
            }
        });
    }

    public class MyWebViewClient extends WebViewClient{

        @Override
        public void onPageFinished(WebView view, String url) {
            swipe.setRefreshing(false);
            currentURL = url;
            super.onPageFinished(view, url);
            spinner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed (){

        if (webview.isFocused() && webview.canGoBack()) {
            webview.goBack();

        }else{
            super.onBackPressed();
        }

    }
}
