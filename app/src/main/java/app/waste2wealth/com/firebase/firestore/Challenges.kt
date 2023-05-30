package app.waste2wealth.com.firebase.firestore

data class Challenges(
    val date: String,
    val time: String,
    val location: String,
    val title: String,
    val image: String,
    val points: Int,
    val emoji: String,
    val type: String
)

val challengesList = listOf(
    Challenges(
        date = "23th Monday 2023",
        time = "10:00 AM",
        location = "Kathmandu",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/images%20(1).jpeg?alt=media&token=1ea91756-543d-4651-95ad-b54f4532024a",
        title = "Dadar Beach Clean up",
        emoji = "\uD83E\uDD1D",
        type = "Weekly"
    ),
    Challenges(
        date = "23th Monday 2023",
        time = "10:00 AM",
        location = "Kathmandu",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/image%207.png?alt=media&token=5ee8672f-b690-408b-b15a-756e4da1f952",
        title = "Dadar Beach Clean up",
        emoji = "\uD83D\uDE13",
        type = "Monthly"
    )
)

