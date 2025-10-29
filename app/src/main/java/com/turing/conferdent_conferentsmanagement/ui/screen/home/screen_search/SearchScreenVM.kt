package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.data.event.EventDetail
import com.turing.conferdent_conferentsmanagement.data.event.EventRepository
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
                _uiState.value = SearchScreenViewState.Success(result.data)
            }

            is APIResult.Error -> {
                _uiState.value = SearchScreenViewState.Error(result.message)
            }
        }
    }
}