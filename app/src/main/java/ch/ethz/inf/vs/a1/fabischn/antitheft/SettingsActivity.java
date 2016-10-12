package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        int sens = getPreferences(Context.MODE_PRIVATE).getInt("Sensitivity", 2);
        Log.d("Sensitivity is", sens + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        Log.d("Test", i.getStringExtra("name"));

    }
}
