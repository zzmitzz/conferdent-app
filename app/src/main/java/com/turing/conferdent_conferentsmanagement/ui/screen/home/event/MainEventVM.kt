package com.turing.conferdent_conferentsmanagement.ui.screen.home.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.data.event.EventDetail
import com.turing.conferdent_conferentsmanagement.data.event.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject


sealed class MainEventVMState {
    data class Success(val event: EventDetail) : MainEventVMState()
    data class Error(val message: String) : MainEventVMState()
    object Loading : MainEventVMState()
}


@HiltViewModel
class MainEventVM @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainEventVMState>(MainEventVMState.Loading)
    val uiState: StateFlow<MainEventVMState> = _uiState

    fun fetchEventDetail(
        eventID: String?
    ){
        viewModelScope.launch(Dispatchers.IO) {
            if (eventID == null) {
                _uiState.value = (MainEventVMState.Error("Event ID is not valid"))
            } else {
                when (val result = eventRepository.getEventDetail(eventID)) {
                    is APIResult.Success -> {
                        _uiState.value = (MainEventVMState.Success(result.data))
                    }

                    is APIResult.Error -> {
                        _uiState.value = (MainEventVMState.Error(result.message))
                    }
                }
            }
        }
    }
}