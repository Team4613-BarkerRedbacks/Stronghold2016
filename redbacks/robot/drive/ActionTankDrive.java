package redbacks.robot.drive;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * This controls everything necessary for the drive function of the robot.
 * It is the default action run on the driver subsystem.
 * 
 * @author Sean Zammit
 */
public class ActionTankDrive extends Action
{
	double l, r;
	double lEnc, rEnc;
	
	/**
	 * @param check The condition that will finish the action.
	 */
	public ActionTankDrive(Check check, double lSpeed, double rSpeed) {
		super(check);
		l = lSpeed;
		r = rSpeed;
	}

	public void runAction(CommandRB command) {
		tankDrive(-l, -r);
	}
	
	public void onStart(CommandRB command) {
		CommandBase.driver.left.disable();
		CommandBase.driver.right.disable();
		CommandBase.driver.isAutoController = false;
	}
	
	/**
	 * Tank drive method of control. Calculate adjustments to the inputs in this method.
	 * 
	 * @param l Left wheel speed.
	 * @param r Right wheel speed.
	 */
	public void tankDrive(double l, double r) {
		CommandBase.driver.drivetrain.tankDrive(l, r);
	}
}
