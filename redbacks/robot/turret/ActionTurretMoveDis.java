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
		
		double speed = 
			Math.abs(enc - encPos) < 200 ? 
					RobotMap.turretPrecisionSpeed * 
					Math.min(Math.max(1, command.timeSinceInitialized() - startTime), 3): 
			Math.abs(enc - encPos) < 2000 ? RobotMap.turretCentraliseSpeed : 
			RobotMap.turretRotationSpeed;
		
		CommandBase.turret.pan.set(enc > encPos ? -speed : speed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.pan.disable();
		encPos = 9999;
	}
}
