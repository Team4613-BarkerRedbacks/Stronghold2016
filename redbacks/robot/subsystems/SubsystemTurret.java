package redbacks.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import redbacks.arachne.lib.motors.MotorControllerRB;
import redbacks.arachne.lib.subsystems.SubsystemRB;
import static redbacks.arachne.core.references.RobotMap.*;

/**
 * @author Sean Zammit
 */
public class SubsystemTurret extends SubsystemRB
{
	//Set up motors and solenoids here. Make sure to use the RobotMap.
	public MotorControllerRB pan = new MotorControllerRB(new CANTalon(turretPanMotorID));
	public MotorControllerRB tilt = new MotorControllerRB(new CANTalon(turretTiltMotorID));
	
	public boolean isZeroed = false;
	
	public boolean shouldTrack = false;
	public int xPos = 0, yPos = 0;
	public double xMul = 0, yMul = 0;
	
	public SubsystemTurret() {
		super();
	}

	public void onInit() {
		//Perform anything that needs to be done before the subsystem is used here.
	}
}
