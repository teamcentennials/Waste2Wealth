package app.waste2wealth.com.di

import android.app.Application
import app.waste2wealth.com.qrcode.sensors.MeasurableSensors
import app.waste2wealth.com.qrcode.sensors.Sensors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideLightSensor(app: Application): MeasurableSensors {
        return Sensors(app)
    }

}