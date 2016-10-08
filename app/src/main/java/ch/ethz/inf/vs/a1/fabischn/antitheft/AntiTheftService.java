package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class AntiTheftService extends IntentService implements  AlarmCallback {
    public AntiTheftService() {
        super("AntiTheftThread");
    }

    private SpikeMovementDetector spike = null;
    private SensorManager sensorManager = null;
    private MediaPlayer mp = null;

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

        mp = MediaPlayer.create(this, R.raw.alarm);
        mp.setVolume(1.0f, 1.0f);
        mp.setLooping(true);
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
        String TAG = "AntiStealthNotification";
        int NOTIFICATION_ID = 1;
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_info_black_24dp)
                .setContentTitle("Anti-Theft Alarm")
                .setContentText("Activated");
        builder.setOngoing(true);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(TAG, NOTIFICATION_ID, builder.build());


        while (isRunning()){}
        mp.stop();
        notificationManager.cancel(TAG, NOTIFICATION_ID);
        Log.d("Service", "Stop");


        sensorManager.unregisterListener(spike);
    }

    @Override
    public void onDelayStarted() {
        Log.d("ALARM!!!", "ALARM!!!");
        mp.start();

    }
}
