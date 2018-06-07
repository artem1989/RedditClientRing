package com.wix.redditclient;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Created by Backbase R&D B.V on 29/06/16.
 */
public class WebAlertDialog extends AlertDialog {

    private ProgressBar progressBar;
    private View webAlertView;
    private ImageButton webReload;
    private TextView webTitle;
    private WebView webView;

    public WebAlertDialog(Context context) {
        super(context);
        webAlertView = LayoutInflater.from(context).inflate(R.layout.web_alert_dialog, null);
    }

    public void initWithURL(String url){

        //Close button
        ImageButton webClose = (ImageButton) webAlertView.findViewById(R.id.web_close);
        webClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        webTitle = (TextView) webAlertView.findViewById(R.id.web_title);
        progressBar = (ProgressBar) webAlertView.findViewById(R.id.web_progress_bar);


        webView = (WebView) webAlertView.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                webReload.setVisibility(View.GONE);
//                boolean isNetworkAvailable = new DeviceUtils(getContext()).isNetworkAvailable();
//                if(!isNetworkAvailable){
//                    webReload.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webReload.setVisibility(View.VISIBLE);
            }


        });

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webTitle.setText(view.getTitle());
                if(newProgress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }

                progressBar.setProgress(newProgress);
                if(newProgress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }

            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
                cancel();
            }
        });

        webReload = (ImageButton) webAlertView.findViewById(R.id.web_reload);
        webReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

        webView.loadUrl(url);
        setView(webAlertView);
        show();
    }

}
