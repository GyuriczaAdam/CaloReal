package hu.adikaindustries.tracker_presentation.tracker_search

import hu.adikaindustries.tracker_domain.model.MealType
import hu.adikaindustries.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class TrackerSearchEvent{
    data class OnQueryChange(val query:String):TrackerSearchEvent()
    object OnSearch:TrackerSearchEvent()
    data class OnToggleTrackebleFood(val food:TrackableFood):TrackerSearchEvent()
    data class OnAmountForFoodChange(
        val food:TrackableFood,
        val amount:String
    ):TrackerSearchEvent()
    data class OnTrackedFoodClick(
        val food:TrackableFood,
        val mealType: MealType,
        val date: LocalDate
    ):TrackerSearchEvent()
    data class OnSearchFocusChange(val isFocused:Boolean):TrackerSearchEvent()
}
