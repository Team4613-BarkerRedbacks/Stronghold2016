package redbacks.robot.sensors;

import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.commands.CommandRB;
import static redbacks.arachne.lib.navx.NavX.*;
import static redbacks.arachne.core.CommandBase.*;

/**
 * The action that is constantly run by the sensor subsystem.
 * Fill it with sensor data.
 * 
 * @author Sean Zammit
 */
public class ActionReadSensors extends Action
{
	/** The number of loops on the robot */
	static int tick = 0;
	
	public double tickS = 0;

	public ActionReadSensors() {
		super(new CheckNever());
	}
	
	public void runAction(CommandRB command) {
		/*double tickTime = command.timeSinceInitialized() - tickS;
		SmartDashboard.putNumber("Tick Time", tickTime);
		System.out.println(tickTime);
		tickS = command.timeSinceInitialized();
		SmartDashboard.putNumber("Tick", tick++);*/
		
		//TODO Remove irrelevant data
		//SmartDashboard.putNumber("Shooter Speed", launcher.shooter.get());

		putNumber("Pitch", getPitch());
		putNumber("Roll", getRoll());
		putNumber("Yaw", getYaw());
		
		putNumber("Potentiometer", sensors.turretPot.get());

		putNumber("Encoder L", sensors.driveLEncoder.get());
		putNumber("Encoder R", sensors.driveREncoder.get());
		
		putNumber("Turret Pan Encoder", sensors.turretPanEncoder.get());
		putNumber("Turret Tilt Encoder", sensors.turretTiltEncoder.get());

		putBoolean("Mag Switch L", sensors.turretMagLSwitch.get());
		putBoolean("Mag Switch R", sensors.turretMagRSwitch.get());

		putBoolean("Turret Base Switch", sensors.turretBaseSwitch.get());

		putBoolean("Turret Has Zeroed", turret.isZeroed);
		
		putBoolean("Turret Is Safe", sensors.turretMagLSwitch.get() && sensors.turretMagRSwitch.get() && sensors.turretBaseSwitch.get());
		
		RobotMap.isVisionEnabled = getBoolean("Vision Enabled", RobotMap.isVisionEnabled);
		putBoolean("Vision Enabled", RobotMap.isVisionEnabled);
		
		RobotMap.usePotentiometer = getBoolean("Use turret potentiometer", RobotMap.usePotentiometer);
		putBoolean("Use turret potentiometer", RobotMap.usePotentiometer);
	}
}
