package hu.adikaindustries.tracker_data.local.DAO

import androidx.room.*
import hu.adikaindustries.tracker_data.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Query("""
        SELECT * FROM trackedfoodentity WHERE dayOfMont = :day AND month = :month AND year = :year
    """)
    fun getFoodsForDate(day:Int,month:Int,year:Int):Flow<List<TrackedFoodEntity>>

}