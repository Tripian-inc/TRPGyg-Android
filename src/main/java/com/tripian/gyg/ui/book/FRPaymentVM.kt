package com.tripian.gyg.ui.book

import adyen.com.adyencse.pojo.Card
import androidx.lifecycle.MutableLiveData
import com.tripian.gyg.R
import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.base.Tripian
import com.tripian.gyg.domain.GetConfiguration
import com.tripian.gyg.domain.Payment
import com.tripian.gyg.repository.model.PaymentBrand
import com.tripian.gyg.util.exception.ValidationException
import com.tripian.gyg.util.extensions.launch
import dialog
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.util.Calendar

class FRPaymentVM : BaseViewModel() {

    var showImagesListener = MutableLiveData<List<PaymentBrand>>()

    fun init() = launch {
        GetConfiguration(BookingPageData.data.billing?.countryCode ?: "").execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .catch {
                if (it is ValidationException) {
                    goBack()
                }

                throw it
            }
            .collect {
                val method = it.data?.methods?.find { it.name == "encrypted_credit_card" }

                if (method != null) {
                    BookingPageData.data.publicKey = method.publicKey ?: ""

                    showImagesListener.postValue(method.brands)
                }
            }
    }

    fun onClickedPay(cardOnName: String, cardNumber: String, month: String, year: String, cvc: String) = launch {
        val card: Card = Card.Builder()
            .setHolderName(cardOnName.trim())
            .setCvc(cvc.trim())
            .setExpiryMonth(month)
            .setExpiryYear(year)
            .setGenerationTime(Calendar.getInstance().time)
            .setNumber(cardNumber.replace(" ", "").trim())
            .build()

        val encryptedCard: String = card.serialize(BookingPageData.data.publicKey)

//        dialog {
//            titleText = "Success"
//            contentText = "Payment completed successfully"
//            positive = {
//                Tripian.getSuccess()?.invoke(
//                    PayShoppingRes().apply {
//                        status = "unconfirmed"
//                        traveler = BookingPageData.data.traveler
//                        billing = BookingPageData.data.billing
//                        bookings = PayBooking().apply {
//                            bookingId = BookingPageData.data.bookingRes?.bookingId
//                            bookingHash = BookingPageData.data.bookingRes?.bookingHash
//                            bookingStatus = "unconfirmed"
//                            shoppingCartId = BookingPageData.data.bookingRes?.shoppingCartId
//                            shoppingCartHash = BookingPageData.data.bookingRes?.shoppingCartHash
//                            bookable = BookingPageData.data.bookable
//                        }
//                        payInfo = PayInfo().apply {
//                            currency = "EUR"
//                            totalPrice = 15.15
//                            precouponPrice = 15.15
//                            paymentMethod = "cc"
//                            couponInfo = "test"
//                            invoiceReference = "GCI-0032724458"
//                        }
//                        tourName = BookingPageData.data.tourName
//                        cityName = BookingPageData.data.cityName
//                        tourImage = BookingPageData.data.tourImage
//                        shoppingCartId = BookingPageData.data.bookingRes?.shoppingCartId
//                        shoppingCartHash = BookingPageData.data.bookingRes?.shoppingCartHash
//                        bookingHash = BookingPageData.data.bookingRes?.bookingHash
//                    }
//                )
//
////                this@FRPaymentVM.finish()
//            }
//            positiveRes = R.string.ok
//        }

        Payment(BookingPageData.data.billing!!, BookingPageData.data.traveler!!, encryptedCard, BookingPageData.data.bookingRes!!.shoppingCartId!!)
            .execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .catch {
                if (it is ValidationException) {
                    goBack()
                }

                throw it
            }
            .collect {
                dialog {
                    titleText = Tripian.getLanguage()?.invoke("success") ?: "Success"
                    contentText = "Payment completed successfully"
                    positive = {
                        with(it) {
                            tourName = BookingPageData.data.tourName
                            cityName = BookingPageData.data.cityName
                            tourImage = BookingPageData.data.tourImage
                            shoppingCartId = BookingPageData.data.bookingRes?.shoppingCartId
                            shoppingCartHash = BookingPageData.data.bookingRes?.shoppingCartHash
                            bookingHash = BookingPageData.data.bookingRes?.bookingHash
                        }

                        Tripian.getSuccess()?.invoke(it)
                    }
                    positiveRes = R.string.ok
                }
            }
    }
}
