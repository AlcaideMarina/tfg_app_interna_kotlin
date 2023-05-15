package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.services.NewDocumentService
import com.example.hueverianieto.utils.MaterialUtils
import javax.inject.Inject

class NewFeedResourcesUseCase @Inject constructor(
    private val newDocumentService: NewDocumentService
){

    suspend operator fun invoke(feedResourcesData: FeedResourcesData) : Boolean {
        val dataMap = MaterialUtils.feedParcelableToMap(feedResourcesData)
        return when(newDocumentService.newDocument("material_feed", dataMap).getOrNull()) {
            null -> false
            else -> true
        }
    }

}
