package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.can.CheckCANDI;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretTiltReset extends Action
{
	public ActionTurretTiltReset() {
		super(new CheckCANDI(CommandBase.sensors.turretBaseSwitch, true));
	}
	
	public void runAction(CommandRB command) {
		int enc = CommandBase.sensors.turretTiltEncoder.get();
		
		double speed = enc > 20000 ? RobotMap.turretTiltSpeed : RobotMap.turretTiltSpeed / 3;
		
		CommandBase.turret.tilt.set(speed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.tilt.disable();
	}
}
