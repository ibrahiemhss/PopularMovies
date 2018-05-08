package com.nanodegree.ibrahim.popularmovies2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_WEBVIEW_URL;

/**
 *
 * Created by ibrahim on 06/05/18.
 */

public class WebViewActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        Bundle bundle = getIntent().getExtras();
WebView webView=findViewById(R.id.tv_vedioshow);


        assert bundle != null;
        if(bundle.getString(EXTRA_WEBVIEW_URL)!= null)
        {
            String mVideoUrl = bundle.getString(EXTRA_WEBVIEW_URL);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true); // allow pinch to zooom
            webView.getSettings().setDisplayZoomControls(false);
            webView.setWebViewClient(new WebViewClient() {
                @SuppressWarnings("deprecation")
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(WebViewActivity.this, description, Toast.LENGTH_SHORT).show();
                }
                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                }
            });
            webView.loadUrl(mVideoUrl);

        }

    }


}