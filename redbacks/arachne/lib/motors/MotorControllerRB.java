package redbacks.arachne.lib.motors;

import redbacks.arachne.lib.commands.CommandRB;
import edu.wpi.first.wpilibj.CANTalon;

/**
 * A replacement motor controller to enable automatic stopping of motors by commands when they finish.
 * 
 * @author Sean Zammit
 */
public class MotorControllerRB
{
	/** The motor controller held inside this class. */
	public final CANTalon controller;
	
	/** The last command to set the value of this motor. Used to automatically stop the motor when the command finishes. */
	public CommandRB lastCommand;
	
	/** Whether commands should automatically stop this motor when they finish. Disable for motors like the polycord. */
	public boolean shouldCancel = true;
	
	private double speed = 0;
	
	/**
	 * @param motor The motor controller held inside this class. This is the one that is redirected to whenever a method in this class is used.
	 */
	public MotorControllerRB(CANTalon motor) {
		controller = motor;
	}
	
	/**
	 * Used to set the value of the motor. Also tells the motor which command last set its speed.
	 * 
	 * @param outputValue The speed of the motor.
	 * @param command The command that last set the speed of the motor.
	 */
	public void set(double outputValue, CommandRB command) {
		controller.set(outputValue);
		lastCommand = command;
		speed = outputValue;
		if(!command.motorList.contains(this)) command.motorList.add(this);
	}
	
	/**
	 * Used to disable the motor when the command finishes.
	 */
	public void disable() {
		controller.set(0);
		speed = 0;
	}
	
	/**
	 * Used to set the motor to not be stopped when the command that set it ends.
	 * 
	 * @return This motor. Reason being that it allows this method to be used in the same line as the constructor.
	 */
	public MotorControllerRB setUncancellable() {
		shouldCancel = false;
		return this;		
	}
	
	/**
	 * Used to get the current speed of the motor.
	 * 
	 * @return The current speed of the motor, from -1.0 to 1.0.
	 */
	public double get() {
		return speed;
	}
}
