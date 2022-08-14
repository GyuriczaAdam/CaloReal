package hu.adikaindustries.onboarding_presentation.nutreint_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.adikaindustries.core.domain.preferences.Preferences
import hu.adikaindustries.core.domain.use_case.FilterOutDigits
import hu.adikaindustries.core.util.UIEvent
import hu.adikaindustries.onboarding_domain.use_case.ValidateNutrients
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients
): ViewModel() {
    var state by mutableStateOf(NutrientGoalState())
      private set
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent){
        when(event){
            is NutrientGoalEvent.OnCarbRatioEnter->{
                state = state.copy(
                    carbsRatio = filterOutDigits(event.ratio)
                )
            }
            is NutrientGoalEvent.OnProteinRatioEnter->{
                state = state.copy(
                    protenRatio = filterOutDigits(event.ratio)
                )
            }
            is NutrientGoalEvent.OnFatRatioEnter->{
                state = state.copy(
                    fatRatio = filterOutDigits(event.ratio)
                )
            }
            is NutrientGoalEvent.OnNextClick->{
               val result = validateNutrients(
                   carbRatioText = state.carbsRatio,
                   fatRatioText = state.fatRatio,
                   proteinRatioText = state.protenRatio
                   )
                when(result){
                    is ValidateNutrients.Result.Success->{
                        preferences.saveCarbRatio(result.carbsRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        viewModelScope.launch {
                            _uiEvent.send(UIEvent.Success)
                        }
                    }
                    is ValidateNutrients.Result.Error->{
                        viewModelScope.launch {
                            _uiEvent.send(UIEvent.ShowSnackBar(result.message))
                        }
                    }
                }
            }
        }
    }
}