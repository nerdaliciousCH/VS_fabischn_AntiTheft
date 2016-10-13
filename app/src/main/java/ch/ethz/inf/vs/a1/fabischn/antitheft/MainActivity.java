package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.app.PendingIntent;
import android.content.Context;
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

    private String SENSITIVITY_PREFERENCE;
    private String DELAY_PREFERENCE;

    private int sensitivity = 10;
    private int delay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.SENSITIVITY_PREFERENCE = getResources().getString(R.string.pref_sensitivity);
        this.DELAY_PREFERENCE = getResources().getString(R.string.pref_delay);

        setContentView(R.layout.activity_main);
        unlockReceiver = new UnlockReceiver();
        registerReceiver(unlockReceiver, new IntentFilter("android.intent.action.USER_PRESENT"));
        this.sensitivity = PreferenceManager.getDefaultSharedPreferences(this).getInt(this.SENSITIVITY_PREFERENCE, 5);
        this.delay       = PreferenceManager.getDefaultSharedPreferences(this).getInt(this.DELAY_PREFERENCE, 2);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        tb = (ToggleButton) findViewById(R.id.btn_toggle);
        Log.d("!!!!", "onCreate: ");



    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("!!!!!", "onResume: ");
        if (tb.isChecked()) {
            tb.setText(R.string.toggle_deactivate);
        }
        else {
            tb.setText(R.string.toggle_activate);
        }
        registerReceiver(unlockReceiver, new IntentFilter("android.intent.action.USER_PRESENT"));


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.unlockReceiver);
    }

    public void onClickToggle (View v) {
       // tb = (ToggleButton) v;

        Intent intent = new Intent(this, AntiTheftService.class);
        intent.putExtra(this.SENSITIVITY_PREFERENCE, this.sensitivity);
        intent.putExtra(this.DELAY_PREFERENCE, this.delay);



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
                intent.putExtra("SensitivityOnEntry", this.sensitivity);
                intent.putExtra("DelayOnEntry", this.delay);
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
            this.sensitivity = sharedPref.getInt(key, 10);
            Log.d("Sensitivity is:", this.sensitivity + "");
            if (AntiTheftService.spike != null)
                AntiTheftService.spike.setThreshFromSensitivity(this.sensitivity);
        }
        else if (key.equals(this.DELAY_PREFERENCE)) {
            this.delay = sharedPref.getInt(key, 10);
            Log.d("Delay Time is:", this.delay + "");
        }
        //restartService();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);


    }
}
