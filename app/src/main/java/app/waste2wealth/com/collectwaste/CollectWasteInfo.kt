package app.waste2wealth.com.collectwaste

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.reportwaste.DialogBox
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun CollectWasteInfo(
    navController: NavHostController,
    viewModel: LocationViewModel
) {
    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )
    val permissionDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val gesturesEnabled by remember { derivedStateOf { permissionDrawerState.isOpen } }
    var isDialogVisible by remember { mutableStateOf(false) }
    val isWithin = isWithinRadius(
        viewModel.latitude,
        viewModel.longitude,
        viewModel.theirLatitude.value,
        viewModel.theirLongitude.value
    )
    BackHandler {
        viewModel.locationNo.value = ""
        viewModel.address.value = ""
        viewModel.distance.value = ""
        viewModel.time.value = ""
        viewModel.wastePhoto.value = ""
        viewModel.theirLongitude.value = 0.0
        viewModel.theirLatitude.value = 0.0
        navController.popBackStack()
    }
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
        model = R.drawable.camera,
        gesturesEnabled = gesturesEnabled,
        size = 100.dp
    ) {
        Scaffold(bottomBar = {
            BottomBar(navController = navController)
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
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = (-10).dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Collect Waste",
                            color = textColor,
                            fontFamily = monteSB,
                            fontSize = 25.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
                WasteItemCard(
                    locationNo = viewModel.locationNo.value,
                    address = viewModel.address.value,
                    distance = viewModel.distance.value,
                    time = viewModel.time.value,
                    isCollectedInfo = true,
                    isEllipsis = false,
                    onCollected = {
                        val gmmIntentUri =
                            Uri.parse(
                                "google.navigation:q=${viewModel.theirLatitude.value}," +
                                        "${viewModel.theirLongitude.value}"
                            )
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)

                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                var imageUrlState by remember {
                    mutableStateOf("")
                }
                LaunchedEffect(key1 = Unit) {
                    val imageUrl = withContext(Dispatchers.IO) {
                        try {
                            getDownloadUrlFromPath(viewModel.wastePhoto.value)
                        } catch (e: Exception) {
                            ""
                        }
                    }
                    imageUrlState = imageUrl
                }
                if (imageUrlState != "") {
                    AsyncImage(
                        model = imageUrlState,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(bottom = 30.dp)
                            .clip(RoundedCornerShape(30.dp)),
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if (!isWithin) {
                                isDialogVisible = true
                            } else {
                                navController.navigate(Screens.CollectedWasteSuccess.route)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = textColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(35.dp),
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = "Collect Waste",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(bottom = 4.dp),
                            maxLines = 1,
                            softWrap = true
                        )
                    }
                }
                if (!isWithin) {
                    DialogBox(
                        isVisible = isDialogVisible,
                        title = "Are You Sure you reached the location?",
                        description = "It feels like you aren't at the location yet. " +
                                "Please make sure you are at the location before you collect the waste.",
                        successRequest = {
                            isDialogVisible = false
                            navController.navigate(Screens.CollectedWasteSuccess.route)
                        },
                        dismissRequest = {
                            isDialogVisible = false
                        }
                    )
                }
            }

        }
    }
}

suspend fun getDownloadUrlFromPath(path: String): String {
    val storageRef = FirebaseStorage.getInstance().reference
    val fileRef = storageRef.child(path)
    return fileRef.downloadUrl.await().toString()
}

fun isWithinRadius(
    sourceLat: Double,
    sourceLon: Double,
    destLat: Double,
    destLon: Double
): Boolean {
    val earthRadius = 6371 // Radius of the Earth in kilometers

    val dLat = Math.toRadians(destLat - sourceLat)
    val dLon = Math.toRadians(destLon - sourceLon)

    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(sourceLat)) * cos(Math.toRadians(destLat)) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    val distance = earthRadius * c

    return distance <= 1.2 // Check if distance is within x km radius
}