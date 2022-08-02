package hu.adikaindustries.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.adikaindustries.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.adikaindustries.core.domain.preferences.Preferences
import hu.adikaindustries.core.domain.use_case.FilterOutDigits
import hu.adikaindustries.core.navigation.Route
import hu.adikaindustries.core.util.UIEvent
import hu.adikaindustries.core.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
    ) : ViewModel(){
    var age by mutableStateOf("20")
    private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age:String){
        if(age.length <=3){
            this.age = filterOutDigits.invoke(age)
        }
    }

    fun onNextClick(){
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?:kotlin.run {
                _uiEvent.send(
                    UIEvent.ShowSnackBar(UiText.StringResource(R.string.error_age_cant_be_empty))
                )
                return@launch
            }
            preferences.saveAge(ageNumber)
            _uiEvent.send(UIEvent.Navigate(Route.HEIGHT))
        }
    }
}