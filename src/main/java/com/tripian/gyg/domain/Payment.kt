package com.tripian.gyg.domain

import com.tripian.gyg.domain.model.BaseModel
import com.tripian.gyg.repository.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class Payment(var billing: Billing, var traveler: Traveler, var encryptedCard: String, var shoppingCartId: Long) : UseCase<PayShoppingRes>() {

    override fun execute(): Flow<PayShoppingRes> {
        return service.pay(PaymentRequest().apply {
            base = BaseData().apply {
                currency = this@Payment.currency
                lang = this@Payment.lang
            }
            data = PaymentData().apply {
                shoppingCart = PayShopping().apply {
                    billing = this@Payment.billing
                    traveler = this@Payment.traveler
                    payment = PaymentModel().apply {
                        this.card = PaymentCard().apply {
                            data = encryptedCard
                        }
                    }
                    shoppingCartId = this@Payment.shoppingCartId
                }
            }
        }).map {
            it.data?.shopping ?: PayShoppingRes()
        }
    }
}