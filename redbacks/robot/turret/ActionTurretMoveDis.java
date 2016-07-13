package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretMoveDis extends Action
{
	int dis;
	int encPos = 9999;
	double startTime;
	boolean there = false;
	
	public ActionTurretMoveDis(int encoderPosition) {
		super(new CheckNever());
		dis = encoderPosition;
	}

	public void onStart(CommandRB command) {
		startTime = command.timeSinceInitialized();
		encPos = CommandBase.sensors.turretPanEncoder.get() + dis;
	}
	
	public boolean isComplete(CommandRB command) {
		return Math.abs(CommandBase.sensors.turretPanEncoder.get() - encPos) < 20;
	}
	
	protected void runAction(CommandRB command) {
		int enc = CommandBase.sensors.turretPanEncoder.get();
		
		if((encPos > enc) == there) {
			startTime = command.timeSinceInitialized();
			there = !there;
		}
		
		double speed = 
			Math.abs(enc - encPos) < 200 ? 
					(Math.abs(enc) > 8000 ? RobotMap.turretCentraliseSpeed : RobotMap.turretPrecisionSpeed) * 
					Math.min(Math.max(1, command.timeSinceInitialized() - startTime), 3): 
			Math.abs(enc - encPos) < 500 ? RobotMap.turretCentraliseSpeed : 
			RobotMap.turretRotationSpeed;
		
		CommandBase.turret.pan.set(enc > encPos ? -speed : speed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.pan.disable();
		encPos = 9999;
	}
}
