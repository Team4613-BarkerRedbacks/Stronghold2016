package redbacks.robot.launcher.vision;

import static redbacks.arachne.core.CommandBase.*;
import edu.wpi.first.wpilibj.Relay.Value;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * @author Sean Zammit, Tom Schwarz
 */
public class ActionTrackClear extends Action
{
	public ActionTrackClear() {
		super(new CheckBoolean(true));
	}
	
	public void onFinish(CommandRB command) {
		turret.shouldTrack = false;
		turret.xPos = 0;
		turret.yPos = 0;
		turret.xMul= 0;
		turret.yMul = 0;
		sensors.lights.set(Value.kOff);
	}
}
