package hu.adikaindustries.tracker_data.mapper

import hu.adikaindustries.tracker_data.local.entity.TrackedFoodEntity
import hu.adikaindustries.tracker_domain.model.MealType
import hu.adikaindustries.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood():TrackedFood{
    return TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl =imageUrl,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year,month,dayOfMont),
        calories = calories,
        id = id
    )
}

fun TrackedFood.toTrackedFoodEntity():TrackedFoodEntity{
    return TrackedFoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.name,
        amount = amount,
        dayOfMont = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        calories = calories,
        id = id
    )
}
