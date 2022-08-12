package hu.adikaindustries.tracker_presentation.tracker_search

import hu.adikaindustries.tracker_domain.model.TrackableFood

data class SearchState(
    val query:String = "",
    val isHintVisible:Boolean = false,
    val isSearching:Boolean = false,
    val trackableFood: List<TrackableFoodUiState> = emptyList()
)
