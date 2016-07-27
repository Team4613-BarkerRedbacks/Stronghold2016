package redbacks.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.navx.NavX;
import redbacks.arachne.lib.sensors.CANAnalog;
import redbacks.arachne.lib.sensors.CANDigitalInput;
import redbacks.arachne.lib.sensors.CANEncoder;
import redbacks.arachne.lib.subsystems.SubsystemRB;
import static redbacks.arachne.core.CommandBase.*;

/**
 * The sensor subsystem. Only put sensors here.
 * 
 * @author Sean Zammit
 */
public class SubsystemSensors extends SubsystemRB
{
	//Create sensors here. Make sure to use the RobotMap.
	//Drive
	public CANEncoder driveLEncoder = new CANEncoder(driver.left.controller);
	public CANEncoder driveREncoder = new CANEncoder(driver.right.controller);
		
	//Turret
	public CANDigitalInput turretMagLSwitch = new CANDigitalInput(launcher.shooterL, true);
	public CANDigitalInput turretMagRSwitch = new CANDigitalInput(launcher.shooterL, false);
	public CANDigitalInput turretBaseSwitch = new CANDigitalInput(launcher.shooterR, true);
	public CANEncoder turretPanEncoder = new CANEncoder(turret.pan);
	public CANEncoder turretTiltEncoder = new CANEncoder(turret.tilt);
	public CANAnalog turretPot = new CANAnalog(intake.intakeRear);//new CANTalon(8));
	
	public Relay lights = new Relay(RobotMap.lightsRelayID, Direction.kForward);
	
	public SubsystemSensors() {
		super();
	}

	/**
	 * Called by CommandBase on initialisation. Reset/set up all sensors here.
	 */
	public final void initSensors() {
		NavX.reset();
	}

	//Set up functions using the sensors here. For example, if sensors should be grouped because they fulfill the same function.
}