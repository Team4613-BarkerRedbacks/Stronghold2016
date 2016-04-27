package redbacks.arachne.lib.checks.digital;

import redbacks.arachne.lib.checks.Check.CheckDigital;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Checks whether a command is complete.
 * May be useful when waiting for various actions on a subsystem to be complete before starting the next action.
 * 
 * @author Sean Zammit
 */
public class CheckCommand extends CheckDigital {
	CommandRB command;
	
	/**
	 * @param command The command that the check is waiting for.
	 */
	public CheckCommand(CommandRB command) {
		super(true);
		this.command = command;
	}

	public boolean isTrue(CommandRB command) {
		return command.isDone() == type;
	}
}