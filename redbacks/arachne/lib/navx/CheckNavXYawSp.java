package redbacks.arachne.lib.navx;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Checks whether the NavX has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckNavXYawSp extends CheckAnalog
{
	double startReading;
	
	/**
	 * @param isGreaterThan Whether the reading must be greater than the input.
	 * @param reading The reading required from the sensor.
	 * @param type The sensor to get the reading from.
	 */
	public CheckNavXYawSp(boolean isGreaterThan, double reading) {
		super(isGreaterThan, reading);
	}

	public boolean isTrue(CommandRB command) {
		return (NavX.getYaw() - startReading >= value) == type;
	}
	
	public void begin(CommandRB command, Action action) {
		startReading = NavX.getYaw();
	}
}
