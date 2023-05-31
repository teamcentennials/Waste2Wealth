package app.waste2wealth.com.login.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.appBackground
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlin.time.Duration.Companion.seconds


@Composable
fun SettingUp(navHostController: NavHostController) {
    LaunchedEffect(key1 = Unit) {
        delay(5.seconds)
        navHostController.navigate(Screens.Dashboard.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val composition3 by rememberLottieComposition(
            spec = LottieCompositionSpec.Asset("settingup.json")
        )
        val context = LocalContext.current
//        var imageUrlState by remember {
//            mutableStateOf("")
//        }
//        val coroutineScope = rememberCoroutineScope()
//
//        LaunchedEffect(key1 = Unit){
//            val imageUrl = withContext(Dispatchers.IO) {
//                getDownloadUrlFromPath("kailashps.1011@gmail.com/DJXUGBo4bU.jpg")
//            }
//           imageUrlState = imageUrl
//        }
//        if (imageUrlState != ""){
//            AsyncImage(
//                model = imageUrlState,
//                contentDescription = ""
//            )
//        }

        LottieAnimation(
            composition = composition3,
            iterations = Int.MAX_VALUE,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

private suspend fun getDownloadUrlFromPath(path: String): String {
    val storageRef = FirebaseStorage.getInstance().reference
    val fileRef = storageRef.child(path)
    return fileRef.downloadUrl.await().toString()
}
