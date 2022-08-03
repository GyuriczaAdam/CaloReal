package hu.adikaindustries.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.adikaindustries.core.domain.model.ActivityLevel
import hu.adikaindustries.core.domain.model.GoalType
import hu.adikaindustries.core.domain.preferences.Preferences
import hu.adikaindustries.core.navigation.Route
import hu.adikaindustries.core.util.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel@Inject constructor(
    private val preferences: Preferences,
): ViewModel() {
    var selectedGoal by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalClick(goal: GoalType){
        selectedGoal = goal
    }

    fun onNextClick(){
        viewModelScope.launch {
            preferences.saveGoalType((selectedGoal))
            _uiEvent.send(UIEvent.Navigate(Route.GENDER))
        }
    }



}