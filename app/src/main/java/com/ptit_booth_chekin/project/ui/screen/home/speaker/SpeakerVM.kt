package com.ptit_booth_chekin.project.ui.screen.home.speaker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.data.event.EventSpeakers
import com.ptit_booth_chekin.project.data.event.EventRepository
import com.ptit_booth_chekin.project.data.event.SpeakerSessionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class SpeakerVMState {
    data class Success(
        val speaker: EventSpeakers,
        val sessions: List<SpeakerSessionItem>
    ) : SpeakerVMState()
    data class Error(val message: String) : SpeakerVMState()
    object Loading : SpeakerVMState()
}


@HiltViewModel
class SpeakerVM @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SpeakerVMState>(SpeakerVMState.Loading)
    val uiState: StateFlow<SpeakerVMState> = _uiState

    fun fetchSpeakerDetail(
        speakerId: String?,
        eventId: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (speakerId == null) {
                _uiState.value = SpeakerVMState.Error("Speaker ID is not valid")
            } else {
                when (val result = eventRepository.getSpeakerSessions(speakerId)) {
                    is APIResult.Success -> {
                        _uiState.value = SpeakerVMState.Success(
                            speaker = result.data.speaker,
                            sessions = result.data.sessions
                        )
                    }
                    is APIResult.Error -> {
                        _uiState.value = SpeakerVMState.Error(result.message)
                    }
                }
            }
        }
    }

    fun clearState() {
        _uiState.value = SpeakerVMState.Loading
    }
}
