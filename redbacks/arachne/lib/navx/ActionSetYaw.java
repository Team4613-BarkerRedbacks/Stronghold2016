package redbacks.arachne.lib.navx;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.navx.NavX;

/**
 * This resets the yaw readings of the NavX.
 * 
 * @author Sean Zammit
 */
public class ActionSetYaw extends Action
{
	float offset;
	
	public ActionSetYaw(float offset) {
		super(new CheckBoolean(true));
		this.offset = offset;
	}

	public void runAction(CommandRB command) {
		NavX.setYaw(offset);
	}
}
