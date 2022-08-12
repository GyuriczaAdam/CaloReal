package hu.adikaindustries.tracker_presentation.tracker_search

import hu.adikaindustries.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val food:TrackableFood,
    val isExpanded:Boolean =false,
    val amount:String =""
)
