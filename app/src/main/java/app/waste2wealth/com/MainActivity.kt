package app.waste2wealth.com

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.navigation.NavigationController
import app.waste2wealth.com.ui.theme.Waste2WealthTheme
import app.waste2wealth.com.ui.theme.backGround
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Waste2WealthTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(backGround)
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    snackbarHost = {
                        SnackbarHost(hostState = it) { data ->
                            Snackbar(
                                snackbarData = data,
                                backgroundColor = Color.LightGray,
                                shape = RoundedCornerShape(10.dp),
                                contentColor = Color.Black,
                                actionColor = Color.Black
                            )
                        }
                    },
                ) {
                    val locationViewModel: LocationViewModel = hiltViewModel()
                    println(it)
                    NavigationController(scaffoldState, locationViewModel)
                }
            }
        }
    }
}



