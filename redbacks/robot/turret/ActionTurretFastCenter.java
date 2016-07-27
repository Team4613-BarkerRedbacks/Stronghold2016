package redbacks.robot.turret;

import static redbacks.arachne.core.CommandBase.*;
import redbacks.arachne.core.references.CommandList;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.actions.ActionMotor;
import redbacks.arachne.lib.actions.ActionSeq;
import redbacks.arachne.lib.checks.CheckMulti;
import redbacks.arachne.lib.checks.CheckTime;
import redbacks.arachne.lib.checks.can.CheckCANDI;
import redbacks.arachne.lib.checks.can.CheckCANEncoder;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretFastCenter extends Action
{
	double startTime;
	
	public ActionTurretFastCenter() {
		super(	new CheckMulti.Or(
						new CheckMulti.And(
								new CheckCANDI(sensors.turretMagLSwitch, true), 
								new CheckCANDI(sensors.turretMagRSwitch, true)
						),
						new CheckCANEncoder(18000, sensors.turretPanEncoder),
						new CheckTime(5)
				)
		);
	}
	
	public void onStart(CommandRB command) {
		startTime = command.timeSinceInitialized();
	}
	
	public void runAction(CommandRB command) {
		turret.pan.set(-0.6D, command);
		if(!sensors.turretBaseSwitch.get()) turret.tilt.set(RobotMap.turretTiltSpeed / 3, command);
		else turret.tilt.disable();
	}
	
	public void onFinish(CommandRB command) {
		turret.pan.disable();
		if(command.timeSinceInitialized() - startTime < 3) 
			new CommandHolder(turret, 
					new ActionMotor.Set(turret.pan, RobotMap.turretCentraliseSpeed, new CheckMulti.And(
							new CheckCANDI(sensors.turretMagLSwitch, false), 
							new CheckCANDI(sensors.turretMagRSwitch, false)
					)),
					new ActionMotor.Set(turret.pan, RobotMap.turretCentraliseSpeed, new CheckCANEncoder(500, sensors.turretPanEncoder)),
					new ActionSeq.Parallel(CommandList.turretCenterSlow)
			).c().start();
	}
}
