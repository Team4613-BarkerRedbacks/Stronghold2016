package redbacks.arachne.lib.checks;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.motors.MotorControllerRB;

/**
 * Checks whether an encoder has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckShouldTrack extends Check
{
	MotorControllerRB motor;
	
	/**
	 * @param pulses The number of pulses the encoder must reach past the starting position in any direction.
	 * @param encoder The encoder itself.
	 */
	public CheckShouldTrack() {
		super();
	}

	public boolean isTrue(CommandRB command) {
		return CommandBase.turret.shouldTrack;
	}
}
