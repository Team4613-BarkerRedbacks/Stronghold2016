package redbacks.robot.launcher.vision;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import static redbacks.arachne.core.CommandBase.*;
import redbacks.arachne.core.references.CommandList;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.commands.CommandHolder;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * @author Sean Zammit, Tom Schwarz
 */
public class ActionTrackV4 extends Action
{
	NetworkTable table;
	double startTime;
	int findStage = 0;
	int[] startPos;
	//int[] disVar = new int[]{500, 2500};
	//double[] sp = new double[]{RobotMap.turretCentraliseSpeed, RobotMap.turretTiltSlowSpeed};
	
	public ActionTrackV4() {
		super(new CheckNever());
		table = NetworkTable.getTable("vision");
	}
	
	public void onStart(CommandRB command) {
		if(turret.shouldTrack) sensors.lights.set(Value.kOn);
		startTime = command.timeSinceInitialized();
		startPos = new int[]{sensors.turretPanEncoder.get(), sensors.turretTiltEncoder.get()};
		findStage = 0;
	}
	
	public void runAction(CommandRB command) {
		if(command.timeSinceInitialized() - startTime > 0.5D) {
			switch(findStage) {
			case(1):
				boolean cont = false;
				if(getPan() < startPos[0] + 1000) turret.pan.set(0.5D, command);
				else {
					turret.pan.disable();
					cont = true;
				}
				if(getTilt() < startPos[1] + 5000) turret.tilt.set(-1D, command);
				else {
					turret.tilt.disable();
					if(cont) findStage++;
				}
				break;
			case(2):
				if(getTilt() > startPos[1] - 5000) turret.tilt.set(1D, command);
				else {
					turret.tilt.disable();
					findStage++;
				}
				break;
			case(3):
				if(getPan() > startPos[0] - 1000) turret.pan.set(-0.5D, command);
				else {
					turret.pan.disable();
					findStage++;
				}
				break;
			case(4):
				if(getTilt() < startPos[1] + 5000) turret.tilt.set(-1D, command);
				else {
					turret.tilt.disable();
					findStage++;
				}
				break;
			case(5):
				startTime = command.timeSinceInitialized();
				findStage++;
				break;
			case(6):
				if(command.timeSinceInitialized() - startTime > 0.5D) findStage++;
			default:
				findStage++;
			}
		}
	}

	public boolean isComplete(CommandRB command) {
		return !turret.shouldTrack || table.getBoolean("Found", false) || findStage >= 7;
	}
	
	public void onFinish(CommandRB command) {
		turret.pan.disable();
		turret.tilt.disable();
		if(findStage >= 7) CommandList.turretToBase.c().start();
		else if(!turret.shouldTrack || !RobotMap.isVisionEnabled) CommandList.launcherShootManual.c().start();
		else new CommandHolder(vision, new ActionTrackV4Calc(findStage > 0 ? 0.5D : 0)).c().start();
		findStage = 0;
	}
	
	public static int getPan() {
		return sensors.turretPanEncoder.get();
	}
	
	public static int getTilt() {
		return sensors.turretTiltEncoder.get();
	}
}
