package com.atome.merchant.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.atome.sdk.AtomeSDK
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

import okhttp3.OkHttpClient
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var responseBean: ResponseBean? = null
    private lateinit var etCallbackUrl: EditText
    private lateinit var btnJump: Button
    private lateinit var btnRequest: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvResult: TextView
    private var appPaymentUrl: String = ""
    private val domainMap = mutableMapOf<Int, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRequest = findViewById(R.id.btnRequest)
        progressBar = findViewById(R.id.progressBar)
        tvResult = findViewById(R.id.tvResult)
        btnJump = findViewById(R.id.btnJump)
        etCallbackUrl = findViewById(R.id.etUrl)
//        etCallbackUrl.setText(SCHEME_ATOME_DEMO)

        val callbackUrl = etCallbackUrl.text.toString()
        btnRequest.setOnClickListener {
            val request = buildRequest(callbackUrl.ifBlank { SCHEME_ATOME_DEMO })
            requestOrder(request)
        }

        //can be open Atome staging app
        domainMap[R.id.radioButtonStag] = STAGING_DOMAIN
        //can be open Atome release app
        domainMap[R.id.radioButtonRelease] = RELEASE_DOMAIN
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        btnJump.setOnClickListener {
            val domain = domainMap[radioGroup.checkedRadioButtonId]
            domain?.let {
                //replace domain in appPaymentUrl
                appPaymentUrl = replaceDomainInUrl(appPaymentUrl, domain)
            }
            AtomeSDK.handleUrl(appPaymentUrl)
        }

        findViewById<Button>(R.id.open_webView).setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

    }

    private fun requestOrder(request: Request) {
        val client: OkHttpClient = OkHttpClient.Builder()
            .build()
        progressBar.visibility = View.VISIBLE
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    tvResult.text = e.toString()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    runCatching {
                        progressBar.visibility = View.GONE
                        responseBean = Gson().fromJson(
                            response.body?.string(),
                            ResponseBean::class.java
                        )
                        //This is the appPaymentUrl you need to use to open the Atome app,
                        // but you should replace the domain (either the staging or release domain).
                        responseBean?.data?.appPaymentUrl?.let {
                            appPaymentUrl = it
                        }
                        tvResult.text = responseBean.toString()
                    }
                    Log.i(TAG, "responseBean $responseBean")
                }

            }
        })
    }

    private fun buildRequest(paymentResultUrl: String): Request {
        val requestBean =
            RequestBean("SGD", 1234, paymentResultUrl)
        val request = Request.Builder()
            .post(
                Gson().toJson(requestBean)
                    .toRequestBody(MediaType.toMediaType())
            )
            .url(orderApi).build()

        Log.i(TAG, "requestBean " + Gson().toJson(requestBean))
        return request
    }

    private fun replaceDomainInUrl(originalUrlString: String, newDomain: String): String {
        try {
            val url = URL(originalUrlString)
            val path = url.path // e.g., "/path/to/resource"
            return "$newDomain$path"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return originalUrlString
    }

    companion object {
        const val TAG = "MainActivity"
        const val MediaType = "application/json; charset=utf-8"
        const val orderApi = "https://demo-app-test.apaylater.net/api/orders"
        const val SCHEME_ATOME_DEMO = "atomedemo://appdemo.apaylater.net"

        //        const val SCHEME_HTTP = "https://appdemo.apaylater.net"
        const val STAGING_DOMAIN = "https://app.apaylater.net"
        const val RELEASE_DOMAIN = "https://app.apaylater.com"
    }
}