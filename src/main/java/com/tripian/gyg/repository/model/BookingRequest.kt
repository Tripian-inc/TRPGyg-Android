package com.tripian.gyg.repository.model

import com.google.gson.annotations.SerializedName
import com.tripian.gyg.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 31.12.2021.
 */
class BookingRequest : BaseModel() {
    @SerializedName("base_data")
    var base: BaseData? = null
    var data: BookingData? = null
}

class BaseData : BaseModel() {
    var currency: String? = null

    @SerializedName("cnt_language")
    var lang: String? = null
}

class BookingData : BaseModel() {
    var booking: BookingModel? = null
}

class BookingModel : BaseModel() {
    var bookable: Bookable? = null
}

class Bookable : BaseModel() {
    @SerializedName("tour_id")
    var tourId: Long? = null
    @SerializedName("option_id")
    var optionId: Long? = null
    @SerializedName("datetime_type")
    var dateTimeType: String? = null
    @SerializedName("datetime")
    var dateTime: String? = null
    @SerializedName("valid_until")
    var validUntil: String? = null

    @SerializedName("cancellation_policy_text")
    var cancellableText: String? = null
    @SerializedName("currently_cancellable_at_no_fee")
    var cancellable: Boolean? = null

    @SerializedName("booking_parameters")
    var parameters: ArrayList<BookingParameters>? = null
    var price: Double = 0.0

    @Transient
    var optionTitle: String? = null

    var categories: List<BookingCategory>? = null
}

class BookingParameters : BaseModel() {
    var name: String? = null

    @SerializedName("value_1")
    var value1: String? = null

    @SerializedName("value_2")
    var value2: String? = null

    var description: String? = null
    var mandatory: Boolean? = null
}

class BookingCategory : BaseModel() {
    @SerializedName("number_of_participants")
    var participants: Int? = null

    @SerializedName("category_id")
    var categoryId: Long? = null

    @Transient
    var categoryName: String? = null
}