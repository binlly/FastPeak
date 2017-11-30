package com.binlly.gankee.business.web

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.binlly.gankee.R
import com.binlly.gankee.base.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

/**
 * Created by yy on 2017/11/17.
 */
class WebActivity: BaseActivity() {

    private var url: String? = null
    private var title: String? = null

    override fun getContentView(): Int = R.layout.activity_web

    override fun handleIntent(intent: Intent): Boolean {
        title = intent.getStringExtra("title")
        if (intent.hasExtra("url")) {
            url = intent.getStringExtra("url")
            if (url.isNullOrEmpty()) return false
            return true
        }
        return false
    }

    override fun initView(savedInstanceState: Bundle?) {
        initWebView(webview)
        getToolbar().title = title
        webview.loadUrl(url)
    }

    private fun initWebView(webview: WebView) {
        webview.settings.javaScriptEnabled = true
        webview.settings.builtInZoomControls = true
        webview.settings.domStorageEnabled = true
        webview.settings.setGeolocationEnabled(true)
        webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webview.settings.useWideViewPort = true
        webview.setLayerType(View.LAYER_TYPE_NONE, null)
        webview.webChromeClient = WebChromeClient()
        webview.webViewClient = WebViewClient()
    }

    override fun isNeedToolbar(): Boolean = true
}