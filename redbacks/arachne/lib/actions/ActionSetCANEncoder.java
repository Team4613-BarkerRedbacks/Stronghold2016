package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.sensors.CANEncoder;

/**
 * JAVADOC
 * 
 * @author Sean Zammit
 */
public class ActionSetCANEncoder extends Action
{
	public CANEncoder enc;
	public int value;
	
	public ActionSetCANEncoder(CANEncoder encoder, int value) {
		super(new CheckBoolean(true));
		enc = encoder;
		this.value = value;
	}

	public void runAction(CommandRB command) {
		enc.set(value);
	}
}
