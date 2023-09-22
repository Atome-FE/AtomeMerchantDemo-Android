package com.atome.merchant.demo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import com.atome.sdk.core.TakePictureLauncher

class WebViewActivity : AppCompatActivity() {

    lateinit var takePictureLauncher: TakePictureLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        // create a TakePictureLauncher
        takePictureLauncher = TakePictureLauncher.create(this)

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
//        webView.settings.setAppCacheEnabled(true)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
//                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
                return takePictureLauncher.onShowFileChooser(
                    webView,
                    filePathCallback,
                    fileChooserParams
                )
            }
        }

        findViewById<Button>(R.id.openUrl).setOnClickListener {
            webView.loadUrl("file:///android_asset/test.html")
        }
    }
}

