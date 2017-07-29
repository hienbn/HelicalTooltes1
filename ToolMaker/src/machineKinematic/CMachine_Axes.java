package machineKinematic;

import Jama.Matrix;

/**<h1> This class includes = [A, W, G(gx, gy, gz)] </h1>
 * 
 * G: wheel location in Workpiece coordinate.
 * Is used as out put of inverse kinematic.
 *
 *  Fields:<p>
 *  <li>A:   A -axis
 *  <li>W:   W- axis
 *  <li>G:   (Gx, Gy, Gz) vector 3x1
 *
 */

public class CMachine_Axes {

	public double A; // A- axis
	public double W; // W-axis	
	public Matrix G; // matrix [3x1] = [Gx, Gy, Gz]
	
	public double X;
	public double Y;
	public double Z;
	
	// Default Constructor
	public CMachine_Axes()
	{
		G = new Matrix(3, 1); // 3x1 vector		
	}
		
	
	
}
