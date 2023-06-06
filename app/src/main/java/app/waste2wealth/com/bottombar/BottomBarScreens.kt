package app.waste2wealth.com.bottombar

import app.waste2wealth.com.R
import app.waste2wealth.com.navigation.Screens

sealed class BottomBarScreens(val route: String?, val title: String?, val icon: Int?) {
    object HomeScreen : BottomBarScreens(Screens.Dashboard.route, "Home", R.drawable.homei)
    object RewardsScreen : BottomBarScreens(Screens.Rewards.route, "Rewards", R.drawable.rewardsi)
    object RecordScreen :
        BottomBarScreens(Screens.AllActivities.route, "Activity", R.drawable.recordsi)

    object CommunityScreen :
        BottomBarScreens(Screens.Community.route, "Community", R.drawable.commi)
}

val items = listOf(
    BottomBarScreens.HomeScreen,
    BottomBarScreens.RewardsScreen,
    BottomBarScreens.CommunityScreen
)
