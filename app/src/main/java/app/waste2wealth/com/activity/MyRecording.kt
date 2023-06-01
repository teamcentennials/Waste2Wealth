package app.waste2wealth.com.activity

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.collectwaste.getTimeAgo
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.TemporaryActivityItem
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun MyRecordings(navController: NavHostController, viewModel: LocationViewModel) {

    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )
    val permissionDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val gesturesEnabled by remember { derivedStateOf { permissionDrawerState.isOpen } }
    var allRecordings by remember { mutableStateOf<List<TemporaryActivityItem>?>(null) }
    var myRecordings by remember { mutableStateOf<MutableList<TemporaryActivityItem>?>(null) }
    LaunchedEffect(key1 = Unit) {
        viewModel.getPlaces()
    }
    PermissionDrawer(
        drawerState = permissionDrawerState,
        permissionState = permissionState,
        rationaleText = "To continue, allow Waste2Wealth to access your device's camera" +
                ". Tap Settings > Permission, and turn \"Access Camera On\" on.",
        withoutRationaleText = "Camera permission required for this feature to be available." +
                " Please grant the permission.",
        model = R.drawable.location,
        gesturesEnabled = gesturesEnabled,
        size = 100.dp
    ) {
        Scaffold(bottomBar = {
            BottomBar(navController = navController)
        }) {
            JetFirestore(path = {
                collection("TemporaryActivities")
            }, onRealtimeCollectionFetch = { values, _ ->
                allRecordings = values?.getListOfObjects()


            }) {

                println(it)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appBackground)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp, start = 0.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "",
                            tint = textColor,
                            modifier = Modifier
                                .padding(start = 15.dp)
                                .size(25.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = (-10).dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "My Recordings",
                                color = textColor,
                                fontFamily = monteSB,
                                fontSize = 25.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    if (allRecordings != null) {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                bottom = 150.dp,
                                top = 40.dp
                            )
                        ) {
                            itemsIndexed(allRecordings ?: emptyList()) { _, wasteItem ->
                                if (wasteItem.email == user?.email) {
                                    MyRecordingCard(
                                        title = wasteItem.title,
                                        time = getTimeAgo(wasteItem.startTime),
                                        onClick = {
                                            viewModel.beforeActivityPath.value =
                                                wasteItem.beforeTaskPath
                                            viewModel.activityTitle.value = wasteItem.title
                                            viewModel.startActivityTime.value = wasteItem.startTime
                                            navController.navigate(Screens.StopRecording.route)
                                        }
                                    )
                                }
                            }
                        }
                    }

                }


            }
        }
    }
}


@Composable
fun MyRecordingCard(
    title: String,
    time: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(13.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(0xFFD5E1DA),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 7.dp, end = 15.dp)
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontFamily = monteSB,
                    fontSize = 15.sp,
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 7.dp, end = 15.dp)
            ) {
                Text(
                    text = time,
                    color = Color.Black,
                    fontFamily = monteSB,
                    fontSize = 15.sp,
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                        onClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFD5065),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = "Stop Recording",
                        color = Color.White,
                        fontFamily = monteSB,
                        fontSize = 10.sp
                    )
                }

            }
        }


    }

}