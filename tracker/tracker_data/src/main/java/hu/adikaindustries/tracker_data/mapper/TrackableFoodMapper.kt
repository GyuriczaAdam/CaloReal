package hu.adikaindustries.tracker_data.mapper

import hu.adikaindustries.tracker_data.remote.DTO.Product
import hu.adikaindustries.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood():TrackableFood?{
    val carbsPer100g= nutriments.carbohydrates100g.roundToInt()
    val proteinPer100g = nutriments.proteins100g.roundToInt()
    val fatPer100g = nutriments.fat100g.roundToInt()
    val caloriesPer100g = nutriments.energyKcal100g.roundToInt()


    return TrackableFood(
        name = productName ?: return null,
        caloriesPer100g = caloriesPer100g,
        proteinPer100g = proteinPer100g,
        fatPer100g = fatPer100g,
        carbsPer100g = carbsPer100g,
        imageUrl = imageFrontThumbUrl
    )
}