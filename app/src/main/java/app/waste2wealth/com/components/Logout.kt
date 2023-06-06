package app.waste2wealth.com.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.waste2wealth.com.navigation.Screens
import app.waste2wealth.com.ui.theme.monteNormal

@Composable
fun LogOut(navHostController: NavHostController) {
    TextButton(onClick = {
        navHostController.popBackStack()
        navHostController.navigate(Screens.LoginScreen.route)
    }) {
        Text(
            text = "Logout",
            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
            color = Color.Gray,
            fontSize = 15.sp,
            fontFamily = monteNormal
        )
    }
}