package app.waste2wealth.com.components.permissions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.waste2wealth.com.R
import app.waste2wealth.com.ui.theme.monteNormal
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun PermissionDrawer(
    drawerState: BottomDrawerState,
    model: Any = R.drawable.location,
    permissionState: MultiplePermissionsState,
    rationaleText: String,
    gesturesEnabled: Boolean = false,
    withoutRationaleText: String,
    size: Dp = 60.dp,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()


    BottomDrawer(
        drawerContent = {
            PermissionDrawerContent(
                drawerState = drawerState,
                scope = scope,
                permissionState = permissionState,
                model = model,
                rationaleText = rationaleText,
                withoutRationaleText = withoutRationaleText,
                size = size
            )
        },
        drawerBackgroundColor = Color.White,
        drawerElevation = 0.dp,
        drawerShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        scrimColor = Color.Black.copy(0.59f),
        drawerContentColor = Color.White,
        content = content
    )
}


@OptIn(ExperimentalMaterialApi::class)
@ExperimentalPermissionsApi
@Composable
fun PermissionDrawerContent(
    drawerState: BottomDrawerState,
    size: Dp,
    model: Any,
    scope: CoroutineScope,
    rationaleText: String,
    withoutRationaleText: String,
    permissionState: MultiplePermissionsState,
) {

    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(
            top = 15.dp,
            bottom = 10.dp,
            start = 25.dp,
            end = 25.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Grapple(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .requiredHeight(10.dp)
                .requiredWidth(50.dp)
                .alpha(0.22f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = model,
                contentDescription = null,
                modifier = Modifier.size(size),
                contentScale = ContentScale.FillWidth,
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                modifier = Modifier
                    .rotate(90f)
                    .size(40.dp),
                tint = Color.Black
            )
            Image(
                painter = painterResource(R.drawable.appicon),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.FillWidth
            )
        }
        if (permissionState.shouldShowRationale)
            Text(
                text = rationaleText,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = monteNormal,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        else
            Text(
                text = withoutRationaleText,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = monteNormal,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

        Button(
            modifier = Modifier.padding(top = 5.dp),
            onClick = {
                if (permissionState.shouldShowRationale) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:" + context.packageName)
                    context.startActivity(intent)
                } else {
                    permissionState.launchMultiplePermissionRequest()
                }
                scope.launch { drawerState.close() }
            },
            shape = CircleShape
        ) {
            if (permissionState.shouldShowRationale)
                Text(
                    text = "Settings",
                    color = Color.White,
                    fontFamily = monteNormal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            else
                Text(
                    text = "Grant Permission",
                    color = Color.White,
                    fontFamily = monteNormal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
        }
    }
}

