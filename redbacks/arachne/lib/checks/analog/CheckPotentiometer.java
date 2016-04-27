package redbacks.arachne.lib.checks.analog;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Checks whether the value of a potentiometer has passed a specific point.
 * 
 * @author Sean Zammit
 */
public class CheckPotentiometer extends CheckAnalog
{
	AnalogPotentiometer pot;
	
	/**
	 * @param isGreaterThan Whether the potentiometer must be greater than the set value to fulfil the check.
	 * @param position The value that the potentiometer must pass.
	 * @param potentiometer The potentiometer itself.
	 */
	public CheckPotentiometer(boolean isGreaterThan, double position, AnalogPotentiometer potentiometer) {
		super(isGreaterThan, position);
		this.pot = potentiometer;
	}

	public boolean isTrue(CommandRB command) {
		if(type) return pot.get() > value;
		return pot.get() < value;
	}
}
