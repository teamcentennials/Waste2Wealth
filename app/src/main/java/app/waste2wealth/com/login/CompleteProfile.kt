package app.waste2wealth.com.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHomeWork
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.UserDatastore
import app.waste2wealth.com.firebase.firestore.updateInfoToFirebase
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompleteProfile(navHostController: NavHostController) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    println("Current user is ${user?.displayName}")
    var email by remember {
        mutableStateOf(TextFieldValue(user?.email ?: ""))
    }
    var fullName by remember {
        mutableStateOf(TextFieldValue(user?.displayName ?: ""))
    }
    var organization by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var gender by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var address by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var phone by remember {
        mutableStateOf(TextFieldValue("+91"))
    }
    var otp by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var vfId by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val currentActivity = context as? Activity
    var isOtpSent by remember {
        mutableStateOf(false)
    }
    var isOTPVerified by remember {
        mutableStateOf(false)
    }
    var isGenderCorrect by remember {
        mutableStateOf(false)
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var isGenderExpansded by remember {
        mutableStateOf(false)
    }
    val token = stringResource(R.string.default_web_client_id)
    val gso =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail().requestProfile()
            .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    var listOfOrganizations by remember {
        mutableStateOf(
            mutableListOf(
                "Individual",
                "Organization"
            )
        )
    }
    var listOfGender by remember { mutableStateOf(mutableListOf("Male", "Female", "Other")) }
    val dataStore = UserDatastore(context)
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appBackground)
                .verticalScroll(rememberScrollState())
        ) {

            LaunchedEffect(key1 = gender) {
                isGenderCorrect = gender.text.contains(
                    "Male",
                    ignoreCase = true
                ) || (gender.text.contains(
                    "Female",
                    ignoreCase = true
                )) || (gender.text.contains(
                    "Other",
                    ignoreCase = true
                ))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, top = 30.dp)
                    .clickable {
                        coroutineScope.launch {
                            dataStore.saveEmail("")
                            dataStore.savePfp("")
                            dataStore.saveName("")
                        }
                        Firebase.auth.signOut()
                        googleSignInClient.signOut()
                        navHostController.navigate(Screens.Onboarding.route)
                    },
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIos,
                    contentDescription = "Arrow Back",
                    tint = textColor,
                    modifier = Modifier
                        .padding(start = 15.dp, end = 80.dp)
                        .size(25.dp)
                )

                Text(
                    text = "Complete Profile",
                    color = textColor,
                    fontSize = 20.sp,
                    fontFamily = monteSB
                )
            }
            TextFieldWithIcons(
                textValue = "Email ID",
                placeholder = "Enter Your Email",
                icon = Icons.Filled.Email,
                mutableText = email,
                onValueChanged = {
                    email = it
                },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            )
            TextFieldWithIcons(
                textValue = "Name/Organization Name",
                placeholder = "Enter Your Name",
                icon = Icons.Filled.Person,
                mutableText = fullName,
                onValueChanged = {
                    fullName = it
                },
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            )
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = {
                    isExpanded = it
                }
            ) {
                TextFieldWithIcons(
                    textValue = "Type of Membership",
                    placeholder = "Type of Membership",
                    icon = Icons.Filled.CardMembership,
                    mutableText = organization,
                    onValueChanged = {
                        organization = it
                    },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isEnabled = false
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {
                        isExpanded = false
                    }) {
                    listOfOrganizations.forEach {
                        DropdownMenuItem(onClick = {
                            organization = TextFieldValue(it)
                            isExpanded = false
                        }) {
                            Text(text = it)
                        }
                    }


                }

            }

            TextFieldWithIcons(
                textValue = "Phone Number",
                placeholder = "Phone Number (with Country Code)",
                icon = Icons.Filled.PhoneIphone,
                mutableText = phone,
                onValueChanged = {
                    phone = it
                },
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
                isTrailingVisible = true,
                trailingIcon = Icons.Filled.Send,
                ifIsOtp = true,
                onTrailingClick = {
                    currentActivity?.let {
                        val callbacks =
                            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                    // Verification successful, automatically sign in the user
                                    signInWithPhoneAuthCredential(credential, auth)
                                }

                                override fun onVerificationFailed(exception: FirebaseException) {
                                    // Verification failed, show error message to the user
                                    Log.w(TAG, "onVerificationFailed", exception)
                                    Toast.makeText(
                                        context,
                                        "exception: ${exception.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                override fun onCodeSent(
                                    verificationId: String,
                                    token: PhoneAuthProvider.ForceResendingToken,
                                ) {
                                    vfId = vfId.copy(verificationId)
                                }
                            }


                        val options = PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber(phone.text) // Phone number to verify
                            .setTimeout(
                                0L,
                                java.util.concurrent.TimeUnit.SECONDS
                            ) // Timeout and unit
                            .setCallbacks(callbacks)
                            .setActivity(currentActivity)// OnVerificationStateChangedCallbacks
                            .build()
                        PhoneAuthProvider.verifyPhoneNumber(options)
                    }
                    isOtpSent = true
                    Toast.makeText(context, "Please Wait..", Toast.LENGTH_LONG).show()
                },

                )
            TextFieldWithIcons(
                textValue = "OTP Here",
                placeholder = "Enter OTP Sent to Your Phone",
                icon = Icons.Filled.Message,
                mutableText = otp,
                onValueChanged = {
                    otp = it
                },
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
                isEnabled = isOtpSent,
            )
            ExposedDropdownMenuBox(
                expanded = isGenderExpansded,
                onExpandedChange = {
                    isGenderExpansded = it
                }
            ) {
                TextFieldWithIcons(
                    textValue = "Gender",
                    placeholder = "Enter Your Gender",
                    icon = Icons.Filled.SupervisorAccount,
                    mutableText = gender,
                    onValueChanged = {
                        gender = it
                    },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isTrailingVisible = true,
                    trailingIcon = if (!isGenderCorrect) Icons.Filled.NotInterested else Icons.Filled.Check,
                    isEnabled = false
                )
                ExposedDropdownMenu(
                    expanded = isGenderExpansded,
                    onDismissRequest = {
                        isGenderExpansded = false
                    }) {
                    listOfGender.forEach {
                        DropdownMenuItem(onClick = {
                            gender = TextFieldValue(it)
                            isGenderExpansded = false
                        }) {
                            Text(text = it)
                        }
                    }


                }

            }

            TextFieldWithIcons(
                textValue = "Address",
                placeholder = "Enter Your Address",
                icon = Icons.Filled.AddHomeWork,
                mutableText = address,
                onValueChanged = {
                    address = it
                },
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Go,
            )


        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {

                        if (isGenderCorrect) {
                            if (fullName.text != "" && email.text != ""
                                && phone.text != "" && organization.text != ""
                                && gender.text != "" && address.text != ""
                            ) {
                                try {
                                    val credential =
                                        PhoneAuthProvider.getCredential(vfId.text, otp.text)
                                    FirebaseAuth.getInstance().signInWithCredential(credential)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                // Sign in success
                                                val users = task.result?.user
                                                println("Success")
                                                Toast.makeText(
                                                    context,
                                                    "Success",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                                isOTPVerified = true
                                                // Do something with user
                                            } else {
                                                // Sign in failed
                                                val message = task.exception?.message
                                                // Handle error
                                            }
                                        }
                                } catch (e: Exception) {
                                    println("Exception ${e.message}")
                                }

                                updateInfoToFirebase(
                                    context,
                                    name = fullName.text,
                                    email = email.text,
                                    phoneNumber = phone.text,
                                    gender = gender.text,
                                    organization = organization.text,
                                    address = address.text,
                                    pointsEarned = 100,
                                    pointsRedeemed = 0
                                )
                                coroutineScope.launch {
                                    dataStore.saveEmail(email.text)
                                    dataStore.savePfp(user?.photoUrl.toString())
                                    dataStore.saveName(fullName.text)
                                }

                                navHostController.navigate(Screens.Dashboard.route)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please Fill All Fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Incorrect Gender",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFD5065),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 70.dp, vertical = 20.dp),
                ) {
                    Text(
                        text = "Submit",
                        color = Color.White,
                        modifier = Modifier,
                        fontFamily = monteSB,
                        fontSize = 18.sp
                    )
                }

            }
        }
    }

}

@Composable
fun TextFieldWithIcons(
    textValue: String,
    placeholder: String,
    icon: ImageVector,
    mutableText: TextFieldValue,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    isTrailingVisible: Boolean = false,
    trailingIcon: ImageVector? = null,
    onTrailingClick: () -> Unit = {},
    ifIsOtp: Boolean = false,
    isEnabled: Boolean = true,
    onValueChanged: (TextFieldValue) -> Unit,
) {
    TextField(
        value = mutableText,
        leadingIcon = {
            Icon(
                imageVector = icon,
                tint = textColor,
                contentDescription = "Icon"
            )
        },
        trailingIcon = {
            if (isTrailingVisible && trailingIcon != null) {
                if (!ifIsOtp) {
                    IconButton(onClick = {
                        onTrailingClick()
                    }) {
                        Icon(
                            imageVector = trailingIcon,
                            tint = textColor,
                            contentDescription = "Icon"
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            onTrailingClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFB0EDF5),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(35.dp),
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = "Get OTP",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontFamily = monteBold,
                            modifier = Modifier.padding(bottom = 4.dp),
                            maxLines = 1,
                            softWrap = true
                        )
                    }
                }
            }
        },
        onValueChange = onValueChanged,
        label = { Text(text = textValue, color = textColor) },
        placeholder = { Text(text = placeholder, color = textColor) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = Modifier
            .padding(start = 15.dp, top = 5.dp, bottom = 15.dp, end = 15.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = appBackground,
            textColor = textColor,
            disabledTextColor = textColor
        ),
        enabled = isEnabled
    )
}

fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, auth: FirebaseAuth) {
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Successful")
            } else {
                println("Failed")
            }
        }
}

