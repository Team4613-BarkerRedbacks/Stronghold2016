package redbacks.arachne.core.references;

import static redbacks.arachne.core.CommandBase.*;
import static redbacks.arachne.core.references.CommandList.*;
import static redbacks.arachne.core.references.RobotMap.*;
import static redbacks.robot.arm.ArmPosition.*;
import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.actions.ActionDoNothing;
import redbacks.arachne.lib.actions.ActionMotor;
import redbacks.arachne.lib.actions.ActionMulti;
import redbacks.arachne.lib.actions.ActionResetCANEncoder;
import redbacks.arachne.lib.actions.ActionSeq;
import redbacks.arachne.lib.actions.ActionSetCANEncoder;
import redbacks.arachne.lib.actions.ActionWait;
import redbacks.arachne.lib.checks.CheckMulti;
import redbacks.arachne.lib.checks.CheckTime;
import redbacks.arachne.lib.checks.can.CheckCANDI;
import redbacks.arachne.lib.checks.can.CheckCANEncoder;
import redbacks.arachne.lib.checks.can.CheckCANEncoderNoReset;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.navx.ActionResetYaw;
import redbacks.arachne.lib.navx.CheckNavX;
import redbacks.arachne.lib.navx.NavXReadingType;
import redbacks.robot.arm.ArmPosition;
import redbacks.robot.drive.ActionDriveStraight;
import redbacks.robot.drive.ActionShift;
import redbacks.robot.drive.ActionTankDrive;
import redbacks.robot.launcher.vision.ActionTrackSetup;
import redbacks.robot.turret.ActionSetZeroed;
import redbacks.robot.turret.ActionTurretMoveToPos;
import redbacks.robot.turret.ActionTurretTiltReset;
import redbacks.robot.turret.ActionTurretTiltToPos;
import redbacks.robot.turret.ActionTurretTiltToSafety;

/**
 * Autonomous holds all the autonomous functions used on the robot, and any autonomous specific commands.
 *
 * @author Sean Zammit
 */
public class Autonomous
{
	/** 
	 * The size of the list of autonomous functions. Eventually I decided to make this a bigger number than necessary - I got sick of changing it.
	 */
	private static int noAutos = 64;
		
	/**  The list of autonomous functions.
	 * 
	 * Start Pos	Index		Scoring
	 * 
	 * Spy bot		1			1 goal
	 * Spy bot		2			1 goal, return over low bar, cross low bar
	 * 1			3			Low bar, 1 goal from LHS corner
	 * 1			4			Low bar, 1 goal from LHS batter 			UNTESTED
	 * 2 - 5		5 - 8		Cross D, 1 goal from front					
	 * 2			9			Cross D, 1 goal from LHS corner	v1			UNTESTED
	 * 2			10			Cross D, 1 goal from LHS corner	v2			UNTESTED
	 * Spy bot		11			1 goal, intake, 1 goal
	 */
	public static CommandRB[] autonomous = new CommandRB[noAutos];
	
	/**
	 * Called by OI. Set the sequence of actions run in autonomous here.
	 */
	public static void initAutonomous() {
		//Do nothing
		autonomous[0] = doNothing.c();
		
		//Spy bot		1 goal
		autonomous[1] = createAuto(NOT_CENTRE,
				new ActionMotor.RampTime(launcher.shooter, 1D, 0.5D, true),
				new ActionWait(1D),
				new ActionSeq.Parallel(launcherShoot)
		);
		
		//Spy bot		1 goal, return over low bar, cross low bar
		autonomous[2] = createAuto(NOT_CENTRE,
				new ActionResetYaw(),
				new ActionMotor.RampTime(launcher.shooter, 1D, 0.5D, true),
				new ActionWait(0.25D),
				new ActionSeq.Parallel(launcherShoot),
				new ActionWait(0.7D),
				new ActionSeq.Parallel(armToBase),
				new ActionSeq.Parallel(turretCenterFromLeft),
				new ActionMotor.Set(driver.right, 0.6D, new CheckCANEncoder(250, sensors.driveREncoder)),
				new ActionMotor.Set(driver.left, 0.6D, new CheckCANEncoder(3000, sensors.driveLEncoder)),
				new ActionDriveStraight(new CheckCANEncoder(2300, sensors.driveLEncoder), 0.8D),
				new ActionShift(false),
				new ActionDoNothing(new CheckMulti.And(
						new CheckCANDI(CommandBase.sensors.turretMagLSwitch, true), 
						new CheckCANDI(CommandBase.sensors.turretMagRSwitch, true)
				)),
				new ActionDriveStraight(new CheckCANEncoder(1500, sensors.driveLEncoder), 0.6D),
				new ActionShift(true),
				new ActionDriveStraight(new CheckCANEncoder(2700, sensors.driveLEncoder), 0.6D),
				new ActionDriveStraight(new CheckCANEncoder(4000, sensors.driveLEncoder), -0.8D)
		);
		
		//Position 1	Low bar, 1 goal from LHS Corner
		autonomous[3] = createAuto(BASE,
				new ActionResetYaw(),
				new ActionSeq.Parallel(intake, new ActionDoNothing()),
				new ActionSetCANEncoder(sensors.turretPanEncoder, 0),
				//new ActionSeq.Parallel(turretCenterFromLeftAuto),
				new ActionWait(2.5D),
				new ActionSeq.Parallel(intakeIntakeBall),
				new ActionDriveStraight(new CheckCANEncoder(5000, sensors.driveLEncoder), -0.8D),
				new ActionSeq.Parallel(new ActionTurretTiltToPos(RobotMap.turretIntakePos)),
				new ActionSeq.Parallel(sequenceIntakeToShooter),
				new ActionWait(3D),
				new ActionSeq.Parallel(turretToShootCornerAuto3),
				new ActionSeq.Parallel(launcherHold),
				new ActionSeq.Parallel(new ActionMotor.RampTime(launcher.shooter, 1D, 0.5D, true)),
				new ActionDriveStraight(new CheckCANEncoder(2850, sensors.driveLEncoder), -0.6D),
				new ActionDoNothing(new CheckCANEncoderNoReset(47000, sensors.turretTiltEncoder)),
				new ActionWait(1D),
				new ActionDriveStraight(new CheckTime(1D), -0.5D),// Was 0.5, -0.4
				new ActionSeq.Parallel(launcherShoot)
		);
		
		//TODO
		//Position 1	Low bar, 1 goal from LHS Batter
		autonomous[4] = createAuto(BASE,
				new ActionResetYaw(),
				new ActionSeq.Parallel(intake, new ActionDoNothing()),
				new ActionSeq.Parallel(turretCenterFromLeft),
				new ActionWait(2.5D),
				new ActionDriveStraight(new CheckCANEncoder(5000, sensors.driveLEncoder), -0.8D),
				new ActionSeq.Parallel(turretToShootEdge),
				new ActionTankDrive(new CheckNavX(true, 70, NavXReadingType.ANGLE_YAW), 0.6D, -0.6D),
				new ActionTankDrive(new CheckCANEncoder(2000, sensors.driveLEncoder), -0.8D, -0.8D),
				new ActionWait(0.5D),
				new ActionDriveStraight(new CheckCANEncoder(4500, sensors.driveLEncoder), -0.9D),
				new ActionDriveStraight(new CheckMulti.Or(new CheckTime(1.5D), new CheckCANEncoder(2500, sensors.driveLEncoder)), -0.75D),
				new ActionDriveStraight(new CheckMulti.Or(new CheckTime(1D), new CheckNavX(true, 0.25D, NavXReadingType.ACCEL_FORWARD)), -0.75D),
				new ActionDriveStraight(new CheckTime(0.5D), -0.4D),
				new ActionDoNothing(new CheckCANEncoderNoReset(RobotMap.turretEdgeH - 1000, sensors.turretTiltEncoder)),
				new ActionWait(1D),
				new ActionDriveStraight(new CheckTime(0.5D), -0.4D),
				new ActionSeq.Parallel(launcherShoot)
		);
		
		//TEST
		//Position 2	Cross D, 1 goal from Front
		autonomous[5] = createDAuto(autoCross2Dis, autoCross2Ang);
		
		//TEST
		//Position 3	Cross D, 1 goal from Front
		autonomous[6] = createDAuto(autoCross3Dis, autoCross3Ang);
		
		//TEST
		//Position 4	Cross D, 1 goal from Front
		autonomous[7] = createDAuto(autoCross4Dis, autoCross4Ang);
		
		//TEST
		//Position 5	Cross D, 1 goal from Front
		autonomous[8] = createDAuto(autoCross5Dis, autoCross5Ang);
		
		//TEST
		//Position 2	Cross D, 1 goal from LHS
		autonomous[9] = createAuto(TOP,
				new ActionResetYaw(),
				new ActionSeq.Parallel(launcherHold),
				new ActionSeq.Parallel(turretCenterFromLeft),
				new ActionWait(0.5D),
				new ActionMulti(
						new ActionMotor.Set(driver.right, -0.6D, new CheckCANEncoder(1000, sensors.driveREncoder)),
						new ActionMotor.Set(driver.left, -0.6D, new CheckCANEncoder(1000, sensors.driveLEncoder))
				),
				new ActionDriveStraight(new CheckCANEncoder(4500, sensors.driveLEncoder), -1D),
				new ActionWait(0.5D),
				new ActionSeq.Parallel(turretToShootEdge),
				new ActionTankDrive(new CheckNavX(true, 40, NavXReadingType.ANGLE_YAW), 0.6D, -0.6D),
				new ActionWait(0.5D),
				new ActionDriveStraight(new CheckCANEncoder(5000, sensors.driveLEncoder), -0.9D),
				new ActionDriveStraight(new CheckMulti.Or(new CheckTime(1.5D), new CheckCANEncoder(2500, sensors.driveLEncoder)), -0.75D),
				new ActionDriveStraight(new CheckMulti.Or(new CheckTime(1D), new CheckNavX(true, 0.25D, NavXReadingType.ACCEL_FORWARD)), -0.75D),
				new ActionDriveStraight(new CheckTime(0.5D), -0.4D),
				new ActionDoNothing(new CheckCANEncoderNoReset(RobotMap.turretEdgeH - 1000, sensors.turretTiltEncoder)),
				new ActionWait(1D),
				new ActionDriveStraight(new CheckTime(0.5D), -0.4D),
				new ActionSeq.Parallel(launcherShoot)
		);
		
		//TEST
		//Position 2	Cross D, 1 goal from LHS (alternative 2)
		autonomous[10] = createAuto(CENTRE,
				new ActionResetYaw(),
				new ActionSeq.Parallel(launcherHold),
				new ActionSeq.Parallel(turretCenterFromLeft),
				new ActionWait(0.5D),
				new ActionMulti(
						new ActionMotor.Set(driver.right, 0.6D, new CheckCANEncoder(1000, sensors.driveREncoder)),
						new ActionMotor.Set(driver.left, 0.6D, new CheckCANEncoder(1000, sensors.driveLEncoder))
				),
				new ActionDriveStraight(new CheckCANEncoder(4500, sensors.driveLEncoder), 1D),
				new ActionSeq.Parallel(turretToShootEdgeAuto10),
				new ActionSeq.Parallel(launcherHold),
				new ActionSeq.Parallel(new ActionMotor.RampTime(launcher.shooter, 1D, 0.5D, true)),
				new ActionDriveStraight(new CheckCANEncoder(2850, sensors.driveLEncoder), -0.6D),
				new ActionDoNothing(new CheckCANEncoderNoReset(RobotMap.turretEdgeH - 5000, sensors.turretTiltEncoder)),
				new ActionWait(1D),
				new ActionDriveStraight(new CheckTime(1D), -0.5D),// Was 0.5, -0.4
				new ActionSeq.Parallel(launcherShoot)
		);
		
		//TEST
		//Position 2	1 goal, intake, 1 goal
		autonomous[11] = createAuto(TOP,
				new ActionSeq.Parallel(
						new ActionWait(2.5D),
						new ActionSeq.Parallel(armToBase),
						new ActionSeq.Parallel(intakeIntakeBall),
						new ActionMotor.Set(turret.pan, turretRotationSpeed, new CheckCANEncoder(5000, sensors.turretPanEncoder)),
						new ActionMotor.Disable(turret.pan),
						new ActionTurretTiltReset(),
						new ActionMotor.Set(turret.pan, RobotMap.turretCentraliseSpeed, new CheckMulti.And(
								new CheckCANDI(sensors.turretMagLSwitch, true), 
								new CheckCANDI(sensors.turretMagRSwitch, true)
						)),
						new ActionMotor.Disable(turret.pan),
						new ActionSetCANEncoder(sensors.turretPanEncoder, -RobotMap.turretCentreOffsetL),
						new ActionTurretMoveToPos(0), 
						new ActionSetZeroed(),
						new ActionSeq.Parallel(
								new ActionTurretTiltToPos(RobotMap.turretIntakePos)
						),
						new ActionSeq.Parallel(sequenceIntakeToShooter),
						new ActionWait(2.5D),
						new ActionMulti(
								new ActionTurretTiltToSafety(turretCornerH, turretCornerR),
								new ActionTurretMoveToPos(turretCornerR)
						),
						new ActionTurretTiltToPos(turretCornerH),
						new ActionWait(0.5D),
						new ActionSeq.Parallel(launcherShoot)
				),
				new ActionMotor.RampTime(launcher.shooter, 1D, 0.5D, true),
				new ActionWait(1D),
				new ActionSeq.Parallel(launcherShoot)
		);
		
		//TEST
		//Position 1	Low bar, 1 goal from LHS Corner (fast)
		autonomous[12] = createAuto(BASE,
				new ActionResetYaw(),
				new ActionSeq.Parallel(intake, new ActionDoNothing()),
				new ActionSetCANEncoder(sensors.turretPanEncoder, 0),
				new ActionSeq.Parallel(new ActionDriveStraight(new CheckCANEncoder(1500, sensors.driveLEncoder), -0.8D)),
				new ActionWait(2.5D),
				new ActionSeq.Parallel(intakeIntakeBall),
				new ActionDriveStraight(new CheckCANEncoder(3500, sensors.driveLEncoder), -0.8D),
				new ActionSeq.Parallel(new ActionTurretTiltToPos(RobotMap.turretIntakePos)),
				new ActionSeq.Parallel(sequenceIntakeToShooter),
				new ActionSeq.Parallel(
						new ActionDriveStraight(new CheckCANEncoder(2850, sensors.driveLEncoder), -0.6D),
						new ActionDoNothing(new CheckCANEncoderNoReset(RobotMap.turretCornerH - 1000, sensors.turretTiltEncoder)),
						new ActionWait(1D),
						new ActionDriveStraight(new CheckTime(1D), -0.5D),// Was 0.5, -0.4
						new ActionSeq.Parallel(launcherShoot)
				),
				new ActionWait(3D),
				new ActionSeq.Parallel(turretToShootCornerAuto3),
				new ActionSeq.Parallel(launcherHold),
				new ActionSeq.Parallel(new ActionMotor.RampTime(launcher.shooter, 1D, 0.5D, true))
		);
		
		//Pos 3/4 RT
		autonomous[13] = createAuto(TOP,
				new ActionResetYaw(),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherHold),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 1, 1, true),
						new ActionMotor.RampTime(driver.right, 1, 1, true)
				),
				new ActionDriveStraight(new CheckCANEncoderNoReset(5500, sensors.driveLEncoder), 1D),
				new ActionTankDrive(new CheckTime(0.2D), -0.2D, -0.2D),
				new ActionSeq.Parallel(launcherIntakeManual),
				new ActionTrackSetup(65, -60, 12.5D, -150D),
				new ActionWait(0.5D),
				new ActionSeq.Parallel(launcherSpeedWheels),
				new ActionWait(1.5D),
				new ActionSeq.Parallel(shootWithCorrection)
		);
		
		//Pos 3/4 RW
		autonomous[14] = createAuto(TOP,
				new ActionResetYaw(),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherHold),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 1, 1, true),
						new ActionMotor.RampTime(driver.right, 1, 1, true)
				),
				new ActionDriveStraight(new CheckCANEncoderNoReset(6000, sensors.driveLEncoder), 1D),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 0, 0.3D, true),
						new ActionMotor.RampTime(driver.right, 0, 0.3D, true)
				),
				new ActionDriveStraight(new CheckCANEncoderNoReset(200, sensors.driveLEncoder, false), -0.5D),
				new ActionSeq.Parallel(launcherIntakeManual),
				new ActionTrackSetup(65, -60, 12.5D, -150D),
				new ActionWait(0.5D),
				new ActionSeq.Parallel(launcherSpeedWheels),
				new ActionWait(1.5D),
				new ActionSeq.Parallel(shootWithCorrection)
		);
		
		//Pos 5 Cat D
		autonomous[15] = createAuto(TOP,
				new ActionResetYaw(),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherHold),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 1, 1, true),
						new ActionMotor.RampTime(driver.right, 1, 1, true)
				),
				new ActionDriveStraight(new CheckCANEncoderNoReset(6000, sensors.driveLEncoder), 1D),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 0, 0.3D, true),
						new ActionMotor.RampTime(driver.right, 0, 0.3D, true)
				),
				new ActionMulti(
						new ActionDriveStraight(new CheckCANEncoderNoReset(4750, sensors.driveLEncoder), 0.75D),
						new ActionMulti(new CheckCANEncoderNoReset(70000, sensors.turretTiltEncoder), new ActionTurretTiltToPos(73000))
				),
				new ActionTankDrive(new CheckTime(0.2D), -0.2D, -0.2D),
				new ActionTrackSetup(78, -88,17.2D, -232.5D),
				new ActionWait(0.5D),
				new ActionSeq.Parallel(shootWithCorrection)
		);
		
		//Pos 2 RT
		autonomous[16] = createAuto(TOP,
				new ActionResetYaw(),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherHold),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 1, 1, true),
						new ActionMotor.RampTime(driver.right, 1, 1, true)
				),
				new ActionDriveStraight(new CheckCANEncoderNoReset(5500, sensors.driveLEncoder), 1D),
				new ActionTankDrive(new CheckTime(0.2D), -0.2D, -0.2D),
				new ActionSeq.Parallel(launcherIntakeManual),
				new ActionTrackSetup(82, -48, 10.2D, -150D),
				new ActionWait(0.5D),
				new ActionSeq.Parallel(launcherSpeedWheels),
				new ActionWait(1.5D),
				new ActionSeq.Parallel(shootWithCorrection)
		);
		
		//Pos 2 RW
		autonomous[17] = createAuto(TOP,
				new ActionResetYaw(),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherHold),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 1, 1, true),
						new ActionMotor.RampTime(driver.right, 1, 1, true)
				),
				new ActionDriveStraight(new CheckCANEncoderNoReset(6000, sensors.driveLEncoder), 1D),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 0, 0.3D, true),
						new ActionMotor.RampTime(driver.right, 0, 0.3D, true)
				),
				new ActionDriveStraight(new CheckCANEncoderNoReset(200, sensors.driveLEncoder, false), -0.5D),
				new ActionSeq.Parallel(launcherIntakeManual),
				new ActionTrackSetup(82, -48, 10.2D, -150D),
				new ActionWait(0.5D),
				new ActionSeq.Parallel(launcherSpeedWheels),
				new ActionWait(1.5D),
				new ActionSeq.Parallel(shootWithCorrection)
		);
	}
	
	public static CommandRB createAuto(ArmPosition armPos, Action... actions) {
		Action[] sequence = new Action[actions.length + 1];
		sequence[0] = new ActionSeq.Parallel(armPos.action);
		
		for(int i = 0; i < actions.length; i++) sequence[i + 1] = actions[i];
		
		return new CommandHolder(null, sequence).c();
	}
	
	public static CommandRB createDAuto(int autoCrossDis, int turningAng) {
		boolean dir = turningAng > 0;
		final double turnSpeed = 0.5D;
		return createAuto(TOP,
				new ActionResetYaw(),
				new ActionSeq.Parallel(launcherHold),
				new ActionSeq.Parallel(turretCenterFromLeft),
				new ActionWait(0.5D),
				new ActionMulti(
						new ActionMotor.Set(driver.right, 0.6D, new CheckCANEncoder(1000, sensors.driveREncoder)),
						new ActionMotor.Set(driver.left, 0.6D, new CheckCANEncoder(1000, sensors.driveLEncoder))
				),
				new ActionDriveStraight(new CheckCANEncoder(4500, sensors.driveLEncoder), 1D),
				new ActionWait(0.5D),
				new ActionSeq.Parallel(turretToShootFront),
				
				new ActionTankDrive(new CheckNavX(dir, turningAng, NavXReadingType.ANGLE_YAW), dir ? turnSpeed : -turnSpeed, dir ? -turnSpeed : turnSpeed),
				new ActionWait(0.5D),
				new ActionTankDrive(new CheckCANEncoder(autoCrossDis * autoEncMul, sensors.driveLEncoder), 0.8D, 0.8D),
				new ActionTankDrive(new CheckNavX(!dir, -turningAng + 5, NavXReadingType.ANGLE_YAW), dir ? -turnSpeed : turnSpeed, dir ? turnSpeed : -turnSpeed),
				
				new ActionSeq.Parallel(shootWithCorrection)
		);
	}
}
