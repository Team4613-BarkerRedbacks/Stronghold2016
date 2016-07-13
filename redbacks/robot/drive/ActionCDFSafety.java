package redbacks.robot.drive;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.navx.NavX;

public class ActionCDFSafety extends Action {

	CommandHolder commandToRun;
	
	public ActionCDFSafety(CommandHolder commandToRun) {
		super(new CheckBoolean(true));
		this.commandToRun = commandToRun;
	}
	
	public void onFinish(CommandRB command) {
		if(NavX.getPitch() > 10) {
			commandToRun.c().start();
		}
	}
}
