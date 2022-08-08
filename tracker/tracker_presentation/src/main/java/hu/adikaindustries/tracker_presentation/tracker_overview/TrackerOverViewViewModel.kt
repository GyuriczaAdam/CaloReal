package hu.adikaindustries.tracker_presentation.tracker_overview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.adikaindustries.core.domain.preferences.Preferences
import hu.adikaindustries.core.util.UIEvent
import hu.adikaindustries.tracker_domain.use_case.TrackerUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class TrackerOverViewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
):ViewModel(){
    private val _uiEvent= Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    init {
        preferences.savedShouldShowOnBoarding(false)
    }

    fun onEvent(event: TrackerOverViewEvent){
        when(event){
            is TrackerOverViewEvent.OnAddFoodClick->{
                
            }
            is TrackerOverViewEvent.OnDeleteTrackedFoodClick->{

            }
            is TrackerOverViewEvent.OnNextDayClick->{

            }
            is TrackerOverViewEvent.OnPreviousDayClick->{

            }
            is TrackerOverViewEvent.OnToggleMealClick->{

            }

        }
    }
}