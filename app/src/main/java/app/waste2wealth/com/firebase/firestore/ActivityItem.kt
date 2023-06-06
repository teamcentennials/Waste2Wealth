package app.waste2wealth.com.firebase.firestore

data class TemporaryActivityItem(
    val startTime: Long,
    val endTime: Long = 0L,
    val title: String,
    val beforeTaskPath: String,
    val email: String,

    ) {
    constructor() : this(0L, 0L, "", "", "")
}

data class AllActivityItem(
    val startTime: Long,
    val endTime: Long,
    val duration: String,
    val title: String,
    val beforeTaskPath: String,
    val endTaskPath: String,
    val email: String,

    ) {
    constructor() : this(0L, 0L, "", "", "", "", "")
}