package GeometryPackage;

// This class used to return result of findanpha function

public class CWheelSetting_out {

	//fields
	//[flag,anpha,anpha1,Y0,Z0,r_min]
	public boolean flag_result;
	public int flag_int;
	public double anpha, anpha1;
	double Y0, Z0;
	double r_min;
	
	public double theta =0;
	
	public CWheelSetting_out(boolean flag_result, double anpha,double anpha1, double Y0, double Z0, double r_min )
	{
		this.flag_result = flag_result;
		this.anpha =anpha;
		this.anpha1 = anpha1;
		this.Y0 = Y0;
		this.Z0 = Z0;
		this.r_min = r_min;
		flag_int =0;
	}

	public CWheelSetting_out(boolean flag_result,int flag_int, double anpha,double anpha1, double Y0, double Z0, double r_min )
	{
		this.flag_result = flag_result;
		this.flag_int = flag_int;
		this.anpha =anpha;
		this.anpha1 = anpha1;
		this.Y0 = Y0;
		this.Z0 = Z0;
		this.r_min = r_min;
		flag_int =0;
	}
	
	public CWheelSetting_out()
	{
		this(false, 0, 0, 0, 0, 0);
		
	}
	
	/** Result of fluting multiple teeth endmill
	 * 
	 * @param flag_result
	 * @param anpha
	 * @param theta
	 */
	public  CWheelSetting_out(int flag_result,double anpha,double theta)
	{
		this(false, 0, 0, 0, 0, 0);
		
		this.flag_int = flag_int;
		this.anpha = anpha;
		this.theta = theta;
	}
	
	
	
	
	
}
