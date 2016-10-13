package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.content.Context;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    int sensitivityOnEntry;
    int delayOnEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.sensitivityOnEntry = intent.getIntExtra("SensitivityOnEntry", 10);
        this.delayOnEntry = intent.getIntExtra("DelayOnEntry", 2);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        Log.d("Test", i.getStringExtra("name"));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int sensitivityOnExit = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.pref_sensitivity), 5);
        int delayOnExit = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.pref_delay), 5);


        Log.d("Sensitivity " + sensitivityOnEntry, sensitivityOnExit + "");
        Log.d("Delay "  + delayOnEntry, delayOnExit + "");
        if (sensitivityOnEntry == sensitivityOnExit && delayOnEntry == delayOnExit) {
            Context context = getApplicationContext();
            CharSequence text = "Nothing has changed.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        Log.d("Back Pressed", "!!!!!");

    }
}
