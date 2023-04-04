package app.waste2wealth.com.location

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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
    private val lightSensors: MeasurableSensors
) : AndroidViewModel(application) {
    private val locationClient = LocationClientProvider(
        application.applicationContext,
        LocationServices.getFusedLocationProviderClient(application.applicationContext)
    )
    var locationState: MutableStateFlow<String> = MutableStateFlow("Location Not Found")
    var isDark by mutableStateOf(false)

    init {
        lightSensors.startListening()
        lightSensors.setOnSensorValuesChangedListener { values ->
            val lux = values[0]
            println("The values are $lux")
            isDark = lux == 0f
            println("what is is Dark $isDark")
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


}