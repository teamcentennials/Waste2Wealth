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
    val courses = ProfileInfo(
        name, email, phoneNumber, gender, organization, address
    )

    val db = FirebaseFirestore.getInstance()
    email?.let {
        db.collection("ProfileInfo").document(it).set(courses)
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