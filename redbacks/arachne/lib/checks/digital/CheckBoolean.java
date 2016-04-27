package redbacks.arachne.lib.checks.digital;

import redbacks.arachne.lib.checks.Check.CheckDigital;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Creates a check that will always return a set value.
 * True will mean that the action immediately ends.
 * False will mean that the action will only end when another action is queued.
 * 
 * To have an action never end, use CheckNever.
 * 
 * @author Sean Zammit
 */
public class CheckBoolean extends CheckDigital
{
	/**
	 * @param checkResult The value that the check will always return.
	 */
	public CheckBoolean(boolean checkResult) {
		super(checkResult);
	}

	public boolean isTrue(CommandRB command) {
		return type;
	}
}
