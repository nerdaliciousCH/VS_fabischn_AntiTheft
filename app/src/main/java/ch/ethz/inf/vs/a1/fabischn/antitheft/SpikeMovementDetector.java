package ch.ethz.inf.vs.a1.fabischn.antitheft;

import android.util.Log;

import ch.ethz.inf.vs.a1.fabischn.antitheft.AlarmCallback;

public class SpikeMovementDetector extends AbstractMovementDetector {


    public SpikeMovementDetector(AlarmCallback callback, int sensitivity) {
        super(callback, sensitivity);
    }

    @Override
    public boolean doAlarmLogic(float[] values) {
		// TODO, insert your logic here

        //printValues(values);
        float sum = absSum(values);
        Log.d(this.thresh + "", sum + "");
        return (sum >= this.thresh);
    }

    private float absSum(float[] values) {
        int result = 0;
        for (int i = 0; i < values.length; ++i) {
            result += Math.abs(values[i]);
        }
        return result;
    }

    public void printValues(float[] values) {
        String s =  "[" + values[0] + ", " + values[1] + ", " + values[2] + "]";
        Log.d("Values", s);


    }

}
