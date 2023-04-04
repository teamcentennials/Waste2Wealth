package app.waste2wealth.com.qrcode.sensors

abstract class MeasurableSensors(protected val sensorType: Int) {

    protected var onSensorValuesChanged: ((List<Float>) -> Unit)? = null

    abstract val doesSensorExist: Boolean

    abstract fun startListening()

    abstract fun stopListening()

    fun setOnSensorValuesChangedListener(listner: (List<Float>) -> Unit) {
        onSensorValuesChanged = listner
    }


}