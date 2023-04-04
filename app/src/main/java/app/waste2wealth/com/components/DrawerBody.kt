package app.waste2wealth.com.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.waste2wealth.com.navigation.Screens

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit,
    currentItem: String = Screens.Dashboard.route,
) {
    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
        items(items) { item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(if (currentItem == item.id) Color(0xFFdcf6f5) else Color.White)
                .clickable {
                    onItemClick(item)
                }
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    item.icon,
                    contentDescription = item.contentDesc,
                    tint = if (currentItem == item.id) Color(0xFF48c5a3)
                    else Color.Black,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    color = if (currentItem == item.id) Color(0xFF48c5a3)
                    else Color.Black,
                    modifier = Modifier,
                    fontSize = 16.sp,
                    fontWeight = if (currentItem == item.id) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}