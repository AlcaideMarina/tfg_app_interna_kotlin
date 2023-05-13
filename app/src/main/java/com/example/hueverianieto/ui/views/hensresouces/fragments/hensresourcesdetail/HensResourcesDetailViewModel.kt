package com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.usecases.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HensResourcesDetailViewModel @Inject constructor(
    val homeUseCase: HomeUseCase
) : ViewModel() {

    fun navigateToModifyHensResources(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_hensResourcesDetailFragment_to_modifyHensResourcesFragment, bundle)
            ?: Log.e(
                HensResourcesDetailViewModel::class.simpleName,
                "Error en la navegación a modificar ticket de gallinas"
            )
    }

}
