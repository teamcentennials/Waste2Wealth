package app.waste2wealth.com.activity

import android.Manifest
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.NavHostController
import app.waste2wealth.com.R
import app.waste2wealth.com.bottombar.BottomBar
import app.waste2wealth.com.collectwaste.getDownloadUrlFromPath
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.firebase.firestore.ProfileInfo
import app.waste2wealth.com.firebase.firestore.calculateActivityPointsEarned
import app.waste2wealth.com.firebase.firestore.updateActivityToFirebase
import app.waste2wealth.com.firebase.firestore.updateInfoToFirebase
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.reportwaste.DialogBox
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.monteBold
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.textColor
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.storage.FirebaseStorage
import com.jet.firestore.JetFirestore
import com.jet.firestore.getListOfObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun StopRecording(
    navController: NavHostController,
    viewModel: LocationViewModel,
    email: String,
    name: String
) {

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
        )
    )
    val permissionDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val gesturesEnabled by remember { derivedStateOf { permissionDrawerState.isOpen } }
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val context = LocalContext.current
    var isDialogVisible by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            println("Bitmaps is ${it?.asImageBitmap()}")
            imageBitmap = it?.asImageBitmap()
            viewModel.getPlaces()
            bitmap = it


        }
    )
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var address by remember {
        mutableStateOf("")
    }
    var isCOinVisible by remember {
        mutableStateOf(false)
    }
    var profileList by remember {
        mutableStateOf<List<ProfileInfo>?>(null)
    }
    var userAddress by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var gender by remember {
        mutableStateOf("")
    }
    var organization by remember {
        mutableStateOf("")
    }
    var pointsEarned by remember {
        mutableStateOf(0)
    }
    var pointsRedeemed by remember {
        mutableStateOf(0)
    }
    var noOfTimesReported by remember {
        mutableStateOf(0)
    }
    var noOfTimesCollected by remember {
        mutableStateOf(0)
    }
    var noOfTimesActivity by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getPlaces()
    }

    JetFirestore(path = {
        collection("ProfileInfo")
    }, onRealtimeCollectionFetch = { value, _ ->
        profileList = value?.getListOfObjects()
    }) {
        if (profileList != null) {
            for (i in profileList!!) {
                if (i.email == email) {
                    userAddress = i.address ?: ""
                    gender = i.gender ?: ""
                    phoneNumber = i.phoneNumber ?: ""
                    organization = i.organization ?: ""
                    pointsEarned = i.pointsEarned
                    pointsRedeemed = i.pointsRedeemed
                    noOfTimesReported = i.noOfTimesReported
                    noOfTimesCollected = i.noOfTimesCollected
                    noOfTimesActivity = i.noOfTimesActivity
                }
            }
        }

        PermissionDrawer(
            drawerState = permissionDrawerState,
            permissionState = permissionState,
            rationaleText = "To continue, allow Waste2Wealth to access your device's camera" +
                    ". Tap Settings > Permission, and turn \"Access Camera On\" on.",
            withoutRationaleText = "Camera permission required for this feature to be available." +
                    " Please grant the permission.",
            model = R.drawable.location,
            gesturesEnabled = gesturesEnabled,
            size = 100.dp
        ) {
            Scaffold(bottomBar = {
                BottomBar(navController = navController)
            }) {
                println(it)
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(appBackground)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 30.dp, start = 0.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBackIos,
                                    contentDescription = "",
                                    tint = textColor,
                                    modifier = Modifier
                                        .padding(start = 15.dp)
                                        .size(25.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(x = (-10).dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "My Recordings",
                                        color = textColor,
                                        fontFamily = monteSB,
                                        fontSize = 25.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(30.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Before Activity",
                                    fontFamily = monteBold,
                                    fontSize = 10.sp,
                                    color = textColor,
                                    modifier = Modifier
                                        .padding(start = 10.dp, bottom = 10.dp)
                                )
                                Text(
                                    text = "After Activity",
                                    fontFamily = monteBold,
                                    fontSize = 10.sp,
                                    color = textColor,
                                    modifier = Modifier
                                        .padding(start = 10.dp, bottom = 10.dp)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    backgroundColor = appBackground,
                                    shape = RoundedCornerShape(7.dp),
                                    elevation = 5.dp,
                                    modifier = Modifier
                                        .padding(horizontal = 7.dp, vertical = 10.dp)
                                ) {


                                    var imageUrlState by remember {
                                        mutableStateOf("")
                                    }
                                    LaunchedEffect(key1 = Unit) {
                                        val imageUrl = withContext(Dispatchers.IO) {
                                            try {
                                                getDownloadUrlFromPath(viewModel.beforeActivityPath.value)
                                            } catch (e: Exception) {
                                                ""
                                            }
                                        }
                                        imageUrlState = imageUrl
                                    }
                                    if (imageUrlState != "") {
                                        AsyncImage(
                                            model = imageUrlState,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .height(200.dp)
                                                .padding(bottom = 30.dp)
                                                .clip(RoundedCornerShape(30.dp)),
                                        )
                                    }
                                }

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
                                                .height(200.dp),
                                            contentScale = ContentScale.Fit
                                        )

                                    }

                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 30.dp)
                                    .clickable {
                                    }
                            ) {
                                Text(
                                    text = "Choose Your Location",
                                    color = textColor,
                                    fontSize = 15.sp
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 30.dp)
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = isExpanded,
                                    onExpandedChange = {
                                        isExpanded = it
                                    }
                                ) {
                                    TextField(
                                        value = address,
                                        onValueChange = {
                                            address = it
                                        },
                                        colors = TextFieldDefaults.textFieldColors(
                                            textColor = textColor
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(15.dp),

                                        )
                                    ExposedDropdownMenu(
                                        expanded = isExpanded,
                                        onDismissRequest = {
                                            isExpanded = false
                                        }) {
                                        viewModel.listOfAddresses.forEach {
                                            if (it != null) {
                                                DropdownMenuItem(onClick = {
                                                    address = it
                                                    isExpanded = false
                                                }) {
                                                    Text(text = it)
                                                }
                                            }
                                        }


                                    }

                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 15.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Points you can Earn",
                                    color = textColor,
                                    fontSize = 16.sp,
                                    fontFamily = monteBold,
                                    modifier = Modifier.padding(start = 45.dp, end = 60.dp)
                                )
                                Row(modifier = Modifier.padding(end = 25.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.coins),
                                        contentDescription = "coins",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .padding(end = 5.dp),
                                        tint = Color.Unspecified
                                    )
                                    Text(
                                        text = "8999999",
                                        color = textColor,
                                        fontSize = 15.sp,
                                        fontFamily = monteNormal,
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        isDialogVisible = true
                                    }, colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFFFD5065),
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(35.dp),
                                    modifier = Modifier.padding(start = 0.dp)
                                ) {
                                    Text(
                                        text = "Report Waste",
                                        color = textColor,
                                        fontFamily = monteNormal,
                                        fontSize = 15.sp
                                    )

                                }
                            }


                        }

                    }
                }
                DialogBox(
                    title = "Stop Activity",
                    description = "Are you sure you want to stop this activity?",
                    isVisible = isDialogVisible,
                    successRequest = {
                        Toast.makeText(context, "Please Wait", Toast.LENGTH_SHORT).show()
                        if (bitmap != null) {
                            val storageRef = FirebaseStorage.getInstance().reference
                            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                            var imageName = (1..10)
                                .map { allowedChars.random() }
                                .joinToString("")
                            imageName = "Activities/${email}/${imageName}.jpg"

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
                                        updateActivityToFirebase(
                                            context = context,
                                            startTime = viewModel.startActivityTime.value,
                                            endTime = System.currentTimeMillis(),
                                            beforeTaskPath = viewModel.beforeActivityPath.value,
                                            endTaskPath = imageName,
                                            title = viewModel.activityTitle.value,
                                            email = email ?: "",
                                            duration = calculateDuration(
                                                viewModel.startActivityTime.value,
                                                System.currentTimeMillis()
                                            )
                                        )
                                        updateInfoToFirebase(
                                            context,
                                            name = name,
                                            email = email,
                                            phoneNumber = phoneNumber,
                                            gender = gender,
                                            organization = organization,
                                            address = userAddress,
                                            pointsEarned = pointsEarned + calculateActivityPointsEarned(
                                                noOfTimesReported,
                                                noOfTimesCollected,
                                                noOfTimesActivity,
                                                calculateDurationPoints(
                                                    viewModel.startActivityTime.value,
                                                    System.currentTimeMillis()
                                                ).toInt()
                                            ),
                                            pointsRedeemed = pointsRedeemed,
                                            noOfTimesReported = noOfTimesReported,
                                            noOfTimesCollected = noOfTimesCollected + 1,
                                            noOfTimesActivity = noOfTimesActivity,
                                        )
                                        isCOinVisible = true

                                    }
                                }

                            }

                        }

                        isDialogVisible = false
                    }) {
                    isDialogVisible = !isDialogVisible
                }
            }
        }
        if (isCOinVisible) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                val currenanim by rememberLottieComposition(
                    spec = LottieCompositionSpec.Asset("coins.json")
                )
                LottieAnimation(
                    composition = currenanim,
                    iterations = 1,
                    contentScale = ContentScale.Crop,
                    speed = 1f,
                    modifier = Modifier
                        .fillMaxSize()
                        .size(250.dp)
                )
            }
            LaunchedEffect(key1 = isCOinVisible) {
                if (isCOinVisible) {
                    delay(4000)
                    navController.navigate(Screens.Dashboard.route)
                }
            }

        }
    }


}

fun calculateDuration(startTime: Long, endTime: Long): String {
    val durationInMillis = endTime - startTime

    val minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(durationInMillis)
    val days = TimeUnit.MILLISECONDS.toDays(durationInMillis)
    val months = TimeUnit.MILLISECONDS.toDays(durationInMillis) / 30
    val years = TimeUnit.MILLISECONDS.toDays(durationInMillis) / 365

    return when {
        years >= 1 -> "$years years"
        months >= 1 -> "$months months"
        days >= 1 -> "$days days"
        hours >= 1 -> "$hours hours"
        else -> "$minutes minutes"
    }
}

fun calculateDurationPoints(startTime: Long, endTime: Long): Long {
    val durationInMillis = endTime - startTime

    val minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(durationInMillis)
    val days = TimeUnit.MILLISECONDS.toDays(durationInMillis)
    val months = TimeUnit.MILLISECONDS.toDays(durationInMillis) / 30
    val years = TimeUnit.MILLISECONDS.toDays(durationInMillis) / 365

    return when {
        years >= 1 -> years
        months >= 1 -> months
        days >= 1 -> days
        hours >= 1 -> hours
        else -> minutes
    }
}



