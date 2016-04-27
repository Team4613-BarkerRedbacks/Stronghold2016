package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * @author Sean Zammit
 */
public class ActionSetZeroed extends Action
{
	public ActionSetZeroed() {
		super(new CheckBoolean(true));
	}

	public void onFinish(CommandRB command) {
		CommandBase.turret.isZeroed = true;
	}
}
