package redbacks.robot.drive;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.commands.CommandRB;
//import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.*;
import redbacks.arachne.lib.navx.NavX;

/**
 * This controls everything necessary for the drive function of the robot.
 * It is the default action run on the driver subsystem.
 * 
 * @author Sean Zammit
 */
public class ActionDrivePIDToPos extends Action
{
	final int enc;
	
	final double 
		mp, 
		mi, 
		md;
	
	int i, d;
	boolean there = false;
//	double startTime;
	final double cor = 0.02D;
	
	/**
	 * JAVADOC
	 */
	public ActionDrivePIDToPos(int encPos, double mp, double mi, double md, Check check) {
		super(check);
		enc = encPos;
		this.mp = mp;
		this.mi = mi;
		this.md = md;
	}
	
	public void onStart(CommandRB command) {
		CommandBase.driver.left.disable();
		CommandBase.driver.right.disable();
		CommandBase.driver.isAutoController = false;
//		startTime = command.timeSinceInitialized();
	}

	public void runAction(CommandRB command) {
//		mp = getNumber("mp", 0);
//		mi = getNumber("mi", 0);
//		md = getNumber("md", 0);
//		enc = (int) getNumber("enc", 100);
		
		double p = Math.abs(CommandBase.sensors.driveLEncoder.get() - enc);
		d = enc - d;
		if((enc > CommandBase.sensors.driveLEncoder.get()) == there) {
			i = 0;
			there = !there;
		}
		else i += p;
		
		tankDrive(
				(p * mp / 1000 + i * mi / 1000 - d * md / 1000) * (enc > CommandBase.sensors.driveLEncoder.get() ? -1 : 1), 
				(p * mp / 1000 + i * mi / 1000 - d * md / 1000) * (enc > CommandBase.sensors.driveLEncoder.get() ? -1 : 1));
				
		/*int encVal = CommandBase.sensors.driveLEncoder.get();
		
		if((enc > encVal) == there) {
			startTime = command.timeSinceInitialized();
			there = !there;
		}
		
		double speed = 
			Math.abs(encVal - enc) < 200 ? 
					Math.min((command.timeSinceInitialized() - startTime) / 5, 0.5D): 
			Math.abs(encVal - enc) < 2000 ? 0.75D : 
			1D;
					
		tankDrive(speed* (enc > encVal ? -1 : 1), speed* (enc > encVal ? -1 : 1));*/
	}
	
	/**
	 * Tank drive method of control. Calculate adjustments to the inputs in this method.
	 * 
	 * @param l Left wheel speed.
	 * @param r Right wheel speed.
	 */
	public void tankDrive(double l, double r) {
		CommandBase.driver.drivetrain.tankDrive(l + NavX.getYaw() * cor, r - NavX.getYaw() * cor);
	}
}
