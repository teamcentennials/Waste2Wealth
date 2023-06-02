 package app.waste2wealth.com.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.com.ui.theme.monteNormal

 @Composable
fun LoginPage(navController: NavHostController, scaffoldState: ScaffoldState) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var isPhoneError by remember {
        mutableStateOf(true)
    }
    var proceedToOTP by remember {
        mutableStateOf(false)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(proceedToOTP) {
        if (proceedToOTP)
            focusRequester.requestFocus()
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(backGround)
        .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Waste 2 Wealth",
            color = Color.White,
            fontFamily = monteNormal,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 45.dp, start = 15.dp, bottom = 15.dp)
        )
        Column {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    isPhoneError = false
                    if (phoneNumber.isEmpty()) {
                        isPhoneError = true
                    }
                    if (phoneNumber.length != 10) {
                        isPhoneError = true
                    }
                    if (!phoneNumber.isDigitsOnly()) {
                        isPhoneError = true
                    }
                },
                enabled = !proceedToOTP,
                isError = isPhoneError,
                leadingIcon = {
                    Icon(
                        Icons.Filled.Phone,
                        contentDescription = "Phone Number",
                        tint = backGround,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                placeholder = {
                    Text(text = "Enter your mobile number", color = Color.DarkGray)
                },
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                ),
                modifier = Modifier
                    .padding(start = 15.dp)
            )

            if (isPhoneError) {
                Text(
                    text = "Phone Number Invalid",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp)
                )
            }

        }
        OutlinedButton(
            onClick = {
                if (!isPhoneError) {
                    proceedToOTP = !proceedToOTP
                    isTimerRunning = true
                }
            },
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color(0xFF10857f),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = ButtonDefaults.elevation(15.dp),
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = monteNormal
            )
        }
        OTPScreen(
            visible = proceedToOTP,
            phoneNumber = phoneNumber,
            isTimerRunning = isTimerRunning,
            scaffoldState = scaffoldState,
            navHostController = navController,
            focusRequester = focusRequester
        )

    }
}