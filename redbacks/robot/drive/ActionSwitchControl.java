package redbacks.robot.drive;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * This action switches the driver control variable in the driver subsystem to the inverse of its current value.
 * NOTE: It does not do anything other than change the variable. All driver code must be based on that variable for it to work.
 * 
 * @author Sean Zammit
 */
public class ActionSwitchControl extends Action
{
	public ActionSwitchControl() {
		super(new CheckBoolean(true));
	}

	public void onFinish(CommandRB command) {
		CommandBase.driver.isDriverControl = !CommandBase.driver.isDriverControl;
	}
}