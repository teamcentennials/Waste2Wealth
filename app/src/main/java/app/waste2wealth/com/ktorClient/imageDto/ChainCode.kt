package app.waste2wealth.com.ktorClient.imageDto


import com.google.gson.annotations.SerializedName

data class ChainCode(
    @SerializedName("image")
    val image: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("thumb")
    val thumb: String?
)