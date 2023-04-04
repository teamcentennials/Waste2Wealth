package app.waste2wealth.com.login

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.com.ui.theme.monteNormal
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OTPScreen(
    visible: Boolean,
    phoneNumber: String,
    isTimerRunning: Boolean,
    scaffoldState: ScaffoldState,
    focusRequester: FocusRequester,
    navHostController: NavHostController,
) {

    var otpText by remember {
        mutableStateOf("")
    }
    val totalTime = 30L * 1000L
    val initialValue = 1f
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isResendVisible by remember {
        mutableStateOf(false)
    }
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)

    AnimatedVisibility(visible = visible,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Code Sent to +91$phoneNumber",
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp, bottom = 15.dp),
                fontSize = 25.sp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                val otpAnimation by rememberLottieComposition(spec = LottieCompositionSpec.Asset("oa.json"))
                LottieAnimation(composition = otpAnimation,
                    iterations = 2,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(200.dp))
            }
            Column {
                OutlinedTextField(value = otpText,
                    onValueChange = {
                        otpText = it
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Password,
                            contentDescription = "OTP",
                            tint = backGround,
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Send),
                    placeholder = {
                        Text(text = "Enter 6-Digit OTP sent", color = Color.DarkGray)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.White,
                    ),
                    modifier = Modifier
                        .padding(15.dp)
                        .focusRequester(focusRequester)
                )
                LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
                    if (currentTime > 0 && isTimerRunning) {
                        delay(100L)
                        currentTime -= 100L
                        value = currentTime / totalTime.toFloat()
                    }
                    if (currentTime == 0L) {
                        isResendVisible = true
                    }
                }
                AnimatedVisibility(
                    visible = true,
                    exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                ) {
                    if (currentTime != 0L) {
                        Text(text = "Resend OTP in ${(currentTime / 1000L)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(15.dp))
                    }
                    AnimatedVisibility(visible = isResendVisible,
                        enter = fadeIn(animationSpec = tween(durationMillis = 300))) {
                        OutlinedButton(
                            onClick = {
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "We're Calling You",
                                        actionLabel = "Dismiss"
                                    )
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(
                                0xFF10857f), contentColor = Color.White),
                            modifier = Modifier.padding(10.dp),
                            shape = RoundedCornerShape(15.dp),
                            elevation = ButtonDefaults.elevation(15.dp),
                        ) {
                            Text(
                                text = "Get OTP on Call",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontFamily = monteNormal
                            )
                        }
                    }

                }


            }
            OutlinedButton(
                onClick = {
                    navHostController.navigate(Screens.Dashboard.route)
                },
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(
                    0xFF10857f), contentColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = ButtonDefaults.elevation(15.dp),
            ) {
                Text(
                    text = "Proceed Ahead",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = monteNormal
                )
            }
        }

    }
}



