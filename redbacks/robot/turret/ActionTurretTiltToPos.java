package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretTiltToPos extends Action
{
	int encPos;
	
	public ActionTurretTiltToPos(int encoderPosition) {
		super(new CheckNever());
		encPos = encoderPosition;
	}
	
	public boolean isComplete(CommandRB command) {
		return Math.abs(CommandBase.sensors.turretTiltEncoder.get() - encPos) < 500;		
	}
	
	public void runAction(CommandRB command) {
		int enc = CommandBase.sensors.turretTiltEncoder.get();
		
		double speed = Math.abs(enc - encPos) > 10000 ? RobotMap.turretTiltSpeed : RobotMap.turretTiltSlowSpeed;//RobotMap.turretTiltSpeed;
		
		CommandBase.turret.tilt.set(enc > encPos ? speed : -speed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.tilt.disable();
	}
}
