package hu.adikaindustries.onboarding_presentation.height

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
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
    ) : ViewModel(){
    var hegight by mutableStateOf("180")
    private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEnter(height:String){
        if(height.length <=3){
            this.hegight = filterOutDigits.invoke(height)
        }
    }

    fun onNextClick(){
        viewModelScope.launch {
            val heightNumber = hegight.toIntOrNull() ?:kotlin.run {
                _uiEvent.send(
                    UIEvent.ShowSnackBar(UiText.StringResource(R.string.error_height_cant_be_empty))
                )
                return@launch
            }
            preferences.saveHeight(heightNumber)
            _uiEvent.send(UIEvent.Navigate(Route.WEIGHT))
        }
    }
}