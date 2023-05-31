package app.waste2wealth.com.profile

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.SupportAgent
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun NewProfileScreen(navController: NavHostController) {
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
        Scaffold(bottomBar = {
            BottomBar(navController = navController)
        }) {
            println(it)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appBackground)
            ) {
                var profileList by remember {
                    mutableStateOf<List<ProfileInfo>?>(null)
                }
                var myPhoneNumber by remember {
                    mutableStateOf("")
                }
                JetFirestore(path = {
                    collection("ProfileInfo")
                }, onRealtimeCollectionFetch = { value, _ ->
                    profileList = value?.getListOfObjects()
                }) {
                    if (profileList != null) {
                        for (i in profileList!!) {
                            if (i.email == user?.email) {
                                myPhoneNumber = i.phoneNumber ?: ""
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 35.dp, bottom = 7.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ProfileImage(
                            imageUrl = user?.photoUrl,
                            modifier = Modifier
                                .size(100.dp)
                                .border(
                                    width = 1.dp,
                                    color = appBackground,
                                    shape = CircleShape
                                )
                                .padding(3.dp)
                                .clip(CircleShape),
                            initial = user?.displayName?.first() ?: 'A'
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 7.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = user?.displayName ?: "User Name",
                            color = textColor,
                            fontSize = 20.sp,
                            fontFamily = monteBold,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 7.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${user?.email ?: "User Email"} | $myPhoneNumber",
                            color = textColor,
                            fontSize = 12.sp,
                            fontFamily = monteSB,
                            softWrap = true,
                            modifier = Modifier.padding(end = 7.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))

                    Card(
                        backgroundColor = appBackground,
                        elevation = 10.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Column {

                            RepeatedProfileInfo(
                                icon = Icons.Filled.EditNote,
                                text = "Edit Profile"
                            )
                            RepeatedProfileInfo(
                                icon = Icons.Filled.Notifications,
                                text = "Notifications"
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Card(
                        backgroundColor = appBackground,
                        elevation = 10.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Column {

                            RepeatedProfileInfo(
                                icon = Icons.Filled.SupportAgent,
                                text = "Help and Support"
                            )
                            RepeatedProfileInfo(
                                icon = Icons.Filled.ContactPage,
                                text = "Contact Us"
                            )
                            RepeatedProfileInfo(
                                icon = Icons.Filled.PrivacyTip,
                                text = "Privacy Policy"
                            )
                        }

                    }

                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFD5065),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(35.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(50.dp)
                    ) {
                        Text(
                            text = "Logout",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(bottom = 4.dp),
                            maxLines = 1,
                            softWrap = true
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun RepeatedProfileInfo(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit?)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick?.invoke()
            }
            .padding(start = 10.dp, top = 15.dp, bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = textColor,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 13.dp)
        )
        Text(
            text = text,
            color = textColor,
            fontSize = 15.sp,
            fontFamily = monteSB
        )
    }
}