package redbacks.robot.launcher.vision;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import static redbacks.arachne.core.CommandBase.*;
import redbacks.arachne.core.references.CommandList;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.actions.ActionMotor;
import redbacks.arachne.lib.actions.ActionSeq;
import redbacks.arachne.lib.actions.ActionWait;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.checks.can.CheckCANEncoderNoAbs;
import redbacks.arachne.lib.checks.can.CheckCANEncoderNoReset;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.robot.turret.ActionTurretCameraMove;
import redbacks.robot.turret.ActionTurretCameraTilt;

/**
 * @author Sean Zammit, Tom Schwarz
 */
public class ActionTrackV3WithReset extends Action
{
	NetworkTable table;
	
	public ActionTrackV3WithReset() {
		super(new CheckNever());
		table = NetworkTable.getTable("vision");
	}
	
	public void onStart(CommandRB command) {
		if(turret.shouldTrack) sensors.lights.set(Value.kOn);
	}

	public boolean isComplete(CommandRB command) {
		return !turret.shouldTrack || table.getBoolean("Found", false);
	}
	
	public void onFinish(CommandRB command) {
		if(!turret.shouldTrack || !RobotMap.isVisionEnabled) CommandList.launcherShootManual.c().start();
		else {			
			int offsetX = (int) (table.getNumber("centerX", 0) - turret.xPos);
			
			int xcor = (int) (offsetX * turret.xMul);
			System.out.println("X cor: " + xcor);
			
			int offsetY = (int) (table.getNumber("centerY", 0) - turret.yPos);
			
			int ycor = (int) (offsetY * turret.yMul);
			System.out.println("Y cor: " + ycor);
			
			new CommandHolder(turret,
					new ActionTurretCameraMove(xcor),
					new ActionTurretCameraTilt(ycor),
					/*TODO Get both turret camera corrections simultaneously.
						new ActionMulti(
							new ActionTurretMoveDis(xcor),
							new ActionTurretTiltDis(ycor)
					),*/
					new ActionWait(0.5D),
					new ActionSeq.Parallel(CommandList.launcherShootManual),
					new ActionWait(2), 
					new ActionSeq.Parallel(CommandList.turretCenterFast)
			).c().start();
		}
	}
}
