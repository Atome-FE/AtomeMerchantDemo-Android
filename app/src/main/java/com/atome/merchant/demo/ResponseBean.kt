package com.atome.merchant.demo

import java.io.Serializable

data class ResponseBean(val code: String?, val message: String?, val data: DataBean?) : Serializable

data class DataBean(
    val referenceId: String?,
    val currency: String?,
    val amount: Number?,
    val refundableAmount: String?,
    val status: String?,
    val redirectUrl: String?,
    val qrCodeUrl: String?,
    val qrCodeContent: String?,
    val merchantReferenceId: String?,
    val appPaymentUrl: String?,
    val extra: String?
) : Serializable