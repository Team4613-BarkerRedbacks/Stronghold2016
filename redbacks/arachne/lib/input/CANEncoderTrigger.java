package redbacks.arachne.lib.input;

import redbacks.arachne.lib.checks.can.CheckCANEncoderNoReset;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * @author Sean Zammit
 */
public class CANEncoderTrigger extends Button
{
    CheckCANEncoderNoReset check;

    /**
     * JAVADOC
     */
    public CANEncoderTrigger(CheckCANEncoderNoReset check) {
    	this.check = check;
    }

    public boolean get() {
        return check.isTrue(null);
    }
}
