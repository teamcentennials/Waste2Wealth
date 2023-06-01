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
) {
    val profile = ProfileInfo(
        name, email, phoneNumber, gender, organization, address
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

