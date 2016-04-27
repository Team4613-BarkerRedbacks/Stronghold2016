package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.checks.digital.CheckBoolean;

/**
 * This action should be called when the command should not perform any function.
 * It functions as a placeholder so that the command does not end.
 * 
 * @author Sean Zammit
 */
public class ActionDoNothing extends Action
{
	public ActionDoNothing() {
		this(new CheckBoolean(false));
	}
	
	public ActionDoNothing(Check check) {
		super(check);
	}
}
