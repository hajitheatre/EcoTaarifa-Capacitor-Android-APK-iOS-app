package com.otaarifa.app;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.splashscreen.SplashScreen;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    private volatile boolean pageVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Android 12 splash: keep it until web content is visible
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> !pageVisible);

        super.onCreate(savedInstanceState);

        if (getBridge() != null && getBridge().getWebView() != null) {
            WebView wv = getBridge().getWebView();

            // Keep background consistent (prevents flashes)
            wv.setBackgroundColor(Color.parseColor("#309365"));

            // Release splash when the first pixels are visible
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageCommitVisible(WebView view, String url) {
                    pageVisible = true;
                    super.onPageCommitVisible(view, url);
                }
            });
        } else {
            // Safety: don't block splash forever
            pageVisible = true;
        }
    }
}