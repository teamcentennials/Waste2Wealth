package app.waste2wealth.com.tasksList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.components.TasksListsAppBar
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.navigation.TaskDetailsConstants
import app.waste2wealth.com.tasksDetail.TaskItem
import app.waste2wealth.com.tasksDetail.list
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB

@Composable
fun TasksLists(
    items: List<TaskItem> = list,
    navHostController: NavHostController,
) {
    val tasksList = mutableListOf<TaskItem>()
    LaunchedEffect(key1 = Unit){

    }
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TasksListsAppBar(navHostController = navHostController)
    }) {
        println(it)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Spacer(modifier = Modifier.height(50.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    contentPadding = PaddingValues(bottom = 100.dp, top = 50.dp)
                ) {
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 1",
                            address = "2nd Floor, Tower A, Advant Navis Business Park," +
                                    " Sector 142, Noida Expressway, Noida, Uttar Pradesh 201301",
                            noOfKMs = 6,
                            image = R.drawable.one
                        )
                    )
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 2",
                            address = "1202, Raheja Towers, MG Road, Bengaluru, Karnataka 560001x",
                            noOfKMs = 2,
                            image = R.drawable.two
                        )
                    )
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 3",
                            address = "F-202, Laxmi Nagar, Near Laxmi Park, Mumbai, Maharashtra 400075",
                            noOfKMs = 1,
                            image = R.drawable.three
                        )
                    )
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 4",
                            address = "34, Dr. B.R. Ambedkar Road, Sangamwadi, Pune, Maharashtra 411001",
                            noOfKMs = 15,
                            image = R.drawable.four
                        )
                    )
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 5",
                            address = "5th Floor, Atria Building, The V, Near Marol Naka " +
                                    "Metro Station, Andheri Kurla Road, Mumbai, Maharashtra 400059",
                            noOfKMs = 9,
                            image = R.drawable.five
                        )
                    )
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 6",
                            address = "House No. 36, Sector 7, Near Community Centre, Dwarka, New Delhi, Delhi 110075",
                            noOfKMs = 7,
                            image = R.drawable.six
                        )
                    )
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 7",
                            address = "2nd Floor, Tower A, Advant Navis Business Park," +
                                    " Sector 142, Noida Expressway, Noida, Uttar Pradesh 201301",
                            noOfKMs = 15,
                            image = R.drawable.seven
                        )
                    )
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 8",
                            address = "S-24, 2nd Floor, Ocean Complex, Sector 18, Noida, Uttar Pradesh 201301",
                            noOfKMs = 10,
                            image = R.drawable.eight
                        )
                    )
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 9",
                            address = "14/2, Wadala Shree Ram Industrial Estate, G.D. Ambekar Marg, " +
                                    "Mumbai, Maharashtra 400031",
                            noOfKMs = 6,
                            image = R.drawable.nine
                        )
                    )
                    tasksList.add(
                        TaskItem(
                            locationNumber = "Location No: 10",
                            address = "Plot No. 5, Phase III, Udyog Vihar, " +
                                    "Sector 20, Gurugram, Haryana 122016",
                            noOfKMs = 9,
                            image = R.drawable.ten
                        )
                    )
                    itemsIndexed(tasksList) { index, taskItem ->
                        Card(
                            elevation = 3.dp,
                            backgroundColor = Color.White,
                            shape = RoundedCornerShape(7.dp),
                            modifier = Modifier
                                .padding(20.dp)
                                .customShadow()
                                .clickable {
//                                    isExpanded = !isExpanded

                                    navHostController.navigate(
                                        Screens.TaskDetail.route +
                                                "?${TaskDetailsConstants.taskCode.value}=${taskItem.locationNumber}" +
                                                "?${TaskDetailsConstants.address.value}=${taskItem.address}" +
                                                "?${TaskDetailsConstants.noOfAttempts.value}=${taskItem.noOfKMs}" +
                                                "?${TaskDetailsConstants.taskPrice.value}=${taskItem.image}"
                                    )


                                }

                        ) {
                            Column(Modifier.fillMaxWidth()) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    CustomRow(
                                        imageVector = painterResource(id = R.drawable.name),
                                        textDesc = taskItem.locationNumber
                                    )
                                    Card(
                                        elevation = 0.dp,
                                        backgroundColor = Color(0xFFe9fbfb),
                                        shape = RoundedCornerShape(100.dp),
                                        modifier = Modifier.padding(top = 5.dp, end = 10.dp)
                                    ) {
                                        Text(
                                            text = " ${taskItem.noOfKMs} Km(s)",
                                            color = Color.Black,
                                            fontSize = 12.sp,
                                            fontFamily = monteSB,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(15.dp))

                                Spacer(modifier = Modifier.height(15.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    CustomRow(
                                        imageVector = painterResource(id = R.drawable.locationicon),
                                        textDesc = taskItem.address,
                                        fontSize = 15.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(18.dp))
                                Spacer(modifier = Modifier.height(15.dp))
                            }
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
                    text = "10 Tasks Pending",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = monteSB,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 20.dp),
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Button(
                        onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = backGround,
                            disabledBackgroundColor = backGround.copy(0.5f)
                        ), shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        enabled = false
                    ) {
                        Text(
                            text = "END TRIP",
                            color = Color.White,
                            modifier = Modifier.padding(10.dp),
                            fontFamily = monteNormal
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun CustomRow(
    imageVector: Painter,
    textDesc: String,
    size: Dp = 15.dp,
    fontSize: TextUnit = 13.sp,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = imageVector,
            contentDescription = "",
            tint = backGround,
            modifier = Modifier
                .padding(start = 10.dp)
                .size(size)
        )
        Text(
            text = textDesc,
            color = Color.Black,
            fontSize = fontSize,
            fontFamily = monteSB,
            modifier = Modifier.padding(end = 15.dp)
        )
    }
}

fun Modifier.customShadow(): Modifier {
    return shadow(
        elevation = 13.dp,
        shape = RoundedCornerShape(7.dp),
        spotColor = Color(0xFFC7C4C4)
    )
}