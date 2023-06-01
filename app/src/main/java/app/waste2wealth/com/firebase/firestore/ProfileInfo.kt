package app.waste2wealth.com.firebase.firestore

data class ProfileInfo(
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val gender: String?,
    val organization: String?,
    val address: String?,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
    )
}