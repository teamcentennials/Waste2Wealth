package app.waste2wealth.com.navigation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import app.waste2wealth.com.ui.theme.appBackground
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, email: String) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(1000L)
        println("User is $user")
        navController.navigate(if (user != null) Screens.Dashboard.route else Screens.Onboarding.route)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(appBackground)
    ) {
        Image(
            painter = painterResource(id = app.waste2wealth.com.R.drawable.appicon),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}