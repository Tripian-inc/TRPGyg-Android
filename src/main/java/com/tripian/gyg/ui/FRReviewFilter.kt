package com.tripian.gyg.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.tripian.gyg.base.BaseBottomDialogFragment
import com.tripian.gyg.base.Tripian
import com.tripian.gyg.databinding.FrTourReviewFilterBinding

/**
 * Created by semihozkoroglu on 19.08.2020.
 */
class FRReviewFilter : BaseBottomDialogFragment<FrTourReviewFilterBinding>() {

    override val binding: (LayoutInflater) -> FrTourReviewFilterBinding = FrTourReviewFilterBinding::inflate

    companion object {
        fun newInstance(): FRReviewFilter {
            return FRReviewFilter()
        }
    }

    override fun setListeners() {
        super.setListeners()
        vi.tvTitle.text = Tripian.getLanguage()?.invoke("sort_by")
        vi.tvNewest.text = Tripian.getLanguage()?.invoke("newest_first")
        vi.tvHighRated.text = Tripian.getLanguage()?.invoke("highest_rated")
        vi.tvLowRated.text = Tripian.getLanguage()?.invoke("lowest_rated")
        vi.btnCancel.text = Tripian.getLanguage()?.invoke("trips.deleteTrip.cancel")

        vi.tvNewest.setOnClickListener {
            setResult(0)
            dismiss()
        }
        vi.tvHighRated.setOnClickListener {
            setResult(1)
            dismiss()
        }
        vi.tvLowRated.setOnClickListener {
            setResult(2)
            dismiss()
        }
        vi.btnCancel.setOnClickListener { dismiss() }
        vi.btnCancel.text
    }

    private fun setResult(sortType: Int) {
        setFragmentResult("ReviewFilter", Bundle().apply {
            putInt("sortType", sortType)
        })
    }
}