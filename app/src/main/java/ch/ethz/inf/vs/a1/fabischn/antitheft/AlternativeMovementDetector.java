package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.util.Log;

/**
 * Created by Olivier on 13.10.2016.
 */

public class AlternativeMovementDetector extends AbstractMovementDetector {


    public AlternativeMovementDetector(AlarmCallback callback, int sensitivity) {
        super(callback, sensitivity);
    }


    @Override
    public boolean doAlarmLogic(float[] values) {
        // TODO, insert your logic here

        return (values[2] >= this.thresh);
    }

    private float absSum(float[] values) {
        int result = 0;
        for (int i = 0; i < values.length; ++i) {
            result += Math.abs(values[i]);
        }
        return result;
    }
}
