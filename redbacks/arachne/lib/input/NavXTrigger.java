package redbacks.arachne.lib.input;

import redbacks.arachne.lib.navx.CheckNavX;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * @author Sean Zammit
 */
public class NavXTrigger extends Button
{
    CheckNavX check;

    /**
     * JAVADOC
     */
    public NavXTrigger(CheckNavX check) {
    	this.check = check;
    }

    public boolean get() {
        return check.isTrue(null);
    }
}
