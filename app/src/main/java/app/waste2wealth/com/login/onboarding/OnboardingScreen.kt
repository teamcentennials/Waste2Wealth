package app.waste2wealth.com.login.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.UserDatastore
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.login.rememberFirebaseAuthLauncher
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Onboarding(navHostController: NavHostController) {
    val items = ArrayList<ObData>()
    val pagerState = rememberPagerState(initialPage = 0)

    items.add(
        ObData(
            image = R.drawable.ob1,
            title = "Welcome",
            description = "Making Cities Sustainable, Rewarding, and Fun."
        )
    )
    items.add(
        ObData(
            image = R.drawable.ob2,
            title = "Report and collect Waste ",
            description = "Make a difference by reporting and collecting waste in your community."
        )
    )
    items.add(
        ObData(
            image = R.drawable.ob3,
            title = "Track your \n" +
                    "social Contribution",
            description = "See your impact grow as you report and collect waste."
        )
    )
    items.add(
        ObData(
            image = R.drawable.ob4,
            title = "Team up \n" +
                    "With your \n" +
                    "Local Communities",
            description = "Join forces for a cleaner environment."
        )
    )
    items.add(
        ObData(
            image = R.drawable.ob5,
            title = "Get Rewarded ",
            description = "Earn points for your efforts and redeem exciting rewards or donate it."
        )
    )

    Bgresource(
        item = items,
        pagerState = pagerState,
        navHostController = navHostController
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Bgresource(
    item: List<ObData>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val token = stringResource(R.string.default_web_client_id)
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    var profileList by remember {
        mutableStateOf<List<ProfileInfo>?>(null)
    }
    val dataStore = UserDatastore(context)
    val coroutineScope = rememberCoroutineScope()
    JetFirestore(path = {
        collection("ProfileInfo")
    }, onRealtimeCollectionFetch = { value, _ ->
        profileList = value?.getListOfObjects()
    }) {
        LaunchedEffect(key1 = user) {
            if (user != null) {
                val registered = profileList?.any { it.email == user?.email }

                coroutineScope.launch {
                    dataStore.saveEmail(user?.email.toString())
                    dataStore.savePfp(user?.photoUrl.toString())
                    dataStore.saveName(user?.displayName.toString())
                }
                navHostController.popBackStack()
                navHostController.navigate(
                    if (registered == true)
                        Screens.SettingUp.route else Screens.CompleteProfile.route
                )
            }
        }
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail().requestProfile()
                .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val launcher = rememberFirebaseAuthLauncher(
            onAuthComplete = { result ->
                user = result.user
            },
            onAuthError = {
                user = null
            }
        )
        Column(
            modifier = modifier.background(
                color = appBackground
            )
        ) {
            val coroutineScope = rememberCoroutineScope()
            HorizontalPager(
                pageCount = item.size,
                state = pagerState,
                modifier = Modifier.background(appBackground)
            ) { page ->
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(appBackground)
                        .fillMaxHeight(0.95f)
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxHeight()
                            .background(color = appBackground)
                    ) {
                        Image(
                            painter = painterResource(id = item[page].image),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = modifier
                                .fillMaxHeight(0.62f)
                                .fillMaxWidth(),
                            alignment = Alignment.Center
                        )
                        Box(contentAlignment = Alignment.BottomCenter) {
                            Card(
                                shape = RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp),
                                elevation = 20.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(appBackground)
                                    .fillMaxHeight(2.5f),
                            )
                            {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                ) {
                                    Text(
                                        text = item[page].title,
                                        fontSize = 30.sp,
                                        color = textColor,
                                        softWrap = true,
                                        modifier = Modifier.offset(y = (-20).dp)
                                    )
                                    Text(
                                        text = item[page].description,
                                        color = textColor,
                                        modifier = Modifier
                                            .padding(vertical = 50.dp)
                                            .offset(y = (-20).dp),
                                        softWrap = true,
                                    )
                                }
                            }
                            Box(
                                contentAlignment = Alignment.BottomStart,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 50.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.padding(top = 5.dp, start = 15.dp)
                                ) {
                                    repeat(item.size) {
                                        if (page != (item.size - 1)) {
                                            Indicator(isSelected = it == pagerState.currentPage)
                                        }
                                    }
                                }
                            }
                            Box(
                                contentAlignment = if (page != (item.size - 1)) Alignment.BottomEnd
                                else Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 15.dp)
                            ) {
                                if (page != (item.size - 1)) {
                                    val px = (pagerState.currentPage + 1) / (item.size - 1)
                                        .toFloat()
                                    var animationplayed by remember {
                                        mutableStateOf(false)
                                    }
                                    val curPer = animateFloatAsState(
                                        targetValue = if (animationplayed) px else 0f,
                                        animationSpec = tween(
                                            durationMillis = 1000
                                        ), label = ""
                                    )
                                    LaunchedEffect(key1 = true) {
                                        animationplayed = true
                                    }
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.size(100.dp)
                                    ) {
                                        Canvas(modifier = Modifier.size(50.dp)) {
                                            drawArc(
                                                color = Color(0xFF01FF73),
                                                startAngle = -90f,
                                                sweepAngle = (360 * curPer.value),
                                                useCenter = false,
                                                style = Stroke(
                                                    width = 4f,
                                                    cap = StrokeCap.Round
                                                )
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    pagerState.animateScrollToPage(
                                                        pagerState
                                                            .currentPage + 1
                                                    )
                                                }
                                            },
                                            modifier = Modifier.size(70.dp)
                                        ) {
                                            Icon(
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .clip(CircleShape),
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = "next",
                                                tint = textColor
                                            )
                                        }
                                    }
                                } else {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 25.dp,
                                                end = 25.dp,
                                                bottom = 30.dp,
                                                top = 20.dp
                                            )
                                            .clickable {
                                                launcher.launch(googleSignInClient.signInIntent)
                                            }
                                            .clip(RoundedCornerShape(80.dp))
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.gooogle),
                                            contentDescription = null,
                                            tint = Color.Unspecified,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .padding(end = 10.dp)
                                        )
                                        Text(
                                            "Continue with Google",
                                            color = textColor,
                                            fontFamily = monteSB,
                                            fontSize = 19.sp,
                                            modifier = Modifier.padding(end = 7.dp)
                                        )

                                    }

                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 25.dp else 10.dp, label = "")
    Box(
        modifier = Modifier
            .padding(5.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) Color(0xFF55C1D4) else Color(0xFFF8D6A1)
            )
    )
}