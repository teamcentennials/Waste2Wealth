package app.waste2wealth.com.challenges

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.waste2wealth.com.firebase.firestore.challengesList
import app.waste2wealth.com.firebase.firestore.organisationList
import app.waste2wealth.com.profile.ProfileImage
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import com.omercemcicekli.cardstack.CardStack
import com.omercemcicekli.cardstack.HorizontalAlignment
import com.omercemcicekli.cardstack.HorizontalAnimationStyle
import com.omercemcicekli.cardstack.Orientation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Challenges() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardStack(
            { index ->
                Card(
                    backgroundColor = appBackground,
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                ) {
                    Column {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(horizontal = 0.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ProfileImage(
                                imageUrl = challengesList[index].image,
                                modifier = Modifier
                                    .fillMaxWidth(0.65f)
                                    .size(200.dp)
                                    .border(
                                        width = 1.dp,
                                        color = appBackground,
                                        shape = RoundedCornerShape(45.dp)
                                    )
                                    .padding(3.dp)
                                    .clip(RoundedCornerShape(30.dp)),
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = challengesList[index].title,
                            color = textColor,
                            fontSize = 20.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "When",
                            color = Color(0xFFF37952),
                            fontSize = 12.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = challengesList[index].date,
                            color = textColor,
                            fontSize = 15.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Where",
                            color = Color(0xFFF37952),
                            fontSize = 12.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = challengesList[index].location,
                            color = textColor,
                            fontSize = 15.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Time",
                            color = Color(0xFFF37952),
                            fontSize = 12.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = challengesList[index].time,
                            color = textColor,
                            fontSize = 15.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFFD5065),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(35.dp),
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(
                                text = "Register Now",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontFamily = monteSB,
                                modifier = Modifier.padding(bottom = 4.dp),
                                maxLines = 1,
                                softWrap = true
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }


                }
            },
            cardCount = challengesList.size,
            paddingBetweenCards = 55.dp,
            orientation = Orientation.Horizontal(
                alignment = HorizontalAlignment.StartToEnd,
                animationStyle = HorizontalAnimationStyle.FromTop
            )

        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Clubs() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardStack(
            { index ->
                Card(
                    backgroundColor = appBackground,
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                ) {
                    Column {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(horizontal = 0.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ProfileImage(
                                imageUrl = organisationList[index].image,
                                modifier = Modifier
                                    .fillMaxWidth(0.65f)
                                    .size(200.dp)
                                    .border(
                                        width = 1.dp,
                                        color = appBackground,
                                        shape = RoundedCornerShape(45.dp)
                                    )
                                    .padding(3.dp)
                                    .clip(RoundedCornerShape(30.dp)),
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = organisationList[index].title,
                            color = textColor,
                            fontSize = 20.sp,
                            fontFamily = monteBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Location",
                            color = Color(0xFFF37952),
                            fontSize = 15.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = organisationList[index].location,
                            color = textColor,
                            fontSize = 12.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "About",
                            color = Color(0xFFF37952),
                            fontSize = 15.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = organisationList[index].about,
                            color = textColor,
                            fontSize = 12.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        val text = remember {
                            mutableStateOf("Join Now")
                        }
                        Button(
                            onClick = { text.value = "Joined" },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFFD5065),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(35.dp),
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(
                                text = text.value,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontFamily = monteSB,
                                modifier = Modifier.padding(bottom = 4.dp),
                                maxLines = 1,
                                softWrap = true
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }


                }
            },
            cardCount = organisationList.size,
            paddingBetweenCards = 55.dp,
            orientation = Orientation.Horizontal(
                alignment = HorizontalAlignment.StartToEnd,
                animationStyle = HorizontalAnimationStyle.FromTop
            )

        )
    }
}