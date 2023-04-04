package app.waste2wealth.com.tasksDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.components.TasksDetailsAppBar
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.navigation.TaskDetailsConstants
import app.waste2wealth.com.tasksList.CustomRow
import app.waste2wealth.com.tasksList.customShadow
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB
import kotlinx.coroutines.launch

@Composable
fun TasksDetails(
    taskCode: String,
    taskPrice: String = "0",
    noOfKms: String,
    address: String,
    phoneNumber: String = "123456789",
    image: String,
    navHostController: NavHostController,
) {
    val scaffoldState = rememberScaffoldState()
    val ctx = LocalContext.current
    var isDialogVisible by remember { mutableStateOf(false) }
    var loadProgress by remember { mutableStateOf(false) }
    var isSuccessVisible by remember { mutableStateOf(false) }
    val width = LocalConfiguration.current.screenWidthDp
    val coroutineScope = rememberCoroutineScope()
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TasksDetailsAppBar(onClick = {
//            val u = Uri.parse("tel:$phoneNumber")
//            val i = Intent(Intent.ACTION_DIAL, u)
//            ctx.startActivity(i)
        }, navHostController = navHostController, text = "Task Detail", isCallVisible = true)
    }) {
        println(it)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Card(
                    elevation = 15.dp,
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .padding(top = 80.dp, end = 20.dp, start = 20.dp)
                        .customShadow()
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomRow(
                                imageVector = painterResource(id = R.drawable.ic_baseline_refresh_24),
                                textDesc = "$noOfKms Km(s)"
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomRow(
                                imageVector = painterResource(id = R.drawable.locationicon),
                                textDesc = address,
                                fontSize = 15.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp, bottom = 10.dp)
                        ) {
                            Button(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                ),
                                elevation = ButtonDefaults.elevation(0.dp),
                                shape = RoundedCornerShape(5.dp),
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .border(
                                        width = 1.dp,
                                        color = backGround,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                            ) {
                                CustomRow(
                                    imageVector = painterResource(id = R.drawable.navigation),
                                    textDesc = "Navigate", size = 20.dp
                                )
                            }


                        }
                        Spacer(modifier = Modifier.height(15.dp))
                    }


                }
                Spacer(modifier = Modifier.height(15.dp))
                Card(elevation = 3.dp,
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .customShadow()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = image.toInt()),
                            contentDescription = "",
                            contentScale = ContentScale.Fit
                        )
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
                    text = "$taskCode",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = monteSB,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 20.dp),
                )
            }
            if (isSuccessVisible) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                    Button(
                        onClick = {
                            navHostController.navigate(
                                Screens.FailureTask.route +
                                        "?${TaskDetailsConstants.phoneNumber.value}=${phoneNumber}"
                            )

                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFfe0000)
                        ), modifier = Modifier
                            .width(width = (width / 2.5).dp)
                            .height(45.dp)
                            .padding(start = 20.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "FAIL",
                            color = Color.White,
                            fontFamily = monteNormal,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(end = 0.dp)
                        )
                    }

                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                    Button(
                        onClick = {
                            navHostController.navigate(
                                Screens.SuccessTask.route +
                                        "?${TaskDetailsConstants.taskPrice.value}=${taskPrice}"
                            )
                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF008002)
                        ), modifier = Modifier
                            .width(width = (width / 2.0).dp)
                            .height(45.dp)
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "SUCCESS",
                            color = Color.White,
                            fontFamily = monteNormal,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(end = 0.dp)
                        )
                    }

                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    Button(
                        onClick = {
                            isDialogVisible = true
                            loadProgress = true
                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = backGround
                        ), modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .padding(horizontal = 65.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "I REACHED",
                            color = Color.White,
                            fontFamily = monteNormal,
                            fontSize = 15.sp
                        )

                    }

                }
            }


            DialogBox(isVisible = isDialogVisible, successRequest = {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Successfully Checked In",
                        actionLabel = "Close",
                        duration = SnackbarDuration.Long
                    )
                }
                isSuccessVisible = true
                isDialogVisible = false
            }) {
                isDialogVisible = !isDialogVisible
            }
        }
    }
}


@Composable
fun DialogBox(isVisible: Boolean, successRequest: () -> Unit, dismissRequest: () -> Unit) {
    if (isVisible) {

        Dialog(onDismissRequest = dismissRequest) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
                elevation = 8.dp
            ) {
                Column(
                    Modifier
                        .background(Color.White)
                ) {

                    Image(
                        Icons.Filled.Warning,
                        contentDescription = null, // decorative
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(
                            color = Color.Red.copy(0.6f)
                        ),
                        modifier = Modifier
                            .padding(top = 35.dp)
                            .height(70.dp)
                            .fillMaxWidth(),

                        )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Incorrect Location",
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontFamily = monteSB,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(top = 0.dp)
                                .fillMaxWidth(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "The Location of Waste and your location are not matching. " +
                                    "Do you still want to continue check in ?",
                            textAlign = TextAlign.Center,
                            fontFamily = monteNormal,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .padding(top = 7.dp, start = 25.dp, end = 13.dp)
                                .fillMaxWidth(),
                            color = Color.Black
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .background(backGround),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        TextButton(onClick = dismissRequest) {
                            Text(
                                "NO",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                        TextButton(onClick = successRequest) {
                            Text(
                                "YES",
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                }
            }
        }

    }
}
