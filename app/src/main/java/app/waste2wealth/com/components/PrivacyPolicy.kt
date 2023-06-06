package app.waste2wealth.com.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.waste2wealth.com.ui.theme.monteNormal

@Composable
fun PrivacyPolicy() {
    TextButton(onClick = {

    }) {
        Text(
            text = "Privacy Policy",
            modifier = Modifier.padding(start = 10.dp),
            color = Color.Gray,
            fontSize = 15.sp,
            fontFamily = monteNormal
        )
    }
}