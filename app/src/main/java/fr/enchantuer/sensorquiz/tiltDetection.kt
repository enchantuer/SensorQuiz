package fr.enchantuer.sensorquiz

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import fr.enchantuer.sensorquiz.data.QuestionType

class SensorTiltDetection(
    private val sensorManager: SensorManager,
    private val onTiltDetected: (tiltValue: TiltValue) -> Unit
) : SensorEventListener {
    private val linearAcceleration: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

    private var isCheckingTilt = false
    private var tiltBuffer = mutableListOf<TiltValue>()
    private val handler = Handler(Looper.getMainLooper())

    private val checkDuration = 200L // ms

    private val debounceTime = 300L
    private var lastDetectionTime = 0L
    private var hasResponded = false

    var questionType: QuestionType = QuestionType.TWO_CHOICES

    fun startListening() {
        isCheckingTilt = false
        lastDetectionTime = System.currentTimeMillis()
        hasResponded = false
        tiltBuffer.clear()

        linearAcceleration?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_LINEAR_ACCELERATION || hasResponded ) return

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastDetectionTime < debounceTime) return

        val x = event.values[0]  // gauche/droite
        val z = event.values[2]  // vers/loin

        var currentTilt = when {
            x > 3 -> TiltValue.LEFT
            x < -3 -> TiltValue.RIGHT
            z > 6 -> TiltValue.SHAKE // vers l'utilisateur
            z < -6 -> TiltValue.SHAKE
            else -> TiltValue.NONE
        }
        if (questionType != QuestionType.THREE_CHOICES && currentTilt == TiltValue.SHAKE) {
            currentTilt = TiltValue.NONE
        }

//        if (currentTilt != TiltValue.NONE) {
//            Log.d("SensorTest", "currentTilt: $currentTilt")
//            hasResponded = true
//            lastDetectionTime = System.currentTimeMillis()
//            onTiltDetected(currentTilt)
//
//            stopListening()
//        }

        if (!isCheckingTilt && currentTilt != TiltValue.NONE) {
            isCheckingTilt = true
            tiltBuffer.add(currentTilt)

            // Continue à accumuler les valeurs pendant checkDuration
            handler.postDelayed({
                validateTilt()
            }, checkDuration)
        } else if (isCheckingTilt) {
            tiltBuffer.add(currentTilt)
        }
    }

    private fun validateTilt() {
        isCheckingTilt = false

        // On garde seulement les mouvements significatifs
        val filtered = tiltBuffer.filter { it != TiltValue.NONE }
        val detected = filtered.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        Log.d("SensorTest", "hasResponded: $hasResponded | lastDetectionTime: $lastDetectionTime | debounceTime: ${System.currentTimeMillis() - lastDetectionTime}")
        if (detected != null/* && hasResponded.not() && (System.currentTimeMillis() - lastDetectionTime > debounceTime)*/) {
            hasResponded = true
            lastDetectionTime = System.currentTimeMillis()
            Log.d("SensorTest", "Tilt confirmed: $detected")
            onTiltDetected(detected)

            stopListening()
        }

        tiltBuffer.clear()
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

enum class TiltValue {
    LEFT,
    RIGHT,
    SHAKE,
    NONE
}