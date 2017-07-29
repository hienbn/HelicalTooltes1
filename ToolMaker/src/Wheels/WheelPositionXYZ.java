package Wheels;

public class WheelPositionXYZ {

	private double x_offset;
	private double y_offset;
	private double z_offset;
	
	// Constructor
	// In the future read initialize it from file
	
	
	/** Set wheel location in machine coordinate system Oxyz
	 * 
	 * @param x_off
	 * @param y_off
	 * @param z_off
	 */
	public void setWheellocation(double x_off, double y_off, double z_off)
	{
		x_offset = 	x_off;
		y_offset =  y_off;
		z_offset =  z_off;
	}
	
	// Get X-offset of the current wheel.
	public double getXOff()
	{
		return x_offset;
	}
	
	// Get Y-offset of the current wheel.
	public double getYOff()
	{
		return y_offset;
	}
	
	// Get Z-offset of the current wheel.
	public double getZOff()
	{
		return z_offset;
	}
	
}
