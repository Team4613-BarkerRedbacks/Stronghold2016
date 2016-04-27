package redbacks.arachne.lib.subsystems;

import static redbacks.arachne.core.references.RobotMap.*;

/**
 * @author Author
 */
public class SubsystemTemplate extends SubsystemRB
{
	//Set up motors and solenoids here. Make sure to use the RobotMap.

	public SubsystemTemplate() {
		super();
	}

	public void onInit() {
		//Perform anything that needs to be done before the subsystem is used here. 
		//Do not start sensors. Do that in SubsystemSensors.initSensors().
	}
}
