package app.waste2wealth.com.location

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.waste2wealth.com.ktorClient.Resource
import app.waste2wealth.com.ktorClient.UriPathFinder
import app.waste2wealth.com.ktorClient.repository.PlacesRepository
import app.waste2wealth.com.qrcode.sensors.MeasurableSensors
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    application: Application,
    private val lightSensors: MeasurableSensors,
    private val repository: PlacesRepository,
    private val locationTracker: LocationTracker,
) : AndroidViewModel(application) {
    private val locationClient = LocationClientProvider(
        application.applicationContext,
        LocationServices.getFusedLocationProviderClient(application.applicationContext)
    )
    var locationState: MutableStateFlow<String> = MutableStateFlow("Location Not Found")
    var isDark by mutableStateOf(false)
    var latitude by mutableStateOf(0.0)
    var theirLatitude = mutableStateOf(0.0)
    var longitude by mutableStateOf(0.0)
    var theirLongitude = mutableStateOf(0.0)
    var locationNo = mutableStateOf("")
    var address = mutableStateOf("")
    var distance = mutableStateOf("")
    var time = mutableStateOf("")
    var wastePhoto = mutableStateOf("")
    var beforeActivityPath = mutableStateOf("")
    var activityTitle = mutableStateOf("")
    var rewardImage = mutableStateOf("")
    var rewardTitle = mutableStateOf("")
    var rewardDescription = mutableStateOf("")
    var rewardNoOfPoints = mutableStateOf(0)
    var startActivityTime = mutableStateOf(0L)
    var listOfAddresses by mutableStateOf(mutableListOf<String?>(null))


    init {
        lightSensors.startListening()
        lightSensors.setOnSensorValuesChangedListener { values ->
            val lux = values[0]
//            println("The values are $lux")
            isDark = lux == 0f
//            println("what is is Dark $isDark")
        }
    }

    val result = MutableLiveData<String>()


    fun getPlaces() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let {
                repository.getPlaces("${it.latitude},${it.longitude}").let { resource ->
                    when (resource) {
                        is Resource.Failure -> {
                            println("API is Failed")
                            println("API is ${resource.exception.message}")
                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            resource.result.results?.forEach { address ->
                                listOfAddresses.add(address.formattedAddress)
                            }
                            latitude = it.latitude
                            longitude = it.longitude
                        }
                    }
                }
            }
        }
    }

    fun updateLocation() {
        viewModelScope.launch {
            locationClient.getLocationUpdates(2000L).collectLatest { location ->
                println("New Location is ${location.longitude} & ${location.latitude}")
                locationState
                    .emit("latitude = ${location.latitude} & longitude = ${location.longitude}")
            }
        }
    }

    private fun changeUriToPath(uris: Uri, context: Context) =
        UriPathFinder().getPath(context, uris)

    fun onFilePathsListChange(list: Uri, context: Context): String? = changeUriToPath(list, context)


}