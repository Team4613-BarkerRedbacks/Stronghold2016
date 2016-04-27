package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * This action should be called when the command should not perform any function.
 * It functions as a placeholder so that the command does not end.
 * 
 * @author Sean Zammit
 */
public class ActionPrint extends Action
{
	String stringToPrint;
	
	public ActionPrint(String string) {
		super(new CheckBoolean(true));
		stringToPrint = string;
	}
	
	protected void onFinish(CommandRB command) {
		System.out.println(stringToPrint);
	}
	
	public boolean isComplete(CommandRB command) {
		return true;
	}
}
