package hu.adikaindustries.caloreal.navigation

import androidx.navigation.NavController
import hu.adikaindustries.core.util.UIEvent

fun NavController.navigate(event:UIEvent.Navigate){
    this.navigate(event.route)
}