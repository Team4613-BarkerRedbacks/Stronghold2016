package redbacks.arachne.lib.actions;

import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.checks.CheckNever;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;
import redbacks.arachne.lib.motors.MotorControllerRB;

/**
 * Holds all motor actions.
 * 
 * @author Sean Zammit
 */
public class ActionMotor
{
	private ActionMotor() {}
	
	/**
	 * Basic Action class used to immediately set a motor to a certain speed.
	 * For gradual speed changes, use RampTime or RampAccel.
	 */
	public static class Set extends Action
	{
		private MotorControllerRB motor;
		private double speed;
		
		/**
		 * @param motor The motor being set.
		 * @param speed The speed that the motor should be set to.
		 * @param check The condition that will finish the action.
		 */
		public Set(MotorControllerRB motor, double speed, Check check) {
			super(check);
			this.motor = motor;
			this.speed = speed;
		}
		
		public void onStart(CommandRB command) {
			motor.set(speed, command);
		}
		
		public void runAction(CommandRB command) {
			motor.set(speed, command);
		}
	}
	
	/**
	 * Basic Action class used to stop the motor.
	 */
	public static class Disable extends Set
	{
		/**
		 * @param motor The motor being set.
		 */
		public Disable(MotorControllerRB motor) {
			super(motor, 0, new CheckBoolean(true));
		}
	}
	
	/**
	 * Action class used to ramp a motor to a certain speed over a set period of time.
	 */
	public static class RampTime extends Action
	{
		private MotorControllerRB motor;
		private double speed, time, startSpeed, startTime;
		private boolean shouldEnd;
		
		/**
		 * Default constructor.
		 * 
		 * @param motor The motor being set.
		 * @param speed The speed that the motor should reach at the end of the action.
		 * @param time The time over which the change in speed should occur.
		 * @param check The condition that will finish the action.
		 */
		public RampTime(MotorControllerRB motor, double speed, double time, Check check) {
			this(motor, speed, time, check, false);
		}
		
		/**
		 * Constructor to set the action to automatically finish once the time is up.
		 * 
		 * @param motor The motor being set.
		 * @param speed The speed that the motor should reach at the end of the action.
		 * @param time The time over which the change in speed should occur.
		 * @param shouldEndWhenComplete Whether the action should automatically finish once the time is up.
		 */
		public RampTime(MotorControllerRB motor, double speed, double time, boolean shouldEndWhenComplete) {
			this(motor, speed, time, new CheckNever(), shouldEndWhenComplete);
		}
		
		private RampTime(MotorControllerRB motor, double speed, double time, Check check, boolean shouldEndWhenComplete) {
			super(check);
			this.motor = motor;
			this.speed = speed;
			this.time = time;
			this.shouldEnd = shouldEndWhenComplete;
		}
		
		public void onStart(CommandRB command) {
			startSpeed = motor.get();
			startTime = command.timeSinceInitialized();
		}
		
		public void runAction(CommandRB command) {
			double actionTime = command.timeSinceInitialized() - startTime;
			if(speed >= startSpeed) motor.set(Math.min(startSpeed + actionTime / time * Math.abs(speed - startSpeed), speed), command);
			else motor.set(Math.max(startSpeed - actionTime / time * Math.abs(speed - startSpeed), speed), command);
		}
		
		public boolean isComplete(CommandRB command) {
			if(shouldEnd) return super.isComplete(command) || command.timeSinceInitialized() - startTime > time;
			return super.isComplete(command);
		}
		
		public void onFinish(CommandRB command) {
			motor.set(speed, command);
		}
	}
	
	/**
	 * Action class used to ramp a motor to a certain speed at a set rate of change.
	 */
	public static class RampAccel extends Action
	{
		private MotorControllerRB motor;
		private double speed, maxAccel, startSpeed, startTime;
		private boolean shouldEnd;
		
		/**
		 * Default constructor.
		 * 
		 * @param motor The motor being set.
		 * @param speed The speed that the motor should reach at the end of the action.
		 * @param maxAccelPerSec The maximum change in speed of the motor per second. e.g. If the motor starts at -1, and this is set to 0.5, it will take 4 seconds to reach 1.
		 * @param check The condition that will finish the action.
		 */
		public RampAccel(MotorControllerRB motor, double speed, double maxAccelPerSec, Check check) {
			this(motor, speed, maxAccelPerSec, check, false);
		}
		
		/**
		 * Constructor to set the action to automatically finish once the time is up.
		 * 
		 * @param motor The motor being set.
		 * @param speed The speed that the motor should reach at the end of the action.
		 * @param maxAccelPerSec The maximum change in speed of the motor per second. e.g. If the motor starts at -1, and this is set to 0.5, it will take 4 seconds to reach 1.
		 * @param check The condition that will finish the action.
		 * @param shouldEndWhenComplete Whether the action should automatically finish once the time is up.
		 */
		public RampAccel(MotorControllerRB motor, double speed, double maxAccelPerSec, boolean shouldEndWhenComplete) {
			this(motor, speed, maxAccelPerSec, new CheckNever(), shouldEndWhenComplete);
		}
		
		private RampAccel(MotorControllerRB motor, double speed, double maxAccelPerSec, Check check, boolean shouldEndWhenComplete) {
			super(check);
			this.motor = motor;
			this.speed = speed;
			this.maxAccel = maxAccelPerSec;
			this.shouldEnd = shouldEndWhenComplete;
		}
		
		public void onStart(CommandRB command) {
			startSpeed = motor.get();
			startTime = command.timeSinceInitialized();
		}
		
		public void runAction(CommandRB command) {
			double actionTime = command.timeSinceInitialized() - startTime;
			if(speed >= startSpeed) motor.set(Math.min(startSpeed + actionTime * maxAccel, speed), command);
			else motor.set(Math.max(startSpeed - actionTime * maxAccel, speed), command);
		}
		
		public boolean isComplete(CommandRB command) {
			if(shouldEnd) return super.isComplete(command) || motor.get() == speed;
			return super.isComplete(command);
		}
		
		public void onFinish(CommandRB command) {
			motor.set(speed, command);
		}
	}
}
