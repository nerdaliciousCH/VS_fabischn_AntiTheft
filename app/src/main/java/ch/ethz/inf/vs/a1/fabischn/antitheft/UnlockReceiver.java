package ch.ethz.inf.vs.a1.fabischn.antitheft;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UnlockReceiver extends BroadcastReceiver{



    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Unlocked!", "Yay");
        AntiTheftService.setRunning(false);


    }
}
