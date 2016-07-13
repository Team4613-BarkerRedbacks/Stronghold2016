package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.CommandList;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckMulti;
import redbacks.arachne.lib.checks.can.CheckCANDI;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretCenter extends Action
{
	boolean isLeft = true;
	boolean startPosIsReset = false;
	
	public ActionTurretCenter() {
		super(new CheckMulti.And(
				new CheckCANDI(CommandBase.sensors.turretMagLSwitch, true), 
				new CheckCANDI(CommandBase.sensors.turretMagRSwitch, true)
		));
	}
	
	public void onStart(CommandRB command) {
		startPosIsReset = Math.abs(CommandBase.sensors.turretPanEncoder.get()) < 200;
		CommandList.trackerClear.c().start();
	}
	
	public void runAction(CommandRB command) {
		int enc = CommandBase.sensors.turretPanEncoder.get();
		if(enc > 200) isLeft = true;
		else if(enc < -200) isLeft = false;
		
		double speed = Math.abs(enc) < 200 ? RobotMap.turretPrecisionSpeed : Math.abs(enc) < 1500 ? RobotMap.turretCentraliseSpeed : RobotMap.turretRotationSpeed;
		
		CommandBase.turret.pan.set(isLeft ? -speed : speed, command);
	}
	
	public void onFinish(CommandRB command) {
		//if(!startPosIsReset) CommandBase.sensors.turretPanEncoder.set(isLeft ? -RobotMap.turretCentreOffsetR : -RobotMap.turretCentreOffsetL);
		CommandBase.turret.pan.disable();
	}
}
