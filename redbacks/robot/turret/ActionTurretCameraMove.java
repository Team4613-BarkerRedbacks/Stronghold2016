package redbacks.robot.turret;

import static redbacks.arachne.core.CommandBase.turret;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretCameraMove extends ActionTurretMoveDis
{
	NetworkTable table;
	
	public ActionTurretCameraMove(int encoderPosition) {
		super(encoderPosition);
		table = NetworkTable.getTable("vision");
	}
	
	public boolean isComplete(CommandRB command) {
		return super.isComplete(command) || Math.abs(table.getNumber("centerX", 0) - turret.xPos) < 1;
	}
}
