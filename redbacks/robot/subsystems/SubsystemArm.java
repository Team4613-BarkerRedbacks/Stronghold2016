package redbacks.robot.subsystems;

import redbacks.arachne.lib.solenoids.SolenoidRB;
import redbacks.arachne.lib.subsystems.SubsystemRB;
import static redbacks.arachne.core.references.RobotMap.*;

/**
 * @author Sean Zammit
 */
public class SubsystemArm extends SubsystemRB
{
	//Set up motors and solenoids here. Make sure to use the RobotMap.
	public SolenoidRB arm = new SolenoidRB(armSolID);
	public SolenoidRB armSt1 = new SolenoidRB(armIntakePosSolID);
	
	public SubsystemArm() {
		super();
	}

	public void onInit() {
		//Perform anything that needs to be done before the subsystem is used here. 
	}
}
