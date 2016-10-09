package ch.ethz.inf.vs.a1.fabischn.antitheft;


import android.content.Context;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SeekbarPreference extends Preference {

    public SeekbarPreference(Context context) {
        super(context);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        LayoutInflater li = (LayoutInflater)getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        return li.inflate( R.layout.seekbar_preference, parent, false);
    }
}
