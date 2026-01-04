package com.ptit_booth_chekin.project.ui.screen.home.screen_category_filter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.data.event.EventRepository
import com.ptit_booth_chekin.project.navigation.Routes
import com.ptit_booth_chekin.project.ui.screen.home.screen_home.components.EventCardInformationUI
import com.ptit_booth_chekin.project.utils.Constants
import com.ptit_booth_chekin.project.utils.parseTimeFromServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CategoryFilterState {
    data object Loading : CategoryFilterState()
    data class Success(val events: List<EventCardInformationUI>) : CategoryFilterState()
    data class Error(val message: String) : CategoryFilterState()
}

@HiltViewModel
class CategoryFilterVM @Inject constructor(
    private val eventRepository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryType: String = savedStateHandle.get<String>(Routes.CATEGORY_TYPE) ?: ""

    private val _uiState = MutableStateFlow<CategoryFilterState>(CategoryFilterState.Loading)
    val uiState: StateFlow<CategoryFilterState> = _uiState.asStateFlow()

    init {
        loadEventsByCategory()
    }

    private fun loadEventsByCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = CategoryFilterState.Loading
            try {
                val result = eventRepository.getEvents()
                when(result){
                    is APIResult.Success -> {
                        val serverCategory = mapAppCategoryToServer(categoryType)
                        val eventList = result.data
                        val filteredEvents = eventList
                            .filter { it.categoryId == serverCategory }
                            .map {
                                EventCardInformationUI(
                                    id = it.Id ?: "",
                                    title = it.name ?: "",
                                    location = it.location ?: "",
                                    startTime = it.startTime ?: "" ,
                                    endTime = it.endTime ?: "",
                                    category = it.categoryId ?: "",
                                    organization = "VNTechConf",
                                    logo = it.logo ?: "",
                                    thumbnail = it.thumbnail ?: "",
                                )
                            }

                        _uiState.value = CategoryFilterState.Success(filteredEvents)
                    }
                    is APIResult.Error -> {
                        _uiState.value = CategoryFilterState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = CategoryFilterState.Error(
                    e.message ?: "An unexpected error occurred"
                )
            }
        }
    }

    private fun mapAppCategoryToServer(appCategory: String): String {
        return when (appCategory) {
            "ENVIRONMENT" -> "ENVIRONMENT"
            "TECH" -> "TECHNOLOGY"
            "ECONOMIC" -> "ECONOMY"
            "EDUCATION" -> "EDUCATION"
            "MEDICAL" -> "HEALTH"
            else -> appCategory
        }
    }

    private fun mapServerCategoryToDisplay(serverCategory: String): String {
        return when (serverCategory) {
            "ENVIRONMENT" -> "Môi trường"
            "TECHNOLOGY" -> "Công nghệ"
            "ECONOMY" -> "Kinh tế"
            "EDUCATION" -> "Giáo dục"
            "HEALTH" -> "Y tế"
            else -> serverCategory
        }
    }

    fun getCategoryDisplayName(): String {
        return when (categoryType) {
            "ENVIRONMENT" -> "Môi trường"
            "TECH" -> "Công nghệ"
            "ECONOMIC" -> "Kinh tế"
            "EDUCATION" -> "Giáo dục"
            "MEDICAL" -> "Y tế"
            else -> categoryType
        }
    }
}
