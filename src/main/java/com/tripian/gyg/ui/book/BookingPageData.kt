package com.tripian.gyg.ui.book

import com.tripian.gyg.repository.model.*

/**
 * Created by semihozkoroglu on 31.01.2022.
 */
class BookingPageData {
    var date: String = ""
    var tourId: Long = 0
    var tourName: String = ""
    var cityName: String = ""
    var tourImage: String = ""

    var bookable: Bookable? = null
    var bookingRes: BookingRes? = null
    var billing: Billing? = null
    var traveler: Traveler? = null
    var publicKey: String = ""

    var pickUp: String? = null
    var language: CondLanguage? = null
    var parameters: List<BookingParameters>? = null

    companion object {
        private var _obj: BookingPageData? = null

        val data: BookingPageData
            get() {
                if (_obj == null)
                    _obj = BookingPageData()

                return _obj!!
            }

        fun clearBooking() {
            _obj?.bookable = null
            _obj?.bookingRes = null
        }

        fun clearTraveler() {
            _obj?.billing = null
            _obj?.traveler = null
        }

        fun destroy() {
            _obj = null
        }
    }
}