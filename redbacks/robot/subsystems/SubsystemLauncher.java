package redbacks.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import redbacks.arachne.lib.motors.MotorControllerRB;
import redbacks.arachne.lib.motors.MultiMotorControllerRB;
import redbacks.arachne.lib.subsystems.SubsystemRB;
import static redbacks.arachne.core.references.RobotMap.*;

/**
 * @author Sean Zammit
 */
public class SubsystemLauncher extends SubsystemRB
{
	//Set up motors and solenoids here. Make sure to use the RobotMap.
	MotorControllerRB shooterL = new MotorControllerRB(new CANTalon(launcherShootLMotorID));
	MotorControllerRB shooterR = new MotorControllerRB(new CANTalon(launcherShootRMotorID));
	
	public MultiMotorControllerRB shooter = new MultiMotorControllerRB(shooterL.controller, shooterR.controller);
	
	public MotorControllerRB polycord = new MotorControllerRB(new CANTalon(launcherPolycordMotorID));
	
	public SubsystemLauncher() {
		super();
		/*shooterSlave.controller.setControlMode(TalonControlMode.Follower.value);
		shooterSlave.controller.set(shooter.controller.getDeviceID());*/
	}

	public void onInit() {
		//Perform anything that needs to be done before the subsystem is used here.
	}
}
