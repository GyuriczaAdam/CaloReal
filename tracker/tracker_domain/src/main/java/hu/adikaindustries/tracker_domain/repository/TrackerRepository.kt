package hu.adikaindustries.tracker_domain.repository

import hu.adikaindustries.tracker_domain.model.TrackableFood

interface TrackerRepository {
    suspend fun searchFood(
        query:String,
        page:Int,
        pageSize:Int
    ):Result<List<TrackableFood>>

    suspend fun insertTrackedFood(food:TrackableFood)
}