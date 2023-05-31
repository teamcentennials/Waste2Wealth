package app.waste2wealth.com.ktorClient.imageDto


import com.google.gson.annotations.SerializedName

data class Success(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?
)