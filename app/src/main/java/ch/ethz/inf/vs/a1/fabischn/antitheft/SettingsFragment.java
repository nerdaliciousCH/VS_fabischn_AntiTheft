package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.SeekBar;


public class SettingsFragment extends PreferenceFragment {

    private SeekbarPreference sensitivity_pref;
    private SeekbarPreference delay_pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addPreferencesFromResource(R.xml.preferences);
        sensitivity_pref = (SeekbarPreference) findPreference("Sensitivity");
        sensitivity_pref.setDescription("Sensitivity: ");

        delay_pref = (SeekbarPreference) findPreference("Delay Time");

        delay_pref.setDescription("Delay-Time: ");
    }


}
