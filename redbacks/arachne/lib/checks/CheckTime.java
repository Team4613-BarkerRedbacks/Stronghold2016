package redbacks.arachne.lib.checks;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Uses the parent command's timer to run an action until a specified amount of time has passed.
 * 
 * @author Sean Zammit
 */
public class CheckTime extends Check
{
	double time, startTime;
	
	/**
	 * @param timeout The number of seconds until the check should be completed.
	 */
	public CheckTime(double timeout) {
		super();
		time = timeout;
	}

	public boolean isTrue(CommandRB command) {
		return command.timeSinceInitialized() - startTime >= time;
	}
	
	public void begin(CommandRB command, Action action) {
		startTime = command.timeSinceInitialized();
	}
}
