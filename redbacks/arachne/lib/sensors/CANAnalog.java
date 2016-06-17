package redbacks.arachne.lib.sensors;

import redbacks.arachne.lib.motors.MotorControllerRB;
import edu.wpi.first.wpilibj.CANTalon;

public class CANAnalog
{
	private final CANTalon talon;
	
	public CANAnalog(CANTalon talon) {
		this.talon = talon;
	}
	
	public CANAnalog(MotorControllerRB talon) {
		this.talon = talon.controller;
	}
	
	public void reset() {
		talon.setAnalogPosition(0);
	}
	
	public double get() {
		return talon.getAnalogInPosition();
	}
}
