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

    // preference-tags
    private String SENSITIVITY_PREFERENCE;
    private String DELAY_PREFERENCE;
    private String MOVEMENT_DETECTOR_PREFERENCE;

    private int sensitivity;
    private int delay;
    private String movementDetector;


    public static ToggleButton getToggleButton() {
        return tb;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize preference-tags
        this.SENSITIVITY_PREFERENCE = getResources().getString(R.string.pref_sensitivity);
        this.DELAY_PREFERENCE = getResources().getString(R.string.pref_delay);
        this.MOVEMENT_DETECTOR_PREFERENCE = getResources().getString(R.string.pref_movement_detector);

        unlockReceiver = new UnlockReceiver();

        // Fetch current sensitivity and delay preference values and initialize togglebutton
        this.sensitivity = PreferenceManager.getDefaultSharedPreferences(this).getInt(this.SENSITIVITY_PREFERENCE, 5);
        this.delay       = PreferenceManager.getDefaultSharedPreferences(this).getInt(this.DELAY_PREFERENCE, 2);

        tb = (ToggleButton) findViewById(R.id.btn_toggle);



    }

    @Override
    protected void onResume() {
        super.onResume();

        // Display useful text of ToggleButton
        if (tb.isChecked()) {
            tb.setText(R.string.toggle_deactivate);
        }
        else {
            tb.setText(R.string.toggle_activate);
        }
        // Register UnlockReceiver and PreferenceListener
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        registerReceiver(unlockReceiver, new IntentFilter("android.intent.action.USER_PRESENT"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.unlockReceiver);

    }

    public void onClickToggle (View v) {
        this.movementDetector = PreferenceManager.getDefaultSharedPreferences(this).getString(this.MOVEMENT_DETECTOR_PREFERENCE, "Bla");

        // create Intent and pass the current preference-values
        Intent intent = new Intent(this, AntiTheftService.class);
        intent.putExtra(this.SENSITIVITY_PREFERENCE, this.sensitivity);
        intent.putExtra(this.DELAY_PREFERENCE, this.delay);
        intent.putExtra(this.MOVEMENT_DETECTOR_PREFERENCE, this.movementDetector);



        if (tb.isChecked()) {
            // Start the service and set the ToggleButton text accordingly
            AntiTheftService.setRunning(true);
            startService(intent);
            tb.setText(R.string.toggle_deactivate);
        }
        else {
            //Unset the 'running' flag of the service s.t. it can terminate successfully.
            AntiTheftService.setRunning(false);
            tb.setText(R.string.toggle_activate);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Create OptionsMenu according to XML-layout
        getMenuInflater().inflate(R.menu.menu_actuators, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, SettingsActivity.class);
        /*Fetch current preference values and pass them to the SettingsActivity,
        * The SettingsActivity can use these values to detect whether the user changed
        * any settings. If not it will inform the user by a TOAST that no settings were
        * changed after resuming from ActititySettings*/
        switch (item.getItemId()) {
            case R.id.menu_settings:
                intent.putExtra("SensitivityOnEntry", this.sensitivity);
                intent.putExtra("DelayOnEntry", this.delay);
                intent.putExtra("MovementDetectorOnEntry", this.movementDetector);
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
            if (AntiTheftService.amd != null ) {
                AntiTheftService.amd.setThreshFromSensitivity(this.sensitivity);
            }
        }
        else if (key.equals(this.DELAY_PREFERENCE)) {
            this.delay = sharedPref.getInt(key, 10);
            Log.d("Delay Time is:", this.delay + "");
        }
        else if (key.equals(this.MOVEMENT_DETECTOR_PREFERENCE)) {
            this.movementDetector = sharedPref.getString(key, "bla");
            Log.d(this.movementDetector, "!!!!!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);


    }
}
