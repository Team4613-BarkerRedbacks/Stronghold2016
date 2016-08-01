package redbacks.arachne.core;

import redbacks.arachne.core.references.Autonomous;
import redbacks.arachne.lib.actions.ActionMotor;
import redbacks.arachne.lib.checks.can.CheckCANDI;
import redbacks.arachne.lib.checks.can.CheckCANEncoderNoReset;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.input.CANDITrigger;
import redbacks.arachne.lib.input.CANEncoderTrigger;
import redbacks.arachne.lib.input.JoystickAxis;
import redbacks.arachne.lib.input.JoystickAxisButton;
import redbacks.arachne.lib.input.JoystickPOVButton;
import redbacks.arachne.lib.input.NavXTrigger;
import redbacks.arachne.lib.navx.CheckNavX;
import redbacks.arachne.lib.navx.NavXReadingType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import static redbacks.arachne.core.CommandBase.*;
import static redbacks.arachne.core.references.CommandList.*;

/**
 * OI is used to map inputs to functions. An instance of OI can be found in CommandBase. DO NOT create one anywhere else.
 *
 * @author Sean Zammit
 */
@SuppressWarnings("unused")
public class OI
{
	/**
	 * Inputs are mapped to functions inside this constructor.
	 */
	public OI() {
		Autonomous.initAutonomous();
		
		//Set what commands will run when buttons are pressed/held/released here.
		//Driver
		whenPressed(d_RStick, driverShift.c());
		whenPressed(d_X, lightsOn.c());
		whenPressed(d_Y, lightsOff.c());
		
		//whenPressed(d_B, sequenceShootFromStop.c());
		whenPressed(d_B, shootWithCorrection.c());
		whenReleased(d_B, launcherSlow.c(), turretToCDWithCheck.c(), stopVision.c());
		
		whenPressed(d_LT, shootWithCorrection.c());
		whenReleased(d_LT, launcherSlow.c(), turretToCDWithCheck.c(), stopVision.c());
		
		whenHeld(d_A, launcherShootSlow.c());

		whenPressed(d_LB, armToBase.c());
		whenPressed(d_RB, armToIntake.c());
		whenPressed(d_RT, armToMax.c());
		
		whenPressed(d_Start, resetYaw.c());
		whenHeld(d_Back, sequenceIntake.c());
		whenReleased(d_Back, sequenceStopBallHandling.c());

		//Operator
		whenPressed(o_ShootTL1, turretToShootCorner.c());
		whenPressed(o_ShootTL2, turretToShootMid.c());
		whenPressed(o_ShootTL3, turretToShootEdge.c());
		whenPressed(o_ShootTF, turretToShootFront.c());
		whenPressed(o_ShootTR, turretToShootSecretPassage.c());
		
		whenPressed(o_ShootD1, turretToShootLowBar.c()); //TODO
		whenPressed(o_ShootD2, turretToShootD2.c());
		whenPressed(o_ShootD3, turretToShootD3.c());
		whenPressed(o_ShootD4, turretToShootD4.c());
		whenPressed(o_ShootD5, turretToShootD5.c());
		
		whenPressed(o_LauncherFix, launcherFixCatch.c());
		
		toggleWhenPressed(o_LauncherIn, launcherIntakeManual.c());
		toggleWhenPressed(o_LauncherInFast, launcherIntakeManualFast.c());
		whenHeld(o_LauncherIn, intakeIntakeBall.c());
		whenHeld(o_LauncherInFast, intakeIntakeBallFast.c());

		whenPressed(o_TurretZero, turretCenterFast.c());
		whenPressed(o_TurretCenter, turretToCD.c());

		whenHeld(o_TurretUp, turretToTop.c());
		whenHeld(o_TurretDown, turretToBase.c());
		whenHeld(o_TurretLeft, turretLManual.c());
		whenHeld(o_TurretRight, turretRManual.c());

		//whenHeld(o_IntakeToShooter, sequenceIntakeToShooterHeld.c());
		//whenReleased(o_IntakeToShooter, sequenceStopBallHandling.c());
		
		whenPressed(o_IntakeToShooter, turretToInverse.c());
		whenHeld(o_IntakeFromGround, sequenceIntake.c());
		whenReleased(o_IntakeFromGround, sequenceStopBallHandling.c());

		whenHeld(o_IntakeDirection, intakeSpitBall.c());

		whenPressed(o_ArmTop, armToMax.c());
		whenPressed(o_ArmMid, armToIntake.c());
		whenPressed(o_ArmBase, armToBase.c());
		
		//Sensors
		pulseWhileHeld(new CANDITrigger(new CheckCANDI(sensors.turretBaseSwitch, true)), resetTiltEncoder.c());
		whenPressed(new CANEncoderTrigger(new CheckCANEncoderNoReset(30000, sensors.turretTiltEncoder)), launcherSpeedWheels.c());
	}

	//Set up joysticks and buttons here.
	private static final Joystick stick_d = new Joystick(0);
	private static final Joystick stick_o = new Joystick(1);
	private static final Joystick stick_l = new Joystick(2);
	private static final Joystick stick_r = new Joystick(3);
	
	public static final JoystickAxis
		axis_d_LX = new JoystickAxis(stick_d, 0),
		axis_d_LY = new JoystickAxis(stick_d, 1),
		axis_d_LT = new JoystickAxis(stick_d, 2),
		axis_d_RT = new JoystickAxis(stick_d, 3),
		axis_d_RX = new JoystickAxis(stick_d, 4),
		axis_d_RY = new JoystickAxis(stick_d, 5),
		
		axis_l_X = new JoystickAxis(stick_l, 0),
		axis_r_X = new JoystickAxis(stick_r, 0),
		
		axis_l_Y = new JoystickAxis(stick_l, 1),
		axis_r_Y = new JoystickAxis(stick_r, 1);
	
	public static final Button
		d_A = new JoystickButton(stick_d, 1),
		d_B = new JoystickButton(stick_d, 2),
		d_X = new JoystickButton(stick_d, 3),
		d_Y = new JoystickButton(stick_d, 4),
		d_LB = new JoystickButton(stick_d, 5),
		d_RB = new JoystickButton(stick_d, 6),
		d_Back = new JoystickButton(stick_d, 7),
		d_Start = new JoystickButton(stick_d, 8),
		d_LStick = new JoystickButton(stick_d, 9),
		d_RStick = new JoystickButton(stick_d, 10),

		d_POV_U = new JoystickPOVButton(stick_d, 0),
		d_POV_R = new JoystickPOVButton(stick_d, 90),
		d_POV_D = new JoystickPOVButton(stick_d, 180),
		d_POV_L = new JoystickPOVButton(stick_d, 270),

		d_LT = new JoystickAxisButton(axis_d_LT, false, 0.5D),
		d_RT = new JoystickAxisButton(axis_d_RT, false, 0.5D);
	
	public static final Button
		o_ShootTL1 = new JoystickButton(stick_o, 1),
		o_ShootTL2 = new JoystickButton(stick_o, 2),
		o_ShootTL3 = new JoystickButton(stick_o, 3),
		o_ShootTF = new JoystickButton(stick_o, 4),
		o_ShootTR = new JoystickButton(stick_o, 5),
		
		o_ShootD1 = new JoystickButton(stick_o, 6),
		o_ShootD2 = new JoystickButton(stick_o, 7),
		o_ShootD3 = new JoystickButton(stick_o, 8),
		o_ShootD4 = new JoystickButton(stick_o, 9),
		o_ShootD5 = new JoystickButton(stick_o, 10),
		
		o_LauncherFix = new JoystickButton(stick_o, 11),
		o_LauncherIn = new JoystickButton(stick_o, 12),
		o_LauncherInFast = new JoystickButton(stick_o, 13),
		
		o_TurretZero = new JoystickButton(stick_o, 14),
		o_TurretCenter = new JoystickButton(stick_o, 15),
		
		o_TurretUp = new JoystickButton(stick_o, 16),
		o_TurretLeft = new JoystickButton(stick_o, 17),
		o_TurretRight = new JoystickButton(stick_o, 18),
		o_TurretDown = new JoystickButton(stick_o, 19),
		
		o_IntakeToShooter = new JoystickButton(stick_o, 20),
		o_IntakeFromGround = new JoystickButton(stick_o, 21),
		
		o_IntakeDirection = new JoystickButton(stick_o, 22),
		
		o_ArmTop = new JoystickButton(stick_o, 23),
		o_ArmMid = new JoystickButton(stick_o, 24),
		o_ArmBase = new JoystickButton(stick_o, 25);

	/**
	 * Triggers the command once when the button is first pressed.
	 * 
	 * @param button The button that should trigger the command. More complicated buttons can be found in 'redbacks.lib.input'.
	 * @param commands Any commands to run. Commands should be set up in the file of the required subsystem, or CommandList should they not require a subsystem.
	 */
	private void whenPressed(Button button, CommandRB... commands) {
		for(CommandRB command : commands) button.whenPressed(command);
	}

	/**
	 * Triggers the command once when the button is first pressed. The command will automatically be cancelled if the button is released.
	 * 
	 * @param button The button that should trigger the command. More complicated buttons can be found in 'redbacks.lib.input'.
	 * @param commands Any commands to run. Commands should be set up in the file of the required subsystem, or CommandList should they not require a subsystem.
	 */
	private void whenHeld(Button button, CommandRB... commands) {
		for(CommandRB command : commands) button.whenPressed(command.setCancelWhenReleased(button));
	}

	/**
	 * Triggers the command once when the button is first released.
	 * 
	 * @param button The button that should trigger the command. More complicated buttons can be found in 'redbacks.lib.input'.
	 * @param commands Any commands to run. Commands should be set up in the file of the required subsystem, or CommandList should they not require a subsystem.
	 */
	private void whenReleased(Button button, CommandRB... commands) {
		for(CommandRB command : commands) button.whenReleased(command);
	}

	/**
	 * Cancels the command when the button is first pressed.
	 * 
	 * @param button The button that should trigger the command. More complicated buttons can be found in 'redbacks.lib.input'.
	 * @param commands Any commands to stop. Commands should be set up in the file of the required subsystem, or CommandList should they not require a subsystem.
	 */
	private void cancelWhenPressed(Button button, CommandRB... commands) {
		for(CommandRB command : commands) button.cancelWhenPressed(command);
	}

	/**
	 * Toggles the command each time the button is first pressed.
	 * 
	 * @param button The button that should trigger the command. More complicated buttons can be found in 'redbacks.lib.input'.
	 * @param commands Any commands to run. Commands should be set up in the file of the required subsystem, or CommandList should they not require a subsystem.
	 */
	private void toggleWhenPressed(Button button, CommandRB... commands) {
		for(CommandRB command : commands) button.toggleWhenPressed(command);
	}
	
	/**
	 * Triggers the command every loop while the button is pressed.
	 * 
	 * @param button The button that should trigger the command. More complicated buttons can be found in 'redbacks.lib.input'.
	 * @param commands Any commands to run. Commands should be set up in the file of the required subsystem, or CommandList should they not require a subsystem.
	 */
	private void pulseWhileHeld(Button button, CommandRB... commands) {
		for(CommandRB command : commands) button.whileHeld(command);
	}
}
