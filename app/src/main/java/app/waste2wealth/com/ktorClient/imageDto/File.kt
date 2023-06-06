package app.waste2wealth.com.ktorClient.imageDto


import com.google.gson.annotations.SerializedName

data class File(
    @SerializedName("resource")
    val resource: Resource?
)