package redbacks.arachne.lib.checks.digital;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import redbacks.arachne.lib.checks.Check.CheckDigital;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.solenoids.DoubleSolenoidRB;
import redbacks.arachne.lib.solenoids.SolenoidRB;

/**
 * Holds the solenoid position checks.
 * 
 * @author Sean Zammit
 */
public class CheckSolenoid
{
	/**
	 * The check for a single solenoid.
	 * Uses a variable in SolenoidRB to get around the normal bugs when checking solenoid position.
	 */
	public static class Single extends CheckDigital
	{
		SolenoidRB solenoid;

		/**
		 * @param solenoid The solenoid being checked.
		 * @param isOn Whether the check is for it being on.
		 */
		public Single(SolenoidRB solenoid, boolean isOn) {
			super(isOn);
			this.solenoid = solenoid;
		}

		public boolean isTrue(CommandRB command) {
			return solenoid.get() == type;
		}
	}

	/**
	 * The check for a double solenoid.
	 * Uses a variable in DoubleSolenoidRB to get around the normal bugs when checking solenoid position.
	 */
	public static class Double extends CheckDigital
	{
		DoubleSolenoidRB solenoid;

		/**
		 * @param solenoid The double solenoid being checked.
		 * @param isOn Whether the check is for it being on.
		 */
		public Double(DoubleSolenoidRB solenoid, boolean isOn) {
			super(isOn);
			this.solenoid = solenoid;
		}

		public boolean isTrue(CommandRB command) {
			return (solenoid.get() == Value.kForward) == type;
		}
	}
}