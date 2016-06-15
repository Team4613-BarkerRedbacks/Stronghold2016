package redbacks.robot.sensors;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.core.references.RobotMap;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.sensors.CANEncoder;

/**
 * @author Sean Zammit
 */
public class CheckTurretPan extends CheckAnalog
{
	/**
	 * @param pulses The number of pulses the encoder must reach past the starting position in any direction.
	 */
	public CheckTurretPan(double pulses) {
		super(true, pulses);
	}

	public boolean isTrue(CommandRB command) {
		return (RobotMap.usePotentiometer ? Math.abs(CommandBase.sensors.turretPot.get()) > value / 40 : Math.abs(CommandBase.sensors.turretPanEncoder.get()) > value) && super.isTrue(command);
	}
	
	public void begin(CommandRB command, Action action) {
		super.begin(command, action);
		CommandBase.sensors.turretPanEncoder.reset();
	}
}
