package hu.adikaindustries.tracker_domain.use_case

import hu.adikaindustries.tracker_domain.model.TrackableFood
import hu.adikaindustries.tracker_domain.model.TrackedFood
import hu.adikaindustries.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        trackedFood: TrackedFood
   ){
        return repository.deleteTrackedFood(trackedFood)
   }
}