package redbacks.arachne.lib.checks;

import redbacks.arachne.lib.commands.CommandRB;

/**
 * Functions the same as CheckBoolean(false), except that commands will never skip over this check when there is another action waiting.
 * 
 * @author Sean Zammit
 */
public class CheckNever extends Check
{
	public CheckNever() {}
	
	public boolean isTrue(CommandRB command) {
		return false;
	}
}
