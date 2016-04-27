package redbacks.arachne.core;

import redbacks.arachne.core.references.Autonomous;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as described in the IterativeRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource directory.
 */
public class Main extends IterativeRobot
{
	/** This is used to provide public access to whether the robot is in autonomous or teleoperated mode. */
	public static boolean isAuto;

	/**
	 * This function is run when the robot is first started up and should be used for any initialization code.
	 */
	public void robotInit() {
		CommandBase.init();
	}

	public void autonomousInit() {
		isAuto = true;
		
		//This sets the command used to begin the autonomous sequence
		int av = (int) SmartDashboard.getNumber("Autonomous Version", 3);//REVERT Auto
		SmartDashboard.putNumber("Autonomous Version", av);

		//This starts the autonomous sequence.
		Autonomous.autonomous[av].start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		isAuto = false;
	}

	/**
	 * This function is called periodically during operator control.
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
