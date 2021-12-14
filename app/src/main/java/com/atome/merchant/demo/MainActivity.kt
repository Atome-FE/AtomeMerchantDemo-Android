package com.atome.merchant.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.atome.sdk.AtomeSDK
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import com.readystatesoftware.chuck.ChuckInterceptor

import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {

    var responseBean: ResponseBean? = null
    private lateinit var etUrl: EditText
    lateinit var btnJump: Button
    lateinit var btnRequest: Button
    lateinit var progressBar: ProgressBar
    lateinit var tvResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRequest = findViewById(R.id.btnRequest)
        progressBar = findViewById(R.id.progressBar)
        tvResult = findViewById(R.id.tvResult)
        btnJump = findViewById(R.id.btnJump)
        etUrl = findViewById(R.id.etUrl)
        etUrl.setText(scheme_atomedemo)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(this.applicationContext))
            .build()


        btnRequest.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val etText = etUrl.text.toString()
            val requestBean =
                RequestBean("SGD", 1234, etText)
            val request = Request.Builder()
                .post(
                    Gson().toJson(requestBean)
                        .toRequestBody(MediaType.toMediaType())
                )
                .url(orderApi).build()

            Log.i(TAG, "requestBean " + Gson().toJson(requestBean))

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
                               response.body!!.string(),
                               ResponseBean::class.java
                           )

                           tvResult.text = responseBean.toString()
                       }
                        Log.i(TAG, "responseBean $responseBean")
                    }

                }
            })

        }

        btnJump.setOnClickListener {
            responseBean?.data?.appPaymentUrl?.let {
                AtomeSDK.setPaymentUrl(it)
            }
        }

        findViewById<Button>(R.id.open_webView).setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }
    }

    companion object {
        const val TAG = "MainActivity"
        const val MediaType = "application/json; charset=utf-8"
        const val orderApi = "https://demo-app-test.apaylater.net/api/orders"
        const val scheme_atomedemo = "atomedemo://appdemo.apaylater.net"
        const val scheme_http = "https://appdemo.apaylater.net"
    }
}