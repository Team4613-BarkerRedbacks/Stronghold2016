package redbacks.arachne.lib.checks;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * The main check class. Provides a base for all checks to be built on.
 * Do not create instances of this class.
 * 
 * It also holds the superclasses for the digital and analog checks.
 * 
 * @author Sean Zammit
 */
public abstract class Check
{
	/**
	 * Performs the actual check.
	 * 
	 * @param command The parent command of the action this check applies to.
	 * @return Whether the check conditions have been fulfilled.
	 */
	public abstract boolean isTrue(CommandRB command);

	/**
	 * A blank method that can be overwritten by individual checks. Runs every loop.
	 * This method will usually not be necessary.
	 * 
	 * @param command The parent command of the action this check applies to.
	 * @param action The action this check applies to.
	 */
	public void run(CommandRB command, Action action) {}

	/**
	 * A blank method that can be overwritten by individual checks. Called once when the action begins.
	 * This method will usually not be necessary.
	 * 
	 * @param command The parent command of the action this check applies to.
	 * @param action The action this check applies to.
	 */
	public void begin(CommandRB command, Action action) {}

	/**
	 * A blank method that can be overwritten by individual checks. Called once when the action ends.
	 * This method will usually not be necessary.
	 * 
	 * @param command The parent command of the action this check applies to.
	 * @param action The action this check applies to.
	 */
	public void done(CommandRB command, Action action) {}
	
	/**
	 * The superclass for the digital checks. 
	 * It holds a variable specifying whether the value should be true or false.
	 */
	public static abstract class CheckDigital extends Check
	{
		/** Whether the value should be true. */
		public boolean type;
		
		/**
		 * @param isTriggered Whether the value should be true.
		 */
		public CheckDigital(boolean isTriggered) {
			type = isTriggered;
		}
	}
	
	/**
	 * The superclass for the analog checks. 
	 * It holds a variable specifying whether the value should be greater or less than the target, and a variable for the target.
	 */
	public static abstract class CheckAnalog extends Check
	{
		/** Whether the returned value should be greater than the target. */
		public boolean type;
		
		/** The target value for the check. */
		public double value;
		
		double startTime;
		
		/**
		 * @param isGreaterThan Whether the returned value should be greater than the target.
		 * @param value The target value for the check.
		 */
		public CheckAnalog(boolean isGreaterThan, double value) {
			type = isGreaterThan;
			this.value = value;
		}
		
		public void begin(CommandRB command, Action action) {
			startTime = command.timeSinceInitialized();
		}

		public boolean isTrue(CommandRB command) {
			return command.timeSinceInitialized() - startTime >= 0.3D;
		}
	}
}
