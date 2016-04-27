package redbacks.arachne.lib.checks.can;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.sensors.CANAnalog;

/**
 * Checks whether an analog sensor wired into a CAN controller has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckCANAnalog extends CheckAnalog
{
	CANAnalog sensor;
	
	/**
	 * @param degrees The value the analog sensor must reach past the starting position in any direction.
	 * @param sensor The sensor being checked.
	 */
	public CheckCANAnalog(double degrees, CANAnalog sensor) {
		super(true, degrees);
		this.sensor = sensor;
	}

	public boolean isTrue(CommandRB command) {
		return Math.abs(sensor.get()) > value && super.isTrue(command);
	}
	
	public void begin(CommandRB command, Action action) {
		super.begin(command, action);
		sensor.reset();
	}
}
