package redbacks.arachne.lib.motors;

import redbacks.arachne.core.CommandBase;
import redbacks.arachne.lib.commands.CommandRB;
import edu.wpi.first.wpilibj.CANTalon;

/**
 * A replacement motor controller to enable automatic stopping of motors by commands when they finish.
 * 
 * @author Sean Zammit
 */
public class DriveControllerRB extends MotorControllerRB
{
	private double speed = 0;
	
	/**
	 * @param motor The motor controller held inside this class. This is the one that is redirected to whenever a method in this class is used.
	 */
	public DriveControllerRB(CANTalon motor) {
		super(motor);
	}
	
	/**
	 * Used to set the value of the motor. Also tells the motor which command last set its speed.
	 * 
	 * @param outputValue The speed of the motor.
	 * @param command The command that last set the speed of the motor.
	 */
	public void set(double outputValue, CommandRB command) {
		lastCommand = command;
		speed = outputValue;
		CommandBase.driver.isAutoController = true;
		if(!command.motorList.contains(this)) command.motorList.add(this);
	}
	
	/**
	 * Used to disable the motor when the command finishes.
	 */
	public void disable() {
		speed = 0;
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
