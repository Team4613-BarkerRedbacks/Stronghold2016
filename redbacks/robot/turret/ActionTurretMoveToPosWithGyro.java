package redbacks.robot.turret;

import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.commands.CommandRB;
import static redbacks.arachne.lib.navx.NavX.getYaw;

public class ActionTurretMoveToPosWithGyro extends ActionTurretMoveToPos
{
	int origEncPos;
	
	public ActionTurretMoveToPosWithGyro(int encoderPosition) {
		super(encoderPosition);
		origEncPos = encoderPosition;
	}

	public void onStart(CommandRB command) {
		startTime = command.timeSinceInitialized();
		if(Math.abs(getYaw()) < 45) encPos = (int) (origEncPos - getYawEncOffset());
	}
	
	public double getYawEncOffset() {
		return RobotMap.turretEPD * getYaw();
	}
	
	public void onFinish(CommandRB command) {
		encPos = origEncPos;
	}
}
