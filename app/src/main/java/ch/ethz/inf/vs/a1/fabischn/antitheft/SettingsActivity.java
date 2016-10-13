package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.content.Context;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
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
        int sens = getPreferences(getApplicationContext().MODE_PRIVATE).getInt("Sensitivity", 4);

        Log.d("SENSITIFITI is", sens + "");
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
        int sensitivityOnExit = getPreferences(Context.MODE_PRIVATE).getInt("Sensitivity", 2);
        int delayOnExit = getPreferences(Context.MODE_PRIVATE).getInt("Delay Time", 2);
       // if (sensitivityOnExit == sensitivityOnExit && delayOnEntry == delayOnExit) {
            Context context = getApplicationContext();
            CharSequence text = "Nothing has changed.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
       // }
        Log.d("Back Pressed", "!!!!!");

    }
}
