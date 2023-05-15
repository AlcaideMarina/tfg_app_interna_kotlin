package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.data.services.GetBoxesAndCartonsService
import javax.inject.Inject

class GetBoxesAndCartonsUseCase @Inject constructor(
    private val getBoxesAndCartonsService: GetBoxesAndCartonsService
) {

    suspend operator fun invoke() :List<BoxesAndCartonsResourcesData?>? =
        getBoxesAndCartonsService.getBoxesAndCartons()

}
