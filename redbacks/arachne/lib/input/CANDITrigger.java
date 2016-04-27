package redbacks.arachne.lib.input;

import redbacks.arachne.lib.checks.can.CheckCANDI;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * @author Sean Zammit
 */
public class CANDITrigger extends Button
{
    CheckCANDI check;

    /**
     * JAVADOC
     */
    public CANDITrigger(CheckCANDI check) {
    	this.check = check;
    }

    public boolean get() {
        return check.isTrue(null);
    }
}
