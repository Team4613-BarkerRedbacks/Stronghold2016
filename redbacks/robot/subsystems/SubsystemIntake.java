package redbacks.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import redbacks.arachne.lib.motors.MotorControllerRB;
import redbacks.arachne.lib.subsystems.SubsystemRB;
import static redbacks.arachne.core.references.RobotMap.*;

/**
 * @author Sean Zammit
 */
public class SubsystemIntake extends SubsystemRB
{
	//Set up motors and solenoids here. Make sure to use the RobotMap.
	public MotorControllerRB intake = new MotorControllerRB(new CANTalon(intakeMotorID));
	
	public SubsystemIntake() {
		super();
	}

	public void onInit() {
		//Perform anything that needs to be done before the subsystem is used here.
		
	}
}
