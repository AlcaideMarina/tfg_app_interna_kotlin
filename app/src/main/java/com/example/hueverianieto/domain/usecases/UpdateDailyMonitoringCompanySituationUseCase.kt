package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.UpdateFirestoreService
import javax.inject.Inject

class UpdateDailyMonitoringCompanySituationUseCase @Inject constructor(
    private val updateFirestoreService: UpdateFirestoreService
) {

    suspend operator fun invoke(dataMap: Map<String, Any?>, documentId: String): Boolean =
        updateFirestoreService.updateDocumentFirestore(dataMap, documentId, "farm_situation")

}
