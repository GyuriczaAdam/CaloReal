package hu.adikaindustries.onboarding_domain.use_case

class ValidateNutrients {
    operator fun invoke(
        carbRatioText:String,
        fatRatioText:String,
        proteinRatioText:String
    ){

    }

    sealed class Result{
        data class Success()
    }
}