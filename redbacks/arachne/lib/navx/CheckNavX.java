package redbacks.arachne.lib.navx;

import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Checks whether the NavX has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckNavX extends CheckAnalog
{
	NavXReadingType desiredReading;
	
	/**
	 * @param isGreaterThan Whether the reading must be greater than the input.
	 * @param reading The reading required from the sensor.
	 * @param type The sensor to get the reading from.
	 */
	public CheckNavX(boolean isGreaterThan, double reading, NavXReadingType type) {
		super(isGreaterThan, reading);
		this.desiredReading = type;
	}

	public boolean isTrue(CommandRB command) {
		switch(desiredReading) {
			case ACCEL_FORWARD: 
				System.out.println(NavX.getAccelForward() + ", " + value + ", " + type);
				return (NavX.getAccelForward() >= value) == type;
			case ACCEL_RIGHT: 	return (NavX.getAccelRight() >= value) == type;
			case ACCEL_UP: 		return (NavX.getAccelUp() >= value) == type;
			
			case SPEED_FORWARD: 
				System.out.println(NavX.getSpeedForward() + ", " + value + ", " + type);
				return (NavX.getSpeedForward() >= value) == type;
			case SPEED_RIGHT: 	return (NavX.getSpeedRight() >= value) == type;
			case SPEED_UP: 		return (NavX.getSpeedUp() >= value) == type;
			
			case ANGLE_PITCH:	return (NavX.getPitch() >= value) == type;
			case ANGLE_ROLL:	return (NavX.getRoll() >= value) == type;
			case ANGLE_YAW:		return (NavX.getYaw() >= value) == type;
			
			case RATE_PITCH:	return (NavX.getRatePitch() >= value) == type;
			case RATE_ROLL:		return (NavX.getRateRoll() >= value) == type;
			case RATE_YAW:		return (NavX.getRateYaw() >= value) == type;
			
			default: return false;
		}
	}
	
	public void begin(CommandRB command, Action action) {
		if(desiredReading == NavXReadingType.ANGLE_YAW) NavX.reset();
	}
}
