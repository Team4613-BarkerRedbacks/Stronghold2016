package redbacks.arachne.lib.sensors;

import redbacks.arachne.lib.motors.MotorControllerRB;
import edu.wpi.first.wpilibj.CANTalon;

public class CANEncoder
{
	private final CANTalon talon;
	
	public CANEncoder(CANTalon talon) {
		this.talon = talon;
	}
	
	public CANEncoder(MotorControllerRB talon) {
		this.talon = talon.controller;
	}
	
	public void reset() {
		set(0);
	}
	
	public void set(int value) {
		talon.setEncPosition(value);
	}
	
	public int get() {
		return talon.getEncPosition();
	}
}
