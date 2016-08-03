package redbacks.arachne.lib.navx;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;

public class BetterAHRS extends AHRS 
{
	private float offset = 0;
	
	public BetterAHRS(Port serial_port_id) {
		super(serial_port_id);
	}

	public void setYawOffset(float offset) {
		this.offset = offset;
	}

	public float getYaw() {
		float offseted_value = super.getYaw() + offset;
        if (offseted_value < -180) {
            offseted_value += 360;
        }
        if (offseted_value > 180) {
            offseted_value -= 360;
        }        
        return offseted_value;
	}
}
