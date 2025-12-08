package com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.data.event.EventRepository
import com.turing.conferdent_conferentsmanagement.navigation.Routes.Companion.EVENT_ID
import com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.models.SessionTypeState
import com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.models.SessionUIWrap
import com.turing.conferdent_conferentsmanagement.ui.screen.home.conferent_session.models.SpeakerSession
import com.turing.conferdent_conferentsmanagement.utils.parseTimeFromServer
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime


sealed class ConferenceViewState() {
    object Loading : ConferenceViewState()
    data class Error(val message: String) : ConferenceViewState()
    data class Success(
        val currentSelectDate: LocalDate = LocalDate.now(),
        val data: List<SessionUIWrap>
    ) : ConferenceViewState()
}


@HiltViewModel
class ConferenceSessionVM @Inject constructor(
    private val eventRepository: EventRepository,
    private val stateSavedHandle: SavedStateHandle
) : ViewModel() {
    private val _viewState = MutableStateFlow<ConferenceViewState>(ConferenceViewState.Loading)
    val uiState = _viewState.asStateFlow()
    var sessionData: List<SessionUIWrap> = emptyList()
    val eventID = stateSavedHandle.get<String>(EVENT_ID)

    fun fetchSessionData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = eventRepository.getSessionEvent(eventID!!)
            if (result is APIResult.Success) {
                sessionData = result.data.map {

                    val startTimeSession = parseTimeFromServer(it.startTime!!)
                    val endTimeSession = parseTimeFromServer(it.endTime!!)
                    val isOnGoingSession = if ((it.isActive != true)) {
                        SessionTypeState.CANCELED
                    } else {
                        if (LocalDateTime.now().isAfter(startTimeSession) && LocalDateTime.now()
                                .isBefore(endTimeSession)
                        ) {
                            SessionTypeState.HAPPENING
                        } else {
                            SessionTypeState.UPCOMING
                        }
                    }

                    SessionUIWrap(
                        id = it.id ?: 0,
                        sessionName = it.title ?: "Không có thông tin",
                        description = it.description ?: "Không có thông tin",
                        startTime = startTimeSession,
                        endTime = endTimeSession,
                        place = it.place ?: "Không có thông tin",
                        status = isOnGoingSession,
                        speaker = it.speakers.map { (id, avatar) ->
                            SpeakerSession(
                                id.toString(), avatar
                            )
                        },
                        isNotificationOn = false

                    )
                }
            }
            _viewState.value = ConferenceViewState.Success(LocalDate.now(), sessionData.filter {
                it.startTime.toLocalDate().isEqual(LocalDate.now())
            })
        }
    }

    fun updateFilterDate(dateSelected: LocalDate) {
        _viewState.value = ConferenceViewState.Success(dateSelected, sessionData.filter {
            it.startTime.toLocalDate().isEqual(dateSelected)
        })
    }


}