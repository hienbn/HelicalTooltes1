package GeometryPackage;
import javax.swing.JOptionPane;

/**<h1> This class decribe a 3D Vector. </h1>
 * </p>
 * With three compenents I(Ix, Iy, Iz).
 * With some functions:
 *    normalize()
 *    
 * @author HIENBN
 *
 */

public class CWO_Vector {

	private double ix = 0;
	private double iy = 0;
	private double iz = 0;
	
	// Constructors
	
	/** Default Constructor
	 * 
	 */
	public CWO_Vector()
	{
		ix = 0;
		iy = 0;
		iz = 0;
	}
	
	/** Initlize Vector with specified values, no need unit vector
	 * 
	 * @param theIx
	 * @param theIy
	 * @param theIz
	 */
	public CWO_Vector(double theIx, double theIy, double theIz)
	{
		ix = theIx;
		iy = theIy;
		iz = theIz;
		
	}
	
	/** Copy Constructor
	 * 
	 * @param otherVC
	 */
	public CWO_Vector(CWO_Vector otherVC)
	{
		ix = otherVC.ix;
		iy = otherVC.iy;
		iz = otherVC.iz;
	}

	// Getters and Setters
	
	public double getIx() {
		return ix;
	}

	public void setIx(double ix) {
		this.ix = ix;
	}

	public double getIy() {
		return iy;
	}

	public void setIy(double iy) {
		this.iy = iy;
	}

	public double getIz() {
		return iz;
	}

	public void setIz(double iz) {
		this.iz = iz;
	}
	
	/** Normalize its self.
	 *  
	 */
	public CWO_Vector normalize()
	{
		double length = Math.sqrt(ix*ix + iy*iy +iz*iz);
		
		try
		{
			ix = ix/length;
			iy = iy/length;
			iz = iz/length;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Zero vector cannot be normalized", "Error", JOptionPane.ERROR);
			throw e;
		}		
		
		return this;
	}
	
	/** a = a + b
	 * 
	 * @param other
	 * @return reference of itsself.
	 */
	public CWO_Vector plusVec(CWO_Vector other)
	{
		ix =ix +other.ix;
		iy =iy +other.iy;
		iz =iz +other.iz;
		
		return this;
		
	}
	
	/** Plus other vector and return a copy of result
	 *  C = A+B;
	 */
	public CWO_Vector plus_cop_Vec(CWO_Vector other)
	{
		CWO_Vector c = new CWO_Vector();
		c.ix = this.ix + other.ix;
		c.iy = this.iy + other.iy;
		c.iz = this.iz + other.iz;
		
		return c;
	}
	
	/** a = a - b
	 * 
	 * @param other
	 * @return reference of itsself.
	 */
	public CWO_Vector minusVec(CWO_Vector other)
	{
		ix =ix -other.ix;
		iy =iy -other.iy;
		iz =iz -other.iz;		
		return this;		
	}
	
	
}
