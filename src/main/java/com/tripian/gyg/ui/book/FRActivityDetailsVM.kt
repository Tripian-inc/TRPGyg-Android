package com.tripian.gyg.ui.book

import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.domain.Booking
import com.tripian.gyg.ui.book.model.Language
import com.tripian.gyg.util.extensions.launch
import com.tripian.gyg.util.extensions.navigateToFragment
import com.tripian.gyg.util.fragment.AnimationType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class FRActivityDetailsVM : BaseViewModel() {

    fun onClickedLanguage(languages: ArrayList<Language>) {
        navigateToFragment(FRLangSelect.newInstance(languages))
    }

    fun onClickedOk() = launch {
        if (BookingPageData.data.bookable != null) {
            Booking(BookingPageData.data.bookable!!).execute()
                .onStart { loading { true } }
                .onCompletion { loading { false } }
                .collect {
                    BookingPageData.data.bookingRes = it.data?.bookings

                    navigateToFragment(FRBilling.newInstance(), animation = AnimationType.ENTER_FROM_RIGHT)
                }
        }
    }
}
