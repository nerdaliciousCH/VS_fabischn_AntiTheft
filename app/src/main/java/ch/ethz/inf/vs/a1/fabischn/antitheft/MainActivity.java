package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{



    public static synchronized void setToggleButton(boolean b) {
        tb.setChecked(b);
    }

    private static ToggleButton tb = null;
    private UnlockReceiver unlockReceiver = null;

    private final String SENSITIVITY_PREFERENCE = "pref_sensitivity";
    private final String DELAY_PREFERENCE = "pref_delay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unlockReceiver = new UnlockReceiver();
        registerReceiver(unlockReceiver, new IntentFilter("android.intent.action.USER_PRESENT"));
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(unlockReceiver, new IntentFilter("android.intent.action.USER_PRESENT"));


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.unlockReceiver);
    }

    public void onClickToggle (View v) {
        tb = (ToggleButton) v;
        Intent intent = new Intent(this, AntiTheftService.class);

        if (tb.isChecked()) {
            AntiTheftService.setRunning(true);
            startService(intent);
            tb.setText(R.string.toggle_deactivate);
        }
        else {
            AntiTheftService.setRunning(false);
            tb.setText(R.string.toggle_activate);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actuators, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("name", "Olivier");

        switch (item.getItemId()) {
            case R.id.menu_settings:

                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if (key.equals(this.SENSITIVITY_PREFERENCE)) {
            boolean bool = sharedPref.getBoolean(SENSITIVITY_PREFERENCE, true);
            Log.d("Sensitivity is:", bool + "");
        }
        else if (key.equals(this.DELAY_PREFERENCE)) {
            boolean bool = sharedPref.getBoolean(DELAY_PREFERENCE, true);
            Log.d("Delay is:", bool + "");
        }

    }
}
