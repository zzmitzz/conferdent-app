package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turing.conferdent_conferentsmanagement.core.data.IPersistentStorage
import com.turing.conferdent_conferentsmanagement.data.auth.repository.UserRepository
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.data.event.EventRepository
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components.EventCardInformationUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ScreenHomeViewState {
    object Loading : ScreenHomeViewState()
    data class Success(
        val name: String,
    ) : ScreenHomeViewState()

    data class Error(val message: String) : ScreenHomeViewState()
}

sealed class ScreenHomeEvent {
    object LoadEvent : ScreenHomeEvent()
    data class LoadEventSuccess(val eventCardInformationUIList: List<EventCardInformationUI>) : ScreenHomeEvent()
    data class LoadEventError(val message: String) : ScreenHomeEvent()
}


@HiltViewModel
class ScreenHomeVM @Inject constructor(
    private val persistentStorage: IPersistentStorage,
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository
) : ViewModel() {
    private var _uiState: MutableStateFlow<ScreenHomeViewState> =
        MutableStateFlow(ScreenHomeViewState.Loading)

    val uiState: StateFlow<ScreenHomeViewState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ScreenHomeViewState.Loading
    )

    private var _eventState : MutableStateFlow<ScreenHomeEvent> = MutableStateFlow(ScreenHomeEvent.LoadEvent)
    val eventState : StateFlow<ScreenHomeEvent> = _eventState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ScreenHomeEvent.LoadEvent
    )

    init {
        userFetchFlow()
        eventFetchFlow()
    }

    private fun updateState(state: ScreenHomeViewState) {
        _uiState.value = state
    }

    private fun updateEventState(state: ScreenHomeEvent) {
        _eventState.value = state
    }

    private fun eventFetchFlow() {
        viewModelScope.launch {
            delay(2000L)
            val result = eventRepository.getEvents(true)
            when (result) {
                is APIResult.Success -> {
                    updateEventState(ScreenHomeEvent.LoadEventSuccess(result.data.map {
                        EventCardInformationUI(
                            title = it.name ?: "",
                            location = it.location ?: "",
                            startTime = it.startTime ?: "" ,
                            endTime = it.endTime ?: "",
                            category = it.categoryId ?: "",
                            organization = "VNTechConf",
                            logo = it.logo ?: "",
                        )
                    }))
                }

                is APIResult.Error -> {
                    updateEventState(ScreenHomeEvent.LoadEventError(result.message))
                }
            }
        }
    }

    private fun getMoreEvent(){
        viewModelScope.launch {
            val result = eventRepository.getEvents(false)
            when (result) {
                is APIResult.Success -> {
                    updateEventState(ScreenHomeEvent.LoadEventSuccess(result.data.map {
                        EventCardInformationUI(
                            title = it.name ?: "",
                            location = it.location ?: "",
                            startTime = it.startTime ?: "" ,
                            endTime = it.endTime ?: "",
                            category = it.categoryId ?: "",
                            organization = "VNTechConf",
                            logo = it.logo ?: "",
                        )
                    }))
                }

                is APIResult.Error -> {
                    updateEventState(ScreenHomeEvent.LoadEventError(result.message))
                }
            }
        }
    }

    private fun userFetchFlow() {
        viewModelScope.launch {
            val result = userRepository.getMe()
            when (result) {
                is APIResult.Success -> {
                    updateState(ScreenHomeViewState.Success(result.data.fullName ?: ""))
                }

                is APIResult.Error -> {
                    updateState(ScreenHomeViewState.Error(result.message))
                }
            }
        }
    }
}