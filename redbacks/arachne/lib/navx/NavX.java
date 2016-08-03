package redbacks.arachne.lib.navx;

import edu.wpi.first.wpilibj.SPI;

//JAVADOC

public class NavX
{
	public static BetterAHRS navx = new BetterAHRS(SPI.Port.kMXP);
	
	//Rotation functions
	public static double getPitch() {
		return navx.getRoll();
	}
	
	public static double getRatePitch() {
		return navx.getRawGyroY();
	}
	
	public static double getRoll() {
		return navx.getPitch();
	}
	
	public static double getRateRoll() {
		return navx.getRawGyroX();
	}
	
	public static double getYaw() {
		return navx.getYaw();
	}
	
	public static double getRateYaw() {
		return navx.getRawGyroZ();
	}
	
	//Acceleration functions
	public static double getAccelForward() {
		return navx.getWorldLinearAccelX();
	}
	
	public static double getAccelRight() {
		//TEST Axes
		return navx.getWorldLinearAccelY();
	}
	
	public static double getAccelUp() {
		//TEST Axes
		return navx.getWorldLinearAccelZ();
	}
	
	//Speed functions
	public static double getSpeedForward() {
		return navx.getVelocityX();
	}
	
	public static double getSpeedRight() {
		//TEST Axes
		return navx.getVelocityY();
	}
	
	public static double getSpeedUp() {
		//TEST Axes
		return navx.getVelocityZ();
	}
	
	public static void reset() {
		navx.reset();
		navx.setYawOffset(0);
	}
	
	public static void setYaw(float offset) {
		navx.setYawOffset(offset);
	}
}
