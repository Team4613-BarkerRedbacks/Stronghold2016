package redbacks.arachne.lib.actions;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.subsystems.SubsystemRB;

/**
 * Holds all actions that interrupt other functions.
 * 
 * @author Sean Zammit
 */
public class ActionInterrupt
{
	/**
	 * Cancels the operation of a single action in a specified command.
	 */
	public static class KillAction extends Action
	{
		CommandRB target;
		
		/**
		 * @param commandToProgress The command which will be manually progressed to the next action.
		 */
		public KillAction(CommandRB commandToProgress) {
			super(new CheckBoolean(true));
			target = commandToProgress;
		}
		
		public void onFinish(CommandRB command) {
			target.progressActionSequence();
		}
	}
	
	/**
	 * Cancels the operation of a specified command.
	 */
	public static class KillCommand extends Action
	{
		CommandRB target;
		
		/**
		 * @param commandToKill The command which will be cancelled.
		 */
		public KillCommand(CommandRB commandToKill) {
			super(new CheckBoolean(true));
			target = commandToKill;
		}
		
		public void onFinish(CommandRB command) {
			target.cancel();
		}
	}
	
	/**
	 * Cancels the operation of whatever command is running on a specified subsystem.
	 */
	public static class KillSubsystem extends Action
	{
		SubsystemRB target;
		
		/**
		 * @param subsystemToReset The subsystem to cancel the current command on.
		 */
		public KillSubsystem(SubsystemRB subsystemToReset) {
			super(new CheckBoolean(true));
			target = subsystemToReset;
		}
		
		public void onFinish(CommandRB command) {
			target.getCurrentCommand().cancel();
		}
	}
	
	/**
	 * Cancels the operation of all commands that have a dedicated subsystem.
	 */
	/*TODO public static class KillAllSubsystems extends Action
	{
		public KillAllSubsystems() {
			super(new CheckBoolean(true));
		}
		
		public void onFinish(CommandRB command) {
			for(int a = 0; a < CommandBase.subsystemList.size(); a++) 
				CommandBase.subsystemList.get(a).getCurrentCommand().cancel();
		}
	}*/
}
