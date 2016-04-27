package redbacks.robot.drive;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * This action shifts the gears on the drivebase.
 * 
 * @author Sean Zammit
 */
public class ActionShift extends Action
{
	public int engageSpeed; //0 is auto select, 1 is true, 2 is false
	
	/**
	 * Switches the gears to the inverse of their current state.
	 */
	public ActionShift() {
		super(new CheckBoolean(true));
	}

	/**
	 * Switches the gears to the specified state.
	 * 
	 * @param engageSpeed Whether the robot should be set to fast speed.
	 */
	public ActionShift(boolean engageSpeed) {
		this();
		this.engageSpeed = engageSpeed ? 1 : 2;
	}

	public void onFinish(CommandRB command) {
		if(engageSpeed != 0) CommandBase.driver.isSpeedy = engageSpeed == 1 ? true : false;
		else CommandBase.driver.isSpeedy = !CommandBase.driver.isSpeedy;
		
		CommandBase.driver.shifter.set(!CommandBase.driver.isSpeedy);
	}
}
