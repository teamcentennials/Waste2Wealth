package app.waste2wealth.com.di

import android.app.Application
import app.waste2wealth.com.ktorClient.repository.PlacesRepoImpl
import app.waste2wealth.com.ktorClient.repository.PlacesRepository
import app.waste2wealth.com.qrcode.sensors.MeasurableSensors
import app.waste2wealth.com.qrcode.sensors.Sensors
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {


    @Provides
    @Singleton
    fun provideLightSensor(app: Application): MeasurableSensors {
        return Sensors(app)
    }


    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(JsonFeature) {
                this.serializer = GsonSerializer() {
                    setPrettyPrinting()
                    disableHtmlEscaping()
                }
            }
        }
    }

    @Provides
    @Singleton
    fun providesMessageService(client: HttpClient): PlacesRepository {
        return PlacesRepoImpl(client = client)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

}

