package hu.adikaindustries.tracker_data.local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.adikaindustries.tracker_data.local.DAO.TrackerDAO
import hu.adikaindustries.tracker_data.local.entity.TrackedFoodEntity

@Database(entities = [TrackedFoodEntity::class], version = 1)
abstract class TrackerDatabase: RoomDatabase() {
    abstract val dao:TrackerDAO
}