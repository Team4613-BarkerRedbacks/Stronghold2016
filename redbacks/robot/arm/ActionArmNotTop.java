package redbacks.robot.arm;

import static redbacks.arachne.core.CommandBase.arm;
import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.actions.ActionSolenoid;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionArmNotTop extends Action
{
	public ActionArmNotTop() {
		super(new CheckBoolean(true));
	}
	
	public void onFinish(CommandRB command) {
		new CommandHolder(CommandBase.arm, 
				new ActionSolenoid.Single(arm.arm, true)).c().start();
	}
}
