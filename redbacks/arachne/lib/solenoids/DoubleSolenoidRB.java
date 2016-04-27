package redbacks.arachne.lib.solenoids;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Functions as a double solenoid, but provides a workaround to get the state of the solenoid.
 * 
 * @author Sean Zammit
 */
public class DoubleSolenoidRB extends DoubleSolenoid
{
	Value state = Value.kReverse;
	
	/**
	 * @param portForward The forward port on the PCM where the solenoid is plugged into.
	 * @param portReverse The reverse port on the PCM where the solenoid is plugged into.
	 */
	public DoubleSolenoidRB(int portForward, int portReverse) {
		super(portForward, portReverse);
	}
	
	public Value get() {
		return state;
	}
	
	public void set(Value value) {
		if(value != Value.kOff) state = value;
		super.set(value);
	}
}