package com.tripian.gyg.ui

import androidx.lifecycle.MutableLiveData
import com.tripian.gyg.base.BaseViewModel
import com.tripian.gyg.domain.GetTourDetail
import com.tripian.gyg.domain.model.ExperienceDetail
import com.tripian.gyg.util.extensions.launch
import dialog
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException

class ACExperienceDetailVM : BaseViewModel() {

    var experience: ExperienceDetail? = null
    var onTourDetailListener = MutableLiveData<ExperienceDetail>()

    fun getTours(tourId: Long) = launch {
        GetTourDetail(tourId)
            .execute()
            .onStart { loading { true } }
            .onCompletion { loading { false } }
            .catch { e ->
                if (e is HttpException) {
                    e.response()?.errorBody()?.string()?.let {
                        try {
                            val error = com.google.gson.Gson().fromJson(it, com.tripian.gyg.repository.model.ErrorResponse::class.java)

                            dialog {
                                titleResId = com.tripian.gyg.R.string.error_title
                                contentText = error?.errors?.get(0)?.errorMessage ?: e.message
                                positive = { goBack() }
                            }
                        } catch (e: Exception) {
                            dialog {
                                titleResId = com.tripian.gyg.R.string.error_title
                                contentText = e.message ?: e.localizedMessage
                                positive = { goBack() }
                            }
                        }
                    } ?: run {
                        dialog {
                            titleResId = com.tripian.gyg.R.string.error_title
                            contentText = e.message ?: e.localizedMessage
                            positive = { goBack() }
                        }
                    }
                } else {
                    throw e
                }
            }
            .collect {
                experience = it
                onTourDetailListener.postValue(it)
            }
    }

    fun onClickedBack() {
        goBack()
    }
}
