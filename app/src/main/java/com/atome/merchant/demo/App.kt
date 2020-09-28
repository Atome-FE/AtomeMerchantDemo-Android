package com.atome.merchant.demo

import android.app.Application
import com.atome.sdk.AtomeSDK

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AtomeSDK.init(this)
    }
}