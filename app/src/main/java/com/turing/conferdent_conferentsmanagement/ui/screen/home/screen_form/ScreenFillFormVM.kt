package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.data.event.EventRepository
import com.turing.conferdent_conferentsmanagement.data.event.models.FormData
import com.turing.conferdent_conferentsmanagement.models.FormFields
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class ScreenFFState {
    object Loading : ScreenFFState()
    data class Success(
        val formData: FormData
    ) : ScreenFFState()

    data class Error(val message: String? = null) : ScreenFFState()
}

sealed class ScreenFFUIEffect {
    data class ShowToast(val message: String) : ScreenFFUIEffect()
    object NavigateToNextScreen : ScreenFFUIEffect()
}


@HiltViewModel
class ScreenFillFormVM @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenFFState>(ScreenFFState.Loading)
    val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<ScreenFFUIEffect>()
    val effect = _effect

    val answerMap: MutableMap<String, String> = mutableMapOf()

    fun updateAnswer(questionId: String, answer: String) {
        answerMap[questionId] = answer
    }

    fun getFormField(eventID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ScreenFFState.Loading
            try {
                val result = eventRepository.registerEvent(eventID)
                _state.value = when (result) {
                    is APIResult.Success -> {
                        ScreenFFState.Success(result.data)
                    }

                    is APIResult.Error -> {
                        ScreenFFState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _state.value = ScreenFFState.Error(e.message)

            }
        }
    }

    fun submitForm(eventID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ScreenFFState.Loading
            try {
                val result = eventRepository.submitResponse(eventID, answerMap)
                if (result is APIResult.Success) {
                    _effect.emit(ScreenFFUIEffect.NavigateToNextScreen)
                } else {
                    _effect.emit(ScreenFFUIEffect.ShowToast((result as APIResult.Error).message))
                }
            } catch (e: Exception) {
                _state.value = ScreenFFState.Success(formData = (state.value as ScreenFFState.Success).formData)
                _effect.emit(ScreenFFUIEffect.ShowToast(e.message.toString()))
            }

        }
    }

    fun showValidationError(message: String) {
        viewModelScope.launch {
            _effect.emit(ScreenFFUIEffect.ShowToast(message))
        }
    }

}