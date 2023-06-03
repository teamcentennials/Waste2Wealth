package app.waste2wealth.com.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import app.waste2wealth.com.UserDatastore
import app.waste2wealth.com.activity.AllActivities
import app.waste2wealth.com.activity.MyRecordings
import app.waste2wealth.com.activity.StopRecording
import app.waste2wealth.com.challenges.Community
import app.waste2wealth.com.collectwaste.CollectWaste
import app.waste2wealth.com.collectwaste.CollectWasteInfo
import app.waste2wealth.com.collectwaste.SuccessfullyCollected
import app.waste2wealth.com.dashboard.NewDashboard
import app.waste2wealth.com.failuretask.TaskUndelivered
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.login.CompleteProfile
import app.waste2wealth.com.login.LoginPage
import app.waste2wealth.com.login.onboarding.Onboarding
import app.waste2wealth.com.login.onboarding.SettingUp
import app.waste2wealth.com.profile.NewProfileScreen
import app.waste2wealth.com.qrcode.ui.ScanQr
import app.waste2wealth.com.reportwaste.ReportWaste
import app.waste2wealth.com.rewards.NewRewardsScreen
import app.waste2wealth.com.rewards.RewardDetails
import app.waste2wealth.com.successtask.DeliveryDetailsScreen
import app.waste2wealth.com.tasksDetail.TasksDetails
import app.waste2wealth.com.tasksList.TasksLists
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun NavigationController(
    scaffoldState: ScaffoldState,
    locationViewModel: LocationViewModel,
    navController: NavHostController
) {
    val viewModel: LocationViewModel = hiltViewModel()
    val context = LocalContext.current
    val store = UserDatastore(context)
    val email = store.getEmail.collectAsState(initial = "")
    val profile = store.getPfp.collectAsState(initial = "")
    val name = store.getName.collectAsState(initial = "")
    AnimatedNavHost(navController = navController, startDestination = Screens.Splash.route) {
        composable(
            route = Screens.LoginScreen.route,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300)
                ) +
                        fadeOut(animationSpec = tween(durationMillis = 300))
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(durationMillis = 300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(durationMillis = 300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300)
                ) +
                        fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ) {
            LoginPage(navController = navController, scaffoldState = scaffoldState)
        }
        composable(
            route = Screens.Dashboard.route,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300)
                ) +
                        fadeOut(animationSpec = tween(durationMillis = 300))
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(durationMillis = 300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(durationMillis = 300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300)
                ) +
                        fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ) {

            NewDashboard(
                navController = navController,
                email = email.value,
                name = name.value,
                pfp = profile.value
            )
//            DashBoardPage(navHostController = navController, locationViewModel = locationViewModel)
        }
        composable(Screens.Profile.route) {
//            ProfileScreen(navHostController = navController)
            NewProfileScreen(
                navController = navController,
                email = email.value,
                name = name.value,
                pfp = profile.value
            )
        }
        composable(Screens.Onboarding.route) {
            Onboarding(navHostController = navController)
        }
        composable(Screens.TasksLists.route) {
            TasksLists(navHostController = navController)
        }
        composable(Screens.CompleteProfile.route) {
            CompleteProfile(navHostController = navController)
        }
        composable(Screens.SettingUp.route) {
            SettingUp(navHostController = navController)

        }
        composable(Screens.Community.route) {
            Community(
                navController = navController,
                email = email.value,
                name = name.value,
                pfp = profile.value
            )
        }
        composable(Screens.ReportWaste.route) {
            ReportWaste(
                navController = navController, viewModel = viewModel,
                email = email.value,
                name = name.value,
                pfp = profile.value
            )
        }
        composable(Screens.CollectWasteLists.route) {
            CollectWaste(navController = navController, viewModel = viewModel)
        }
        composable(Screens.CollectWasteInfo.route) {
            CollectWasteInfo(navController = navController, viewModel = viewModel)
        }
        composable(Screens.CollectedWasteSuccess.route) {
            SuccessfullyCollected(
                navController = navController, viewModel = viewModel,
                email = email.value,
                name = name.value,
                pfp = profile.value
            )
        }
        composable(Screens.AllActivities.route) {
            AllActivities(
                navController = navController, viewModel = viewModel,
                email = email.value,
                name = name.value,
                pfp = profile.value
            )
        }
        composable(Screens.MyRecordings.route) {
            MyRecordings(navController = navController, viewModel = viewModel)
        }
        composable(Screens.StopRecording.route) {
            StopRecording(
                navController = navController,
                viewModel = viewModel,
                email = email.value,
                name = name.value
            )
        }
        composable(Screens.Rewards.route) {
            NewRewardsScreen(
                navController = navController,
                email = email.value,
                name = name.value,
                pfp = profile.value,
                viewModel = viewModel
            )
        }
        composable(Screens.RewardsDetails.route) {
            RewardDetails(
                navController = navController,
                email = email.value,
                viewModel = viewModel,
                name = name.value,
            )
        }
        composable(
            Screens.TaskDetail.route
                .plus(
                    "?${TaskDetailsConstants.taskCode.value}={taskCode}" +
                            "?${TaskDetailsConstants.address.value}={address}" +
                            "?${TaskDetailsConstants.noOfAttempts.value}={noOfKMs}" +
                            "?${TaskDetailsConstants.taskPrice.value}={image}"
                ),
            arguments = listOf(
                navArgument("taskCode") {
                    this.type = NavType.StringType
                },
                navArgument("noOfKMs") {
                    this.type = NavType.StringType
                },
                navArgument("address") {
                    this.type = NavType.StringType
                },
                navArgument("image") {
                    this.type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            val taskCode = backStackEntry.arguments?.getString("taskCode") ?: ""
            val address = backStackEntry.arguments?.getString("address") ?: ""
            val noOfKMs = backStackEntry.arguments?.getString("noOfKMs") ?: ""
            val image = backStackEntry.arguments?.getString("image") ?: ""
            TasksDetails(
                address = address,
                navHostController = navController,
                taskCode = taskCode,
                noOfKms = noOfKMs,
                image = image
            )
        }

        composable(route = Screens.SuccessTask.route.plus(
                    "?${TaskDetailsConstants.taskPrice.value}={taskPrice}"
        ), arguments = listOf(
            navArgument("taskPrice") {
                this.type = NavType.StringType
            }
        )
        ) { backStackEntry ->
            val taskPrice = backStackEntry.arguments?.getString("taskPrice") ?: ""
            DeliveryDetailsScreen(
                navHostController = navController,
                taskPrice = taskPrice
            )
        }

        composable(Screens.FailureTask.route
            .plus("?${TaskDetailsConstants.phoneNumber.value}={phoneNumber}"),
            arguments = listOf(
                navArgument("phoneNumber") {
                    this.type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            TaskUndelivered(navHostController = navController, phoneNumber = phoneNumber)
        }
        composable(Screens.QrCodeScanner.route) {
            ScanQr(viewModel = locationViewModel)
        }
        
        composable(Screens.Splash.route){
            SplashScreen(navController = navController, email = email.value)
        }

    }
}


