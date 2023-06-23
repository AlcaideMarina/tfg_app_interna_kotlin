package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.services.GetFeedService
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(
    private val getFeedService: GetFeedService
) {

    suspend operator fun invoke(): List<FeedResourcesData?>? = getFeedService.getFeed()

}
