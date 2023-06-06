package app.waste2wealth.com.ktorClient.placesAPI.dto


import com.google.gson.annotations.SerializedName

data class Places(
    @SerializedName("plus_code")
    val plusCode: PlusCode?,
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("status")
    val status: String?
)