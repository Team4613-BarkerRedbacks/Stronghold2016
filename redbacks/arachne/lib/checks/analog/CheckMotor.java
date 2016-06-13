package redbacks.arachne.lib.checks.analog;

import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.motors.MotorControllerRB;

/**
 * Checks whether an encoder has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckMotor extends CheckAnalog
{
	MotorControllerRB motor;
	
	/**
	 * @param pulses The number of pulses the encoder must reach past the starting position in any direction.
	 * @param encoder The encoder itself.
	 */
	public CheckMotor(double speed, MotorControllerRB motor) {
		super(true, speed);
		this.motor = motor;
	}

	public boolean isTrue(CommandRB command) {
		return Math.abs(motor.get()) > value && super.isTrue(command);
	}
}
