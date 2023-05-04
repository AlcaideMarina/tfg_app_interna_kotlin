package com.example.hueverianieto.ui.views

import androidx.lifecycle.ViewModel
import com.example.hueverianieto.domain.usecases.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val homeUseCase: HomeUseCase) : ViewModel() {}
