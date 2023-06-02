package app.waste2wealth.com.firebase.firestore

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

fun updateInfoToFirebase(
    context: Context,
    name: String?,
    email: String?,
    phoneNumber: String?,
    gender: String?,
    organization: String?,
    address: String?,
    pointsEarned: Int,
    pointsRedeemed: Int,
    noOfTimesReported: Int = 0,
    noOfTimesCollected: Int = 0,
    noOfTimesActivity: Int = 0,
) {
    val profile = ProfileInfo(
        name,
        email,
        phoneNumber,
        gender,
        organization,
        address,
        pointsEarned,
        pointsRedeemed,
        noOfTimesReported,
        noOfTimesCollected,
        noOfTimesActivity
    )

    val db = FirebaseFirestore.getInstance()
    email?.let {
        db.collection("ProfileInfo").document(it).set(profile)
            .addOnSuccessListener {

                Toast.makeText(context, "Profile Updated successfully..", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Fail to update Profile : " + exception.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }

}

fun updateWasteToFirebase(
    context: Context,
    latitude: Double,
    longitude: Double,
    imagePath: String,
    timeStamp: Long,
    userEmail: String,
    address: String,
) {
    val wasteItem = WasteItem(
        latitude, longitude, imagePath, timeStamp, userEmail, address
    )

    val db = FirebaseFirestore.getInstance()
    timeStamp.let {
        db.collection("AllWastes").document(it.toString()).set(wasteItem)
            .addOnSuccessListener {

                Toast.makeText(context, "Waste Reported Successfully", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Fail to Report Waste " + exception.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }


}

fun updateCollectedWasteToFirebase(
    context: Context,
    latitude: Double,
    longitude: Double,
    imagePath: String,
    timeStamp: Long,
    userEmail: String,
    address: String,
    isWasteCollected: Boolean,
    allWasteCollected: Boolean,
    feedBack: String,
) {
    val wasteItem = CollectedWasteItem(
        latitude,
        longitude,
        imagePath,
        timeStamp,
        userEmail,
        address,
        isWasteCollected,
        allWasteCollected,
        feedBack
    )

    val db = FirebaseFirestore.getInstance()
    timeStamp.let {
        db.collection("CollectedWastes").document(it.toString()).set(wasteItem)
            .addOnSuccessListener {

                Toast.makeText(context, "Waste Reported Successfully", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Fail to Report Waste " + exception.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }


}

fun updateTemporaryActivityToFirebase(
    context: Context,
    startTime: Long,
    endTime: Long = 0L,
    beforeTaskPath: String,
    title: String,
    email: String
) {
    val temporaryActivityItem = TemporaryActivityItem(
        startTime = startTime,
        endTime = endTime,
        title = title,
        beforeTaskPath = beforeTaskPath,
        email = email
    )

    val db = FirebaseFirestore.getInstance()
    startTime.let {
        db.collection("TemporaryActivities").document(it.toString()).set(temporaryActivityItem)
            .addOnSuccessListener {

                Toast.makeText(context, "Recording Started Succesfully", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Something Went Wrong" + exception.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }


}

fun updateActivityToFirebase(
    context: Context,
    startTime: Long,
    endTime: Long = 0L,
    beforeTaskPath: String,
    endTaskPath: String,
    duration: String,
    title: String,
    email: String
) {
    val allActivityItem = AllActivityItem(
        startTime = startTime,
        endTime = endTime,
        title = title,
        beforeTaskPath = beforeTaskPath,
        endTaskPath = endTaskPath,
        email = email,
        duration = duration
    )
    val db = FirebaseFirestore.getInstance()
    startTime.let { time ->
        db.collection("AllActivities").document(time.toString()).set(allActivityItem)
            .addOnSuccessListener {
                db.collection("TemporaryActivities").document(time.toString()).delete()

                Toast.makeText(context, "Activity Updated Succesfully", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Something Went Wrong" + exception.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }


}
