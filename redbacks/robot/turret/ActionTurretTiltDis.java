package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckTime;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretTiltDis extends Action
{
	int dis;
	int encPos = 9999;
	
	public ActionTurretTiltDis(int encoderPosition) {
		super(new CheckTime(0.5D));
		dis = encoderPosition;
	}
	
	public void onStart(CommandRB command) {
		encPos = CommandBase.sensors.turretTiltEncoder.get() + dis;
	}
	
	public boolean isComplete(CommandRB command) {
		return encPos != 9999 && Math.abs(CommandBase.sensors.turretTiltEncoder.get() - encPos) < 200 && super.isComplete(command);		
	}
	
	public void runAction(CommandRB command) {
		int enc = CommandBase.sensors.turretTiltEncoder.get();
		
		double speed = Math.abs(enc - encPos) > 10000 ? RobotMap.turretTiltSpeed : RobotMap.turretTiltSlowSpeed;
		
		CommandBase.turret.tilt.set(enc > encPos ? speed : -speed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.tilt.disable();
		encPos = 9999;
	}
}
