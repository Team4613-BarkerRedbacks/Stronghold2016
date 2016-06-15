package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretTiltToSafety extends ActionTurretTiltToPos
{
	int panPos;
	
	public ActionTurretTiltToSafety(int endPos, int panPos) {
		super(Math.min(35000, endPos));
		this.panPos = panPos;
	}
	
	public boolean isComplete(CommandRB command) {
		return Math.abs(CommandBase.sensors.turretTiltEncoder.get() - encPos) < 3000 || Math.abs((RobotMap.usePotentiometer ? CommandBase.sensors.turretPot.get() * 40 : CommandBase.sensors.turretPanEncoder.get()) - panPos) < 1000;		
	}
	
	public void runAction(CommandRB command) {
		int enc = CommandBase.sensors.turretTiltEncoder.get();
		
		double speed = Math.abs(enc - encPos) > 7500 ? RobotMap.turretTiltSpeed : RobotMap.turretTiltSlowSpeed;
		
		CommandBase.turret.tilt.set(enc > encPos ? speed : -speed, command);
	}
}
