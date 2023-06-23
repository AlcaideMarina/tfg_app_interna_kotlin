package com.example.hueverianieto.domain.usecases

import android.os.Parcelable
import com.example.hueverianieto.data.services.GetClientDataService
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val getClientDataService: GetClientDataService
) {

    suspend operator fun invoke(documentId: String, collection: String): Parcelable? =
        getClientDataService.getClientData(documentId, collection)

}
