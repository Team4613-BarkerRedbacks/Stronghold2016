package redbacks.arachne.core.references;

import redbacks.arachne.lib.actions.*;
import redbacks.arachne.lib.checks.*;
import redbacks.arachne.lib.checks.can.*;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.navx.*;
import redbacks.arachne.lib.subsystems.SubsystemRB;
import redbacks.robot.arm.ArmPosition;
import redbacks.robot.drive.*;
import redbacks.robot.intake.ActionIntakeWhileAtBase;
import redbacks.robot.launcher.vision.*;
import redbacks.robot.sensors.ActionReadSensors;
import redbacks.robot.turret.*;
import static redbacks.arachne.core.CommandBase.*;
import static redbacks.robot.arm.ArmPosition.*;

/**
 * Holds all commands.
 * 
 * @author Sean Zammit
 */
public class CommandList
{
	private static SubsystemRB subsystemToUse;
	
	//Commands which require no subsystem.
	public static CommandHolder
		doNothing = newCom(new ActionDoNothing()),
		resetYaw = newCom(new ActionResetYaw()),
		invertYaw = newCom(new ActionSetYaw(180)),
		resetDriveEncoders = newCom(
				new ActionResetCANEncoder(sensors.driveLEncoder), 
				new ActionResetCANEncoder(sensors.driveREncoder)
		),
		resetTiltEncoder = newCom(new ActionResetCANEncoder(sensors.turretTiltEncoder)),
					
		driverShift = newCom(new ActionShift()),
		driverShiftFast = newCom(new ActionShift(true)),
		driverShiftSlow = newCom(new ActionShift(false)),
									
		driverSwitchControl = newCom(new ActionSwitchControl()),
		driverSwitchControlToD = newCom(new ActionSwitchControlTo(true)),
		driverSwitchControlToO = newCom(new ActionSwitchControlTo(false)),
					
		armToMax = newCom(TOP.action),
		armToIntake = newCom(CENTRE.action),
		armToBase = newCom(BASE.action),

		armNotTop = newCom(NOT_TOP.action),
		armNotCentre = newCom(NOT_CENTRE.action),
		armNotBase = newCom(NOT_BASE.action),
		
		lightsOn = newCom(new ActionRelay(sensors.lights, true)),
		lightsOff = newCom(new ActionRelay(sensors.lights, false)),
		lightsSwitch = newCom(new ActionRelay(sensors.lights)),
		
		trackerClear = newCom(new ActionTrackClear());

	//Sensors
	static SwitchSubsystem s_sensors = new SwitchSubsystem(sensors);
	public static CommandHolder sensorsRead = newCom(new ActionReadSensors());
			
	//Driver
	static SwitchSubsystem s_driver = new SwitchSubsystem(driver);
	public static CommandHolder	driverControl = newCom(new ActionDrive(new CheckNever()));

	//Vision
	static SwitchSubsystem s_vision = new SwitchSubsystem(vision);
	public static CommandHolder
		stopVision = newCom(new ActionDoNothing(new CheckTime(0.5D))),
		shootWithCorrection = newCom(new ActionTrackV4());
		
	//Launcher
	static SwitchSubsystem s_launcher = new SwitchSubsystem(launcher);
	public static CommandHolder
		launcherSpeedWheels = newCom(
				new ActionMotor.Set(launcher.polycord, -0.4D, new CheckBoolean(true)),
				new ActionMotor.RampTime(launcher.shooter, 1D, 0.5D, true),
				new ActionMotor.Disable(launcher.polycord),
				new ActionMotor.Set(launcher.shooter, 1D, new CheckTime(1D)),
				new ActionDoNothing(new CheckCANEncoderNoReset(30000, sensors.turretTiltEncoder, false))
		),
		launcherShoot = newCom(
				new ActionMotor.Set(launcher.shooter, 1D, new CheckBoolean(true)),
				new ActionMotor.Set(launcher.polycord, 0.8D, new CheckTime(1D)),
				new ActionMotor.Set(launcher.shooter, 1D, new CheckTime(1D)),
				new ActionMotor.RampTime(launcher.shooter, 0, 1D, true)
		),
		launcherShootManual = newCom(
				new ActionMotor.Set(launcher.shooter, 1D, new CheckBoolean(true)),
				new ActionMotor.Set(launcher.polycord, 0.8D, new CheckTime(1D)),
				new ActionMotor.Set(launcher.shooter, 1D, new CheckNever())
		),
		launcherShootSlow = newCom(
				new ActionMotor.RampTime(launcher.shooter, 0.5D, 0.5D, true),
				new ActionMotor.Set(launcher.polycord, 0.8D, new CheckTime(1D)),
				new ActionMotor.Set(launcher.shooter, 0.5D, new CheckNever())
		),
		launcherSlow = newCom(new ActionMotor.RampTime(launcher.shooter, 0, 1D, true)),
		launcherIntake = newCom(
				new ActionMulti(new CheckTime(1D),
						new ActionMotor.RampTime(launcher.shooter, -0.6D, 0.5D, new CheckNever()),
						new ActionMotor.Set(launcher.polycord, -0.5D, new CheckNever())
				),
				new ActionWait(5D),
				new ActionMotor.Disable(launcher.shooter),
				new ActionMotor.Disable(launcher.polycord)
		),
		launcherIntakeFast = newCom(
				new ActionMulti(new CheckTime(1D),
						new ActionMotor.RampTime(launcher.shooter, -0.8D, 0.5D, new CheckNever()),
						new ActionMotor.Set(launcher.polycord, -0.5D, new CheckNever())
				),
				new ActionWait(5D),
				new ActionMotor.Disable(launcher.shooter),
				new ActionMotor.Disable(launcher.polycord)
		),
		launcherIntakeManual = newCom(
				new ActionMulti(new CheckTime(1D),
						new ActionMotor.RampTime(launcher.shooter, -0.6D, 0.5D, new CheckNever()),
						new ActionMotor.Set(launcher.polycord, -0.5D, new CheckNever())
				),
				new ActionDoNothing()
		),
		launcherIntakeManualFast = newCom(
				new ActionMulti(new CheckTime(1D),
						new ActionMotor.RampTime(launcher.shooter, -1D, 0.5D, new CheckNever()),
						new ActionMotor.Set(launcher.polycord, -0.5D, new CheckNever())
				),
				new ActionDoNothing()
		),
		launcherHold = newCom(new ActionMotor.Set(launcher.polycord, -0.4D, new CheckNever())),
		
		launcherFixCatch = newCom(
				new ActionMulti(new CheckTime(1D),
						new ActionMotor.RampTime(launcher.shooter, -0.6D, 0.5D, new CheckNever()),
						new ActionMotor.Set(launcher.polycord, 0.4D, new CheckNever())
				),
				new ActionMotor.Set(launcher.polycord, -0.5D, new CheckNever()),
				new ActionDoNothing()
		);

	//Arm
	static SwitchSubsystem s_arm = new SwitchSubsystem(arm);
	
	//Intake
	static SwitchSubsystem s_intake = new SwitchSubsystem(intake);
	public static CommandHolder
		intakeIntakeBall = newCom(new ActionMotor.Set(intake.intake, 0.7D, new CheckNever())),
		intakeIntakeBallFast = newCom(new ActionMotor.Set(intake.intake, 1D, new CheckNever())),
		intakeSpitBall = newCom(new ActionMotor.Set(intake.intake, -1D, new CheckNever())),
		
		intakeWhileAtBase = newCom(new ActionIntakeWhileAtBase());
		
	//Turret
	static SwitchSubsystem s_turret = new SwitchSubsystem(turret);
	public static CommandHolder
		turretLManual = newCom(new ActionMotor.Set(turret.pan, -RobotMap.turretRotationSpeed, new CheckCANEncoderNoAbs(0, sensors.turretPanEncoder, false))),
		turretRManual = newCom(new ActionMotor.Set(turret.pan, RobotMap.turretRotationSpeed, new CheckCANEncoderNoAbs(18000, sensors.turretPanEncoder))),
		
		turretToTop = newCom(new ActionMotor.Set(turret.tilt, -RobotMap.turretTiltSpeed, new CheckCANEncoderNoReset(85000, sensors.turretTiltEncoder))),
		turretToBase = newCom(new ActionTurretTiltReset()),
		
		turretCenterSlow = newCom(
				new ActionSeq.Parallel(trackerClear),
				new ActionTurretTiltReset(),
				new ActionMotor.Set(turret.pan, -RobotMap.turretCentraliseSpeed, new CheckMulti.And(
						new CheckCANDI(sensors.turretMagLSwitch, true), 
						new CheckCANDI(sensors.turretMagRSwitch, true)
				)),
				new ActionSetCANEncoder(sensors.turretPanEncoder, 0),
				new ActionSetZeroed(),
				new ActionTurretMoveToPos(0)
		),
		turretCenterFast = newCom(
				new ActionSeq.Parallel(trackerClear),
				new ActionTurretTiltDownToSafety(),
				new ActionTurretFastCenter(),
				new ActionTurretTiltReset()
		),
		turretCenter = newCom(
				new ActionSeq.Parallel(trackerClear),//new ActionTurretCenter(),
				new ActionTurretMoveToPos(0)
		),
		turretToCD = newCom(
				new ActionTurretTiltReset(),
				new ActionSeq.Parallel(trackerClear),//new ActionTurretCenter(),
				new ActionTurretMoveToPos(0)
		),
		turretToInverse = newCom(
				new ActionTurretTiltReset(),
				new ActionSeq.Parallel(trackerClear),//new ActionTurretCenter(),
				new ActionTurretMoveToPos(10800)
		),
		turretToCDWithCheck = newCom(
				new ActionCDIfNotFailedVision()
		),
		turretToIntake = newCom(
				new ActionTurretTiltReset(),
				new ActionSeq.Parallel(trackerClear),//new ActionTurretCenter(),
				new ActionTurretMoveToPos(0)
		),
		
		//XXX These are the shooting positions.
		//Format is tilt, pan, arm position, then optionally, correct camera x, y, compensation multiplier x, y.
		turretToShootCorner = goToShootPos(51000, 16400, NOT_BASE),
		turretToShootEdge = goToShootPos(70000, 16100, CENTRE),
		turretToShootFront = goToShootPos(70000, 11000, CENTRE, 72, -90, 17.2D, -232.5D),
		turretToShootMid = goToShootPos(57000, 16250, NOT_BASE),
		turretToShootSecretPassage = goToShootPos(85000, 6200, CENTRE),
		
		turretToShootLowBar = goToShootPos(60000, 13275, CENTRE),//Don't use.
		turretToShootD2 = goToShootPos(42000, 13500, CENTRE, 11, 20, 15D, -380D),//Don't use.
		turretToShootD3 = goToShootPos(50000, 12400, CENTRE, 53, -5, 13.5D, -250D),
		turretToShootD4 = goToShootPos(50000, 10900, CENTRE, 60, -25, 13D, -250D),
		turretToShootD5 = goToShootPos(52000, 10200, CENTRE, 48, -15, 15D, -380D);
	
	//Sequences
	static SwitchSubsystem s_sequencer = new SwitchSubsystem(sequencer);
	public static CommandHolder
		sequenceCrossingConfig = newCom(
				new ActionSeq.Parallel(turretToInverse),
				new ActionSeq.Parallel(armToIntake)
		),
		sequenceLowConfig = newCom(
				new ActionSeq.Parallel(turretToCD),
				new ActionSeq.Parallel(armToBase)
		),
		sequenceIntake = newCom(
				new ActionSeq.Parallel(armToBase),
				new ActionSeq.Parallel(intakeIntakeBall),
				new ActionSeq.Parallel(turretToCD),
				new ActionSeq.Parallel(launcherIntake)
		),
		/*sequenceIntakeToShooter = newCom(
				new ActionSeq.Parallel(intake, new ActionMotor.Set(intake.intake, -0.5D, new CheckTime(0.1D)), new ActionMotor.Disable(intake.intake)),//new ActionWait(0.5D)),
				new ActionSeq.Parallel(armToIntake),
				new ActionSeq.Parallel(launcherIntakeFast),
				new ActionWait(1D),
				new ActionSeq.Parallel(intakeIntakeBallFast),
				new ActionWait(1.5D),
				new ActionSeq.Parallel(turret, new ActionWait(0.5D)),
				new ActionSeq.Parallel(launcher, new ActionWait(0.5D)),
				new ActionSeq.Parallel(intake, new ActionWait(0.5D))
		),
		sequenceIntakeToShooterHeld = newCom(
				new ActionSeq.Parallel(intake, new ActionMotor.Set(intake.intake, -0.5D, new CheckTime(0.1D)), new ActionMotor.Disable(intake.intake)),//new ActionWait(0.5D)),
				new ActionSeq.Parallel(armToIntake),
				new ActionSeq.Parallel(launcherIntakeFast),
				new ActionWait(1.5D),
				new ActionSeq.Parallel(intakeIntakeBallFast),
				new ActionDoNothing()
		),*/
		sequenceStopBallHandling = newCom(
				new ActionSeq.Parallel(turret, new ActionWait(0.1D)),
				new ActionSeq.Parallel(launcher, new ActionWait(0.1D)),
				new ActionSeq.Parallel(intake, new ActionWait(0.1D))
		),
		sequenceShootFromStop = newCom(
				new ActionSeq.Parallel(launcherSpeedWheels),
				new ActionWait(1.5D),
				new ActionSeq.Parallel(launcherShoot)
		);

	/**
	 * Creates a new command from a list of actions.
	 * 
	 * @param actions The list of actions to be run in the command.
	 * @return The holder of the command. Use .c() to access the command.
	 */
	private static CommandHolder newCom(Action... actions) {
		return new CommandHolder(subsystemToUse, actions);
	}
	
	/**
	 * Creates a new command from a list of actions and a subsystem.
	 * 
	 * @param actions The list of actions to be run in the command.
	 * @param subsystem The subsystem the command requires.
	 * @return The holder of the command. Use .c() to access the command.
	 */
	public static CommandHolder newCom(SubsystemRB subsystem, Action... actions) {
		return new CommandHolder(subsystem, actions);
	}
	
	public static class SwitchSubsystem
	{
		public SwitchSubsystem(SubsystemRB subsystem) {
			subsystemToUse = subsystem;
		}
	}
	
	public static CommandHolder goToShootPos(int height, int rotation, ArmPosition armPosition) {
		return newCom(
				armPosition.action,
				new ActionTurretTiltDownToSafety(),
				new ActionMulti(
						new ActionTurretTiltToSafety(height, rotation),
						new ActionTurretMoveToPos(rotation)
				),
				new ActionTurretTiltToPos(height),
				new ActionTrackClear());
	}
	
	public static CommandHolder goToShootPos(int height, int rotation, ArmPosition armPosition, int visionX, int visionY, double mulX, double mulY) {
		return newCom(
				armPosition.action,
				new ActionTurretTiltDownToSafety(),
				new ActionMulti(
						new ActionTurretTiltToSafety(height, rotation),
						new ActionTurretMoveToPos(rotation)
				),
				new ActionTurretTiltToPos(height),
				new ActionTrackSetup(visionX, visionY, mulX, mulY));
	}
	
	public static CommandHolder goToShootPosYawCor(int height, int rotation, ArmPosition armPosition, int visionX, int visionY, double mulX, double mulY) {
		return newCom(
				armPosition.action,
				new ActionTurretTiltDownToSafety(),
				new ActionMulti(
						new ActionTurretTiltToSafety(height, rotation),
						new ActionTurretMoveToPosWithGyro(rotation)
				),
				new ActionTurretTiltToPos(height),
				new ActionTrackSetup(visionX, visionY, mulX, mulY));
	}
}
