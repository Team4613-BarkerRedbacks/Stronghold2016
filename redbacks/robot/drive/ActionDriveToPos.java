package redbacks.robot.drive;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.math.Vector;
import redbacks.arachne.lib.navx.NavX;

/**
 * This controls everything necessary for the drive function of the robot.
 * It is the default action run on the driver subsystem.
 * 
 * @author Sean Zammit
 */
public class ActionDriveToPos extends Action
{
	double sp;
	final double cor = 0.02D;
	
	Vector currentVector, desiredVector;
	double lastEncoderPos;
	
	/**
	 * JAVADOC
	 * @param check The condition that will finish the action.
	 */
	public ActionDriveToPos(Check check, double speed, Vector pos) {
		super(check);
		sp = speed;
		desiredVector = pos;
	}
	
	public void onStart(CommandRB command) {
		lastEncoderPos = CommandBase.sensors.driveREncoder.get();
		currentVector = new Vector(0, 0);
	}

	public void runAction(CommandRB command) {
		double angle = NavX.getYaw();
		double distance = Math.abs(CommandBase.sensors.driveREncoder.get() - lastEncoderPos);
		lastEncoderPos = CommandBase.sensors.driveREncoder.get();
		currentVector.setVector(currentVector.addVector(new Vector(angle, distance)));
		
		Vector requiredVector = new Vector(desiredVector.getHDistance() - currentVector.getHDistance(), desiredVector.getVDistance() - currentVector.getVDistance(), true);
		
		if(requiredVector.distance > 100) tankDrive(-sp - requiredVector.angle * cor, -sp + requiredVector.angle * cor);
		else tankDrive(-sp + angle * cor, -sp - angle * cor);
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
