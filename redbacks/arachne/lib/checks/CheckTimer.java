package redbacks.arachne.lib.checks;

import redbacks.arachne.lib.commands.CommandRB;

/**
 * Uses the parent command's timer to run an action until a specified amount of time has passed.
 * 
 * @author Sean Zammit
 */
public class CheckTimer extends Check
{
	double time;
	Timer timer;
	
	/**
	 * @param timeout The number of seconds until the check should be completed.
	 */
	public CheckTimer(double timeout, Timer timer) {
		super();
		time = timeout;
		this.timer = timer;
	}

	public boolean isTrue(CommandRB command) {
		return time >= timer.get();
	}
}
