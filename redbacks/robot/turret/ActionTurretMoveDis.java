package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretMoveDis extends Action
{
	int dis;
	double pos = 9999;
	double startTime;
	
	public ActionTurretMoveDis(int encoderPosition) {
		super(new CheckNever());
		dis = encoderPosition;
	}

	public void onStart(CommandRB command) {
		startTime = command.timeSinceInitialized();
		pos = RobotMap.usePotentiometer ? CommandBase.sensors.turretPot.get() * 40 + dis : CommandBase.sensors.turretPanEncoder.get() + dis;
	}
	
	public boolean isComplete(CommandRB command) {
		return Math.abs((RobotMap.usePotentiometer ? CommandBase.sensors.turretPot.get() * 40 : CommandBase.sensors.turretPanEncoder.get()) - pos) < 20;
	}
	
	protected void runAction(CommandRB command) {
		double val = RobotMap.usePotentiometer ? CommandBase.sensors.turretPot.get() * 40 : CommandBase.sensors.turretPanEncoder.get();
		
		double speed = 
			Math.abs(val - pos) < 200 ? 
					RobotMap.turretPrecisionSpeed * 
					Math.min(Math.max(1, command.timeSinceInitialized() - startTime), 3): 
			Math.abs(val - pos) < 2000 ? RobotMap.turretCentraliseSpeed : 
			RobotMap.turretRotationSpeed;
		
		CommandBase.turret.pan.set(val > pos ? -speed : speed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.pan.disable();
		pos = 9999;
	}
}
