package com.ptit_booth_chekin.project.ui.screen.home.screen_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.data.event.EventDetail
import com.ptit_booth_chekin.project.data.event.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


enum class FilterOption {
    NEWEST, A_Z, Z_A, UPCOMING
}

sealed class SearchScreenViewState() {
    data class Success(val events: List<EventDetail>) : SearchScreenViewState()
    data class Error(val message: String) : SearchScreenViewState()
    object Loading : SearchScreenViewState()
}

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchScreenVM
@Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<SearchScreenViewState>(SearchScreenViewState.Success(emptyList()))
    val uiState: StateFlow<SearchScreenViewState> = _uiState.asStateFlow()
    private var fromFirstPage: Boolean = true

    var searchFlow: MutableStateFlow<String> = MutableStateFlow("")
    
    private val _isFilterVisible = MutableStateFlow(false)
    val isFilterVisible: StateFlow<Boolean> = _isFilterVisible.asStateFlow()
    
    private val _selectedFilter = MutableStateFlow(FilterOption.NEWEST)
    val selectedFilter: StateFlow<FilterOption> = _selectedFilter.asStateFlow()
    
    private var unfilteredEvents: List<EventDetail> = emptyList()

    init {
        viewModelScope.launch {
            searchFlow
                .debounce(500)
                .collectLatest { searchTerm ->
                    searchEvent(searchTerm)
                }
        }
    }

    fun doSearch(searchTerm: String) {
        viewModelScope.launch {
            searchFlow.emit(searchTerm)
        }
    }

    private suspend fun searchEvent(
        searchTerm: String
    ) {
        _uiState.value = SearchScreenViewState.Loading
        val result = withContext(Dispatchers.IO) {
            eventRepository.searchEvent(searchTerm, fromFirstPage)
        }

        fromFirstPage = false
        when (result) {
            is APIResult.Success -> {
                unfilteredEvents = result.data
                val filteredData = applyFilter(unfilteredEvents, _selectedFilter.value)
                _uiState.value = SearchScreenViewState.Success(filteredData)
            }

            is APIResult.Error -> {
                _uiState.value = SearchScreenViewState.Error(result.message)
            }
        }
    }

    private fun applyFilter(events: List<EventDetail>, filter: FilterOption): List<EventDetail> {
        return when (filter) {
            FilterOption.NEWEST -> events.sortedByDescending { it.createdAt }
            FilterOption.A_Z -> events.sortedBy { it.name?.lowercase() }
            FilterOption.Z_A -> events.sortedByDescending { it.name?.lowercase() }
            FilterOption.UPCOMING -> events.sortedBy { it.startTime }
        }
    }


    fun toggleFilterVisibility() {
        _isFilterVisible.value = !_isFilterVisible.value
    }
    
    fun selectFilter(option: FilterOption) {
        _selectedFilter.value = option
        if (unfilteredEvents.isNotEmpty()) {
            val filteredData = applyFilter(unfilteredEvents, option)
            _uiState.value = SearchScreenViewState.Success(filteredData)
        }
    }
}