package app.waste2wealth.com.ktorClient.imageDto


import com.google.gson.annotations.SerializedName

data class ImageX(
    @SerializedName("extension")
    val extension: String?,
    @SerializedName("filename")
    val filename: String?,
    @SerializedName("mime")
    val mime: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("url")
    val url: String?
)