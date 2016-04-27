package redbacks.arachne.lib.subsystems;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.ActionDoNothing;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The main subsystem class. Provides a base for all subsystems to be built on.
 * Do not create instances of this class.
 * 
 * @author Sean Zammit
 */
public class SubsystemRB extends Subsystem
{
	/** The unique ID assigned to this subsystem. */
	public int systemID;
	
	public SubsystemRB() {
		super();
		systemID = CommandBase.id;
		CommandBase.subsystemList.add(this);
		CommandBase.id++; 
		setDefCommand(new CommandHolder(this, new ActionDoNothing()).c());
	}

	public final void initDefaultCommand() {
		onInit();
	}

	/**
	 * A blank method that can be overwritten by individual subsystems.
	 * Called once on initialisation.
	 */
	public void onInit() {
	}

	/**
	 * Sets the default command. This method exists to force the command to be an instance of CommandRB.
	 * 
	 * @param command The command that should be run on this subsystem when no other commands are running.
	 */
	public void setDefCommand(CommandRB command) {
		setDefaultCommand(command);
	}
}
