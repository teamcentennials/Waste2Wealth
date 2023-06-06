package app.waste2wealth.com.firebase.firestore

data class WasteItem(
    val latitude: Double,
    val longitude: Double,
    val imagePath: String,
    val timeStamp: Long,
    val userEmail: String,
    val address: String,
) {
    constructor() : this(0.0, 0.0, "", 0, "", "")
}

data class CollectedWasteItem(
    val latitude: Double,
    val longitude: Double,
    val imagePath: String,
    val timeStamp: Long,
    val userEmail: String,
    val address: String,
    val isWasteCollected: Boolean,
    val allWasteCollected: Boolean,
    val feedBack: String,
) {
    constructor() : this(
        0.0,
        0.0,
        "",
        0,
        "",
        "",
        false,
        false,
        ""
    )
}
