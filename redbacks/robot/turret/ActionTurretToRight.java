package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckMulti;
import redbacks.arachne.lib.checks.can.CheckCANDI;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretToRight extends Action
{
	boolean isLeft = true;
	
	public ActionTurretToRight() {
		super(new CheckMulti.And(
				new CheckCANDI(CommandBase.sensors.turretMagRSwitch, true),
				new CheckCANDI(CommandBase.sensors.turretMagLSwitch, false)
		));
	}
	
	public void runAction(CommandRB command) {
		double enc = RobotMap.usePotentiometer ? CommandBase.sensors.turretPot.get() * 40 : CommandBase.sensors.turretPanEncoder.get();
		if(enc > 6000) isLeft = true;
		else if(enc < 5000) isLeft = false;
		
		double speed = enc > 5000 ? RobotMap.turretCentraliseSpeed : RobotMap.turretRotationSpeed;
		
		CommandBase.turret.pan.set(isLeft ? -speed : speed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.pan.disable();
	}
}
