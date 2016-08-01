package redbacks.arachne.core;

import java.util.ArrayList;

import redbacks.arachne.core.references.CommandList;
import redbacks.arachne.lib.subsystems.SubsystemRB;
import redbacks.robot.subsystems.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * CommandBase creates and stores each subsystem and the instance of the operator interface, or OI.
 * 
 * @author Sean Zammit
 */
public abstract class CommandBase extends Command
{
	/** The instance of the operator interface. This is used to map inputs to functions. */
	public static OI oi;

	/**
	 * A full list of subsystems on the robot. 
	 * This is only used at this point to interrupt all subsystems, and add information from all subsystems to the dashboard.
	 */
	public static ArrayList<SubsystemRB> subsystemList = new ArrayList<SubsystemRB>();

	/** Do not remove. This is used to set up each subsystem. */
	public static int id = 0;

	//Create an instance of each subsystem here.
	public static SubsystemDriver driver = new SubsystemDriver();
	public static SubsystemLauncher launcher = new SubsystemLauncher();
	public static SubsystemIntake intake = new SubsystemIntake();
	public static SubsystemTurret turret = new SubsystemTurret();
	public static SubsystemArm arm = new SubsystemArm();
	public static SubsystemRB sequencer = new SubsystemRB();
	public static SubsystemRB vision = new SubsystemRB();
	
	/** Sensor subsystem. **/
	public static SubsystemSensors sensors = new SubsystemSensors();
	
	/**
	 * Called when the robot is first initialised.
	 * In this case, it sets up the subsystems and the instance of the OI.
	 */
	public static void init() {
		driver.setDefCommand(CommandList.driverControl.c());
		sensors.setDefCommand(CommandList.sensorsRead.c());
		//intake.setDefCommand(CommandList.intakeWhileAtBase.c());
		
		//Don't move or change this. EVER. Without it, no operator interface, no mapping, therefore "robot no move."
		oi = new OI();

		//Calls the method in SubsystemSensors that starts all sensors. Do not remove.
		sensors.initSensors();

		SmartDashboard.putNumber("Number of subsystems", id);
	}

	public CommandBase(String name) {
		super(name);
	}

	public CommandBase() {
		super();
	}
}
