package redbacks.robot.drive;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.Main;
import redbacks.arachne.core.OI;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * This controls everything necessary for the drive function of the robot.
 * It is the default action run on the driver subsystem.
 * 
 * @author Sean Zammit
 */
public class ActionDrive extends Action
{
	double minR = 0.4D, difR = 0.5D;
	
	/**
	 * @param check The condition that will finish the action.
	 */
	public ActionDrive(Check check) {
		super(check);
	}

	public void runAction(CommandRB command) {
		if(!Main.isAuto) arcadeDrive(-OI.axis_d_RY.get() * 0.95D, OI.axis_d_LX.get());
		else if(CommandBase.driver.isAutoController) tankDrive(-CommandBase.driver.left.get(), -CommandBase.driver.right.get());
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
	
	/**
	 * Pseudo-arcade drive method of control. Calculate adjustments to the inputs in this method.
	 * 
	 * @param sp The parallel speed and direction of the robot.
	 * @param rotation The speed at which the robot should rotate.
	 */
	public void arcadeDrive(double sp, double rotation) {
		double mod = minR + difR * Math.pow(1 - Math.abs(sp), 2);
		double r = Math.pow(rotation, 3) * mod;
		CommandBase.driver.drivetrain.tankDrive(- sp - r, - sp + r);
	}
}
