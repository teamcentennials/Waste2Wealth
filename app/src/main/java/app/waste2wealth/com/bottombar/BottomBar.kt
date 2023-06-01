package app.waste2wealth.com.bottombar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.textColor

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = appBackground,
        elevation = 5.dp,
        shape = RoundedCornerShape(7.dp)
    ) {
        BottomNavigation(
            modifier = Modifier
                .padding(12.dp, 0.dp, 12.dp, 0.dp)
                .height(80.dp),
            elevation = 0.dp,
            backgroundColor = appBackground
        ) {
            items.forEach {
                val isYellow = currentRoute?.hierarchy?.any { nav ->
                    nav.route == it.route
                } == true
                BottomNavigationItem(
                    icon = {
                        it.icon?.let {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(35.dp)
                                    .padding(bottom = 5.dp),
                                tint = if (isYellow) Color(0xFFFF7E55) else textColor
                            )
                        }
                    },
                    label = {
                        it.title?.let {
                            Text(
                                text = it,
                                color = if (isYellow) Color(0xFFFF7E55) else textColor,
                                softWrap = false
                            )
                        }
                    },
                    selected = isYellow,
                    selectedContentColor = Color.Yellow,
                    unselectedContentColor = Color.White.copy(alpha = 0.4f),
                    onClick = {
                        it.route?.let { it1 ->
                            navController.navigate(it1) {
                                popUpTo(Screens.Dashboard.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}