package GeometryPackage;
import javax.swing.JTextField;

/** <h1> This class describe the gemeotry of Endmill </h1>
 * 
 * @author HIENBN
 *
 */

public class CSquareEM {

	
	
	// Recess Geometry
	/*
	private double dCore2Ratio   =0.8;        //[7] Core2_Ratio
	private double dRecessRatio  =0.85;       //[8] Recess Ratio 
	private double dMarginThick  =20.0;       // [9] Margin THickness
	
	*/
	
	//Clearance Geometry
	private double d1st_Clear_Ang= 11.0;     // [10] First Clearance Ang.
	private double dClear_Width  = 1.0;       //[11] Clearance Width
	private double d2nd_Clear_Ang =17.0;     //[12] Second Clearance
	
	// Gashing
	
	// Endteeth
	
	// Operations Selection
	boolean bFluteOperation =false;
	boolean bHeelOpertation =false;
	boolean bGashingOperation =false;
	boolean b1stClearOperation =false;
	boolean b2stClearOperation =false;	
	boolean b1stEndTeethOperation =false;
	boolean b2stEndTeethOperation =false;
	
	
	// Constructors
	
	public CSquareEM()
	{
		
		
	}
	
	
}
