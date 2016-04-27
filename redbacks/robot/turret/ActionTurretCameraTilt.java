package redbacks.robot.turret;

import static redbacks.arachne.core.CommandBase.turret;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import redbacks.arachne.lib.commands.CommandRB;

public class ActionTurretCameraTilt extends ActionTurretTiltDis
{
	NetworkTable table;
	
	public ActionTurretCameraTilt(int encoderPosition) {
		super(encoderPosition);
		table = NetworkTable.getTable("vision");
	}
	
	public boolean isComplete(CommandRB command) {
		return super.isComplete(command) || Math.abs(table.getNumber("centerY", 0) - turret.yPos) < 1;		
	}
}
