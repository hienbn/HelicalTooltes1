package GeometryPackage;

import Jama.*;


/**<h2> This class describe wheel location as its center location.</h2>
 * <p>And its Axis orientation
 * <p>iG(Ix, Iy, Iz) : Wheel orientation
 * <p>rG(Gx, Gy, Gz) : Wheel center location
 * 
 * @author HIENBN
 *
 */

public class CWheelLocation {
     
	//Wheel's axis orientation in WorkCoordinate
	private Matrix iG; 
	
	//Wheel's center location in WorkCoordinate
	private Matrix rG;	
	public boolean  bool = false;

	/** Get reference of WheelOrientation
	 * 
	 * @return 
	 */
	public Matrix getWheelOrientation() {
		return iG;
	}

	/** Set reference of WheelOrientation to ref of input's array
	 * 
	 * @param iG
	 */
	public void setWheelOrientation(Matrix iG) {
		this.iG = iG;
	}

	/** Get ref of wheelcenter
	 * 
	 * @return ref of wheel center.
	 */
	public Matrix getWheelCenter() {
		return rG;
	}

	/** Set Wheelcenter to a ref
	 * 
	 * @param rG
	 */
	public void setWheelCenter(Matrix rG) {
		this.rG = rG;
	} 
	
	
}
