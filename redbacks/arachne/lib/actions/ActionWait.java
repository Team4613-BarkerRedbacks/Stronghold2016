package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.CheckTime;

/**
 * A convenient shortcut action that does nothing for a specified amount of time before starting the next command.
 * 
 * @author Sean Zammit
 */
public class ActionWait extends Action
{
	/**
	 * @param timeout The number of seconds (as a decimal) that the action will wait for.
	 */	
	public ActionWait(double timeout) {
		super(new CheckTime(timeout));
	}
}
