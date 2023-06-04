package app.waste2wealth.com.rewards

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawerValue
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.textColor
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun NewRewardsScreen(
    navController: NavHostController,
    email: String,
    name: String,
    pfp: String,
    viewModel: LocationViewModel
) {
    val onClick = {
        viewModel.rewardImage.value =
            "https://img.freepik.com/premium-psd/headphone-giveaway-contestpromotion-instagram-facebook-social-media-post-template_501590-116.jpg?w=2000"
        viewModel.rewardTitle.value = "Boat Headphones"
        viewModel.rewardDescription.value =
            "Immerse yourself in exceptional audio quality with these headphones," +
                    " designed for ultimate comfort and delivering a truly immersive sound experience. Whether for music, movies, or calls, these headphones will elevate your audio enjoyment to new heights"
        viewModel.rewardNoOfPoints.value = 60
        navController.navigate(Screens.RewardsDetails.route)
    }
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
    val cList = listOf("Market", "My Rewards")
    var tabIndex by remember { mutableStateOf(0) }
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
                println(it)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        Column {
                            Text(
                                text = "Rewards",
                                color = textColor,
                                fontSize = 25.sp,
                                fontFamily = monteBold,
                            )
                            Text(
                                text = "Grab exciting rewards",
                                color = Color.LightGray,
                                fontSize = 13.sp,
                                fontFamily = monteBold,
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp, end = 0.dp, start = 20.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.padding(end = 25.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.coins),
                                    contentDescription = "coins",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(end = 5.dp),
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = pointsEarned.toString(),
                                    color = textColor,
                                    fontSize = 15.sp,
                                    softWrap = true,
                                    fontFamily = monteNormal,
                                )
                            }

                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(appBackground),
                        contentPadding = PaddingValues(top = 10.dp, bottom = 0.dp)
                    ) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            ) {
                                Text(
                                    text = "Welcome to Rewards Section",
                                    color = textColor,
                                    fontSize = 15.sp,
                                    fontFamily = monteBold,
                                )
                                AsyncImage(
                                    model = "https://img.freepik.com/premium-psd/headphone-giveaway-contestpromotion-instagram-facebook-social-media-post-template_501590-116.jpg?w=2000",
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            onClick()
                                        }
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = "New Offers",
                                    color = textColor,
                                    fontSize = 16.sp,
                                    fontFamily = monteBold,
                                )
                                Text(
                                    text = "Check out the latest offers and rewards",
                                    color = Color.LightGray,
                                    fontSize = 10.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier
                                        .height(300.dp)
                                        .offset(y = (-10).dp),
                                    contentPadding = PaddingValues(top = 30.dp, bottom = 10.dp)
                                ) {
                                    items(2) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Card(
                                                modifier = Modifier.padding(0.dp),
                                                backgroundColor = Color(0xFF3C3E41),
                                                shape = RoundedCornerShape(7.dp)
                                            ) {
                                                Column {
                                                    Card(
                                                        modifier = Modifier.padding(0.dp),
                                                        backgroundColor = Color.White,
                                                        shape = RoundedCornerShape(10.dp)
                                                    ) {
                                                        AsyncImage(
                                                            model = "https://img.freepik.com/premium-psd/headphone-giveaway-contestpromotion-instagram-facebook-social-media-post-template_501590-116.jpg?w=2000",
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .padding(0.dp)
                                                                .clickable {
                                                                    onClick()
                                                                },
                                                            contentScale = ContentScale.Fit
                                                        )
                                                    }
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Text(
                                                            text = "Ends in 2 days",
                                                            color = Color.White,
                                                            fontSize = 10.sp,
                                                            fontFamily = monteBold,
                                                            modifier = Modifier.padding(
                                                                horizontal = 20.dp,
                                                                vertical = 9.dp
                                                            )
                                                        )
                                                    }

                                                }
                                            }


                                        }

                                    }
                                }
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier
                                        .height(300.dp)
                                        .offset(y = (-55).dp)
                                ) {
                                    items(2) {

                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Card(
                                                modifier = Modifier.padding(0.dp),
                                                backgroundColor = Color(0xFF3C3E41),
                                                shape = RoundedCornerShape(7.dp)
                                            ) {
                                                Column {
                                                    Card(
                                                        modifier = Modifier.padding(0.dp),
                                                        backgroundColor = appBackground,
                                                        shape = RoundedCornerShape(10.dp)
                                                    ) {
                                                        AsyncImage(
                                                            model = "https://img.freepik.com/premium-psd/headphone-giveaway-contest" +
                                                                    "-promotion-instagram-facebook-social-media-post-template_501590-116.jpg?w=2000",
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .padding(0.dp)
                                                                .clickable {
                                                                    onClick()
                                                                },
                                                            contentScale = ContentScale.Fit
                                                        )
                                                    }
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Text(
                                                            text = "Ends in 2 days",
                                                            color = Color.White,
                                                            fontSize = 10.sp,
                                                            fontFamily = monteBold,
                                                            modifier = Modifier.padding(
                                                                horizontal = 20.dp,
                                                                vertical = 9.dp
                                                            )
                                                        )
                                                    }

                                                }
                                            }


                                        }

                                    }
                                }
                                Text(
                                    text = "Jackpot Giveaway",
                                    color = textColor,
                                    fontSize = 22.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.offset(y = (-100).dp)
                                )
                                AsyncImage(
                                    model = "https://img.freepik.com/premium-psd/headphone-giveaway-contest" +
                                            "-promotion-instagram-facebook-social-media-post-template_501590-116.jpg?w=2000",
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(top = 0.dp)
                                        .offset(y = (-80).dp)
                                        .fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier
                                        .height(300.dp)
                                        .offset(y = (-80).dp)
                                ) {
                                    items(2) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Card(
                                                modifier = Modifier.padding(0.dp),
                                                backgroundColor = Color(0xFF3C3E41),
                                                shape = RoundedCornerShape(7.dp)
                                            ) {
                                                Column {
                                                    Card(
                                                        modifier = Modifier.padding(0.dp),
                                                        backgroundColor = appBackground,
                                                        shape = RoundedCornerShape(10.dp)
                                                    ) {
                                                        AsyncImage(
                                                            model = "https://img.freepik.com/premium-psd/headphone-giveaway-contest" +
                                                                    "-promotion-instagram-facebook-social-media-post-template_501590-116.jpg?w=2000",
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .padding(0.dp)
                                                                .clickable {
                                                                    onClick()
                                                                },
                                                            contentScale = ContentScale.Fit
                                                        )
                                                    }
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Text(
                                                            text = "Ends in 2 days",
                                                            color = Color.White,
                                                            fontSize = 10.sp,
                                                            fontFamily = monteBold,
                                                            modifier = Modifier.padding(
                                                                horizontal = 20.dp,
                                                                vertical = 9.dp
                                                            )
                                                        )
                                                    }

                                                }
                                            }


                                        }

                                    }
                                }
                                Text(
                                    text = "Keep Reporting",
                                    color = Color.LightGray,
                                    fontSize = 35.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.offset(y = (-130).dp)
                                )
                                Text(
                                    text = "Keep Collecting",
                                    color = Color.LightGray,
                                    fontSize = 25.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.offset(y = (-110).dp)
                                )

                            }

                        }
                    }
                }
            }
        }

    }
}