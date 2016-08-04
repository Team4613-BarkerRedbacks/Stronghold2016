package redbacks.arachne.core.references;

import static redbacks.arachne.core.CommandBase.*;
import static redbacks.arachne.core.references.CommandList.*;
import static redbacks.robot.arm.ArmPosition.*;
import redbacks.arachne.lib.actions.*;
import redbacks.arachne.lib.checks.*;
import redbacks.arachne.lib.checks.analog.*;
import redbacks.arachne.lib.checks.can.*;
import redbacks.arachne.lib.commands.*;
import redbacks.arachne.lib.navx.*;
import redbacks.robot.arm.ArmPosition;
import redbacks.robot.drive.*;
import redbacks.robot.launcher.vision.ActionTrackSetup;
import redbacks.robot.turret.*;

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
		
	/** The list of autonomous functions. */
	public static CommandRB[] autonomous = new CommandRB[noAutos];
	
	/**
	 * Called by OI. Set the sequence of actions run in autonomous here.
	 */
	public static void initAutonomous() {
		Timer timer = new Timer();
		
		//Do nothing
		autonomous[0] = doNothing.c();
		
		//Low bar - pos 1
		//Completely untested. Good luck!
		autonomous[1] = createAuto(BASE,
				new ActionSeq.Parallel(intake, new ActionMotor.Set(intake.intakeFront, 0.7D, new CheckNever())),
				new ActionResetYaw(),
				new ActionSeq.Parallel(turretCenterFast),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				//This should get you to the LB. First number is distance, second is speed.
				new ActionDriveStraight(new CheckCANEncoderNoReset(1500, sensors.driveLEncoder), -0.6D),
				new ActionWait(2D),
				//This should get you a while past the LB. First number is distance, second is speed.
				new ActionDriveStraight(new CheckCANEncoderNoReset(5000, sensors.driveLEncoder), -0.8D),
				new ActionSeq.Parallel(sequenceIntake),
				new ActionSeq.Parallel(
						new ActionWait(4D),
						new ActionSeq.Parallel(sequenceStopBallHandling),
						new ActionWait(0.5D),
						new ActionSeq.Parallel(turretToShootCorner)
				),
				//This should get you to the wall. First number is distance, second is speed.
				//Decrease the distance if it's not shooting - it can't go far enough to progress.
				new ActionDriveStraight(new CheckCANEncoderNoReset(7850, sensors.driveLEncoder), -0.6D),
				new ActionDriveStraight(new CheckTime(1D), -0.5D),
				new ActionDoNothing(new CheckMotor(0.9D, launcher.shooter)),
				new ActionWait(1D),
				new ActionSeq.Parallel(shootWithCorrection)
		);
		
		//D / Moat - pos 2
		//This is similar to the other three D autos, but is a little different in that it doesn't shoot from the defense.
		autonomous[2] = createAuto(CENTRE,
				new ActionResetYaw(),
				new ActionSeq.Parallel(turretCenterFast),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherHold),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, -1, 0.5D, true),
						new ActionMotor.RampTime(driver.right, -1, 0.5D, true)
				),
				//This should get you to the shooting position, which it will reverse to in a moment. First number is distance, second is speed.
				new ActionDriveStraight(new CheckCANEncoderNoReset(8000, sensors.driveLEncoder), -1D),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 0, 0.3D, true),
						new ActionMotor.RampTime(driver.right, 0, 0.3D, true)
				),
				new ActionMulti(
					new ActionDriveStraight(new CheckMulti.Or(new CheckCANEncoderNoReset(200, sensors.driveLEncoder, false), new CheckTime(4)), 0.5D),
					//Shooting settings. Tilt, pan, arm position.
					new ActionSeq.Parallel(CommandList.goToShootPos(50000, 14000, CENTRE)),
					//Make sure this number is a little lower than the first number above.
					new ActionDoNothing(new CheckCANEncoderNoReset(45000, sensors.turretTiltEncoder))
				),
				//Camera settings. Correct x, y, multiplier x, y.
				//These numbers may work better: 53, 10, 13.5D, -250D. Swap them in if it's not working.
				new ActionTrackSetup(65, -60, 12.5D, -150D),
				new ActionWait(1D),
				new ActionSeq.Parallel(shootWithCorrection),
				new ActionDoNothing(new CheckMotor(0.75D, launcher.polycord)),
				new ActionWait(0.5D),
				//Come back over the defense. First number is distance, second is speed.
				//Decrease if it's crossing the midline.
				new ActionDriveStraight(new CheckCANEncoder(4500, sensors.driveLEncoder), 1D),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 0, 0.3D, true),
						new ActionMotor.RampTime(driver.right, 0, 0.3D, true)
				),
				new ActionTankDrive(new CheckTime(0.2D), -0.2D, -0.2D)
		);
		
		//D / Moat - pos 3
		//Defined in the method named lower down. To find, click autoSt..., then hit F3.
		autonomous[3] = autoStandardCentre(turretToShootD3);
		
		//D / Moat - pos 4
		//Defined in the method named lower down. To find, click autoSt..., then hit F3.
		autonomous[4] = autoStandardCentre(turretToShootD4);
		
		//D / Moat - pos 5
		//Defined in the method named lower down. To find, click autoSt..., then hit F3.
		//This one is a different case to the other two. It can shoot from the side, like it did at DDU, by switching it with the commented out code.
		autonomous[5] = 
				autoStandardCentre(turretToShootD5);
				//autoStandardEdge(8200); //The number here is the turret pan value. Increasing makes it go clockwise. Approx 65 pulses per degree.
		
		//Low bar 2 ball - Spy box
		//Requires manual shooter setup
		autonomous[6] = createAuto(TOP,
				//Shooting code. Don't use unless you can magic up some more time
				/*new ActionSeq.Parallel(
						new ActionWait(14.5D),
						new ActionSeq.Parallel(shootWithCorrection)
				),*/
				new ActionMotor.RampTime(launcher.shooter, 1D, 0.5D, true),
				new ActionWait(0.5D),
				new ActionSeq.Parallel(launcherShootManual),
				new ActionResetYaw(),
				new ActionSeq.Parallel(turretCenterFast),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(armToBase),
				new ActionStartTimer(timer),
				//Turns away from the wall.
				//Sensor is gyro, so increase the magnitude of the first number to get it further away, decrease to make it closer to the wall.
				new ActionTankDrive(new CheckNavXNR(false, -15, NavXReadingType.ANGLE_YAW), -0.3D, 1D),
				//To the low bar. 1st is distance, 2nd is speed.
				new ActionDriveStraight(new CheckCANEncoderNoReset(5500, sensors.driveLEncoder), 0.8D),
				new ActionDoNothing(new CheckTimer(3, timer)),
				//Under the low bar. 1st is distance, 2nd is speed.
				new ActionDriveStraight(new CheckCANEncoderNoReset(10000, sensors.driveLEncoder), 0.6D),
				new ActionSeq.Parallel(intake, new ActionMotor.Set(intake.intakeFront, 0.7D, new CheckNever())),
				new ActionTankDrive(new CheckNavXNR(false, -30, NavXReadingType.ANGLE_YAW), -0.7D, 0.7D),
				new ActionTankDrive(new CheckCANEncoder(1000, sensors.driveREncoder), 0.7D, 0.7D),
				new ActionDriveStraight(new CheckMulti.Or(
						new CheckNavXNR(true, -10, NavXReadingType.ANGLE_YAW),
						new CheckTime(2)
				), 0),
				//The magical ball pickup. This may need changing. 1st is distance, 2nd is speed.
				new ActionDriveStraight(new CheckCANEncoderNoReset(12500, sensors.driveLEncoder), 0.5D),
				new ActionTankDrive(new CheckTime(0.2D), -0.2D, -0.2D),
				new ActionDriveStraight(new CheckCANEncoderNoReset(12100, sensors.driveLEncoder, false), -0.5D),
				new ActionTankDrive(new CheckNavXNR(false, -30, NavXReadingType.ANGLE_YAW), -0.7D, 0.7D),
				new ActionTankDrive(new CheckCANEncoder(850, sensors.driveREncoder), -0.55D, -0.55D),
				new ActionDriveStraight(new CheckMulti.Or(
						new CheckNavXNR(true, -10, NavXReadingType.ANGLE_YAW),
						new CheckTime(2)
				), 0),
				new ActionDriveStraight(new CheckCANEncoderNoReset(8000, sensors.driveLEncoder, false), -0.8D),
				new ActionSeq.Parallel(sequenceIntake),
				new ActionSeq.Parallel(
						new ActionWait(2.5D),
						new ActionSeq.Parallel(sequenceStopBallHandling),
						new ActionWait(0.3D),
						new ActionSeq.Parallel(turretToShootCorner)
				),
				new ActionDriveStraight(new CheckCANEncoderNoReset(1000, sensors.driveLEncoder, false), -1D),
				new ActionDriveStraight(new CheckTime(1D), -0.5D)
		);
		
		//CDF - pos 2
		//Defined in the method named lower down. To find, click autoCDF..., then hit F3.
		//The number here is the turret pan value. Increasing makes it go clockwise. Approx 65 pulses per degree.
		autonomous[7] = autoCDFEdge(15000);
		
		//CDF - pos 3
		//Defined in the method named lower down. To find, click autoCDF..., then hit F3.
		autonomous[8] = autoCDFCentre(turretToShootD3);
		
		//CDF - pos 4
		//Defined in the method named lower down. To find, click autoCDF..., then hit F3.
		autonomous[9] = autoCDFCentre(turretToShootD4);
		
		//CDF - pos 5
		//Defined in the method named lower down. To find, click autoCDF..., then hit F3.
		//The number here is the turret pan value. Increasing makes it go clockwise. Approx 65 pulses per degree.
		autonomous[10] = autoCDFEdge(8200);
	}
	
	//Used for cat D/moat autos in pos 3 - 5
	public static CommandRB autoStandardCentre(CommandHolder turretPosition) {
		return createAuto(CENTRE,
				new ActionResetYaw(),
				new ActionSeq.Parallel(turretCenterFast),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherHold),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, -1, 0.5D, true),
						new ActionMotor.RampTime(driver.right, -1, 0.5D, true)
				),
				//Over defense. 1st is distance, 2nd is speed.
				new ActionDriveStraight(new CheckCANEncoderNoReset(6600, sensors.driveLEncoder), -1D),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 0, 0.3D, true),
						new ActionMotor.RampTime(driver.right, 0, 0.3D, true)
				),
				new ActionMulti(
					new ActionDriveStraight(new CheckMulti.Or(new CheckCANEncoderNoReset(200, sensors.driveLEncoder, false), new CheckTime(4)), 0.5D),
					new ActionSeq.Parallel(turretPosition),
					new ActionDoNothing(new CheckCANEncoderNoReset(45000, sensors.turretTiltEncoder))
				),
				//Camera settings. Correct x, y, multiplier x, y. Uses teleop default for D5 instead, these for D3 and D4.
				//If they aren't working, comment out the line below and uncomment the line below that. Then it will use the default teleop settings.
				(turretPosition == turretToShootD5 ? new ActionDoNothing(new CheckShouldTrack()) : new ActionTrackSetup(65, -60, 12.5D, -150D)),
				//new ActionDoNothing(new CheckShouldTrack()),
				new ActionWait(1D),
				new ActionSeq.Parallel(shootWithCorrection),
				new ActionDoNothing(new CheckMotor(0.75D, launcher.polycord)),
				new ActionWait(0.5D),
				//Back to center. 1st is distance, 2nd is speed.
				new ActionDriveStraight(new CheckCANEncoder(3500, sensors.driveLEncoder), 1D),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 0, 0.3D, true),
						new ActionMotor.RampTime(driver.right, 0, 0.3D, true)
				),
				new ActionTankDrive(new CheckTime(0.2D), -0.2D, -0.2D)
		);
	}
		
	//Used for CDF autos in pos 3, 4
	public static CommandRB autoCDFCentre(CommandHolder turretPosition) {
		return createAuto(CENTRE,
				new ActionResetYaw(),
				new ActionSetYaw(180),
				new ActionSeq.Parallel(turretCenterFast),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherIntakeManual),
				new ActionDriveStraightReverse(new CheckMulti.And(
						new CheckCANEncoderNoReset(1000, sensors.driveLEncoder), 
						new CheckTime(3)
				), 0.5D),
				new ActionSeq.Parallel(armToBase),
				new ActionWait(1D),
				new ActionDriveStraightReverse(new CheckTime(0.7D), 0.7D),
				new ActionCDFSafety(new CommandHolder(sequencer, 
						new ActionSeq.Parallel(armToIntake),
						//Over CDF. 1st is distance, 2nd is speed.
						new ActionDriveStraightReverse(new CheckCANEncoderNoReset(5000, sensors.driveLEncoder), 0.7D),
						new ActionSeq.Parallel(turretPosition),
						//Spin. If it's too violent, change to ActionDriveStraight, not VeryStraight
						new ActionDriveVeryStraight(new CheckTime(2), 0),
						//If the light goes on, but it doesn't shoot/look for the goal, remove this line and increase the wait below to 3 seconds.
						new ActionDoNothing(new CheckShouldTrack()),
						new ActionWait(0.5D),
						new ActionSeq.Parallel(shootWithCorrection))
				)
		);
	}
	
	//Used for CDF autos in pos 2, 5
	public static CommandRB autoCDFEdge(int turretPos) {
		return createAuto(CENTRE,
				new ActionResetYaw(),
				new ActionSetYaw(180),
				new ActionSeq.Parallel(turretCenterFast),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherIntakeManual),
				new ActionDriveStraightReverse(new CheckMulti.And(
						new CheckCANEncoderNoReset(1000, sensors.driveLEncoder), 
						new CheckTime(3)
				), 0.5D),
				new ActionSeq.Parallel(turret, new ActionTurretMoveToPos(turretPos)),
				new ActionSeq.Parallel(armToBase),
				new ActionWait(1D),
				new ActionDriveStraightReverse(new CheckTime(0.7D), 0.7D),
				new ActionCDFSafety(new CommandHolder(sequencer, 
						new ActionSeq.Parallel(armToIntake),
						//Over CDF, to shooting position. 1st is distance, 2nd is speed.
						new ActionDriveStraightReverse(new CheckCANEncoderNoReset(10000, sensors.driveLEncoder), 0.7D),
						new ActionSeq.Parallel(turret, new ActionTurretTiltToPos(73000)),
						//Spin. If it's too violent, change to ActionDriveStraight, not VeryStraight
						new ActionDriveVeryStraight(new CheckTime(2), 0),
						//Camera settings. Correct x, y, multiplier x, y.
						new ActionTrackSetup(78, -88, 17.2D, -232.5D),
						new ActionWait(0.5D),
						new ActionSeq.Parallel(shootWithCorrection))
				)
		);
	}
	
	//The standard auto creator. Do. Not. Touch. Please. :)
	public static CommandRB createAuto(ArmPosition armPos, Action... actions) {
		Action[] sequence = new Action[actions.length + 1];
		sequence[0] = new ActionSeq.Parallel(armPos.action);
		
		for(int i = 0; i < actions.length; i++) sequence[i + 1] = actions[i];
		
		return new CommandHolder(null, sequence).c();
	}
	
	//NOTE: At this point this is unused
	@Deprecated
	public static CommandRB autoStandardEdge(int turretPos) {
		return createAuto(CENTRE,
				new ActionResetYaw(),
				new ActionSeq.Parallel(turretCenterFast),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionSeq.Parallel(launcherHold),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, -1, 0.5D, true),
						new ActionMotor.RampTime(driver.right, -1, 0.5D, true)
				),
				new ActionSeq.Parallel(turret, new ActionTurretMoveToPos(turretPos)),
				new ActionDriveStraight(new CheckCANEncoderNoReset(6000, sensors.driveLEncoder), -1D),
				new ActionResetCANEncoder(sensors.driveLEncoder),
				new ActionResetCANEncoder(sensors.driveREncoder),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 0, 0.3D, true),
						new ActionMotor.RampTime(driver.right, 0, 0.3D, true)
				),
				new ActionMulti(
						new ActionDriveStraight(new CheckCANEncoderNoReset(5000, sensors.driveLEncoder), -0.75D),
						new ActionMulti(new CheckCANEncoderNoReset(65000, sensors.turretTiltEncoder), new ActionTurretTiltToPos(70000))
				),
				new ActionTankDrive(new CheckTime(0.2D), 0.2D, 0.2D),
				new ActionTrackSetup(78, -88,17.2D, -232.5D),
				new ActionWait(0.5D),
				new ActionSeq.Parallel(shootWithCorrection),
				new ActionDoNothing(new CheckMotor(0.75D, launcher.polycord)),
				new ActionWait(0.5D),
				new ActionDriveStraight(new CheckCANEncoder(8000, sensors.driveLEncoder), 1D),
				new ActionMulti(
						new ActionMotor.RampTime(driver.left, 0, 0.3D, true),
						new ActionMotor.RampTime(driver.right, 0, 0.3D, true)
				),
				new ActionTankDrive(new CheckTime(0.2D), -0.2D, -0.2D)
		);
	}
}
