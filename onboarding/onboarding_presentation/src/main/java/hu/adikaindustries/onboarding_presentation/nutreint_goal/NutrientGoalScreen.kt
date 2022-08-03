package hu.adikaindustries.onboarding_presentation.nutreint_goal

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import hu.adikaindustries.core.R
import hu.adikaindustries.core.util.UIEvent
import hu.adikaindustries.core_ui.LocalSpacing
import hu.adikaindustries.onboarding_presentation.components.ActionButton
import hu.adikaindustries.onboarding_presentation.components.UnitTextField
import kotlinx.coroutines.flow.collect

@Composable
fun NutrientGoalScreen(
    scaffoldState: ScaffoldState,
    onNavigate:(UIEvent.Navigate)->Unit,
    viewModel:NutrientGoalViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect(){ event->
            when(event){
                is UIEvent.Navigate->onNavigate(event)
                is UIEvent.ShowSnackBar->{
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message.asString(context))
                }
                else->Unit
            }

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.state.carbsRatio,
                onValueChange = {
                                viewModel.onEvent(NutrientGoalEvent.OnCarbRatioEnter(it))
                }, unit = stringResource(
                    id = R.string.carbs
                )
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.state.fatRatio,
                onValueChange = {
                    viewModel.onEvent(NutrientGoalEvent.OnFatRatioEnter(it))
                }, unit = stringResource(
                    id = R.string.fat
                )
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.state.protenRatio,
                onValueChange = {
                    viewModel.onEvent(NutrientGoalEvent.OnProteinRatioEnter(it))
                }, unit = stringResource(
                    id = R.string.protein
                )
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                      viewModel.onEvent(NutrientGoalEvent.OnNextClick)
                      }, modifier = Modifier.align(
                Alignment.BottomEnd))
    }
}