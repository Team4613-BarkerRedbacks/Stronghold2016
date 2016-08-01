package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.Timer;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * This action should be called when the command should not perform any function.
 * It functions as a placeholder so that the command does not end.
 * 
 * @author Sean Zammit
 */
public class ActionStartTimer extends Action
{
	Timer timer;
	
	public ActionStartTimer(Timer timer) {
		super(new CheckBoolean(true));
		this.timer = timer;
	}
	
	protected void onFinish(CommandRB command) {
		timer.start();
	}
}
