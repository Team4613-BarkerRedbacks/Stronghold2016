package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.subsystems.SubsystemRB;

/**
 * This Action will run a command populated with a list of provided actions, or a pre-existing command.
 * 
 * @author Sean Zammit
 */
public class ActionSeq
{
	private ActionSeq() {}
	
	public static class Parallel extends Action {
		/** The command holder from which the command will be created. */
		public CommandHolder sequence;
		
		/**
		 * @param sequence A pre-existing command that will be triggered by this action.
		 */
		public Parallel(CommandHolder sequence) {
			super(new CheckBoolean(true));
			this.sequence = sequence;
		}
		
		/**
		 * @param requiredSystem The subsystem that this command requires, or null for none.
		 * @param actions The list of actions to be run in the command.
		 */
		public Parallel(SubsystemRB requiredSystem, Action... actions) {
			this(new CommandHolder(requiredSystem, actions));
		}
		
		/**
		 * @param actions The list of actions to be run in the command.
		 */
		public Parallel(Action... actions) {
			this(new CommandHolder(null, actions));
		}
		
		public void onFinish(CommandRB command) {
			sequence.c().start();
		}
	}
	
	public static class Sequential extends Action {
		/** The command holder from which the command will be created. */
		public final CommandHolder sequence;
		public CommandRB comToRun;
		
		/**
		 * @param sequence A pre-existing command that will be triggered by this action.
		 */
		public Sequential(CommandHolder sequence) {
			super(new CheckNever());
			this.sequence = sequence;
		}
		
		/**
		 * @param requiredSystem The subsystem that this command requires, or null for none.
		 * @param actions The list of actions to be run in the command.
		 */
		public Sequential(SubsystemRB requiredSystem, Action... actions) {
			this(new CommandHolder(requiredSystem, actions));
		}
		
		/**
		 * @param actions The list of actions to be run in the command.
		 */
		public Sequential(Action... actions) {
			this(new CommandHolder(null, actions));
		}
		
		public void onStart(CommandRB command) {
			comToRun = sequence.c();
			comToRun.start();
		}
		
		public boolean isComplete() {
			if(comToRun != null) return comToRun.isDone();
			return false;
		}
	}
}
