package com.tripian.gyg.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.tripian.gyg.base.BaseActivity
import com.tripian.gyg.base.Tripian
import com.tripian.gyg.databinding.AcExperiencesBinding
import com.tripian.gyg.domain.model.ExperiencesItem
import com.tripian.gyg.util.datalistener.injectVM
import com.tripian.gyg.util.datalistener.startActivity
import com.tripian.gyg.util.extensions.getRequestEndDate
import com.tripian.gyg.util.extensions.getRequestStartDate
import com.tripian.gyg.util.extensions.observe

/**
 * Created by semihozkoroglu on 28.01.2022.
 */
class ACExperiences : BaseActivity<AcExperiencesBinding>() {

    override val binding: (LayoutInflater) -> AcExperiencesBinding = AcExperiencesBinding::inflate

    private val viewModel: ACExperiencesVM by injectVM()

    private var date = ""
    private var cityName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cityName = intent.extras?.getString("city") ?: ""
        date = intent.extras?.getString("date") ?: ""

        viewModel.getList(cityName, date.getRequestStartDate(), date.getRequestEndDate())
    }

    override fun setListeners() {
        vi.imNavigation.setOnClickListener { viewModel.onClickedBack() }
        vi.tvTitle.text = Tripian.getLanguage()?.invoke("trips.myTrips.itinerary.experiences")
    }

    override fun setReceivers() {
        observe(viewModel.onTourListener) {
            if (it.isNullOrEmpty()) {
                vi.rvList.isVisible = false
            } else {
                vi.rvList.isVisible = true

                vi.rvList.adapter = object : AdapterExperiences(this, it) {
                    override fun onClickedItem(item: ExperiencesItem) {
                        Tripian.getEvent()?.invoke("id:${item.id} - title:${item.title}")

                        startActivity(ACExperienceDetail::class, bundle = Bundle().apply {
                            putLong("tourId", item.id!!)
                            putString("date", date)
                            putString("cityName", cityName)
                        })
                    }
                }
            }
        }
    }
}