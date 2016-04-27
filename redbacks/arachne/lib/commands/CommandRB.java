package redbacks.arachne.lib.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.buttons.Button;
import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.motors.MotorControllerRB;
import redbacks.arachne.lib.subsystems.SubsystemRB;

/**
 * Creates a standard setup for all commands.
 * If you really must create a command, first, talk to the head programmer. Then, if they agree, make CommandRB a superclass.
 * 
 * @author Sean Zammit
 */
public class CommandRB extends CommandBase
{
	/**	The sequence of actions. This is never changed. */
	private final ArrayList<Action> actionSeq = new ArrayList<Action>();
	
	/** The position in the list of actions that the command is up to. */
	int actionPos;
	
	/** The list of motors that this command has set the speed of. */
	public ArrayList<MotorControllerRB> motorList = new ArrayList<MotorControllerRB>();

	/** A boolean used to determine whether the command should stop running when the button to trigger it is released. */
	private boolean isWhileHeld = false;
	
	/** The button that, when released, will stop the command. It does not necessarily have to be the same button that activated it. */
	private Button button;
	
	/**
	 * @param requiredSystem The subsystem that this command will run on, or null if none are required.
	 * @param actions The list of actions that are run in sequence by the command.
	 */
	protected CommandRB(SubsystemRB requiredSystem, Action... actions) {
		super();
		if(requiredSystem != null) requires(requiredSystem);
		for(int i = 0; i < actions.length; i++) this.actionSeq.add(i, actions[i]);
	}

	protected final void initialize() {	this.onStart(); }
	
	protected final void end() {
		this.onCompletion();
		for(int i = 0; i < motorList.size(); i++) if(motorList.get(i).lastCommand == this && motorList.get(i).shouldCancel)
			motorList.get(i).disable();
		motorList.clear();
	}
	
	protected void interrupted() { this.end(); }
	
	protected final boolean isFinished() {
		return (isWhileHeld && !button.get()) || isDone();
	}

	/**
	 * Called when the command begins.
	 */
	protected void onStart() {
		actionPos = 0;
		actionSeq.get(0).deploy(this);
	}
	
	
	protected void execute() {
		Action action = readAction();
		if(action != null) action.periodic(this);
	}

	/**
	 * Called when the command finishes.
	 */
	protected void onCompletion() {}
	
	/**
	 * Called to check whether the command should end. Replaces isFinished(), so that buttons can stop commands when they are released.
	 * 
	 * @return Whether the command should end.
	 */
	public boolean isDone() {
		return actionSeq.size() <= actionPos;
	}

	/**
	 * Used to set a command to run only until a button is released.
	 * 
	 * @return This command. Reason being that it allows this method to be used in the same line as the constructor.
	 */
	public final CommandRB setCancelWhenReleased(Button button) {
		isWhileHeld = true;
		this.button = button;
		return this;
	}
	
	/**
	 * Completes and removes the current action running on the command.	
	 */
	public void progressActionSequence() {
		if(actionSeq.size() > actionPos) {
			Action action = actionSeq.get(actionPos);
			action.complete(this);
			actionPos++;
		}
	}
	
	/**
	 * Adds a sequence of actions to the end of the current sequence.
	 * 
	 * @param actions The list of actions to be added.
	 */
	public void queueActions(Action... actions) {
		for(int i = 0; i < actions.length; i++) actionSeq.add(actions[i]);
	}
	
	/**
	 * Handles the sequence of actions in the command.
	 * 
	 * @return The current action. Moves on to the next if the action is complete.
	 */
	private final Action readAction() {
		if(actionSeq.size() > actionPos) {
			Action action = actionSeq.get(actionPos);
			
			if(action.check instanceof CheckBoolean) {
				if(((CheckBoolean) action.check).type == true) {
					action.deploy(this);
					action.periodic(this);
					action.complete(this);
					actionPos++;
					if(actionSeq.size() > actionPos) actionSeq.get(actionPos).deploy(this);
				}
				else if(actionSeq.size() > actionPos + 1) {
					action.complete(this);
					actionPos++;
					actionSeq.get(actionPos).deploy(this);
				}
			}
			else if(action.isComplete(this)) {
				action.complete(this);
				actionPos++;
				if(actionSeq.size() > actionPos) actionSeq.get(actionPos).deploy(this);
			}
			if(actionSeq.size() > actionPos) return actionSeq.get(actionPos);
		}
		return null;
	}
}
