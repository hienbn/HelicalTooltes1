package GeometryPackage;

/** This class describes the common geotrical parameters of a Workpiece.
 * 
 * @author HIENBN
 * 
 * @Field
 *  private    int inumofTeeth;        //[1] Number of Teeth
	private double dWorkRadius;        //[2] Workpiece Radius 
	private double dHelixAngle;        //[3] Helix angle
	private double dcutLen;            //[6] Cutting Length 

 *
 */
public class CBlank {

	
	public  static final double PI = Math.PI;
	
	private    int inumofTeeth;        //[1] Number of Teeth
	private double dWorkRadius;        //[2] Workpiece Radius 
	private double dHelixAngle;        //[3] Helix angle
	private double dcutLen;            //[6] Cutting Length 

	
	// Constructors
	/**<h1> Default Constructors Set Default: </h1>
	 * <li> Number of Teeth   = 2.
	 *  <li>Workpiece Radius  = 5;
	 *  <li> Helix Angle       = 30;
	 *  <li> Cutting length   =  30;
	 */
	public CBlank()
	{
		inumofTeeth   = 2;
		dWorkRadius   = 5.0;
		dHelixAngle   =30.0*PI/180 ;
		dcutLen       =30.0;
	}
	
	public CBlank(int numofTeeth)
	{
		this();
		this.inumofTeeth = numofTeeth;
	}
	
	
	/** Create Blank object with fully specified parameters
	 * 
	 * @param numofTeeth
	 * @param workRadius
	 * @param helix_angle
	 * @param cuttingLength
	 */
	public CBlank(int numofTeeth, double workRadius, double helix_angle, double cuttingLength)
	{
		inumofTeeth   = numofTeeth;
		dWorkRadius   = workRadius;
		dHelixAngle   = helix_angle;
		dcutLen       = cuttingLength;
		
	}
	
	
	/**<h1> Copy Constructor: </h1>
	 * Create Blank by copy parameters of other Blank
	 * 
	 * @param otherBlank
	 */
	public CBlank( CBlank otherBlank)
	{
		inumofTeeth   = otherBlank.inumofTeeth;
		dWorkRadius   = otherBlank.dWorkRadius;
		dHelixAngle   = otherBlank.dHelixAngle;
		dcutLen       = otherBlank.dcutLen;		
	}

	
/**======== Getters and Setters====================*/
	
	public int getInumofTeeth() {
		return inumofTeeth;
	}

	public void setInumofTeeth(int inumofTeeth) {
		this.inumofTeeth = inumofTeeth;
	}

	public double getdWorkRadius() {
		return dWorkRadius;
	}

	public void setdWorkRadius(double dWorkRadius) {
		this.dWorkRadius = dWorkRadius;
	}

	public double getdHelixAngle() {
		return dHelixAngle;
	}

	public void setdHelixAngle(double dHelixAngle) {
		this.dHelixAngle = dHelixAngle;
	}

	public double getDcutLen() {
		return dcutLen;
	}

	public void setDcutLen(double dcutLen) {
		this.dcutLen = dcutLen;
	}
	
// ==================================//
	
	
	
	
	
	
}
