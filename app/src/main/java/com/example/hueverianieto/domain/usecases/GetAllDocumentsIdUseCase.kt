package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.GetAllDocumentsIdService
import javax.inject.Inject

class GetAllDocumentsIdUseCase @Inject constructor(
    private val getAllDocumentsIdUseCase: GetAllDocumentsIdService
) {

    suspend operator fun invoke(collection: String) : List<String?>? =
        getAllDocumentsIdUseCase.getAllDocumentsId(collection)

}
