package app.waste2wealth.com.dashboard

import android.Manifest
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.firebase.firestore.challengesList
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.profile.ProfileImage
import app.waste2wealth.com.ui.theme.CardColor
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects
import kotlin.system.exitProcess


@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun NewDashboard(
    navController: NavHostController,
    viewModel: LocationViewModel = hiltViewModel(),
    email: String,
    name: String,
    pfp: String
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val permissionDrawerState = rememberBottomDrawerState(
        if (permissionState.allPermissionsGranted) BottomDrawerValue.Closed else BottomDrawerValue.Open
    )
    var profileList by remember {
        mutableStateOf<List<ProfileInfo>?>(null)
    }
    var address by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var gender by remember {
        mutableStateOf("")
    }
    var organization by remember {
        mutableStateOf("")
    }
    var pointsEarned by remember {
        mutableStateOf("")
    }
    var pointsRedeemed by remember {
        mutableStateOf("")
    }
    val activity = (LocalContext.current as? Activity)
    BackHandler {
        activity?.finishAndRemoveTask()
        exitProcess(0)
    }
    val scrollState = rememberScrollState()
    JetFirestore(path = {
        collection("ProfileInfo")
    }, onRealtimeCollectionFetch = { value, _ ->
        profileList = value?.getListOfObjects()
    }) {
        if (profileList != null) {
            for (i in profileList!!) {
                if (i.email == email) {
                    address = i.address ?: ""
                    gender = i.gender ?: ""
                    phoneNumber = i.phoneNumber ?: ""
                    organization = i.organization ?: ""
                    pointsEarned = i.pointsEarned.toString()
                    pointsRedeemed = i.pointsRedeemed.toString()
                }
            }
        }
        PermissionDrawer(
            drawerState = permissionDrawerState,
            permissionState = permissionState,
            rationaleText = "To continue, allow Report Waste2Wealth to access your device's location" +
                    ". Tap Settings > Permission, and turn \"Access Location On\" on.",
            withoutRationaleText = "Location permission required for functionality of this app." +
                    " Please grant the permission.",
        ) {
            Scaffold(bottomBar = {
                BottomBar(navController = navController)
            }) {
                println(it)
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .background(appBackground)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        backgroundColor = CardColor,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column {
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
                                Column {
                                    Text(
                                        text = "Welcome Back",
                                        color = Color.Gray,
                                        fontSize = 13.sp,
                                        fontFamily = monteSB,
                                        modifier = Modifier.padding(bottom = 7.dp)
                                    )
                                    Text(
                                        text = name,
                                        color = textColor,
                                        fontSize = 20.sp,
                                        fontFamily = monteBold,
                                        modifier = Modifier.padding(bottom = 7.dp)
                                    )
                                    Text(
                                        text = "Start making a difference today!",
                                        color = Color.Gray,
                                        fontSize = 13.sp,
                                        fontFamily = monteSB,
                                        modifier = Modifier.padding(bottom = 7.dp)
                                    )
                                }
                                ProfileImage(
                                    imageUrl = pfp,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .border(
                                            width = 1.dp,
                                            color = textColor,
                                            shape = CircleShape
                                        )
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            navController.navigate(Screens.Profile.route)
                                        }
                                )
                            }
                            Row(
//                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier.padding(top = 15.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Points Earned",
                                        color = textColor,
                                        fontSize = 14.sp,
                                        fontFamily = monteBold,
                                        softWrap = true,
                                        modifier = Modifier.padding(start = 7.dp)
                                    )
                                    Row(modifier = Modifier.padding(end = 0.dp, top = 7.dp)) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.coins),
                                            contentDescription = "coins",
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 5.dp),
                                            tint = Color.Unspecified
                                        )
                                        Text(
                                            text = pointsEarned,
                                            color = textColor,
                                            fontSize = 15.sp,
                                            fontFamily = monteNormal,
                                        )
                                    }

                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Column(
                                    modifier = Modifier.padding(top = 15.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Points Redeemed",
                                        color = textColor,
                                        fontSize = 14.sp,
                                        fontFamily = monteBold,
                                        softWrap = true,
                                        modifier = Modifier.padding(start = 7.dp)
                                    )
                                    Row(modifier = Modifier.padding(end = 0.dp, top = 7.dp)) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.coins),
                                            contentDescription = "coins",
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 5.dp),
                                            tint = Color.Unspecified
                                        )
                                        Text(
                                            text = pointsRedeemed,
                                            color = textColor,
                                            fontSize = 15.sp,
                                            fontFamily = monteNormal,
                                        )
                                    }

                                }

                            }
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Earn Instant Points",
                            color = textColor,
                            fontSize = 16.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(top = 0.dp)
                        )

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 25.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Card(
                            backgroundColor = CardColor,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, textColor),
                            modifier = Modifier
                                .padding(end = 25.dp)
                                .fillMaxWidth(0.5f)
                                .clickable {
                                    navController.navigate(Screens.ReportWaste.route)
//                        viewModel.getPlaces()
                                }
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.i1),
                                    contentDescription = "image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(110.dp)
                                )
                                Text(
                                    text = "Report Waste",
                                    color = textColor,
                                    fontFamily = monteBold,
                                    modifier = Modifier.padding(
                                        top = 0.dp,
                                        start = 10.dp,
                                        end = 10.dp,
                                        bottom = 5.dp
                                    )
                                )
                                Row(modifier = Modifier.padding(end = 0.dp, top = 7.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.coins),
                                        contentDescription = "coins",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .padding(end = 5.dp),
                                        tint = Color.Unspecified
                                    )
                                    Text(
                                        text = "20",
                                        color = textColor,
                                        fontSize = 15.sp,
                                        fontFamily = monteNormal,
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                            }

                        }
                        Card(
                            backgroundColor = CardColor,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, textColor),
                            modifier = Modifier.clickable {
                                navController.navigate(Screens.CollectWasteLists.route)
                            }
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.i2),
                                    contentDescription = "image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(110.dp)
                                        .padding(horizontal = 13.dp)
                                )
                                Text(
                                    text = "Collect Waste",
                                    color = textColor,
                                    fontFamily = monteBold,
                                    modifier = Modifier
                                        .padding(
                                            top = 0.dp,
                                            start = 10.dp,
                                            end = 10.dp,
                                            bottom = 5.dp
                                        )
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Row(modifier = Modifier.padding(end = 0.dp, top = 7.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.coins),
                                        contentDescription = "coins",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .padding(end = 5.dp),
                                        tint = Color.Unspecified
                                    )
                                    Text(
                                        text = "40",
                                        color = textColor,
                                        fontSize = 15.sp,
                                        fontFamily = monteNormal,
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                            }

                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Join Challenges",
                            color = textColor,
                            fontSize = 16.sp,
                            fontFamily = monteBold,
                        )
                        Text(
                            text = "See all...",
                            color = textColor,
                            fontSize = 16.sp,
                            fontFamily = monteNormal,
                            modifier = Modifier.clickable {
                                navController.navigate(Screens.Community.route)
                            }
                        )
                    }


                    LazyRow(
                        modifier = Modifier.padding(
                            bottom = 10.dp,
                            start = 15.dp,
                            top = 10.dp,
                            end = 15.dp
                        ),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(challengesList.take(2)) { item ->
                            RepeatingCard(
                                title = item.title,
                                date = item.date
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RepeatingCard(
    title: String,
    date: String
) {
    Card(
        backgroundColor = CardColor,
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomEnd = 10.dp,
            bottomStart = 10.dp
        ),
        modifier = Modifier.padding(end = 10.dp)
    ) {
        var register by remember { mutableStateOf("Register") }
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = textColor,
                fontSize = 18.sp,
                fontFamily = monteSB,
                modifier = Modifier.padding(bottom = 10.dp),
                softWrap = true
            )
            Text(
                text = date,
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = monteSB,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Button(
                onClick = {
                    register = "Registered"
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = textColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Text(
                    text = register,
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
