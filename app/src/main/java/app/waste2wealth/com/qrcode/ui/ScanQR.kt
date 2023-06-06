package app.waste2wealth.com.qrcode.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.sharp.Cameraswitch
import androidx.compose.material.icons.sharp.FlashAuto
import androidx.compose.material.icons.sharp.FlashOff
import androidx.compose.material.icons.sharp.FlashOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import app.waste2wealth.com.R
import app.waste2wealth.com.components.permissions.Grapple
import app.waste2wealth.com.components.permissions.PermissionDrawer
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.qrcode.analyzer.BarCodeTypes
import app.waste2wealth.com.qrcode.analyzer.QrCodeAnalyzer
import app.waste2wealth.com.ui.theme.backGround
import app.waste2wealth.com.ui.theme.monteNormal
import app.waste2wealth.com.ui.theme.monteSB
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(
    ExperimentalPermissionsApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun ScanQr(viewModel: LocationViewModel) {
    var code by remember {
        mutableStateOf("No Results Found")
    }
    var isFlashAuto by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
        )
    )
    val isDark = viewModel.isDark
    var barcodeType by remember {
        mutableStateOf(BarCodeTypes.None)
    }
    var isFlashEnabled by remember {
        mutableStateOf(false)
    }
    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
        ProcessCameraProvider.getInstance(context)
    var camera by remember {
        mutableStateOf<Camera?>(null)
    }
    var lens by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    var preview by remember { mutableStateOf<Preview?>(null) }
    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
    val cameraSelector: CameraSelector = CameraSelector.Builder().requireLensFacing(lens).build()
    val permissionDrawerState = rememberBottomDrawerState(
        if (permissionState.allPermissionsGranted) BottomDrawerValue.Closed
        else BottomDrawerValue.Open
    )
    val gesturesEnabled by remember { derivedStateOf { permissionDrawerState.isOpen } }
    PermissionDrawer(
        drawerState = permissionDrawerState,
        permissionState = permissionState,
        rationaleText = "To continue, allow Waste2Wealth to access your device's camera" +
                ". Tap Settings > Permission, and turn \"Access Camera On\" on.",
        withoutRationaleText = "Camera permission required for this feature to be available." +
                " Please grant the permission.",
        model = R.drawable.camera,
        gesturesEnabled = gesturesEnabled,
        size = 100.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            val lifecycleOwner = LocalLifecycleOwner.current
            val barCodeVal = remember { mutableStateOf("") }
            AndroidView(
                factory = { AndroidViewContext ->
                    PreviewView(AndroidViewContext).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    }
                },
                modifier = Modifier.fillMaxSize(),
                update = { previewView ->
                    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
                    cameraProviderFuture.addListener({
                        preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                        val barcodeAnalyser = QrCodeAnalyzer { barcodes ->
                            barcodes.forEach { barcode ->
                                println()
                                when (barcode.valueType) {
                                    8 -> {
                                        barcodeType = BarCodeTypes.Url
                                    }
                                    12 -> {
                                        barcodeType = BarCodeTypes.DrivingLicense
                                    }
                                    2 -> {
                                        barcodeType = BarCodeTypes.Email
                                    }
                                    10 -> {
                                        barcodeType = BarCodeTypes.GeoPoint
                                    }
                                    6 -> {
                                        barcodeType = BarCodeTypes.SMS
                                    }
                                    9 -> {
                                        barcodeType = BarCodeTypes.Wifi
                                    }
                                    4 -> {
                                        barcodeType = BarCodeTypes.Phone
                                    }
                                    11 -> {
                                        barcodeType = BarCodeTypes.CalendarEvent
                                    }
                                }
                                barcode.rawValue?.let { barcodeValue ->
                                    barCodeVal.value = barcodeValue
                                    code = barcodeValue
                                    if (isChecked) {
                                        if (barcode.valueType == 8) {
                                            try {
                                                val urlIntent = Intent(
                                                    Intent.ACTION_VIEW, Uri.parse(code)
                                                )
                                                context.startActivity(urlIntent)
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build().also {
                                it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                            }

                        try {
                            cameraProvider.unbindAll()
                            camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner, cameraSelector, preview, imageAnalysis
                            )
                            if (isFlashAuto) {
                                if (isDark) {
                                    if (camera?.cameraInfo?.hasFlashUnit() == true) {
                                        camera?.cameraControl?.enableTorch(true)
                                        isFlashEnabled = true
                                    }
                                } else {
                                    camera?.cameraControl?.enableTorch(false)
                                    isFlashEnabled = false
                                }
                            }
                        } catch (e: Exception) {
                            Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                        }
                    }, ContextCompat.getMainExecutor(context))
                },

                )


        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp, start = 15.dp, end = 15.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF353739).copy(0.55f)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    if (camera?.cameraInfo?.hasFlashUnit() == true) {
                        camera?.cameraControl?.enableTorch(!isFlashEnabled)
                        isFlashEnabled = !isFlashEnabled
                    }
                }) {
                    if (isFlashAuto) {
                        Icon(
                            imageVector = Icons.Sharp.FlashAuto, contentDescription = "auto"
                        )
                    } else {
                        if (isFlashEnabled) {
                            Icon(
                                imageVector = Icons.Sharp.FlashOn, contentDescription = ""
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Sharp.FlashOff, contentDescription = ""
                            )
                        }
                    }
                }
                IconButton(onClick = {
                    isFlashAuto = !isFlashAuto
                }) {
                    if (isFlashAuto) {
                        Text(
                            text = "Auto Flash On",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = monteNormal
                        )
                    } else {
                        Text(
                            text = "Auto Flash Off",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = monteNormal
                        )
                    }
                }
                IconButton(onClick = {
                    lens =
                        if (lens == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT
                        else CameraSelector.LENS_FACING_BACK
                }) {
                    Icon(
                        imageVector = Icons.Sharp.Cameraswitch, contentDescription = ""
                    )
                }

            }
        }
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
                backgroundColor = Color(0xFF353739).copy(0.55f),
                shape = RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Grapple(
                            modifier = Modifier
                                .padding(bottom = 0.dp)
                                .requiredHeight(15.dp)
                                .requiredWidth(55.dp)
                                .alpha(0.22f), color = Color.White
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 7.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = "Results",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontFamily = monteSB,
                            modifier = Modifier.padding(start = 7.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 5.dp),
                        ) {
                            Checkbox(checked = isChecked, onCheckedChange = {
                                isChecked = !isChecked
                            }, modifier = Modifier.padding(end = 0.dp))
                            Text(
                                text = "Auto Navigate",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontFamily = monteSB
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 7.dp, end = 7.dp, top = 10.dp, bottom = 10.dp
                            )
                            .verticalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = code,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = monteNormal,
                            softWrap = true
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        when (barcodeType) {
                            BarCodeTypes.CalendarEvent -> {
                                CustomIcon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    desc = "Calendar Event"
                                )
                            }
                            BarCodeTypes.DrivingLicense -> {
                                CustomIcon(
                                    imageVector = Icons.Filled.ElectricCar, desc = "Driving License"
                                )
                            }
                            BarCodeTypes.Email -> {
                                CustomIcon(
                                    imageVector = Icons.Filled.Email, desc = "Email"
                                )
                            }
                            BarCodeTypes.GeoPoint -> {
                                CustomIcon(
                                    imageVector = Icons.Filled.Link, desc = "Geo Point"
                                )
                            }
                            BarCodeTypes.SMS -> {
                                CustomIcon(
                                    imageVector = Icons.Filled.Sms, desc = "SMS"
                                )
                            }
                            BarCodeTypes.Wifi -> {
                                CustomIcon(
                                    imageVector = Icons.Filled.Wifi, desc = "Wifi Details"
                                )
                            }
                            BarCodeTypes.Phone -> {
                                CustomIcon(
                                    imageVector = Icons.Filled.Phone, desc = "Contact"
                                )
                            }
                            BarCodeTypes.Url -> {
                                CustomIcon(
                                    imageVector = Icons.Filled.Link, desc = "Url"
                                )
                            }
                            BarCodeTypes.None -> {

                            }
                        }
                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 0.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        onClick = {
                            try {
                                val urlIntent = Intent(
                                    Intent.ACTION_VIEW, Uri.parse(code)
                                )
                                context.startActivity(urlIntent)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = backGround
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 55.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Visit",
                            color = Color.White,
                            fontFamily = monteSB,
                            fontSize = 13.sp
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun CustomIcon(imageVector: ImageVector, desc: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector, contentDescription = desc, modifier = Modifier.size(20.dp)
        )
        Text(
            text = "$desc found",
            color = Color.White,
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 13.sp,
            fontFamily = monteNormal
        )
    }
}

