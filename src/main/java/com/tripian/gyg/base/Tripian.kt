package com.tripian.gyg.base

import com.tripian.gyg.repository.model.PayShoppingRes

/**
 * Created by semihozkoroglu on 31.01.2022.
 */
class Tripian {

    companion object {
//        private var context: Context? = null
        private var gygApiKey: String = ""
        private var success: ((PayShoppingRes) -> Unit)? = null
        private var getLanguage: ((String) -> String)? = null
        private var event: ((String) -> Unit)? = null

        var firstName: String = ""
        var lastName: String = ""
        var email: String = ""
        var allText: String = ""

        fun init(apiKey: String) {
            gygApiKey = apiKey
        }

        fun apiKey() = gygApiKey

        fun setGetLanguage(task: (String) -> String) {
            getLanguage = task
        }

        fun setSuccessListener(task: ((PayShoppingRes) -> Unit)) {
            success = task
        }

        fun sendAnalyticsEvent(task: ((String) -> Unit)) {
            event = task
        }

        fun getSuccess() = success

        fun getEvent() = event

        fun getLanguage() = getLanguage
    }
}