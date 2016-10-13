package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import ch.ethz.inf.vs.a1.fabischn.antitheft.AlarmCallback;

public abstract class AbstractMovementDetector implements SensorEventListener {

    protected AlarmCallback callback;
    protected int sensitivity;
    protected int thresh = 0;

    public AbstractMovementDetector(AlarmCallback callback, int sensitivity){
        this.callback = callback;
        this.sensitivity = sensitivity;
    }

    // Sensor monitoring
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            // Copy values because the event is not owned by the application
            float[] values = event.values.clone();
            if(doAlarmLogic(values)){
                callback.onDelayStarted();
            }
        }
    }

    public synchronized void setThreshFromSensitivity(int sens) {
        this.thresh = SeekbarPreference.getSeekbarMaxValue() - sens;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do not do anything
    }

    public abstract boolean doAlarmLogic(float[] values);

}
