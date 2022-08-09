package hu.adikaindustries.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.adikaindustries.core.domain.preferences.Preferences
import hu.adikaindustries.core.navigation.Route
import hu.adikaindustries.core.util.UIEvent
import hu.adikaindustries.tracker_domain.use_case.TrackerUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverViewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
):ViewModel(){
    var state by mutableStateOf(TrackerOverViewState())
        private set

    private val _uiEvent= Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob:Job ? = null


    init {
        preferences.savedShouldShowOnBoarding(false)
    }

    fun onEvent(event: TrackerOverViewEvent){
        when(event){
            is TrackerOverViewEvent.OnAddFoodClick->{
                viewModelScope.launch {
                    _uiEvent.send(
                        UIEvent.Navigate(
                            route = Route.SEARCH +"/${event.meal.mealType.name}"+
                                    "/${state.date.dayOfMonth}"+
                                    "/${state.date.monthValue}"+
                                    "/${state.date.year}"
                        )
                    )
                }
            }
            is TrackerOverViewEvent.OnDeleteTrackedFoodClick->{
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFood(event.trackedFood)
                    refreshFoods()
                }
            }
            is TrackerOverViewEvent.OnNextDayClick->{
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverViewEvent.OnPreviousDayClick->{
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverViewEvent.OnToggleMealClick->{
                state = state.copy(
                    meals = state.meals.map {
                        if(it.name == event.meal.name){
                            it.copy(isExpanded = !it.isExpanded)
                        }else it
                    }
                )
            }

        }
    }

    private fun refreshFoods(){
       getFoodsForDateJob?.cancel()
       getFoodsForDateJob =   trackerUseCases
            .getFoodsForDate(state.date)
            .onEach { foods->
                val nutrientsResult=trackerUseCases.calculateMealNutrients(foods)
                state = state.copy(
                    totalCarbs = nutrientsResult.totalCarbs,
                    totalProtein = nutrientsResult.totalProtein,
                    totalFat = nutrientsResult.totalFat,
                    totalCalories = nutrientsResult.totalCalories,
                    carbsGoal = nutrientsResult.carbsGoal,
                    proteinGoal = nutrientsResult.proteinGoal,
                    fatGoal = nutrientsResult.fatGoal,
                    caloriesGoal = nutrientsResult.caloriesGoal,
                    trackedFoods = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal=
                            nutrientsResult.mealNutrients[it.mealType]
                                ?:  return@map it.copy(
                                    carbsAmount = 0,
                                    proteinAmount = 0,
                                    fatAmount = 0,
                                    caloriesAmount = 0
                                )
                        it.copy(
                            carbsAmount = nutrientsForMeal.carbs,
                            proteinAmount = nutrientsForMeal.protein,
                            fatAmount = nutrientsForMeal.fat,
                            caloriesAmount = nutrientsForMeal.calories
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }
}