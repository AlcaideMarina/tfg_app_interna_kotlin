package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.services.GetHensService
import javax.inject.Inject

class GetHensUseCase @Inject constructor(
    private val getHensService: GetHensService
) {

    suspend operator fun invoke(): List<HensResourcesData?>? = getHensService.getHens()

}
