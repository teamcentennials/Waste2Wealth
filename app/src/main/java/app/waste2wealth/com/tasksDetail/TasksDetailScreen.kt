package app.waste2wealth.com.tasksDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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


        }
    }
}

