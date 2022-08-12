package hu.adikaindustries.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import hu.adikaindustries.core.util.UIEvent
import hu.adikaindustries.core_ui.LocalSpacing
import hu.adikaindustries.tracker_presentation.tracker_overview.components.DaySelector
import hu.adikaindustries.tracker_presentation.tracker_overview.components.ExpandableMeal
import hu.adikaindustries.tracker_presentation.tracker_overview.components.NutrientHeader

@Composable
fun TrackerOverViewScreen(
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: TrackerOverViewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ){
        item { 
            NutrientHeader(state = state)
            Spacer(modifier = Modifier.padding(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = { viewModel.onEvent(TrackerOverViewEvent.OnPreviousDayClick) },
                onNextDayClick = { viewModel.onEvent(TrackerOverViewEvent.OnNextDayClick) },
                modifier = Modifier
                    .padding(horizontal = spacing.spaceMedium)
                    .fillMaxWidth()
                )
            Spacer(modifier = Modifier.padding(spacing.spaceMedium))
        }

        items(state.meals){ meal->
            ExpandableMeal(
                meal = meal,
                onToggleClick = {viewModel.onEvent(TrackerOverViewEvent.OnToggleMealClick(meal))},
                content = {

                },
                modifier = Modifier.fillMaxWidth()
                )
        }
    }
}