package com.ptit_booth_chekin.project.ui.screen.home.screen_resource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.data.event.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ResourceUIState {
    data object Loading : ResourceUIState()
    data class Success(val documents: List<DocumentItem>) : ResourceUIState()
    data class Error(val message: String) : ResourceUIState()
}

@HiltViewModel
class ResourceVM @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ResourceUIState>(ResourceUIState.Loading)
    val uiState: StateFlow<ResourceUIState> = _uiState.asStateFlow()

    fun fetchResources(eventId: String) {
        viewModelScope.launch {
            _uiState.value = ResourceUIState.Loading
            when (val result = eventRepository.getEventResources(eventId)) {
                is APIResult.Success -> {
                    val documents = result.data.map { DocumentItem.fromResourceItem(it) }
                    _uiState.value = ResourceUIState.Success(documents)
                }
                is APIResult.Error -> {
                    _uiState.value = ResourceUIState.Error(result.message)
                }
            }
        }
    }
}
