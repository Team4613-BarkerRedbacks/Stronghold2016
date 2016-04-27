package redbacks.robot.arm;

import static redbacks.arachne.core.CommandBase.arm;
import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.actions.ActionSolenoid;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionArmNotBase extends Action
{
	public ActionArmNotBase() {
		super(new CheckBoolean(true));
	}
	
	public void onFinish(CommandRB command) {
		new CommandHolder(CommandBase.arm, 
				new ActionSolenoid.Single(arm.armSt1, true)).c().start();
	}
}
