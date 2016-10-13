package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.ListPopupWindow;
import android.widget.SeekBar;


public class SettingsFragment extends PreferenceFragment {

    private SeekbarPreference sensitivity_pref;
    private SeekbarPreference delay_pref;
    private ListPreference    movement_pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addPreferencesFromResource(R.xml.preferences);
        String sensitivity = getString(R.string.pref_sensitivity);
        String delay       = getString(R.string.pref_delay);
        String movementDet = getString(R.string.pref_movement_detector);
        sensitivity_pref   = (SeekbarPreference) findPreference(sensitivity);
        delay_pref         = (SeekbarPreference) findPreference(delay);
        movement_pref      = (ListPreference)    findPreference(movementDet);

        sensitivity_pref.setDescription(sensitivity + ":");
        delay_pref.setDescription(delay + " (s):");
    }


}
