package app.waste2wealth.com.qrcode.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class Sensors(context: Context) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT
)