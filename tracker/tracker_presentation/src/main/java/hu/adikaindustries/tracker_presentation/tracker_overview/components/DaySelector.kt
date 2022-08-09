package hu.adikaindustries.tracker_presentation.tracker_overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import hu.adikaindustries.core.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import java.time.LocalDate

@Composable
fun DaySelector(
    day:LocalDate,
    onPreviousDayClick:()->Unit,
    onNextDayClick:()->Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousDayClick) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.previous_day))
        }

        
    }
    
}