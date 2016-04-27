package redbacks.robot.turret;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.can.CheckCANEncoderNoReset;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretTiltDownToSafety extends Action
{
	public ActionTurretTiltDownToSafety() {
		super(new CheckCANEncoderNoReset(35000, CommandBase.sensors.turretTiltEncoder, false));
	}
	
	public void runAction(CommandRB command) {
		CommandBase.turret.tilt.set(RobotMap.turretTiltSpeed, command);
	}
	
	public void onFinish(CommandRB command) {
		CommandBase.turret.tilt.disable();
	}
}
