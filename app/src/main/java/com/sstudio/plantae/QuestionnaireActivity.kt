package com.sstudio.plantae

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_questionnaire.*


class QuestionnaireActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire)

        val progDailog = ProgressDialog.show(
            this, "Loading",
            "Mohon tunggu...", true
        )
        val webSettings: WebSettings = wv_questionnaire.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        wv_questionnaire.loadUrl("https://docs.google.com/forms/d/1RfXw4Gjdv94HUGS-GY6TJh5DHSnVjVTsOuKupZQ0Orc")
        wv_questionnaire.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                progDailog.show()
                if (url.startsWith("intent://")) {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    if (intent != null) {
                        val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                        return if (fallbackUrl != null) {
                            webView.webChromeClient = WebChromeClient()
                            webView.loadUrl(fallbackUrl)
                            true
                        } else {
                            false
                        }
                    }
                }
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progDailog.dismiss()
            }
        }

    }
}