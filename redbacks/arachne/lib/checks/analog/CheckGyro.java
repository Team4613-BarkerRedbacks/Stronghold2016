package redbacks.arachne.lib.checks.analog;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Checks whether a gyro has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckGyro extends CheckAnalog
{
	Gyro gyro;
	
	/**
	 * @param degrees The degrees the gyro must reach past the starting position in any direction.
	 * @param gyro The gyro itself.
	 */
	public CheckGyro(double degrees, Gyro gyro) {
		super(true, degrees);
		this.gyro = gyro;
	}

	public boolean isTrue(CommandRB command) {
		return Math.abs(gyro.getAngle()) > value && super.isTrue(command);
	}
	
	public void begin(CommandRB command, Action action) {
		super.begin(command, action);
		gyro.reset();
	}
}
