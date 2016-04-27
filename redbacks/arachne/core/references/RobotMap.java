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
		isVisionEnabled = false;
	
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
    
    //Autonomous magic numbers
    public static final int
    	autoAngleCor = 500,
    	
    	//3 taken off magnitude of each value
    	autoCross2Ang = 48,
    	autoCross3Ang = 26,
    	autoCross4Ang = -8,
    	autoCross5Ang = -40,
    	
    	autoCross2Dis = 6027,
    	autoCross3Dis = 4287,
    	autoCross4Dis = 3500,//3826,
    	autoCross5Dis = 5123;

    public static final double
    	autoEncMul = 0.6D;
    
    //Magic Numbers 
    public static final int
		turretIntakePos = 11000,
		
		turretCornerH = 57000,//49000,//50000//47500,
		turretCornerR = -5600,//-5750,//-5950,//Done
		
		turretEdgeH = 70000,//68000,
		turretEdgeR = -6000,//-6225,//Done
		
		turretMidH = 63000,
		turretMidR = -5800,//-6100,//Done

		turretFrontH = 67000,
		
		turretSPH = 80000,//71000,//69000,
		turretSPR = 6625,//6425//Done
		
		turretLBH = 45500,
		turretLBR = 2490,//Done
		
		turretD2H = 47500,
		turretD2R = 1853,
				
		turretD3H = 46500,
		turretD3R = 853,
						
		turretD4H = 45000,
		turretD4R = -500,
				
		turretD5H = 43000,
		turretD5R = -1600,
								
		turretCentreOffsetL = 0,
		turretCentreOffsetR = -520;
    
    public static final double
    	turretShootXTolerance = 5.0D, //TODO Add camera tolerance reference to RobotMap
    	turretShootYTolerance = 7.5D,
    	
    	turretTiltSpeed = 1D,
    	turretTiltSlowSpeed = 0.15D,
    	    	
    	turretRotationSpeed = 1.0D,
    	turretCentraliseSpeed = 0.2D,
    	turretPrecisionSpeed = 0.125D; //WAS 0.1D
}