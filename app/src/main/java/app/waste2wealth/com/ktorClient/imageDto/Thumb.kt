package app.waste2wealth.com.ktorClient.imageDto


import com.google.gson.annotations.SerializedName

data class Thumb(
    @SerializedName("extension")
    val extension: String?,
    @SerializedName("filename")
    val filename: String?,
    @SerializedName("mime")
    val mime: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)