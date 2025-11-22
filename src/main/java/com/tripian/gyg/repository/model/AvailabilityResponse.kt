package com.tripian.gyg.repository.model

import com.google.gson.annotations.SerializedName
import com.tripian.gyg.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 31.12.2021.
 */
class AvailabilityResponse : BaseModel() {
    val data: AvailabilityData? = null
}

open class AvailabilityData : BaseModel() {
    val availabilities: List<Availability>? = null
}

open class Availability : BaseModel() {
    @SerializedName("start_time")
    val startTime: String? = null

    @SerializedName("pricing_id")
    val pricingId: Long? = null
    val vacancies: Long? = null
}

// ---- Tour option
class TourOptionResponse : BaseModel() {
    val data: TourOptionData? = null
}

open class TourOptionData : BaseModel() {
    @SerializedName("tour_options")
    val options: List<TourOption>? = null
}

open class TourOption : BaseModel() {
    @SerializedName("option_id")
    val optionId: Long? = null

    @SerializedName("tour_id")
    val tourId: Long? = null

    @SerializedName("pick_up")
    val pickUp: String? = null

    val title: String? = null
    val description: String? = null

    @SerializedName("meeting_point")
    val meetingPoint: String? = null

    val duration: String? = null

    @SerializedName("duration_unit")
    val durationUnit: String? = null

    @SerializedName("cond_language")
    val language: CondLanguage? = null

    @SerializedName("booking_parameter")
    val bookingParameter: List<BookingParameters>? = null

    val price: TourPriceData? = null
}

open class CondLanguage : BaseModel() {
    @SerializedName("language_audio")
    val audio: List<String>? = null

    @SerializedName("language_booklet")
    val booklet: List<String>? = null

    @SerializedName("language_live")
    val live: List<String>? = null
}

// ---- Option detail

class OptionDetailResponse : BaseModel() {
    val data: OptionDetailData? = null
}

open class OptionDetailData : BaseModel() {
    val pricing: List<OptionDetail>? = null
}

open class OptionDetail : BaseModel() {
    @SerializedName("pricing_id")
    val pricingId: Long? = null

    val categories: List<OptionCategory>? = null
}

open class OptionCategory : BaseModel() {
    val id: Long? = null
    val name: String? = null

    @SerializedName("min_age")
    val minAge: Int? = null

    @SerializedName("max_age")
    val maxAge: Int? = null

    @SerializedName("stand_alone")
    val standAlone: Boolean? = null

    val scale: List<CategoryScale>? = null
}

open class CategoryScale : BaseModel() {
    @SerializedName("min_participants")
    val minParticipants: Int? = null

    @SerializedName("max_participants")
    val maxParticipants: Int? = null

    @SerializedName("retail_price")
    val retailPrice: Double? = null
}
