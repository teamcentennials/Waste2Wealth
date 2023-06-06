package app.waste2wealth.com.successtask

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.components.TasksDetailsAppBar
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun DeliveryDetailsScreen(
    navHostController: NavHostController,
    taskPrice: String,
) {
    val scaffoldState = rememberScaffoldState()
    val radioOptions = listOf("Yes", "No")
    val depositedWaste = listOf("Yes", "No")
    var receiver by remember { mutableStateOf(radioOptions[0]) }
    val coroutineScope = rememberCoroutineScope()
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            println("Bitmaps is ${it?.asImageBitmap()}")
            imageBitmap = it?.asImageBitmap()
        }
    )
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
        )
    )
    val permissionDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val gesturesEnabled by remember { derivedStateOf { permissionDrawerState.isOpen } }
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TasksDetailsAppBar(onClick = {
        }, navHostController = navHostController, text = "Delivery Details", isCallVisible = false)
    }) {
        println(it)
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(bottom = 5.dp)
                            .background(Color(0xFFF3EDED)),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Have you Deposited the Waste",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(start = 13.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, bottom = 10.dp, start = 10.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        depositedWaste.forEach { value ->
                            Row(
                                modifier = Modifier
                                    .selectable(
                                        selected = (value == receiver),
                                        role = Role.RadioButton,
                                        onClick = {
                                            receiver = value
                                        }
                                    )
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = value,
                                    color = Color.Black,
                                    fontFamily = monteSB
                                )
                                RadioButton(
                                    selected = (value == receiver),
                                    onClick = {
                                        receiver = value
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = backGround,
                                        unselectedColor = Color.Gray
                                    )
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 0.dp, top = 15.dp)
                            .height(50.dp)
                            .background(Color(0xFFF3EDED)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Collected All Waste ?",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(start = 13.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, bottom = 10.dp, start = 10.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        radioOptions.forEach { value ->
                            Row(
                                modifier = Modifier
                                    .selectable(
                                        selected = (value == receiver),
                                        role = Role.RadioButton,
                                        onClick = {
                                            receiver = value
                                        }
                                    )
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = value,
                                    color = Color.Black,
                                    fontFamily = monteSB
                                )
                                RadioButton(
                                    selected = (value == receiver),
                                    onClick = {
                                        receiver = value
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = backGround,
                                        unselectedColor = Color.Gray
                                    )
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 0.dp, top = 15.dp)
                            .height(50.dp)
                            .background(Color(0xFFF3EDED)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Proof Of Delivery",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(start = 13.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 7.dp, bottom = 10.dp, start = 10.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (imageBitmap == null) {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        if (!permissionState.allPermissionsGranted) {
                                            permissionDrawerState.open()
                                        } else {
                                            launcher.launch(
                                                ActivityOptionsCompat.makeBasic()
                                            )
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Transparent
                                ),
                                elevation = ButtonDefaults.elevation(0.dp),
                                shape = RoundedCornerShape(7.dp),
                                border = BorderStroke(width = 0.dp, color = backGround)
                            ) {
                                Text(
                                    text = "Upload Proof",
                                    color = backGround,
                                    fontFamily = monteSB
                                )
                            }
                        } else {
                            Card(
                                backgroundColor = Color.Transparent,
                                shape = RoundedCornerShape(7.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp, bottom = 25.dp)
                            ) {
                                Image(
                                    bitmap = imageBitmap!!,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFe2e2e2)),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = "Number of pieces: 4",
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontFamily = monteSB,
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 20.dp),
                    )
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    Button(
                        onClick = {

                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = backGround
                        ), modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .padding(horizontal = 65.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "COMPLETE TASK",
                            color = Color.White,
                            fontFamily = monteNormal,
                            fontSize = 15.sp
                        )

                    }

                }
            }
        }
    }
}


