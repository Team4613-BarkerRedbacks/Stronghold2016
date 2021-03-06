package redbacks.robot.arm;

import static redbacks.arachne.core.CommandBase.arm;
import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.actions.ActionSolenoid;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionArmNotCentre extends Action
{
	public ActionArmNotCentre() {
		super(new CheckBoolean(true));
	}
	
	public void onFinish(CommandRB command) {
		if(!CommandBase.arm.arm.get()) new CommandHolder(CommandBase.arm, 
				new ActionSolenoid.Single(arm.armSt1, true)).c().start();
		else new CommandHolder(CommandBase.arm,
				new ActionSolenoid.Single(arm.armSt1, false)).c().start();
	}
}
