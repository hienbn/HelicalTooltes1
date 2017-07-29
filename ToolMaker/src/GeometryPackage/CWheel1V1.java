package GeometryPackage;


/**<h1> CWheel1V1 class of 1V1 Wheel shape.</h1>
 * <p>
 * 
 *         __________
 *       / \         |
 *      /   \ ____   |
 *     |     |    |  |
 *     |     |    |  |
 *     |     |    DR | 
 *     |     |    |  Dmax
 *     |     |    |  |
 *     |     |    |  | 
 *     |     |____|  |
 *      \   /        |
 *       \./_________|  
 *     |   | | 
 *     -------
 *       TL-TR
 *        
 *  <p> Fields:
 *   Dmax : Maximum Diameter
 *   DR   : Diameter in the rightside
 *   DL   : Dimater in the  leftside      
 *   TR   : Thinkness in the rightside
 *   TL   : Thickness in the leftside     
 *        
 */

public class CWheel1V1 extends CWheel {

	private double DiaMax;
	private double DiaRight;
	private double DiaLeft;
	private double ThicknessRight;
	private double ThicknessLeft;
	private double AngleLeft;
	private double AngleRight;
	private final  String wheelName = "1V1";
	
	
	// Constructors
	public CWheel1V1(double DiaMax, double DiaRight, double DiaLeft, 
			         double ThicknessRight, double ThicknessLeft)
	{
	  	this.DiaMax   = DiaMax;
	  	this.DiaRight = DiaRight;
	  	this.DiaLeft  = DiaLeft;
	  	this.ThicknessRight = ThicknessRight;
	  	this.ThicknessLeft = ThicknessLeft;
		
	}


	public double getDiaMax() {
		return DiaMax;
	}


	public void setDiaMax(double diaMax) {
		DiaMax = diaMax;
	}


	public double getDiaRight() {
		return DiaRight;
	}


	public void setDiaRight(double diaRight) {
		DiaRight = diaRight;
	}


	public double getDiaLeft() {
		return DiaLeft;
	}


	public void setDiaLeft(double diaLeft) {
		DiaLeft = diaLeft;
	}


	public double getThicknessRight() {
		return ThicknessRight;
	}


	public void setThicknessRight(double thicknessRight) {
		ThicknessRight = thicknessRight;
	}


	public double getThicknessLeft() {
		return ThicknessLeft;
	}

	public void setThicknessLeft(double thicknessLeft) {
		ThicknessLeft = thicknessLeft;
	}


	public String getWheelName() {
		return wheelName;
	}
	
   private void calculateAngles()
   {
	   AngleRight = Math.atan((DiaMax -DiaRight)/ThicknessRight);
	   
	   if(ThicknessLeft ==0)
	   {
		   AngleLeft =0;
		   ThicknessLeft = 0.005;
	   }
	   else
	   {
	   AngleLeft = Math.atan((DiaMax -DiaLeft)/ThicknessLeft);
	   }
	   
   }
   
   
   public double getWheelangRight()
   {
	   calculateAngles();
	   return AngleRight;
   }
	
   public double getWheelangLeft()
   {
	   
	   calculateAngles();
	   return AngleLeft;
	   
   }
	
	
	
	
}
