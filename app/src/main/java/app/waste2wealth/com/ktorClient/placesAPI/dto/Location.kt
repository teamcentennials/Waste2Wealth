package app.waste2wealth.com.ktorClient.placesAPI.dto


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lng")
    val lng: Double?
)