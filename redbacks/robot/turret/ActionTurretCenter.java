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
		startPosIsReset = RobotMap.usePotentiometer ? Math.abs(CommandBase.sensors.turretPot.get()) < 5 : Math.abs(CommandBase.sensors.turretPanEncoder.get()) < 200;
		CommandList.trackerClear.c().start();
	}
	
	public void runAction(CommandRB command) {
		double speed;
		
		if(RobotMap.usePotentiometer) {
			int pot = (int) (CommandBase.sensors.turretPot.get());
			if(pot > 5) isLeft = true;
			else if(pot < -5) isLeft = false;
			
			speed = Math.abs(CommandBase.sensors.turretPot.get()) < 38 ? RobotMap.turretCentraliseSpeed : RobotMap.turretRotationSpeed;
		}
		else {
			int enc = CommandBase.sensors.turretPanEncoder.get();
			if(enc > 200) isLeft = true;
			else if(enc < -200) isLeft = false;
			
			speed = Math.abs(enc) < 1500 ? RobotMap.turretCentraliseSpeed : RobotMap.turretRotationSpeed;
		}
		
		CommandBase.turret.pan.set(isLeft ? -speed : speed, command);
	}
	
	public void onFinish(CommandRB command) {
		//if(!startPosIsReset) CommandBase.sensors.turretPanEncoder.set(isLeft ? -RobotMap.turretCentreOffsetR : -RobotMap.turretCentreOffsetL);
		CommandBase.turret.pan.disable();
	}
}
