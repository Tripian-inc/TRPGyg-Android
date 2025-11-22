package com.tripian.gyg.repository.model

import com.google.gson.annotations.SerializedName
import com.tripian.gyg.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 31.01.2022.
 */
class PaymentConfigurationResponse : BaseModel() {
    val data: PaymentConfigurationData? = null
}

open class PaymentConfigurationData : BaseModel() {
    @SerializedName("payment_methods")
    val methods: List<PaymentMethod>? = null
}

open class PaymentMethod : BaseModel() {
    val name: String? = null

    @SerializedName("public_key")
    val publicKey: String? = null
    val brands: List<PaymentBrand>? = null
}

open class PaymentBrand : BaseModel() {
    val code: Int? = null
    val name: String? = null
}

class PayResponse : BaseModel() {
    val data: PayData? = null
}

open class PayData : BaseModel() {
    @SerializedName("shopping_cart")
    val shopping: PayShoppingRes? = null
}

open class PayShoppingRes : BaseModel() {
    @SerializedName("status")
    var status: String? = null

    var traveler: Traveler? = null
    var billing: Billing? = null
    @SerializedName("bookings")
    var bookings: PayBooking? = null
    @SerializedName("payment_info")
    var payInfo: PayInfo? = null


    @Transient
    var tourName: String? = null
    @Transient
    var cityName: String? = null
    @Transient
    var tourImage: String? = null
    @Transient
    var shoppingCartId: Long? = null
    @Transient
    var shoppingCartHash: String? = null
    @Transient
    var bookingHash: String? = null
}

open class PayInfo : BaseModel() {
    @SerializedName("payment_currency")
    var currency: String? = null
    @SerializedName("total_price")
    var totalPrice: Double? = null
    @SerializedName("precoupon_price")
    var precouponPrice: Double? = null
    @SerializedName("payment_method")
    var paymentMethod: String? = null
    @SerializedName("coupon_info")
    var couponInfo: String? = null
    @SerializedName("invoice_reference")
    var invoiceReference: String? = null
}

open class PayBooking : BaseModel() {
    @SerializedName("booking_id")
    var bookingId: Long? = null
    @SerializedName("booking_hash")
    var bookingHash: String? = null
    @SerializedName("booking_status")
    var bookingStatus: String? = null
    @SerializedName("shopping_cart_id")
    var shoppingCartId: Long? = null
    @SerializedName("shopping_cart_hash")
    var shoppingCartHash: String? = null
    @SerializedName("bookable")
    var bookable: Bookable? = null
}