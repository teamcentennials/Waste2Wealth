package app.waste2wealth.com.ktorClient.repository

import app.waste2wealth.com.ktorClient.Resource
import app.waste2wealth.com.ktorClient.placesAPI.dto.Places


interface PlacesRepository {
    suspend fun getPlaces(latLong: String): Resource<Places>
}
