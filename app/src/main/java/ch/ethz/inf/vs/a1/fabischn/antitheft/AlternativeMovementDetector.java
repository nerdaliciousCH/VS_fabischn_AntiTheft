package ch.ethz.inf.vs.a1.fabischn.antitheft;

/**
 * Created by Olivier on 13.10.2016.
 */

public class AlternativeMovementDetector extends AbstractMovementDetector {


    public AlternativeMovementDetector(AlarmCallback callback, int sensitivity) {
        super(callback, sensitivity);
    }



    @Override
    public boolean doAlarmLogic(float[] values) {
        return false;
    }
}
