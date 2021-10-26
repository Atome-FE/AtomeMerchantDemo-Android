package com.atome.merchant.demo

import java.io.Serializable

data class RequestBean(val currency: String, val amount: Number, val paymentResultUrl: String) :
    Serializable