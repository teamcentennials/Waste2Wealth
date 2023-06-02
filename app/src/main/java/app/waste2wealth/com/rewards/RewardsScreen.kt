package app.waste2wealth.com.rewards

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.firebase.firestore.updateInfoToFirebase
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.profile.ProfileImage
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun RewardsScreen(
    navController: NavHostController,
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
    var userAddress by remember {
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
        mutableStateOf(0)
    }
    var pointsRedeemed by remember {
        mutableStateOf(0)
    }
    var noOfTimesReported by remember {
        mutableStateOf(0)
    }
    var noOfTimesCollected by remember {
        mutableStateOf(0)
    }
    var noOfTimesActivity by remember {
        mutableStateOf(0)
    }
    JetFirestore(path = {
        collection("ProfileInfo")
    }, onRealtimeCollectionFetch = { value, _ ->
        profileList = value?.getListOfObjects()
    }) {
        if (profileList != null) {
            for (i in profileList!!) {
                if (i.email == email) {
                    userAddress = i.address ?: ""
                    gender = i.gender ?: ""
                    phoneNumber = i.phoneNumber ?: ""
                    organization = i.organization ?: ""
                    pointsEarned = i.pointsEarned
                    pointsRedeemed = i.pointsRedeemed
                    noOfTimesReported = i.noOfTimesReported
                    noOfTimesCollected = i.noOfTimesCollected
                    noOfTimesActivity = i.noOfTimesActivity
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
                var isCOinVisible by remember {
                    mutableStateOf(false)
                }
                println(it)
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(appBackground)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 35.dp,
                                        bottom = 35.dp,
                                        start = 20.dp,
                                        end = 20.dp
                                    ),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Rewards",
                                    color = textColor,
                                    fontSize = 35.sp,
                                    fontFamily = monteBold,
                                )
                                ProfileImage(
                                    imageUrl = pfp,
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
                                )
                            }
                            val context = LocalContext.current
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.padding(
                                    bottom = 50.dp,
                                    start = 15.dp,
                                    top = 0.dp,
                                    end = 15.dp
                                ),
                                contentPadding = PaddingValues(bottom = 25.dp)
                            ) {

                                items(allRewards) { item ->
                                    RepeatingRewards(
                                        name = item.name,
                                        expiry = item.expiry,
                                        points = item.points
                                    ) {
                                        if (pointsEarned >= item.points.toInt()) {
                                            isCOinVisible = true
                                            Toast.makeText(
                                                context,
                                                "You have earned ${item.points} points",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            updateInfoToFirebase(
                                                context,
                                                name = name,
                                                email = email,
                                                phoneNumber = phoneNumber,
                                                gender = gender,
                                                organization = organization,
                                                address = userAddress,
                                                pointsEarned = pointsEarned - item.points.toInt(),
                                                pointsRedeemed = pointsRedeemed + item.points.toInt(),
                                                noOfTimesReported = noOfTimesReported,
                                                noOfTimesCollected = noOfTimesCollected + 1,
                                                noOfTimesActivity = noOfTimesActivity,
                                            )

                                        } else {
                                            Toast.makeText(
                                                context,
                                                "You do not have enough points to claim this reward",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }

                            }

                        }
                    }
                    if (isCOinVisible) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            val currenanim by rememberLottieComposition(
                                spec = LottieCompositionSpec.Asset("coins.json")
                            )
                            LottieAnimation(
                                composition = currenanim,
                                iterations = 1,
                                contentScale = ContentScale.Crop,
                                speed = 1f,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .size(250.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun RepeatingRewards(
    name: String,
    expiry: String,
    points: String,
    onClaimClick: () -> Unit
) {
    Card(
        backgroundColor = Color(0xFFD5E1DA),
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomEnd = 10.dp,
            bottomStart = 50.dp
        ),
        modifier = Modifier.padding(end = 10.dp, bottom = 20.dp)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = monteSB,
                    modifier = Modifier.padding(bottom = 10.dp),
                    softWrap = true,
                )
            }
            Text(
                text = expiry,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = monteNormal,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = "Points: $points",
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = monteNormal,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Button(
                onClick = { onClaimClick() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFD5065),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = "Claim Now",
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