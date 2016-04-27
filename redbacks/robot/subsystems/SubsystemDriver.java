package redbacks.robot.subsystems;

import redbacks.arachne.lib.motors.DriveControllerRB;
import redbacks.arachne.lib.subsystems.SubsystemRB;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import static redbacks.arachne.core.references.RobotMap.*;

/**
 * @author Sean Zammit
 */
public class SubsystemDriver extends SubsystemRB
{
	public boolean 
		isDriverControl = true, 
		isSpeedy = true, 
		isAutoController = true;

	//Set up motors and solenoids here. Make sure to use the RobotMap.
	private CANTalon l = new CANTalon(driveMotorLID);
	private CANTalon r = new CANTalon(driveMotorRID);

	public DriveControllerRB left = new DriveControllerRB(l);
	public DriveControllerRB right = new DriveControllerRB(r);
	
	protected CANTalon lSlave1 = new CANTalon(driveMotorLID + 2);
	protected CANTalon rSlave1 = new CANTalon(driveMotorRID + 2);

	protected CANTalon lSlave2 = new CANTalon(driveMotorLID + 4);
	protected CANTalon rSlave2 = new CANTalon(driveMotorRID + 4);

	public RobotDrive drivetrain = new RobotDrive(l, r);
	
	public Solenoid shifter = new Solenoid(shifterSolID);
	
	public SubsystemDriver() {
		super();
		//Left side
		lSlave1.setControlMode(TalonControlMode.Follower.value);
		lSlave2.setControlMode(TalonControlMode.Follower.value);
		
		lSlave1.set(l.getDeviceID());
		lSlave2.set(l.getDeviceID());
		
		//Right side
		rSlave1.setControlMode(TalonControlMode.Follower.value);
		rSlave2.setControlMode(TalonControlMode.Follower.value);
		
		rSlave1.set(r.getDeviceID());
		rSlave2.set(r.getDeviceID());
	}

	public void onInit() {
		//Perform anything that needs to be done before the subsystem is used here. 
	}
}
