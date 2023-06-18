package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.services.GetUserDataService
import javax.inject.Inject

class GetUserDataWithUidUseCase @Inject constructor(
    private val getClientDataService: GetUserDataService
) {

    suspend operator fun invoke(uid: String): InternalUserData? =
        getClientDataService.getUserData(uid)

}
