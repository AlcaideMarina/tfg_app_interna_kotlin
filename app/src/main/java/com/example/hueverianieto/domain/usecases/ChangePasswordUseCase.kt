package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.ChangePasswordService
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val changePasswordService: ChangePasswordService
) {

    suspend operator fun invoke(oldPass: String, newPass: String): Boolean {
        val result = changePasswordService.changePassword(oldPass, newPass)
        return result.isSuccess
    }

}
