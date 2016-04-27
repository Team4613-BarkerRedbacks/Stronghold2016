package redbacks.robot.drive;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * This action switches the driver control variable in the driver subsystem to a set value.
 * NOTE: It does not do anything other than change the variable. All driver code must be based on that variable for it to work.
 * 
 * @author Sean Zammit
 */
public class ActionSwitchControlTo extends Action
{
	boolean isDriver;
	
	/**
	 * @param driver Whether the robot should be controlled by the driver
	 */
	public ActionSwitchControlTo(boolean driver) {
		super(new CheckBoolean(true));
		isDriver = driver;
	}

	public void onFinish(CommandRB command) {
		CommandBase.driver.isDriverControl = isDriver;
	}
}
