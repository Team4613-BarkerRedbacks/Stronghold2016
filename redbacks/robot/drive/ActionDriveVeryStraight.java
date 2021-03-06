package redbacks.robot.drive;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.navx.NavX;

/**
 * @author Sean Zammit
 */
public class ActionDriveVeryStraight extends Action
{
	double sp;
	final double cor = 0.05D;
	
	/**
	 * @param check The condition that will finish the action.
	 */
	public ActionDriveVeryStraight(Check check, double speed) {
		super(check);
		sp = speed;
	}

	public void runAction(CommandRB command) {
		tankDrive(-sp + NavX.getYaw() * cor, -sp - NavX.getYaw() * cor);
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
