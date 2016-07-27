package redbacks.robot.launcher.vision;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.CommandList;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * @author Sean Zammit, Tom Schwarz
 */
public class ActionCDIfNotFailedVision extends Action
{
	public ActionCDIfNotFailedVision() {
		super(new CheckBoolean(true));
	}
	
	public void onFinish(CommandRB command) {
		if(CommandBase.sensors.turretTiltEncoder.get() > 10000) CommandList.turretToCD.c().start();
	}
}
