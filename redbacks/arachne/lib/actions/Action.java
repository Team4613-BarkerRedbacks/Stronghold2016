package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * The main action class. Provides a base for all actions to be built on.
 * Do not create instances of this class.
 * 
 * @author Sean Zammit
 */
public class Action
{
	/** The check associated with the action that determines when the action is complete */
	public final Check check;
	
	/**
	 * @param check The check associated with the action that determines when the action is complete
	 */
	protected Action(Check check) {
		this.check = check;
	}
	
	/**
	 * Checks whether the action is complete, and the command can move on to the next action.
	 * This method passes the check along to another method (isComplete) which can be overwritten. Non-overwritable code goes here.
	 * 
	 * @param command The command in which this action is being run.
	 * @return Whether the action should stop running.
	 */
	public final boolean check(CommandRB command) {
		return isComplete(command);
	}
	
	/**
	 * Runs every loop. This method is used to perform the action.
	 * This method also runs another method (runAction) which can be overwritten. Non-overwritable code goes here.
	 * 
	 * @param command The command in which this action is being run.
	 */
	public final void periodic(CommandRB command) {
		check.run(command, this);
		runAction(command);
	}
	
	/**
	 * Runs when the action first starts. This method is used to handle anything that needs to be done before the command runs.
	 * This method also runs another method (onStart) which can be overwritten. Non-overwritable code goes here.
	 * 
	 * @param command The command in which this action is being run.
	 */
	public final void deploy(CommandRB command) {
		check.begin(command, this);
		onStart(command);
	}

	/**
	 * Runs when the action ends. This method is used to handle anything that needs to be done when the command finishes.
	 * This method also runs another method (onFinish) which can be overwritten. Non-overwritable code goes here.
	 * 
	 * @param command The command in which this action is being run.
	 */
	public final void complete(CommandRB command) {
		check.done(command, this);
		onFinish(command);
	}

	/**
	 * Checks whether the action is complete, and the command can move on to the next action.
	 * This method can be individually set by each action. Do not call it. Ever.
	 * 
	 * @param command The command in which this action is being run.
	 * @return Whether the action should stop running.
	 */
	public boolean isComplete(CommandRB command) {
		return check.isTrue(command);
	}
	
	/**
	 * Runs every loop. This method is used to perform the action.
	 * This method can be individually set by each action. Do not call it. Ever.
	 * 
	 * @param command The command in which this action is being run.
	 */
	protected void runAction(CommandRB command) {}	
	
	/**
	 * Runs when the action first starts. This method is used to handle anything that needs to be done before the command runs.
	 * This method can be individually set by each action. Do not call it. Ever.
	 * 
	 * @param command The command in which this action is being run.
	 */
	protected void onStart(CommandRB command) {}
	
	/**
	 * Runs when the action ends. This method is used to handle anything that needs to be done when the command finishes.
	 * This method can be individually set by each action. Do not call it. Ever.
	 * 
	 * @param command The command in which this action is being run.
	 */
	protected void onFinish(CommandRB command) {}
}
