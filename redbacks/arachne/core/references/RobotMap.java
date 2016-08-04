package redbacks.arachne.core.references;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Global variables
	public static boolean
		isVisionEnabled = true;
	
	//Talon SRX CAN IDs
	//Start at 2.
	public static final int
	    driveMotorLID = 2, //Uses 2, 4, 6
	    driveMotorRID = 3, //Uses 3, 5, 7

	    turretPanMotorID = 10,
	    turretTiltMotorID = 11,
	    	    
	    intakeMotorID = 9,
	    
	    launcherPolycordMotorID = 12,
	    launcherShootLMotorID = 13,
	    launcherShootRMotorID = 14;
    
    //Solenoids
    public static final int
    	shifterSolID = 0,
    	armIntakePosSolID = 2,
    	armSolID = 5;
    
    //Relays
    public static final int
    	lightsRelayID = 0;
    
    //Magic Numbers 
    public static final double
    	turretEPD = 65.42D,
    
    	//turretShootXTolerance = 5.0D, //TODO Add camera tolerance reference to RobotMap
    	//turretShootYTolerance = 7.5D,
    	
    	//XXX Turret movement speeds. If it needs some more power, increase these. The slower speeds are used when it's closer to its destination.
    	turretTiltSpeed = 1D,
    	turretTiltSlowSpeed = 0.15D,
    	    	
    	turretRotationSpeed = 1.0D,
    	turretCentraliseSpeed = 0.2D,
    	turretPrecisionSpeed = 0.075D;
}