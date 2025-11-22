package com.tripian.gyg.repository.model

import com.google.gson.annotations.SerializedName
import com.tripian.gyg.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 31.01.2022.
 */
class PaymentRequest : BaseModel() {
    @SerializedName("base_data")
    var base: BaseData? = null
    var data: PaymentData? = null
}

class PaymentData : BaseModel() {
    @SerializedName("shopping_cart")
    var shoppingCart: PayShopping? = null
}

class PayShopping : BaseModel() {
    var billing: Billing? = null
    var traveler: Traveler? = null
    var payment: PaymentModel? = null
    @SerializedName("shopping_cart_id")
    var shoppingCartId: Long? = null
}

class PaymentModel : BaseModel() {
    @SerializedName("encrypted_credit_card")
    var card: PaymentCard? = null
}

class PaymentCard : BaseModel() {
    var format = "adyen"
    var data: String? = null
}

class Billing : BaseModel() {
    @SerializedName("country_code")
    var countryCode: String? = null

    @SerializedName("first_name")
    var firstName: String? = null

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("phone_number")
    var phoneNumber: String? = null
    var email: String? = null

    val is_company = false
    val company_name = "Tripian"
    val invoice = false
    var address_line_1: String? = null
    var address_line_2: String? = null
    var city: String? = null
    var postal_code: String? = null
    var state: String? = null
    val salutation_code = "m"
}

class Traveler : BaseModel() {
    @SerializedName("first_name")
    var firstName: String? = null

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("phone_number")
    var phoneNumber: String? = null
    var email: String? = null

    val salutation_code = "m"
}
