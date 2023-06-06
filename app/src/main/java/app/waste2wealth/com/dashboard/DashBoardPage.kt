package app.waste2wealth.com.dashboard

import android.Manifest
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.components.*
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.location.LocationService
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.com.ui.theme.monteNormal
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class,
    ExperimentalComposeUiApi::class)
@Composable
fun DashBoardPage(
    navHostController: NavHostController,
    locationViewModel: LocationViewModel,
) {
    val scaffoldState = rememberScaffoldState()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(backGround)
    systemUiController.setNavigationBarColor(Color.White)
    val coroutineScope = rememberCoroutineScope()
    var currentItem by remember {
        mutableStateOf(Screens.Dashboard.route)
    }
    val context = LocalContext.current
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(currentPage = "Dashboard", onNavDrawer = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeader()
            DrawerBody(items = listOf(
                MenuItem(
                    id = Screens.Dashboard.route,
                    title = "Dashboard",
                    icon = Icons.Filled.Home,
                    contentDesc = "Dashboard"
                ),
                MenuItem(
                    id = Screens.Profile.route,
                    title = "Profile",
                    icon = Icons.Filled.Person,
                    contentDesc = "Profile"
                ),
                MenuItem(
                    id = Screens.QrCodeScanner.route,
                    title = "Report Waste",
                    icon = Icons.Filled.QrCodeScanner,
                    contentDesc = "Qr Code Scanner"
                )
            ), onItemClick = {
                currentItem = it.id
                when (it.id) {
                    Screens.Dashboard.route -> {
                        navHostController.navigate(Screens.Dashboard.route)
                    }
                    Screens.Profile.route -> {
                        navHostController.navigate(Screens.Profile.route)
                    }
                    Screens.QrCodeScanner.route -> {
                        navHostController.navigate(Screens.QrCodeScanner.route)
                    }
                }
            }, currentItem = Screens.Dashboard.route)
            Divider(color = Color.Gray, modifier = Modifier.padding(
                top = 7.dp,
                start = 10.dp,
                end = 10.dp
            ))
            LogOut(navHostController = navHostController)
            TnC()
            PrivacyPolicy()
            AppVersion()


        },
        drawerBackgroundColor = Color.White,
        drawerScrimColor = Color.White,
        drawerElevation = 15.dp,
        drawerShape = RoundedCornerShape(15.dp)
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
        PermissionDrawer(
            drawerState = permissionDrawerState,
            permissionState = permissionState,
            rationaleText = "To continue, allow Report WasteRider to access your device's location" +
                    ". Tap Settings > Permission, and turn \"Access Location On\" on.",
            withoutRationaleText = "Location permission required for functionality of this app." +
                    " Please grant the permission.",
        ) {
            println(it)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                if (!permissionState.allPermissionsGranted) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    permissionDrawerState.open()
                                }
                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White
                        )
                        ) {
                            Text(
                                text = "Location permission required for this feature to be available. Please grant the permission.",
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                fontFamily = monteNormal,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(15.dp)
                            )
                        }
                    }
                } else {
                    LaunchedEffect(Unit) {
                        Intent(context.applicationContext, LocationService::class.java).apply {
                            action = LocationService.ACTION_START
                            context.startService(this)
                        }
                    }
                    DashboardContent(
                        locationViewModel = locationViewModel,
                        navHostController = navHostController
                    )
                }
            }
        }

    }
}