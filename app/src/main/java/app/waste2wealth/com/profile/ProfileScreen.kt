package app.waste2wealth.com.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.components.*
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.backGround
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    profileName: String = "Krishna Patil",
    organization: String = "Robin Hood",
    phoneNumber: String = "+911234567890",
) {
    val scaffoldState = rememberScaffoldState()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(backGround)
    systemUiController.setNavigationBarColor(Color.White)
    val coroutineScope = rememberCoroutineScope()
    var currentItem by remember {
        mutableStateOf(Screens.Dashboard.route)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(currentPage = "Profile", onNavDrawer = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }, elevation = 0.dp
            )
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
            }, currentItem = Screens.Profile.route)
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
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            println(it)
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.20f)
                .background(backGround)
                .padding(top = 35.dp, start = 10.dp)
            ) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(backGround),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    ProfileImage(
                        imageUrl = app.waste2wealth.com.R.drawable.appicon,
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFF4483D1),
                                shape = CircleShape
                            )
                            .padding(3.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = profileName,
                            fontSize = 31.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 25.dp)
                        )
                        Text(
                            text = "Organization: $organization",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(top = 5.dp, end = 25.dp)
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(start = 15.dp, top = 15.dp)) {
                Text(
                    text = "Personal Details",
                    color = Color(0xFF005F47),
                    fontSize = 20.sp
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 0.dp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Phone",
                        color = backGround,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = phoneNumber,
                        color = Color.Black,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                }
            }
        }
    }
}