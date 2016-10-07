package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    public static ToggleButton tb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickToggle (View v) {
        tb = (ToggleButton) v;
        Intent intent = new Intent(this, AntiTheftService.class);
        if (tb.isChecked()) {
            startService(intent);
            tb.setText(R.string.toggle_deactivate);
        }
        else {
            tb.setText(R.string.toggle_activate);
        }

    }

}
