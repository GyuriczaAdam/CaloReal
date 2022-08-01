package hu.adikaindustries.core.domain.preferences

import hu.adikaindustries.core.domain.model.ActivityLevel
import hu.adikaindustries.core.domain.model.Gender
import hu.adikaindustries.core.domain.model.GoalType

interface Preferences {
    fun saveGender(gender: Gender)
    fun saveAge(age:Int)
    fun saveWeight(weight:Float)
    fun saveHeight(height:Int)
    fun saveActivityLevel(level:ActivityLevel)
    fun saveGoalType(type: GoalType)
    fun saveCarbRatio(ratio:Float)
    fun saveProteinRatio(ratio:Float)
    fun saveFatRatio(ratio: Float)


    fun loadUserInfo()
}