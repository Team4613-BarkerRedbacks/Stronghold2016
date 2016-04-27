package redbacks.arachne.lib.checks.can;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.sensors.CANEncoder;

/**
 * Checks whether an encoder wired into a CAN controller has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckCANEncoder extends CheckAnalog
{
	CANEncoder sensor;
	
	/**
	 * @param pulses The number of pulses the encoder must reach past the starting position in any direction.
	 * @param sensor The sensor being checked.
	 */
	public CheckCANEncoder(double pulses, CANEncoder sensor) {
		super(true, pulses);
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
