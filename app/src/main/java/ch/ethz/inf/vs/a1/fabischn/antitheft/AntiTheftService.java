package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.os.IBinder;
import android.preference.PreferenceManager;
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
    private int sensitivity = 10;
    private int delay = 0;

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




        mp = MediaPlayer.create(this, R.raw.alarm);
        mp.setVolume(1.0f, 1.0f);
        mp.setLooping(true);




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
        this.sensitivity = intent.getIntExtra(getResources().getString(R.string.pref_sensitivity), 10);
        this.delay       = intent.getIntExtra(getResources().getString(R.string.pref_delay), 0);

        spike = new SpikeMovementDetector(this, this.sensitivity);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(spike, accSensor, SensorManager.SENSOR_DELAY_NORMAL);

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
        try {
            Thread.sleep(this.delay * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("ALARM!!!", "ALARM!!!");
        mp.start();

    }


}
