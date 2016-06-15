package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretMoveToPos extends Action
{
	int encPos;
	double startTime;
	
	public ActionTurretMoveToPos(int encoderPosition) {
		super(new CheckNever());
		encPos = encoderPosition;
	}

	public void onStart(CommandRB command) {
		startTime = command.timeSinceInitialized();
	}
	
	public boolean isComplete(CommandRB command) {
		return Math.abs((RobotMap.usePotentiometer ? CommandBase.sensors.turretPot.get() * 40 : CommandBase.sensors.turretPanEncoder.get()) - encPos) < 20;
	}
	
	protected void runAction(CommandRB command) {
		double val = RobotMap.usePotentiometer ? CommandBase.sensors.turretPot.get() * 40 : CommandBase.sensors.turretPanEncoder.get();
		
		double speed = 
			Math.abs(val - encPos) < 200 ? 
					RobotMap.turretPrecisionSpeed * 
					Math.min(Math.max(1, command.timeSinceInitialized() - startTime), 1.5D): 
			Math.abs(val - encPos) < 2000 ? RobotMap.turretCentraliseSpeed : 
			RobotMap.turretRotationSpeed;
		
		CommandBase.turret.pan.set(val > encPos ? -speed : speed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.pan.disable();
	}
}
