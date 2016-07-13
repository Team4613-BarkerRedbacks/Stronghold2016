package redbacks.arachne.lib.navx;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Checks whether the NavX has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckNavXNR extends CheckNavX
{
	/**
	 * @param isGreaterThan Whether the reading must be greater than the input.
	 * @param reading The reading required from the sensor.
	 * @param type The sensor to get the reading from.
	 */
	public CheckNavXNR(boolean isGreaterThan, double reading, NavXReadingType type) {
		super(isGreaterThan, reading, type);
	}
	
	public void begin(CommandRB command, Action action) {
	}
}
