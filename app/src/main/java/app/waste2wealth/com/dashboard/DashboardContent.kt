package app.waste2wealth.com.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.tasksList.customShadow
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB

@Composable
fun DashboardContent(
    connected: Boolean = true,
    tripID: String = "611413",
    hub: String = "5 times",
    tasks: String = "19",
    navHostController: NavHostController,
    locationViewModel: LocationViewModel,
) {
    val ls = locationViewModel.locationState.collectAsState()
    LaunchedEffect(ls) {
        locationViewModel.updateLocation()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = 15.dp,
            modifier = Modifier
                .padding(15.dp)
                .customShadow(),
            backgroundColor = Color.White,
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)) {
                Text(text = "STATUS",
                    color = Color.Black,
                    fontFamily = monteSB,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp, start = 2.dp)
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp)) {
                    Icon(painter = painterResource(id = R.drawable.globe),
                        contentDescription = "Location Icon",
                        tint = if (connected) backGround else Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (connected) "Capturing your Location" else "Not Connected",
                        color = if (connected) Color(0xFF388365) else Color.Red,
                        fontFamily = monteSB,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
        Card(
            elevation = 15.dp,
            modifier = Modifier
                .padding(15.dp)
                .customShadow(),
            backgroundColor = Color.White,
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to Waste2Wealth",
                    color = Color.Black,
                    fontFamily = monteSB,
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 0.dp,
                    startIndent = 0.dp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = "Points Earned",
                        color = Color.Black,
                        fontFamily = monteSB,
                    )
                    Text(
                        text = tripID,
                        color = Color.Black,
                        fontFamily = monteNormal,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 15.dp)
                    )

                }
                Divider(
                    color = Color.LightGray,
                    thickness = 0.dp,
                    startIndent = 0.dp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = "Waste Reported",
                        color = Color.Black,
                        fontFamily = monteSB,
                    )
                    Text(
                        text = hub,
                        color = Color.Black,
                        fontFamily = monteNormal,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 15.dp)
                    )

                }
                Divider(
                    color = Color.LightGray,
                    thickness = 0.dp,
                    startIndent = 0.dp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = "Waste Collected",
                        color = Color.Black,
                        fontFamily = monteSB,
                    )
                    Text(
                        text = hub,
                        color = Color.Black,
                        fontFamily = monteNormal,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 15.dp)
                    )

                }
                Divider(
                    color = Color.LightGray,
                    thickness = 0.dp,
                    startIndent = 0.dp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = "Points Redeemed",
                        color = Color.Black,
                        fontFamily = monteSB,
                    )
                    Text(
                        text = tasks,
                        color = Color.Black,
                        fontFamily = monteNormal,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                }
                Divider(
                    color = Color.LightGray,
                    thickness = 0.dp,
                    startIndent = 0.dp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 10.dp)
//                ) {
//                    Text(
//                        text = "V",
//                        color = Color.Black,
//                        fontFamily = monteSB,
//                    )
//                    Text(
//                        text = v,
//                        color = Color.Black,
//                        fontFamily = monteNormal,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(end = 15.dp),
//                        fontSize = 14.sp
//                    )
//
//                }
            }
        }
        Text(text = ls.value, color = Color.Black, modifier = Modifier.padding(start = 30.dp))
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Button(onClick = {
                navHostController.navigate(Screens.TasksLists.route)
            }, colors = ButtonDefaults.buttonColors(
                backgroundColor = backGround
            ), modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "CONTINUE",
                    color = Color.White,
                    fontFamily = monteSB,
                    fontSize = 13.sp
                )
            }
        }
    }
}