package redbacks.arachne.lib.math;

public class Vector
{
	public double 
		angle,
		distance;
	
	public Vector(double angle, double distance) {
		this.angle = angle;
		this.distance = distance;
	}
	
	public Vector(double h, double v, boolean thisDoesNothing) {
		this.angle = 90 - Math.atan2(v, h);
		this.distance = Math.sqrt(v*v + h*h);
	}
	
	public Vector addVector(Vector vectorToAdd) {
		return new Vector(getHDistance() + vectorToAdd.getHDistance(), getVDistance() + vectorToAdd.getVDistance());
	}
	
	public void setVector(Vector vectorEqualTo) {
		angle = vectorEqualTo.angle;
		distance = vectorEqualTo.distance;
	}
	
	public double getHDistance() {
		return angle >= 0 ? distance * Math.cos(90 - Math.abs(angle)) : -(distance * Math.cos(90 - Math.abs(angle)));
	}
	
	public double getVDistance() {
		return distance * Math.sin(90 - Math.abs(angle));
	}
}
