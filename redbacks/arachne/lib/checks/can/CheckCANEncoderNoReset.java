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
public class CheckCANEncoderNoReset extends CheckAnalog
{
	CANEncoder sensor;
	
	/**
	 * @param pulses The number of pulses the encoder must reach past the starting position in any direction.
	 * @param sensor The sensor being checked.
	 */
	public CheckCANEncoderNoReset(double pulses, CANEncoder sensor) {
		this(pulses, sensor, true);
	}

	/**
	 * @param pulses The number of pulses the encoder must reach past the starting position in any direction.
	 * @param sensor The sensor being checked.
	 * @param isGreaterThan Whether the required reading is greater than the input.
	 */
	public CheckCANEncoderNoReset(double pulses, CANEncoder sensor, boolean isGreaterThan) {
		super(isGreaterThan, pulses);
		this.sensor = sensor;
	}

	public boolean isTrue(CommandRB command) {
		return type ? Math.abs(sensor.get()) > value : Math.abs(sensor.get()) < value;
	}
	
	public void begin(CommandRB command, Action action) {
	}
}
