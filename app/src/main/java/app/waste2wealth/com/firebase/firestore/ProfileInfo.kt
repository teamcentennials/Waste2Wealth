package app.waste2wealth.com.firebase.firestore

data class ProfileInfo(
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val gender: String?,
    val organization: String?,
    val address: String?,
    val pointsEarned: Int,
    val pointsRedeemed: Int,
    val noOfTimesReported: Int = 0,
    val noOfTimesCollected: Int = 0,
    val noOfTimesActivity: Int = 0,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        0,
        0,
        0,
        0
    )
}

fun calculatePointsEarned(
    noOfTimesReported: Int,
    noOfTimesCollected: Int,
    noOfTimesActivity: Int,
    isCollectedWaste: Boolean = false
): Int {
    val basePointsPerReport = if (isCollectedWaste) {
        50
    } else {
        20
    }

    val additionalPoints =
        (noOfTimesReported * 10) +
                (noOfTimesCollected * 20) +
                (noOfTimesActivity * 5)

    val totalPointsEarned = basePointsPerReport + additionalPoints

    val scaledPoints = if (totalPointsEarned > 250) {
        250
    } else {
        totalPointsEarned
    }

    return scaledPoints
}

fun calculateActivityPointsEarned(
    noOfTimesReported: Int,
    noOfTimesCollected: Int,
    noOfTimesActivity: Int,
    noOfMinutes: Int
): Int {
    val basePointsPerHour = 100
    val minutesInAnHour = 60

    val totalPointsPerHour = basePointsPerHour +
            (noOfTimesReported * 10) +
            (noOfTimesCollected * 20) +
            (noOfTimesActivity * 5)

    val totalPointsEarned = (totalPointsPerHour.toDouble() / minutesInAnHour) * noOfMinutes

    val scaledPoints = if (totalPointsEarned > 250) {
        250
    } else {
        totalPointsEarned
    }

    return scaledPoints.toInt()
}