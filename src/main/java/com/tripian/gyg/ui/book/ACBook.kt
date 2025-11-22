package com.tripian.gyg.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.databinding.AcBookBinding
import com.tripian.gyg.util.datalistener.injectVM

/**
 * Created by semihozkoroglu on 28.01.2022.
 */
class ACBook : BaseActivity<AcBookBinding>() {

    override val binding: (LayoutInflater) -> AcBookBinding = AcBookBinding::inflate

    private val viewModel: ACBookVM by injectVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BookingPageData.data.date = intent.extras!!.getString("date", "")
        BookingPageData.data.tourId = intent.extras!!.getLong("tourId")
        BookingPageData.data.tourName = intent.extras!!.getString("title", "")
        BookingPageData.data.cityName = intent.extras!!.getString("cityName", "")
        BookingPageData.data.tourImage = intent.extras!!.getString("tourImage", "")

        viewModel.showDatePage()
    }

    override fun setListeners() {
        vi.imNavigation.setOnClickListener { viewModel.onClickedBack() }
    }

    override fun setReceivers() {
    }

    fun setPageTitle(title: Int) {
        vi.tvTitle.text = getString(title)
    }

    override fun onDestroy() {
        BookingPageData.destroy()
        super.onDestroy()
    }
}