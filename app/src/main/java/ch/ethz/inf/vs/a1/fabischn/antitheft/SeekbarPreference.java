package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekbarPreference extends Preference implements OnSeekBarChangeListener {
    private SeekBar mSeekBar;
    private TextView description;
    private TextView seekBarValue;
    private int mProgress;
    private String DESCRIPTION_TEXT = "";

    public SeekbarPreference(Context context) {
        this(context, null, 0);
    }

    public SeekbarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekbarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(R.layout.seekbar_preference);
    }


    public void setDescription (String str) {
        this.DESCRIPTION_TEXT = str;
    }



    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
        description = (TextView) view.findViewById(R.id.description);
        seekBarValue = (TextView) view.findViewById(R.id.SeekbarValue);
        description.setText(this.DESCRIPTION_TEXT);
        mSeekBar.setProgress(mProgress);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mProgress = progress;
        this.seekBarValue.setText(progress + "");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // not used
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        persistInt(this.mProgress);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if (restoreValue) {
            mProgress = this.getPersistedInt(10);
            } else {
            // Set default state from the XML attribute
            mProgress = (Integer) defaultValue;
            persistInt(mProgress);
            }
    }

    public void setValue(int value) {
        if (shouldPersist()) {
            persistInt(value);
        }

        if (value != mProgress) {
            mProgress = value;
            notifyChanged();
        }
    }


    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        Log.d("Default Value is:", a.getInt(index, 0) + "");
        return a.getInt(index, 0);
    }
}

