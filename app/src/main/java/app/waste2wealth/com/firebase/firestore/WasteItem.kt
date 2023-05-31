package app.waste2wealth.com.firebase.firestore

data class WasteItem(
    val latitude: Double,
    val longitude: Double,
    val imagePath: String,
    val timeStamp: Long,
    val userEmail: String,
    val address: String,
)
