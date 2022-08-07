package hu.adikaindustries.tracker_data.repostiory

import hu.adikaindustries.tracker_data.local.DAO.TrackerDAO
import hu.adikaindustries.tracker_data.mapper.toTrackableFood
import hu.adikaindustries.tracker_data.remote.OpenFoodApi
import hu.adikaindustries.tracker_domain.model.TrackableFood
import hu.adikaindustries.tracker_domain.model.TrackedFood
import hu.adikaindustries.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao:TrackerDAO,
    private val api:OpenFoodApi,
) :TrackerRepository{
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(
                searchDto.products.mapNotNull { it.toTrackableFood() }
            )
        }catch (e:Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        TODO("Not yet implemented")
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        TODO("Not yet implemented")
    }
}