package app.waste2wealth.com.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.com.ui.theme.monteNormal

@Composable
fun AppBar(
    currentPage: String,
    onNavDrawer: () -> Unit,
    elevation: Dp = 9.dp,
) {
    TopAppBar(
        title = {

        }
    )
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(backGround),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavDrawer) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Navigation Drawer",
                    tint = Color.White
                )
            }
            Text(
                text = currentPage,
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .weight(1f)
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White
                )
            }
        }
    }


@Composable
fun TasksListsAppBar(navHostController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Pending Tasks",
                color = Color.White,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(
                    Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Arrow Back",
                    tint = Color.White
                )
            }
        },
    )
}


@Composable
fun TasksDetailsAppBar(
    text: String,
    navHostController: NavHostController,
    isCallVisible: Boolean,
    onClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
        }
    )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Arrow Back",
                    tint = Color.White
                )
            }
            Text(
                text = text,
                color = Color.White,
                fontFamily = monteNormal,
                fontSize = 18.sp
            )
            if (isCallVisible) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                    IconButton(onClick = { onClick() }) {
                        Icon(
                            Icons.Filled.Call,
                            contentDescription = "Arrow Back",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }



