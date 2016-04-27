package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.sensors.CANAnalog;

/**
 * JAVADOC
 * 
 * @author Sean Zammit
 */
public class ActionResetCANAnalog extends Action
{
	public CANAnalog sensor;
	
	public ActionResetCANAnalog(CANAnalog sensor) {
		super(new CheckBoolean(true));
		this.sensor = sensor;
	}

	public void runAction(CommandRB command) {
		sensor.reset();
	}
}
