package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.app.PendingIntent;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity{

    public static ToggleButton tb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickToggle (View v) {
        tb = (ToggleButton) v;
        Intent intent = new Intent(this, AntiTheftService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (tb.isChecked()) {
            startService(intent);
            tb.setText(R.string.toggle_deactivate);
        }
        else {
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



}
