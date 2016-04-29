package redbacks.arachne.core.references;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.actions.ActionDoNothing;
import redbacks.arachne.lib.actions.ActionMotor;
import redbacks.arachne.lib.actions.ActionMulti;
import redbacks.arachne.lib.actions.ActionRelay;
import redbacks.arachne.lib.actions.ActionResetCANEncoder;
import redbacks.arachne.lib.actions.ActionSeq;
import redbacks.arachne.lib.actions.ActionSetCANEncoder;
import redbacks.arachne.lib.actions.ActionWait;
import redbacks.arachne.lib.checks.CheckMulti;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.checks.CheckTime;
import redbacks.arachne.lib.checks.can.CheckCANDI;
import redbacks.arachne.lib.checks.can.CheckCANEncoderNoReset;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.navx.ActionResetYaw;
import redbacks.arachne.lib.subsystems.SubsystemRB;
import redbacks.robot.arm.ArmPosition;
import redbacks.robot.drive.ActionDrive;
import redbacks.robot.drive.ActionShift;
import redbacks.robot.drive.ActionSwitchControl;
import redbacks.robot.drive.ActionSwitchControlTo;
import redbacks.robot.intake.ActionIntakeWhileAtBase;
import redbacks.robot.launcher.vision.ActionTrackClear;
import redbacks.robot.launcher.vision.ActionTrackSetup;
import redbacks.robot.launcher.vision.ActionTrackV3;
import redbacks.robot.sensors.ActionReadSensors;
import redbacks.robot.turret.ActionSetZeroed;
import redbacks.robot.turret.ActionTurretCenter;
import redbacks.robot.turret.ActionTurretFastCenter;
import redbacks.robot.turret.ActionTurretFastCenterAuto;
import redbacks.robot.turret.ActionTurretMoveToPos;
import redbacks.robot.turret.ActionTurretTiltDownToSafety;
import redbacks.robot.turret.ActionTurretTiltReset;
import redbacks.robot.turret.ActionTurretTiltToPos;
import redbacks.robot.turret.ActionTurretTiltToSafety;
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
		
		trackerClear = newCom(new ActionTrackClear()),
		shootWithCorrection = newCom(new ActionTrackV3());

	//Sensors
	static SwitchSubsystem s_sensors = new SwitchSubsystem(sensors);
	public static CommandHolder sensorsRead = newCom(new ActionReadSensors());
			
	//Driver
	static SwitchSubsystem s_driver = new SwitchSubsystem(driver);
	public static CommandHolder	driverControl = newCom(new ActionDrive(new CheckNever()));

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
		turretLManual = newCom(new ActionMotor.Set(turret.pan, -RobotMap.turretRotationSpeed, new CheckNever())),
		turretRManual = newCom(new ActionMotor.Set(turret.pan, RobotMap.turretRotationSpeed, new CheckNever())),
		
		turretToTop = newCom(new ActionMotor.Set(turret.tilt, -RobotMap.turretTiltSpeed, new CheckCANEncoderNoReset(85000, sensors.turretTiltEncoder))),
		turretToBase = newCom(new ActionTurretTiltReset()),
		
		turretCenterFromLeft = newCom(
				new ActionSeq.Parallel(trackerClear),
				new ActionTurretTiltReset(),
				new ActionMotor.Set(turret.pan, RobotMap.turretCentraliseSpeed, new CheckMulti.And(
						new CheckCANDI(sensors.turretMagLSwitch, true), 
						new CheckCANDI(sensors.turretMagRSwitch, true)
				)),
				new ActionSetCANEncoder(sensors.turretPanEncoder, -RobotMap.turretCentreOffsetL),
				new ActionSetZeroed()
		),
		turretCenterFastFromLeft = newCom(
				new ActionSeq.Parallel(trackerClear),
				new ActionTurretTiltDownToSafety(),
				new ActionTurretFastCenter(),
				new ActionTurretTiltReset()
		),
		turretCenterFromLeftAuto = newCom(
				new ActionSeq.Parallel(trackerClear),
				new ActionTurretTiltReset(),
				new ActionMotor.Set(turret.pan, RobotMap.turretCentraliseSpeed, new CheckMulti.And(
						new CheckCANDI(sensors.turretMagLSwitch, true), 
						new CheckCANDI(sensors.turretMagRSwitch, true)
				)),
				new ActionSetCANEncoder(sensors.turretPanEncoder, -RobotMap.turretCentreOffsetL),
				new ActionSetZeroed()
		),
		turretCenterFastFromLeftAuto = newCom(
				new ActionSeq.Parallel(trackerClear),
				new ActionTurretTiltDownToSafety(),
				new ActionTurretFastCenterAuto(),
				new ActionTurretTiltReset()
		),
		turretCenterFromRight = newCom(
				new ActionSeq.Parallel(trackerClear),
				new ActionTurretTiltReset(),
				new ActionMotor.Set(turret.pan, -RobotMap.turretCentraliseSpeed, new CheckMulti.And(
						new CheckCANDI(sensors.turretMagLSwitch, true), 
						new CheckCANDI(sensors.turretMagRSwitch, true)
				)),
				new ActionSetCANEncoder(sensors.turretPanEncoder, -RobotMap.turretCentreOffsetR),
				new ActionSetZeroed(),
				new ActionTurretMoveToPos(0)
		),
		turretCenter = newCom(
				new ActionTurretCenter(),
				new ActionTurretMoveToPos(0)
		),
		turretToCD = newCom(
				new ActionTurretTiltReset(),
				new ActionTurretCenter(),
				new ActionTurretMoveToPos(0)
		),
		//CHECKME
		turretToIntake = newCom(
				new ActionTurretTiltToPos(RobotMap.turretIntakePos),
				new ActionTurretCenter(),
				new ActionTurretMoveToPos(0)
		),
		
		turretToShootCorner = goToShootPos(RobotMap.turretCornerH, RobotMap.turretCornerR, NOT_BASE),
		turretToShootEdge = goToShootPos(RobotMap.turretEdgeH, RobotMap.turretEdgeR, NOT_BASE),
		turretToShootFront = goToShootPos(RobotMap.turretFrontH, 0, NOT_BASE, 72, -90, 17.2D, -232.5D),
		turretToShootMid = goToShootPos(RobotMap.turretMidH, RobotMap.turretMidR, NOT_BASE),		
		turretToShootSecretPassage = goToShootPos(RobotMap.turretSPH, RobotMap.turretSPR, CENTRE),
		
		turretToShootLowBar = goToShootPos(RobotMap.turretLBH, RobotMap.turretLBR, NOT_CENTRE, 70, -30, 12.5D, -150D),
		turretToShootD2 = goToShootPos(RobotMap.turretD2H, RobotMap.turretD2R, NOT_CENTRE),
		turretToShootD3 = goToShootPos(RobotMap.turretD3H, RobotMap.turretD3R, NOT_CENTRE),
		turretToShootD4 = goToShootPos(RobotMap.turretD4H, RobotMap.turretD4R, NOT_CENTRE),
		turretToShootD5 = goToShootPos(RobotMap.turretD5H, RobotMap.turretD5R, NOT_CENTRE),
		
		turretToShootCornerAuto3 = goToShootPos(48000, -5975, NOT_BASE),//TEST
		turretToShootEdgeAuto10 = goToShootPos(RobotMap.turretEdgeH - 1000, RobotMap.turretEdgeR - 50, NOT_BASE);
	
	//Sequences
	static SwitchSubsystem s_sequencer = new SwitchSubsystem(sequencer);
	public static CommandHolder
		sequenceCrossingConfig = newCom(
				new ActionSeq.Parallel(turretToCD),
				new ActionSeq.Parallel(armToIntake)
		),
		sequenceLowConfig = newCom(
				new ActionSeq.Parallel(turretToCD),
				new ActionSeq.Parallel(armToBase)
		),
		sequenceIntakeGround = newCom(
				new ActionSeq.Parallel(armToBase),
				new ActionSeq.Parallel(intakeIntakeBall),
				new ActionSeq.Parallel(turretToIntake)
		),
		sequenceIntakeToShooter = newCom(
				new ActionSeq.Parallel(intake, new ActionWait(0.5D)),
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
				new ActionSeq.Parallel(intake, new ActionWait(0.5D)),
				new ActionSeq.Parallel(armToIntake),
				new ActionSeq.Parallel(launcherIntakeFast),
				new ActionWait(1D),
				new ActionSeq.Parallel(intakeIntakeBallFast),
				new ActionDoNothing()
		),
		sequenceStopBallHandling = newCom(
				new ActionSeq.Parallel(turret, new ActionWait(0.5D)),
				new ActionSeq.Parallel(launcher, new ActionWait(0.5D)),
				new ActionSeq.Parallel(intake, new ActionWait(0.5D))
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
}
