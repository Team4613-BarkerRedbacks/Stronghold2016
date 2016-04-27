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
public class ActionResetYaw extends Action
{
	public ActionResetYaw() {
		super(new CheckBoolean(true));
	}

	public void runAction(CommandRB command) {
		NavX.reset();
	}
}
