package com.example.hueverianieto.ui.views.hensresouces.fragments.newhensresources

import androidx.lifecycle.ViewModel
import com.example.hueverianieto.domain.usecases.NewHensResourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewHensResourcesViewModel @Inject constructor(
    val newHensResourcesUseCase: NewHensResourcesUseCase
) : ViewModel() {
}
