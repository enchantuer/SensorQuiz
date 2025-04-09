package fr.enchantuer.sensorquiz

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme

class MainActivity : ComponentActivity()/*, SensorEventListener*/ {
    private lateinit var sensorManager: SensorManager
//    private var rotationVector: Sensor? = null
//    private val rotationMatrix = FloatArray(9)
//    private val orientationValues = FloatArray(3)
//
//    private var _tiltState by mutableStateOf(TiltValue.NONE)
//
//    private var lastTilt = TiltValue.NONE
//    private var lastSignificantTilt = TiltValue.NONE
//    private var lastTiltTime = 0L
//    private var shakeDetectedTime = 0L
//    private var lastNeutralTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
//        rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        enableEdgeToEdge()
        setContent {
            SensorQuizTheme {
                SensorQuizApp()
            }
        }
    }

/*    override fun onResume() {
        super.onResume()
        rotationVector?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }*/

/*    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }*/

/*    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {

            // Convert the rotation vector to a 3x3 rotation matrix
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)

            // Get the orientation angles
            SensorManager.getOrientation(rotationMatrix, orientationValues)

            val roll = Math.toDegrees(orientationValues[2].toDouble())
                .toFloat() // Left-Right tilt

            // Detect the tilt (Axe roll)
            val currentTilt = when {
                roll > 25 -> TiltValue.RIGHT // Axe corrigé
                roll < -25 -> TiltValue.LEFT // Axe corrigé
                else -> TiltValue.NONE
            }

            val currentTime = System.currentTimeMillis()

            // Reset after a period of inactivity
            if (currentTilt == TiltValue.NONE) {
                if (lastTilt != TiltValue.NONE) {
                    lastNeutralTime = currentTime
                } else if (currentTime - lastNeutralTime > 700) {
                    lastSignificantTilt = TiltValue.NONE
                }
            }

            // Détection du secouement : passage rapide de gauche à droite ou de droite à gauche
            if ((lastSignificantTilt == TiltValue.LEFT && currentTilt == TiltValue.RIGHT) ||
                (lastSignificantTilt == TiltValue.RIGHT && currentTilt == TiltValue.LEFT)
            ) {

                val timeDiff = currentTime - lastTiltTime

                if (timeDiff in 50..400) { // Un mouvement rapide indique un secouement
                    _tiltState = TiltValue.SHAKE
                    shakeDetectedTime = currentTime
                }
            }

            // Garde la dernière inclinaison significative
            if (currentTilt != TiltValue.NONE) {
                lastSignificantTilt = currentTilt
            }

            // Mise à jour des états de bascule
            if (currentTilt != lastTilt) {
                lastTiltTime = currentTime
            }
            lastTilt = currentTilt

            // Réinitialisation après un secouement
            if (_tiltState == TiltValue.SHAKE && (currentTime - shakeDetectedTime) > 300) {
                _tiltState = currentTilt
            } else if (_tiltState != TiltValue.SHAKE) {
                _tiltState = currentTilt
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }*/
}