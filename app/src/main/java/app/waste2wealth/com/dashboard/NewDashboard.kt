package app.waste2wealth.com.dashboard

import android.Manifest
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.challengesList
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.profile.ProfileImage
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import java.util.TimeZone

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun NewDashboard(navController: NavHostController) {
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val permissionDrawerState = rememberBottomDrawerState(
        if (permissionState.allPermissionsGranted) BottomDrawerValue.Closed else BottomDrawerValue.Open
    )
    PermissionDrawer(
        drawerState = permissionDrawerState,
        permissionState = permissionState,
        rationaleText = "To continue, allow Report Waste2Wealth to access your device's location" +
                ". Tap Settings > Permission, and turn \"Access Location On\" on.",
        withoutRationaleText = "Location permission required for functionality of this app." +
                " Please grant the permission.",
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 25.dp,
                        bottom = 15.dp,
                        end = 25.dp,
                        start = 15.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val calendar = Calendar.getInstance(TimeZone.getDefault())

                val currentDate = calendar.get(Calendar.DATE)
                val currentMonth = calendar.get(Calendar.MONTH) + 1
                val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
                var currDay by remember {
                    mutableStateOf("")
                }
                var month by remember {
                    mutableStateOf("")
                }
                when (currentDay) {
                    1 -> currDay = "Sunday"
                    2 -> currDay = "Monday"
                    3 -> currDay = "Tuesday"
                    4 -> currDay = "Wednesday"
                    5 -> currDay = "Thursday"
                    6 -> currDay = "Friday"
                    7 -> currDay = "Saturday"
                }
                when (currentMonth) {
                    1 -> month = "January"
                    2 -> month = "February"
                    3 -> month = "March"
                    4 -> month = "April"
                    5 -> month = "May"
                    6 -> month = "June"
                    7 -> month = "July"
                    8 -> month = "August"
                    9 -> month = "September"
                    10 -> month = "October"
                    11 -> month = "November"
                    12 -> month = "December"
                }
                Column {
                    Text(
                        text = "Hi, ${user?.displayName}",
                        color = textColor,
                        fontSize = 20.sp,
                        fontFamily = monteSB,
                        modifier = Modifier.padding(bottom = 7.dp)
                    )
                    Text(
                        text = "$currDay $currentDate $month",
                        color = textColor,
                        fontSize = 13.sp,
                        fontFamily = monteSB,
                        modifier = Modifier.padding(bottom = 7.dp)
                    )
                    Text(
                        text = "Welcome Back to your Dashboard",
                        color = textColor,
                        fontSize = 13.sp,
                        fontFamily = monteSB,
                        modifier = Modifier.padding(bottom = 7.dp)
                    )
                }
                ProfileImage(
                    imageUrl = user?.photoUrl,
                    modifier = Modifier
                        .size(60.dp)
                        .border(
                            width = 1.dp,
                            color = appBackground,
                            shape = CircleShape
                        )
                        .padding(3.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate(Screens.Profile.route)
                        },
                    initial = user?.displayName?.first() ?: 'A'
                )
            }
            Divider(
                color = textColor.copy(0.35f),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Points Earned      ",
                    color = textColor,
                    fontSize = 16.sp,
                    fontFamily = monteBold,
                    modifier = Modifier.padding(start = 45.dp, end = 60.dp)
                )
                Row(modifier = Modifier.padding(end = 25.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.coins),
                        contentDescription = "coins",
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 5.dp),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "8999999",
                        color = textColor,
                        fontSize = 15.sp,
                        fontFamily = monteNormal,
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Points Redeemed",
                    color = textColor,
                    fontSize = 16.sp,
                    fontFamily = monteBold,
                    modifier = Modifier.padding(start = 45.dp, end = 60.dp)
                )
                Row(modifier = Modifier.padding(end = 25.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.coins),
                        contentDescription = "coins",
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 5.dp),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "8999999",
                        color = textColor,
                        fontSize = 15.sp,
                        fontFamily = monteNormal,
                    )
                }

            }
            Divider(
                color = textColor.copy(0.35f),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Earn Instant Points",
                    color = textColor,
                    fontSize = 16.sp,
                    fontFamily = monteBold,
                    modifier = Modifier.padding(top = 15.dp)
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 25.dp, top = 15.dp, bottom = 25.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    backgroundColor = Color(0xFF878c99),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, textColor),
                    modifier = Modifier.padding(end = 25.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.i1),
                            contentDescription = "image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text = "Report Waste",
                            color = Color.Black,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(
                                top = 10.dp,
                                start = 10.dp,
                                end = 10.dp,
                                bottom = 5.dp
                            )
                        )

                    }

                }
                Card(
                    backgroundColor = Color(0xFF6FCF97),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, textColor)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.i2),
                            contentDescription = "image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text = "Collect Waste",
                            color = Color.Black,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(
                                top = 10.dp,
                                start = 10.dp,
                                end = 10.dp,
                                bottom = 5.dp
                            )
                        )

                    }

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Join Challenges",
                    color = textColor,
                    fontSize = 16.sp,
                    fontFamily = monteNormal,
                )

                Text(
                    text = "See all",
                    color = textColor,
                    fontSize = 16.sp,
                    fontFamily = monteNormal,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.Community.route)
                    }
                )

            }


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(
                    bottom = 15.dp,
                    start = 15.dp,
                    top = 10.dp,
                    end = 15.dp
                ),
                contentPadding = PaddingValues(bottom = 25.dp)
            ) {
                items(challengesList) { item ->
                    RepeatingCard(
                        type = item.type,
                        emoji = item.emoji,
                        title = item.title,
                        date = item.date
                    )
                }

            }


        }
    }
}

@Composable
fun RepeatingCard(
    type: String,
    emoji: String,
    title: String,
    date: String
) {
    Card(
        backgroundColor = Color(0xFFD5E1DA),
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomEnd = 10.dp,
            bottomStart = 50.dp
        ),
        modifier = Modifier.padding(end = 10.dp)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(
                text = type,
                color = Black,
                fontSize = 16.sp,
                fontFamily = monteNormal,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            AndroidView(
                factory = { context ->
                    AppCompatTextView(context).apply {
                        setTextColor(Black.toArgb())
                        text = emoji
                        textSize = 35.0F
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        alpha = 1f
                    }
                },
                update = { update ->
                    update.apply {
                        text = emoji
                    }
                },
            )
            Text(
                text = title,
                color = Black,
                fontSize = 25.sp,
                fontFamily = monteSB,
                modifier = Modifier.padding(bottom = 10.dp),
                softWrap = true
            )
            Text(
                text = date,
                color = Black,
                fontSize = 16.sp,
                fontFamily = monteNormal,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFD5065),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = "Register Now",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = monteSB,
                    modifier = Modifier.padding(bottom = 4.dp),
                    maxLines = 1,
                    softWrap = true
                )
            }

        }

    }
}