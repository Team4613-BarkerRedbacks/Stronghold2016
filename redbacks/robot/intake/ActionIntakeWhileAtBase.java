package redbacks.robot.intake;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.commands.CommandRB;
import static redbacks.arachne.core.CommandBase.*;

/**
 * @author Sean Zammit
 */
public class ActionIntakeWhileAtBase extends Action
{
	public ActionIntakeWhileAtBase() {
		super(new CheckNever());
	}
	
	public void runAction(CommandRB command) {
		if(arm.arm.get() == true && arm.armSt1.get() == false) {
			if(intake.intake.get() == 0) intake.intake.set(0.7D, command);
		}
		else if(intake.intake.get() != 0 && intake.intake.lastCommand == command) intake.intake.disable();
	}
}
