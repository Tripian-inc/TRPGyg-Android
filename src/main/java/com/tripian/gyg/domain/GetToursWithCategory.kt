package com.tripian.gyg.domain

import com.tripian.gyg.domain.model.ExperienceCategory
import com.tripian.gyg.domain.model.Experiences
import com.tripian.gyg.repository.model.isCategoryOk
import com.tripian.gyg.repository.model.isDurationOk
import com.tripian.gyg.repository.model.toExperiencesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by semihozkoroglu on 13.08.2020.
 */
class GetToursWithCategory(val cityName: String?, val categories: String? = null, val dateFrom: String, val dateTo: String) : UseCase<Experiences>() {

    override fun execute(): Flow<Experiences> {
        return service.getTours(
            lang, currency,
            q = cityName,
            categories,
            limit = 500,
            dateFrom = dateFrom,
            dateTo = dateTo
        ).map {
            val sightseeing = Experiences().apply {
                title = ExperienceCategory.SIGHT_SEEING.names
            }

            it.data?.tours?.forEach { tour ->
                if (tour.isDurationOk()) {
                    sightseeing.items.add(tour.toExperiencesItem())
                }
            }

            sightseeing
        }
    }
}