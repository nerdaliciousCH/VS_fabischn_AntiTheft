package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class AntiTheftService extends IntentService implements  AlarmCallback {
    public AntiTheftService() {
        super("AntiTheftThread");
    }

    private SpikeMovementDetector spike = null;
    private SensorManager sensorManager = null;

    public static synchronized boolean isRunning() {
        return running;
    }

    public static synchronized void setRunning(boolean running) {
        AntiTheftService.running = running;
    }

    private static boolean running = false;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Hadsfkjl", "1");
        spike = new SpikeMovementDetector(this, 10);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(spike, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("Hadsfkjl", "2");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Service", "Start");
        while (isRunning()){}
        Log.d("Service", "Stop");


        sensorManager.unregisterListener(spike);
    }

    @Override
    public void onDelayStarted() {
        Log.d("ALARM!!!", "ALARM!!!");

    }
}
