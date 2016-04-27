package redbacks.arachne.lib.actions;

import java.util.ArrayList;

import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * This is an action that enables multiple actions to be run in parallel.
 * 
 * @author Sean Zammit
 */
public class ActionMulti extends Action
{
	/** The list of actions that will be run by this command. */
	ArrayList<Action> actions = new ArrayList<Action>();
	
	/** The original list of actions, used to reset the list. */
	ArrayList<Action> originalList = new ArrayList<Action>();
	
	/**
	 * @param check The condition that will end this command regardless of the state of the individual actions it holds.
	 * The command will also finish when all of its individual actions have finished.
	 * @param actions The actions that will be run by this command.
	 */
	public ActionMulti(Check check, Action... actions) {
		super(check);
		for(int i = 0; i < actions.length; i++) this.actions.add(actions[i]);
		for(int i = 0; i < actions.length; i++) this.originalList.add(actions[i]);
	}
	
	/**
	 * Alternate constructor assuming CheckNever for the list of Actions.
	 * The command will finish when all of its individual actions have finished.
	 * 
	 * @param actions The actions that will be run by this command.
	 */
	public ActionMulti(Action... actions) {
		this(new CheckNever(), actions);
	}
	
	public void runAction(CommandRB command) {
		for(int i = actions.size() - 1; i >= 0; i--) {
			if(actions.get(i).isComplete(command)) {
				actions.get(i).complete(command);
				actions.remove(i);
			}
			else actions.get(i).periodic(command);
		}
	}	
	
	public void onStart(CommandRB command) {
		for(int i = actions.size() - 1; i >= 0; i--) actions.get(i).deploy(command);
	}
	
	public void onFinish(CommandRB command) {
		for(int i = actions.size() - 1; i >= 0; i--) actions.get(i).complete(command);
		actions.clear();
		for(int i1 = 0; i1 < originalList.size(); i1++) actions.add(originalList.get(i1));
	}

	public boolean isComplete(CommandRB command) {
		boolean areAllBooleans = true;
		for(int i = 0; i < actions.size(); i++) if(!(actions.get(i).check instanceof CheckBoolean)) areAllBooleans = false;
		return actions.size() <= 0 || areAllBooleans || super.isComplete(command);
	}
}
