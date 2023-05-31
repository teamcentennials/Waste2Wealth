package app.waste2wealth.com.tasksList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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