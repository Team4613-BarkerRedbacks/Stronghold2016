package redbacks.arachne.lib.checks;

public class Timer {
	double startTime;
	
	public Timer() {}
	
	public void start() {
		startTime = edu.wpi.first.wpilibj.Timer.getFPGATimestamp();
	}
	
	public double get() {
		return edu.wpi.first.wpilibj.Timer.getFPGATimestamp() - startTime;
	}
}
