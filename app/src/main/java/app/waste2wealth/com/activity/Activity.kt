package app.waste2wealth.com.activity

import android.Manifest
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.NavHostController
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.updateTemporaryActivityToFirebase
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.login.TextFieldWithIcons
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.profile.ProfileImage
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun AllActivities(
    navController: NavHostController,
    viewModel: LocationViewModel,
    email: String,
    name: String,
    pfp: String
) {

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
        )
    )
    val permissionDrawerState = rememberBottomDrawerState(
        if (permissionState.allPermissionsGranted) BottomDrawerValue.Closed else BottomDrawerValue.Open
    )
    var receiver by remember { mutableStateOf(allActivities[0]) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            println("Bitmaps is ${it?.asImageBitmap()}")
            imageBitmap = it?.asImageBitmap()
            viewModel.getPlaces()
            bitmap = it


        }
    )


    PermissionDrawer(
        drawerState = permissionDrawerState,
        permissionState = permissionState,
        rationaleText = "To continue, allow Report Waste2Wealth to access your device's location" +
                ". Tap Settings > Permission, and turn \"Access Location On\" on.",
        withoutRationaleText = "Location permission required for functionality of this app." +
                " Please grant the permission.",
    ) {
        Scaffold(bottomBar = {
            BottomBar(navController = navController)
        }) {
            println(it)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appBackground)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 35.dp,
                            bottom = 15.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Activity",
                        color = textColor,
                        fontSize = 35.sp,
                        fontFamily = monteBold,
                    )
                    ProfileImage(
                        imageUrl = pfp,
                        modifier = Modifier
                            .size(60.dp)
                            .border(
                                width = 1.dp,
                                color = appBackground,
                                shape = CircleShape
                            )
                            .padding(3.dp)
                            .clip(CircleShape)
                            .clickable {
                                navController.navigate(Screens.Profile.route)
                            },
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 25.dp, bottom = 20.dp, top = 7.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select your Social Activity",
                        color = textColor,
                        fontSize = 16.sp,
                        fontFamily = monteNormal,
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 0.dp,
                            bottom = 35.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            isDialogOpen = true
                            title = TextFieldValue(receiver.title)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFD5065),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(35.dp),
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = "Start Recording",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(bottom = 4.dp),
                            maxLines = 1,
                            softWrap = true
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate(Screens.MyRecordings.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFD5065),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(35.dp),
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = "My  Recordings",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(bottom = 4.dp),
                            maxLines = 1,
                            softWrap = true
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(
                        bottom = 50.dp,
                        start = 15.dp,
                        top = 0.dp,
                        end = 15.dp
                    ),
                    contentPadding = PaddingValues(bottom = 25.dp)
                ) {
                    items(allActivities) { item ->
                        RepeatingActivity(
                            type = item.type,
                            emoji = item.emoji,
                            title = item.title,
                            time = item.time,
                            value = item,
                            receiver = receiver,
                            onRadioClick = {
                                receiver = item
                            },
                            onStartRecord = {
                                isDialogOpen = true
                                title = TextFieldValue(item.title)
                            }
                        )
                    }

                }
                if (isDialogOpen) {
                    CompleteDialog(
                        textField1 = {
                            TextFieldWithIcons(
                                textValue = "Enter Title of your Activity",
                                placeholder = "Enter Title of your Activity",
                                icon = Icons.Filled.Title,
                                mutableText = title,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                onValueChanged = {
                                    title = it
                                }
                            )

                        },
                        uploadImage = {
                            Card(
                                backgroundColor = appBackground,
                                shape = RoundedCornerShape(7.dp),
                                elevation = 5.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 7.dp, vertical = 10.dp)

                                    .clickable {
                                        if (imageBitmap == null) {
                                            coroutineScope.launch {
                                                if (!permissionState.allPermissionsGranted) {
                                                    permissionDrawerState.open()
                                                } else {
                                                    launcher.launch(
                                                        ActivityOptionsCompat.makeBasic()
                                                    )
                                                }
                                            }
                                        }
                                    }
                            ) {
                                if (imageBitmap == null) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.UploadFile,
                                            contentDescription = "",
                                            tint = textColor,
                                            modifier = Modifier.size(60.dp)
                                        )
                                        Text(
                                            text = "Upload Proof of Attempt",
                                            color = textColor,
                                            fontSize = 16.sp
                                        )
                                    }
                                } else {
                                    Image(
                                        bitmap = imageBitmap!!,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .size(100.dp),
                                        contentScale = ContentScale.Fit
                                    )

                                }

                            }

                        },
                        successRequest = {
                            Toast.makeText(context, "Please Wait", Toast.LENGTH_SHORT).show()
                            if (bitmap != null) {
                                val storageRef = FirebaseStorage.getInstance().reference
                                val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                                var imageName = (1..10)
                                    .map { allowedChars.random() }
                                    .joinToString("")
                                imageName = "TemporaryActivity/${email}/${imageName}.jpg"

                                val imageRef =
                                    storageRef.child(imageName) // Set desired storage location

                                val baos = ByteArrayOutputStream()
                                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                                val imageData = baos.toByteArray()

                                val uploadTask = imageRef.putBytes(imageData)
                                uploadTask.addOnSuccessListener { taskSnapshot ->
                                }.addOnFailureListener { exception ->
                                    println("Firebase storage exception $exception")
                                }.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        imageRef.downloadUrl.addOnSuccessListener {
                                            println("Download url is $it")
                                            updateTemporaryActivityToFirebase(
                                                title = title.text,
                                                context = context,
                                                startTime = System.currentTimeMillis(),
                                                endTime = 0L,
                                                email = email ?: "",
                                                beforeTaskPath = imageName
                                            )

                                        }
                                    }

                                }

                            }
                            isDialogOpen = false

                        }, dismissRequest = {
                            isDialogOpen = false
                        }
                    )
                }

            }
        }
    }
}


@Composable
fun RepeatingActivity(
    type: String,
    emoji: String,
    title: String,
    time: String,
    value: Activities,
    receiver: Activities,
    onRadioClick: () -> Unit,
    onStartRecord: () -> Unit = {}
) {
    Card(
        backgroundColor = Color(0xFFD5E1DA),
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomEnd = 10.dp,
            bottomStart = 50.dp
        ),
        modifier = Modifier.padding(end = 10.dp, bottom = 20.dp)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RadioButton(
                    selected = (value == receiver),
                    onClick = {
                        onRadioClick()
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFFFD5065),
                        unselectedColor = Color.Gray
                    ),
                )
                AndroidView(
                    factory = { context ->
                        AppCompatTextView(context).apply {
                            setTextColor(Color.Black.toArgb())
                            text = emoji
                            textSize = 35.0F
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                            alpha = 1f
                        }
                    },
                    update = { update ->
                        update.apply {
                            text = emoji
                        }
                    },
                    modifier = Modifier.padding(start = 0.dp)
                )
            }
            Text(
                text = time,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = monteNormal,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Button(
                onClick = { onStartRecord() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFD5065),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = "Start Now",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = monteSB,
                    modifier = Modifier.padding(bottom = 4.dp),
                    maxLines = 1,
                    softWrap = true
                )
            }

        }

    }
}

@Composable
fun CompleteDialog(
    textField1: @Composable () -> Unit,
    uploadImage: @Composable () -> Unit = {},
    successRequest: () -> Unit,
    dismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = dismissRequest) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(10.dp, 5.dp, 10.dp, 10.dp)
                .height(600.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .background(appBackground)
                    .fillMaxWidth()
            ) {
                textField1()
                Spacer(modifier = Modifier.height(10.dp))
                uploadImage()
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { successRequest() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFD5065),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(35.dp),
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = "Start Recording",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(bottom = 4.dp),
                            maxLines = 1,
                            softWrap = true
                        )
                    }

                }


            }


        }
    }
}

