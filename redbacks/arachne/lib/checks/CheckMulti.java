package redbacks.arachne.lib.checks;

import java.util.ArrayList;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Holds all the checks that have multiple conditions.
 * Also functions as their superclass.
 * 
 * @author Sean Zammit
 */
public abstract class CheckMulti extends Check
{
	/** The list of subchecks for the main check. */
	public Check[] checklist;
	
	/**
	 * @param checks The list of subchecks for the main check.
	 */
	private CheckMulti(Check... checks) {
		if(checks.length == 0) checklist = new Check[] {
			new Check() {
				public boolean isTrue(CommandRB command) {
					return false;
				}
			}
		};
		else checklist = checks;
	}
	
	public void begin(CommandRB command, Action action) {
		for(Check check : checklist) check.begin(command, action);
	}
	
	public void run(CommandRB command, Action action) {
		for(Check check : checklist) check.run(command, action);
	}
	
	public void done(CommandRB command, Action action) {
		for(Check check : checklist) check.done(command, action);
	}
	
	/**
	 * Requires all subchecks to be true.
	 */
	public static class And extends CheckMulti
	{
		public And(Check... checks) {
			super(checks);
		}
		
		public boolean isTrue(CommandRB command) {
			for(int i = 0; i < checklist.length; i++) {
				if(!checklist[i].isTrue(command)) return false; 
			}
			return true; 
		}
	}

	/**
	 * Requires any subcheck to be true.
	 */
	public static class Or extends CheckMulti
	{
		public Or(Check... checks) {
			super(checks);
		}
		
		public boolean isTrue(CommandRB command) {
			for(int i = 0; i < checklist.length; i++) {
				if(checklist[i].isTrue(command)) return true; 
			}
			return false; 
		}
	}

	/**
	 * Requires one and only one subcheck to be true.
	 */
	public static class Xor extends CheckMulti
	{
		public Xor(Check... checks) {
			super(checks);
		}
		
		public boolean isTrue(CommandRB command) {
			for(int i = 0; i < checklist.length; i++) {
				if(checklist[i].isTrue(command)) {
					for(int j = 0; j < checklist.length; j++) {
						if(i != j && checklist[i].isTrue(command)) return false;
					}
					return true;
				}
			}
			return false; 
		}
	}

	/**
	 * Requires each subcheck to have once returned true.
	 */
	public static class Once extends CheckMulti
	{
		public ArrayList<Check> workingList = new ArrayList<Check>();
		
		public Once(Check... checks) {
			super(checks);
			for(int i = 0; i < checklist.length; i++) workingList.add(checklist[i]);
		}
		
		public void done(CommandRB command, Action action) {
			workingList.clear();
			for(int i = 0; i < checklist.length; i++) workingList.add(checklist[i]);
		}
		
		public boolean isTrue(CommandRB command) {
			for(int i = workingList.size() - 1; i >= 0; i--) {
				if(workingList.get(i).isTrue(command)) workingList.remove(i);
			}
			return workingList.size() <= 0; 
		}
	}

	/**
	 * Requires each subcheck to have once returned true in sequence.
	 */
	public static class Seq extends CheckMulti
	{
		public int pos = 0;
		
		public Seq(Check... checks) {
			super(checks);
		}
		
		public void done(CommandRB command, Action action) {
			pos = 0;
		}
		
		public boolean isTrue(CommandRB command) {
			if(checklist[pos].isTrue(command)) pos++;
			return pos >= checklist.length; 
		}
	}
}
