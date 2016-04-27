package redbacks.arachne.lib.checks.can;

import redbacks.arachne.lib.checks.Check.CheckDigital;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.sensors.CANDigitalInput;

/**
 * Checks the value of a sensor in a Digital Input wired into a CAN controller.
 * 
 * @author Sean Zammit
 */
public class CheckCANDI extends CheckDigital {
	
	CANDigitalInput sensor;
	
	/**
	 * @param sensor The sensor being checked.
	 * @param isTriggered Whether the value should be true.
	 */
	public CheckCANDI(CANDigitalInput sensor, boolean isTriggered) {
		super(isTriggered);
		this.sensor = sensor;
	}

	public boolean isTrue(CommandRB command) {
		return sensor.get();
	}
}