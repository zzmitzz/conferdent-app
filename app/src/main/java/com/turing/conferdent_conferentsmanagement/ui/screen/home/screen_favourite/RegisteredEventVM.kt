package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.data.event.EventDetail
import com.turing.conferdent_conferentsmanagement.data.event.EventRepository
import com.turing.conferdent_conferentsmanagement.utils.parseTimeFromServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject



enum class RegisteredTab {
    PASSED,
    UPCOMING
}


sealed class RegisteredEventVMState {
    data class Success(
        val currentTab: RegisteredTab = RegisteredTab.UPCOMING,
        val currentEvent: EventDetail? = null,
        val listEvent: List<EventDetail> = emptyList()
    ) : RegisteredEventVMState()
    data class Error(val message: String) : RegisteredEventVMState()
    object Loading : RegisteredEventVMState()
}

@HiltViewModel
class RegisteredEventVM @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<RegisteredEventVMState>(RegisteredEventVMState.Loading)
    val uiState: StateFlow<RegisteredEventVMState> = _uiState.asStateFlow()

    init {
        fetchRegisteredFlow()
    }

    private fun fetchRegisteredFlow() = viewModelScope.launch {
        val result = withContext(Dispatchers.IO) {
            eventRepository.getRegisteredEvent()
        }
        when (result) {
            is APIResult.Success -> {
                _uiState.value = RegisteredEventVMState.Success(
                    currentEvent = result.data.find {
                        val now = LocalDateTime.now()
                        val eventStart = parseTimeFromServer(it.startTime!!)
                        val eventEnd = parseTimeFromServer(it.endTime!!)
                        eventStart.isBefore(now) && eventEnd.isAfter(now)
                    },
                    listEvent = result.data
                )
            }
            is APIResult.Error -> {
                _uiState.value = RegisteredEventVMState.Error(result.message)
            }
        }
    }
}