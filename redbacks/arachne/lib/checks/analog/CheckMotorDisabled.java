package redbacks.arachne.lib.checks.analog;

import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.motors.MotorControllerRB;

/**
 * Checks whether an encoder has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckMotorDisabled extends CheckAnalog
{
	MotorControllerRB motor;
	
	/**
	 * @param pulses The number of pulses the encoder must reach past the starting position in any direction.
	 * @param encoder The encoder itself.
	 */
	public CheckMotorDisabled(MotorControllerRB motor) {
		super(true, 0);
		this.motor = motor;
	}

	public boolean isTrue(CommandRB command) {
		return motor.get() == 0 && super.isTrue(command);
	}
}
