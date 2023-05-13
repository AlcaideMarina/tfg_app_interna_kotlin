package com.example.hueverianieto.ui.views.workersresources.fragment.workersdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.remote.InternalUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkerDetailViewModel @Inject constructor(
    val getInternalUserData: InternalUserData
) : ViewModel() {

    fun navigateToModifyWorker(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_workerDetailFragment_to_modifyWorkerFragment, bundle)
            ?: Log.e(
                WorkerDetailViewModel::class.simpleName,
                "Error en la navegación a modificar trabajador"
            )
    }

}