package com.turing.conferdent_conferentsmanagement.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow






abstract class BaseViewModelMVVM<T> : ViewModel() {
    abstract var _uiState: MutableStateFlow<T>
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    protected fun updateState(state: T) {
        _uiState.value = state
    }

}