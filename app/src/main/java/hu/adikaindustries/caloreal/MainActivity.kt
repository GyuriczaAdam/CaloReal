package hu.adikaindustries.caloreal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.adikaindustries.caloreal.ui.theme.CaloRealTheme
import hu.adikaindustries.core.domain.preferences.Preferences
import hu.adikaindustries.caloreal.navigation.navigation.Route
import hu.adikaindustries.onboarding_presentation.activity.ActivityScreen
import hu.adikaindustries.onboarding_presentation.age.AgeScreen
import hu.adikaindustries.onboarding_presentation.gender.GenderScreen
import hu.adikaindustries.onboarding_presentation.goal.GoalScreen
import hu.adikaindustries.onboarding_presentation.height.HeightScreen
import hu.adikaindustries.onboarding_presentation.nutreint_goal.NutrientGoalScreen
import hu.adikaindustries.onboarding_presentation.weight.WeightScreen
import hu.adikaindustries.onboarding_presentation.welcome.WelcomeScreen
import hu.adikaindustries.tracker_presentation.tracker_overview.TrackerOverViewScreen
import hu.adikaindustries.tracker_presentation.tracker_search.SearchScreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnBoarding = preferences.loadShouldShowOnBorading()
        setContent {
            CaloRealTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if(shouldShowOnBoarding){Route.WELCOME}else{Route.TRACKER_OVERVIEW}
                    ){
                        composable(Route.WELCOME){
                            WelcomeScreen(onNextClick = {
                                navController.navigate(Route.GENDER)
                            })
                        }
                        composable(Route.AGE){
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick ={
                                    navController.navigate(Route.HEIGHT)
                                }
                            )
                        }
                        composable(Route.GENDER){
                            GenderScreen(onNextClick = {
                                navController.navigate(Route.AGE)
                            })
                        }
                        composable(Route.HEIGHT){
                            HeightScreen(scaffoldState = scaffoldState, onNextClick = {
                                navController.navigate(Route.WEIGHT)
                            })
                        }
                        composable(Route.WEIGHT){
                            WeightScreen(scaffoldState = scaffoldState, onNextClick = {
                                navController.navigate(Route.ACTIVITY)
                            })
                        }
                        composable(Route.ACTIVITY){
                            ActivityScreen(onNextClick = {
                                navController.navigate(Route.GOAL)
                            })
                        }
                        composable(Route.GOAL){
                            GoalScreen(onNextClick = {
                                navController.navigate(Route.NUTRIENT_GOAL)
                            })
                        }
                        composable(Route.NUTRIENT_GOAL){
                            NutrientGoalScreen(scaffoldState = scaffoldState, onNextClick = {
                                navController.navigate(Route.TRACKER_OVERVIEW)
                            })
                        }
                        composable(Route.TRACKER_OVERVIEW){
                            TrackerOverViewScreen(onNavigateToSearch = { mealName,day,month,year->
                                navController.navigate(
                                    Route.SEARCH +"/$mealName"+"/$day"+"/$month"+"/$year"
                                )
                            } )
                        }
                        composable(
                            route=Route.SEARCH +"/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName"){
                                    type= NavType.StringType
                                },

                                navArgument("dayOfMonth"){
                                    type= NavType.IntType
                                },

                                navArgument("month"){
                                    type= NavType.IntType
                                },

                                navArgument("year"){
                                    type= NavType.IntType
                                }
                            )
                        ){
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonths = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year= it.arguments?.getInt("year")!!

                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonths,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                })
                        }
                    }
                }
            }
        }
    }
}

