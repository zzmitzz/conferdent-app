package com.ptit_booth_chekin.project.ui.screen.home.screen_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.core.data.IPersistentStorage
import com.ptit_booth_chekin.project.data.auth.repository.UserRepository
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.data.event.EventRepository
import com.ptit_booth_chekin.project.ui.screen.home.screen_home.components.EventCardInformationUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject


sealed class ScreenHomeEvent {
    object LoadEvent : ScreenHomeEvent()
    data class LoadEventSuccess(
        val eventCardInformationUIList: List<EventCardInformationUI>, 
        val userFullName: String = "",
        val nearbyEventCardInformationUIList: List<EventCardInformationUI> = emptyList()
    ) : ScreenHomeEvent()

    data class LoadEventError(val message: String) : ScreenHomeEvent()
}


@HiltViewModel
class ScreenHomeVM @Inject constructor(
    private val persistentStorage: IPersistentStorage,  
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _eventState = MutableStateFlow<ScreenHomeEvent>(ScreenHomeEvent.LoadEvent)
    val eventState: StateFlow<ScreenHomeEvent> = _eventState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _eventState.value = ScreenHomeEvent.LoadEvent

            val result = runCatching {
                supervisorScope {
                    val userDeferred = async { userRepository.getMe() }
                    val upcomingDeferred = async { eventRepository.getEvents(true) }
                    val nearbyDeferred = async {
                        eventRepository.getNearbyEvents(null,null)
                    }

                    Triple(
                        userDeferred.await(),
                        upcomingDeferred.await(),
                        nearbyDeferred.await()
                    )
                }
            }

            result.fold(
                onSuccess = { (userResult, upcomingResult, nearbyResult) ->
                    handleSuccess(userResult, upcomingResult, nearbyResult)
                },
                onFailure = { throwable ->
                    _eventState.value = ScreenHomeEvent.LoadEventError(
                        throwable.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }

    fun updateNearbyEvents(lat: Double, lng: Double) {
        viewModelScope.launch {
            val currentState = _eventState.value
            if (currentState !is ScreenHomeEvent.LoadEventSuccess) return@launch

            val result = runCatching {
                eventRepository.getNearbyEvents(lat, lng)
            }

            result.fold(
                onSuccess = { nearbyResult ->
                    val nearbyEventCardInformationUIList = if (nearbyResult is APIResult.Success) {
                        nearbyResult.data.map { it.toEventCardInformationUI() }
                    } else {
                        emptyList()
                    }

                    _eventState.value = currentState.copy(
                        nearbyEventCardInformationUIList = nearbyEventCardInformationUIList
                    )
                },
                onFailure = { throwable ->
                    _eventState.value = ScreenHomeEvent.LoadEventError(
                        throwable.message ?: "Failed to update nearby events"
                    )
                }
            )
        }
    }

    private fun handleSuccess(
        userResult: APIResult<*>,
        upcomingResult: APIResult<*>,
        nearbyResult: APIResult<*>
    ) {
        val userFullName = if (userResult is APIResult.Success) {
            (userResult.data as? com.ptit_booth_chekin.project.data.auth.remote.RegistrationDetail)?.fullName ?: ""
        } else {
            ""
        }

        val upcomingEventsData = if (upcomingResult is APIResult.Success) {
            @Suppress("UNCHECKED_CAST")
            (upcomingResult.data as? List<com.ptit_booth_chekin.project.data.event.EventDetail>)?.map { 
                it.toEventCardInformationUI() 
            } ?: emptyList()
        } else {
            emptyList()
        }

        val nearbyEventCardInformationUIList = if (nearbyResult is APIResult.Success) {
            @Suppress("UNCHECKED_CAST")
            (nearbyResult.data as? List<com.ptit_booth_chekin.project.data.event.EventDetail>)?.map { 
                it.toEventCardInformationUI() 
            } ?: emptyList()
        } else {
            emptyList()
        }

        _eventState.value = ScreenHomeEvent.LoadEventSuccess(
            userFullName = userFullName,
            eventCardInformationUIList = upcomingEventsData,
            nearbyEventCardInformationUIList = nearbyEventCardInformationUIList
        )
    }

    private fun com.ptit_booth_chekin.project.data.event.EventDetail.toEventCardInformationUI() = 
        EventCardInformationUI(
            id = Id ?: "",
            title = name ?: "",
            location = location ?: "",
            startTime = startTime ?: "",
            endTime = endTime ?: "",
            category = categoryId ?: "",
            organization = organizers?.name ?: "",
            logo = logo ?: "",
            tags = tags ?: emptyList(),
            thumbnail = thumbnail ?: ""
        )
}