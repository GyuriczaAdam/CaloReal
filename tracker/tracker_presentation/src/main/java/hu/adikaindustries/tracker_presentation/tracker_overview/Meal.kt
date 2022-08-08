package hu.adikaindustries.tracker_presentation.tracker_overview

import androidx.annotation.DrawableRes
import hu.adikaindustries.core.util.UiText
import hu.adikaindustries.tracker_domain.model.MealType
import hu.adikaindustries.core.R

data class Meal(
    val name:UiText,
    @DrawableRes
    val drawableRes:Int,
    val mealType: MealType,
    val carbsAmount:Int = 0,
    val proteinmount:Int = 0,
    val fatAmount:Int = 0,
    val caloriesAmount:Int = 0,
    val isExpanded:Boolean = false
)

val defaultMeals= listOf(
    Meal(
        name = UiText.StringResource(R.string.breakfast)
    )
)
