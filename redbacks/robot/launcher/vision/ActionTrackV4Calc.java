package redbacks.robot.launcher.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import static redbacks.arachne.core.CommandBase.*;
import redbacks.arachne.core.references.CommandList;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.actions.ActionSeq;
import redbacks.arachne.lib.actions.ActionWait;
import redbacks.arachne.lib.checks.CheckTime;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.robot.turret.ActionTurretCameraMove;
import redbacks.robot.turret.ActionTurretCameraTilt;

/**
 * @author Sean Zammit, Tom Schwarz
 */
public class ActionTrackV4Calc extends Action
{
	NetworkTable table;
	
	public ActionTrackV4Calc(double waitTime) {
		super(new CheckTime(waitTime));
		table = NetworkTable.getTable("vision");
	}
	
	public void onFinish(CommandRB command) {
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
				new ActionSeq.Parallel(CommandList.launcherShootManual)
		).c().start();
	}
}
