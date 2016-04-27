package redbacks.robot.launcher.vision;

import static redbacks.arachne.core.CommandBase.*;
import edu.wpi.first.wpilibj.Relay.Value;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * @author Sean Zammit, Tom Schwarz
 */
public class ActionTrackSetup extends Action
{
	int desX, desY;
	double mulX, mulY;
	
	public ActionTrackSetup(int desX, int desY, double mulX, double mulY) {
		super(new CheckBoolean(true));
		this.desX = desX;
		this.desY = desY;
		this.mulX = mulX;
		this.mulY = mulY;
	}
	
	public void onFinish(CommandRB command) {
		turret.shouldTrack = true;
		turret.xPos = desX;
		turret.yPos = desY;
		turret.xMul = mulX;
		turret.yMul = mulY;
		sensors.lights.set(Value.kOn);
	}
}
