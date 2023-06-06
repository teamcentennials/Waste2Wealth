package app.waste2wealth.com.activity

data class Activities(
    val time: String,
    val title: String,
    val points: Int,
    val emoji: String,
    val type: String
)

val allActivities = listOf(
    Activities(
        time = "30 Minutes",
        points = 100,
        title = "Beach Clean up",
        emoji = "\uD83E\uDD1D",
        type = "Social Activity"
    ),
    Activities(
        time = "1 Hour",
        points = 100,
        title = "Basic Clean up",
        emoji = "\uD83D\uDE13",
        type = "Social Activity"
    ),
    Activities(
        time = "1 Hour",
        points = 100,
        title = "Public Transport",
        emoji = "\uD83D\uDE1B",
        type = "Social Activity"
    ),
    Activities(
        time = "Custom time",
        points = 0,
        title = "Custom Activity",
        emoji = "\uD83D\uDC9B",
        type = "Custom Activity",
    ),
)

