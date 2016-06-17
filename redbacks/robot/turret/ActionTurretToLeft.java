package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckMulti;
import redbacks.arachne.lib.checks.can.CheckCANDI;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretToLeft extends Action
{
	boolean isLeft = true;
	
	public ActionTurretToLeft() {
		super(new CheckMulti.And(
				new CheckCANDI(CommandBase.sensors.turretMagLSwitch, true),
				new CheckCANDI(CommandBase.sensors.turretMagRSwitch, false)
		));
	}
	
	public void runAction(CommandRB command) {
		int enc = CommandBase.sensors.turretPanEncoder.get();
		if(enc > -5000) isLeft = true;
		else if(enc < -6000) isLeft = false;
		
		double speed = enc < -5000 ? RobotMap.turretCentraliseSpeed : RobotMap.turretRotationSpeed;
		
		CommandBase.turret.pan.set(isLeft ? -speed : speed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.pan.disable();
	}
}
