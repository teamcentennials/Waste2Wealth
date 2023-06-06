package app.waste2wealth.com.ktorClient.repository

import app.waste2wealth.com.ktorClient.Resource
import app.waste2wealth.com.ktorClient.placesAPI.dto.Places
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class PlacesRepoImpl(private val client: HttpClient) : PlacesRepository {
    override suspend fun getPlaces(latLong: String): Resource<Places> {
        return try {
            Resource.Success(
                client.get<Places>(
                    "https://maps.googleapis.com/maps/api/geocode/json?" +
                            "latlng=$latLong&key=AIzaSyC_z_sTz6p0bG6pGBetmvs3eJPitx3a8IY"
                )
            )
        } catch (e: Exception) {
            println("API is ${e.message}")
            Resource.Failure(e)
        }
    }

}