package redbacks.arachne.lib.checks.digital;

import edu.wpi.first.wpilibj.DigitalInput;
import redbacks.arachne.lib.checks.Check.CheckDigital;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Checks the value of a sensor in a Digital Input.
 * 
 * @author Sean Zammit
 */
public class CheckDI extends CheckDigital {
	
	DigitalInput sensor;
	
	/**
	 * @param sensor The sensor being checked.
	 * @param isTriggered Whether the value should be true.
	 */
	public CheckDI(DigitalInput sensor, boolean isTriggered) {
		super(isTriggered);
		this.sensor = sensor;
	}

	public boolean isTrue(CommandRB command) {
		return sensor.get() == type;
	}
}