package app.waste2wealth.com

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.rememberScaffoldState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.login.onboarding.SmsBroadcastReceiver
import app.waste2wealth.com.login.onboarding.SmsBroadcastReceiver.SmsBroadcastReceiverListener
import app.waste2wealth.com.navigation.NavigationController
import app.waste2wealth.com.ui.theme.Waste2WealthTheme
import app.waste2wealth.com.ui.theme.appBackground
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.Nullable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var smsBroadcastReceiver: SmsBroadcastReceiver
    private lateinit var viewModel: LocationViewModel

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Waste2WealthTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(appBackground)
                systemUiController.setNavigationBarColor(appBackground)
                val navController = rememberAnimatedNavController()
                viewModel = ViewModelProvider(this)[LocationViewModel::class.java]

                val locationViewModel: LocationViewModel = hiltViewModel()
                val scaffoldState = rememberScaffoldState()
                val client = SmsRetriever.getClient(this)
                client.startSmsUserConsent(null)
//                SettingUp(navHostController = navController)
                NavigationController(scaffoldState, locationViewModel, navController)
//                    CompleteProfile()

            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun registerBroadcastReceiver() {
        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver.smsBroadcastReceiverListener = object : SmsBroadcastReceiverListener {
            override fun onSuccess(intent: Intent?) {
                intent?.let { startActivityForResult(it, 200) }
            }

            override fun onFailure() {}
        }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver, intentFilter)
    }


    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsBroadcastReceiver)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                println("Message $message")
                viewModel.result.value = message
            }
        }
    }

}



