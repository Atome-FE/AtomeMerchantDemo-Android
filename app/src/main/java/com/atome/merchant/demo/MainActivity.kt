package com.atome.merchant.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.atome.sdk.AtomeSDK
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException


class MainActivity : AppCompatActivity() {

    var responseBean: ResponseBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUrl.setText(scheme_atomedemo)
        val client = OkHttpClient()

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
                        progressBar.visibility = View.GONE
                        responseBean = Gson().fromJson(
                            response.body!!.string(),
                            ResponseBean::class.java
                        )

                        tvResult.text = responseBean.toString()
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
    }

    companion object {
        const val TAG = "MainActivity"
        const val MediaType = "application/json; charset=utf-8"
        const val orderApi = "https://demo-app-test.apaylater.net/api/orders"
        const val scheme_atomedemo = "atomedemo://appdemo.apaylater.net"
        const val scheme_http = "https://appdemo.apaylater.net"
    }
}