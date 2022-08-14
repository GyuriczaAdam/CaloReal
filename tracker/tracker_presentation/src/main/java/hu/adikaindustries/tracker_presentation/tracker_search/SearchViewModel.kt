package hu.adikaindustries.tracker_presentation.tracker_search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import hu.adikaindustries.core.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.adikaindustries.core.domain.use_case.FilterOutDigits
import hu.adikaindustries.core.util.UIEvent
import hu.adikaindustries.core.util.UiText
import hu.adikaindustries.tracker_domain.use_case.TrackerUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
):ViewModel() {
    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent= Channel<UIEvent> ()
    val uiEvent=_uiEvent.receiveAsFlow()

    fun onEvent(event:TrackerSearchEvent){
        when(event){
            is TrackerSearchEvent.OnSearch -> {
                executeSearch()
            }
            is TrackerSearchEvent.OnSearchFocusChange->{
                    state = state.copy(
                        isHintVisible =  !event.isFocused && state.query.isBlank()
                    )
            }
            is TrackerSearchEvent.OnQueryChange->{
                state = state.copy( query = event.query)
            }
            is TrackerSearchEvent.OnToggleTrackebleFood->{
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if(it.food == event.food){
                            it.copy(isExpanded = !it.isExpanded)
                        }else it
                    }
                )
            }
            is TrackerSearchEvent.OnTrackedFoodClick->{
                trackFood(event)
            }
            is TrackerSearchEvent.OnAmountForFoodChange->{
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if(it.food == event.food){
                            it.copy(amount = filterOutDigits(event.amount))
                        }else it
                    }
                )
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFood = emptyList()
            )
            trackerUseCases.searchFood(
                state.query
            )
                .onSuccess {foods->
                    state = state.copy(
                        trackableFood = foods.map {
                            TrackableFoodUiState(it)
                        },
                        isSearching = false,
                        query = ""
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(
                        UIEvent.ShowSnackBar(
                            UiText.StringResource(R.string.error_something_went_wrong)
                        )
                    )
                }
        }
    }

    private fun trackFood(event: TrackerSearchEvent.OnTrackedFoodClick) {
        viewModelScope.launch {
            val uiState = state.trackableFood.find { it.food == event.food }
            trackerUseCases.trackFood(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _uiEvent.send(UIEvent.NavigateUp)
        }
    }
}