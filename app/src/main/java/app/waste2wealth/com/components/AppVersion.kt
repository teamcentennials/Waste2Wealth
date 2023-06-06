package app.waste2wealth.com.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.datatransport.runtime.BuildConfig

@Composable
fun AppVersion() {
    TextButton(onClick = {}) {
        Text(text = "App Version v${BuildConfig.VERSION_NAME}",
            modifier = Modifier.padding(start = 10.dp),
            color = Color(0xFFb4b4b4),
            fontSize = 15.sp)
    }

}