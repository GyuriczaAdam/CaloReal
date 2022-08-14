package hu.adikaindustries.tracker_presentation.tracker_search


import android.view.SearchEvent
import androidx.compose.foundation.layout.*
import hu.adikaindustries.core.R
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import hu.adikaindustries.core.util.UIEvent
import hu.adikaindustries.core_ui.LocalSpacing
import hu.adikaindustries.tracker_presentation.tracker_search.components.SearchTextField


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState,
    mealName:String,
    dayOfMonth:Int,
    month:Int,
    year:Int,
    onNavigateUp: ()->Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context  = LocalContext.current
    val keyBoardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = keyBoardController){
        viewModel.uiEvent.collect{ event->
            when(event){
                is UIEvent.ShowSnackBar->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    keyBoardController?.hide()
                }
                is UIEvent.NavigateUp->onNavigateUp()
                else ->Unit
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(id = R.string.add_meal,mealName),
            style = MaterialTheme.typography.h2 
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = state.query,
            onValueChange = {
                            viewModel.onEvent(TrackerSearchEvent.OnQueryChange(it))
            },
            onSearch = {
                       viewModel.onEvent(TrackerSearchEvent.OnSearch)
            },
            onFocusChanged ={
                viewModel.onEvent(TrackerSearchEvent.OnSearchFocusChange(it.isFocused))
            }
        )
    }
}