package app.waste2wealth.com.ktorClient.imageDto


import com.google.gson.annotations.SerializedName

data class ImageHost(
    @SerializedName("image")
    val image: Image?,
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_txt")
    val statusTxt: String?,
    @SerializedName("success")
    val success: Success?
)