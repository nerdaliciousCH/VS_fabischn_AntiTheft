package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AntiTheftService extends IntentService implements  AlarmCallback {
    public AntiTheftService() {
        super("AntiTheftThread");
    }


    @Override
    public void onCreate() {
        super.onCreate();
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
        Log.d("Hello", "1");

    }

    @Override
    public void onDelayStarted() {

    }
}
