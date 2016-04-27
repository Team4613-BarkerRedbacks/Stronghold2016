package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.sensors.CANEncoder;

/**
 * JAVADOC
 * 
 * @author Sean Zammit
 */
public class ActionResetCANEncoder extends Action
{
	public CANEncoder enc;
	
	public ActionResetCANEncoder(CANEncoder encoder) {
		super(new CheckBoolean(true));
		enc = encoder;
	}

	public void runAction(CommandRB command) {
		enc.reset();
	}
}
