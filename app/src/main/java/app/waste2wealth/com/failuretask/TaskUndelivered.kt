package app.waste2wealth.com.failuretask

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
    ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun TaskUndelivered(
    navHostController: NavHostController,
    phoneNumber: String,
) {
    val ctx = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    var reason by remember { mutableStateOf("Select Reason") }
    val dropDownContent = listOf(
        "Waste Not Found",
        "Wrong Address",
        "No Equipment to Collect Waste",
        "Other"
    )

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
    var isDropDownVisible by remember { mutableStateOf(false) }
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TasksDetailsAppBar(onClick = {
        }, navHostController = navHostController, text = "Task Incomplete", isCallVisible = false)
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
                        .padding(top = 10.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(bottom = 5.dp)
                            .background(Color(0xFFF3EDED)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Reason",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(start = 13.dp)
                        )
                    }
                    ExposedDropdownMenuBox(expanded = isDropDownVisible, onExpandedChange = {
                        isDropDownVisible = true
                    }, modifier = Modifier.background(Color.White)) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = Color.White,
                            elevation = 5.dp,
                            shape = RoundedCornerShape(7.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, bottom = 10.dp)
                                    .background(Color.White)
                                    .clickable {
                                        isDropDownVisible = true
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = reason,
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontFamily = monteNormal,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = "dropDownArrow",
                                        tint = Color.LightGray,
                                        modifier = Modifier.padding(end = 10.dp)
                                    )

                                }
                                DropdownMenu(
                                    expanded = isDropDownVisible,
                                    onDismissRequest = {
                                        isDropDownVisible = !isDropDownVisible
                                    },
                                    modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, end = 10.dp)
                                ) {
                                    dropDownContent.forEach { value ->
                                        DropdownMenuItem(onClick = {
                                            reason = value
                                            isDropDownVisible = false
                                        }) {
                                            Text(
                                                text = value,
                                                color = Color.Black,
                                                fontFamily = monteNormal,
                                                fontSize = 15.sp
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 0.dp, top = 35.dp)
                            .height(50.dp)
                            .background(Color(0xFFF3EDED)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Upload Attempt Proof",
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
                                    text = "Upload Proof of Attempt",
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
                            text = "FINISH TASK",
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