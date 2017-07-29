package GeometryPackage;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.lang.management.ThreadMXBean;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JOptionPane;

import HAlgorithm.HFile;
import Jama.Matrix;
import machineKinematic.CMachine_Axes;
import machineKinematic.CinverseKinematic;

/** <h1> This class describe the geometry of heel/ sub fluting operation  </h1>
 * 
 * @author HIENBN
 *
 */
public class CRecess extends CBlank implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final double PI = Math.PI;
	
	// Fields
	private double dCore2Ratio   =0.8;        //[7] Core2_Ratio
	private double dRecessRatio  =0.85;       //[8] Recess Ratio 
	private double dMarginThick  =20.0*PI/180;       //[9] Margin THickness
	private double endPointAngle;
	
	
	//OUT PUT:
	private Matrix Ii = new Matrix(3,1); // Calculated Wheel orientation
	private Matrix Gi = new Matrix(3,1); // [X0,Y0, Z0]
	
	private CWheel1V1 wheel1V1;
	private double settingAngle;
	private ContactLine contactLine = new ContactLine();
	
	private CMachine_Axes machine_Axes = new CMachine_Axes();
	
	public static int flag_out =0;
	
	private double cutLen_Offset =0; // ctl = ctl + ctl_Ofset
	private double back_Distance =0;
	
	
	
	/** Constructor from a Blank and Core2Radio, RecessRatio, Marginthick
	 * 
	 * @param aBlank
	 * @param dCore2Ratio
	 * @param dRecessRatio
	 * @param dMarginThick
	 */
	public CRecess(CBlank aBlank ,double dCore2Ratio, double dRecessRatio, double dMarginThick)
	{
		super(aBlank);
		this.dCore2Ratio   = dCore2Ratio;
	    this.dRecessRatio  = dRecessRatio;
	    this.dMarginThick  = dMarginThick;
	    
	}
	
	// Constructors
	
	/** <h1> Defaul Constructors </h1>
	 *  dCore2Ratio   =0.8;
	    dRecessRatio  =0.85;
	    dMarginThick  =20.0;
	 */
	public CRecess()
	{
		dCore2Ratio   =0.8;
	    dRecessRatio  =0.85;
	    dMarginThick  =20.0;
	}
	
	/** Fully specified Constructor
	 * 
	 * @param dCore2Ratio
	 * @param dRecessRatio
	 * @param dMarginThick
	 */
	public CRecess(double dCore2Ratio, double dRecessRatio, double dMarginThick)
	{
		this.dCore2Ratio   = dCore2Ratio;
	    this.dRecessRatio  = dRecessRatio;
	    this.dMarginThick  = dMarginThick;
	    
	}
	
	/** Copy Constructor
	 * 
	 * @param other
	 */
	
	public CRecess( CRecess other)
	{
		dCore2Ratio   = other.dCore2Ratio;
	    dRecessRatio  = other.dRecessRatio;
	    dMarginThick  = other.dMarginThick;
	}

	
	// Setter and Getter
	public double getdCore2Ratio() {
		return dCore2Ratio;
	}

	public void setdCore2Ratio(double dCore2Ratio) {
		this.dCore2Ratio = dCore2Ratio;
	}

	public double getdRecessRatio() {
		return dRecessRatio;
	}

	public void setdRecessRatio(double dRecessRatio) {
		this.dRecessRatio = dRecessRatio;
	}

	public double getdMarginThick() {
		return dMarginThick;
	}

	public void setdMarginThick(double dMarginThick) {
		this.dMarginThick = dMarginThick;
	}
	
	public void setWheel(CWheel1V1 aWheel)
	{
		this.wheel1V1 = aWheel;
	}
	
	public void setSettingAngle(double angle)
	{
		settingAngle = angle;
	}
	
	public void calCutLenOffset()
	{
		cutLen_Offset = -contactLine.getXmax_inCTLine();
	}
	
	public void calBackDistance()
	{
		back_Distance = contactLine.getXmax_inCTLine();
	}
	
	public double getEndpointAngle()
	{
		endPointAngle = 2*PI/getInumofTeeth() - dMarginThick;
		
		return endPointAngle;
	}
	
    /**  ===FINDING WHEEL LOCATION FOR RECESSING PROCESS===
     */
	
	/*
     *  <Comment here>
     *  Input: 
     *       - Wheel geometry
     *       - Recess Geometry
     *       - Setting angle
     *  
     *  Output:
     *        - Ii Wheel location Matrix(3,1) ---> Vector
     *        - Gi Wheel center location Matrix(3,1)
     *        - openAngle of the generated flute
     *        - xMax  = {xMax = max{xi}| contact points<xi, yi,zi>}
     *  
     *  Algorithm:  BI- SECTION ALGORITHM
     *  
     *        - Idea:
     *        - Find <Y0, Z0> of the wheel center in plane workpiece plan
     *        - convert back <Y0, Z0> ----> <I,G> wheel location.
     *        
     *        
     *  Description:
     *      ^Z    
     *      |
     *      |
     *      |
     *      |O
     *      ---------------->Y     
     *      <OYZ plane>   
     *        
     *  Revision:
     *    -30/4/2014: Created    
     *    
     *        
     */
	
	public void wheelLCTforHeel( CBlank theBlank, CWheel1V1 wheel1V1, double settingAngle)
	{
	  /* Input: 
	   * 
	   */		
		
		
		
		
		
	}
	
	
	/** Find Z location of wheel center so that first sleeve generate core radius
	 * [Zvl,flag] = findY_stf_rcore(Rw,R,Y0,r_Core,anpha,beta)
	 */
	
	/**
	 * Find Z location of wheel center so that first sleeve generate core radius
	 * [Zvl,flag] = findY_stf_rcore(Rw,R,Y0,r_Core,anpha,beta)
	 * 
	 * @param Rw      : Wheel radius of the side face that generated core radius
	 * @param R       : Workpiece cylinder Radius
	 * @param Y0      : Given Y0 coordinate of wheel center in OYZ plane
	 * @param r_Core  : Core radius <---> Inscribed circle radius
	 * @param anpha   : setting angle 32<degree>
	 * @param beta    : helix angle   30<degree>
	 * @return
	 */
	public static double findY_stf_rcore(double Rw, double R, double Y0, double r_Core, double anpha, double beta)
	{
		double Z0=0;//
		double Zvl =0;// use for calculation
		
		double Z01 = 0;// lower bound
		double Z02 = Rw + r_Core;// upper bound
		
		// sub variables
		int bool = 1, flag = 1;
		int i_whileloop =0;
		double r_min = r_Core +1;
		double r_min1 =0, r_min2 =0;
		
		double lamda = anpha; // Setting angle ex:32
		double m_helix = beta;
		final double rCorePrecision =0.001;// Precision
		
		
		// While loop to find Zvl here
		while( (Math.abs(r_min - r_Core)>rCorePrecision) && (i_whileloop<50)&&(bool==1))
		{
			i_whileloop++;
			
			for(int i_loop =1; i_loop<=3; i_loop++)
			{
				if(i_loop ==1) // low bound
				{
					Zvl = Z01;
				}
				else if(i_loop ==2) // up bound 
				{
					Zvl = Z02;
				}
				else if(i_loop >2) // mid-point
				{
					Zvl = (Z01 +Z02)/2;
				}
				
				// Determine Zvl
				
				if(i_loop ==1)
				{
					r_min1 = CFluting.Elip_Circle(Rw, R, Y0, Zvl, 0, anpha, beta);
				}
				else if(i_loop ==2)
				{
					r_min2 = CFluting.Elip_Circle(Rw, R, Y0, Zvl, 0, anpha, beta);
					
					
					// Exception cases
					
					if( (r_min1 -r_Core <0)&&(r_min2 -r_Core <0))// exception case
					{
						bool =0;
						anpha =0;
						flag = 0;
					//	JOptionPane.showMessageDialog(null, "Does not exist Zvl", "Error", JOptionPane.ERROR_MESSAGE);
					    
					}
					else if((r_min1 -r_Core >0)&&(r_min2 -r_Core >0))
					{
						bool =0;
						anpha =0;
						flag =-1;
					//	JOptionPane.showMessageDialog(null, "Does not exist Zvl", "Error", JOptionPane.ERROR_MESSAGE);
					    
					}
					
					
					
				}
					
				else if(i_loop>2)
				{
					if(Z01>Z02)
					{
						bool =0;
						anpha =0;
						flag =-2;
					//	JOptionPane.showMessageDialog(null, "Does not exist Zvl Z01>Z02", "Error", JOptionPane.ERROR_MESSAGE);
					    
					}
					
					// Calculate r-min
					r_min = CFluting.Elip_Circle(Rw, R, Y0, Zvl, 0, anpha, beta);
					
					if(r_min > (r_Core +rCorePrecision))
					{
						Z02 =(Z01 + Z02)/2;
					}
					else
					{
						Z01 =(Z01 + Z02)/2;
					}
					
					
				}				
				
			}			
			
			
		}
		
		
		flag_out = flag; // output 
		
		return Z0 = Zvl;
	}
	
	/** This function find <Y0,Z0> to satisfy the Recess ratio
	 *  	 
	 */
	
	/*
	 * INPUT:
	 * 
	 * 
	 * OUTPUT: Ii, G, X_max, endAngle
	 * 
	 * 
	 * ALGORITHM:
	 *       - Core radius is generated by the first sleeve of wheel.
	 *       - Recess radius is generated by the the other side of the wheel.
	 *       -
	 * 
	 * CREATED: 
	 *    - 31/4/2014
	 *
	 * REVISION:
	 *    - 19/5/2017 Converse to Java
	 *  
	 * 
	 * 
	 * 
	 */
	public void findYZforRecess(CBlank aBlank, CWheel1V1 aWheel1V1, double settingAngle)
	{
		// Read Flute parameters
	    double R = aBlank.getdWorkRadius();  // Work radius
		double m_helix = (aBlank.getdHelixAngle()); // Helix angle
	    double beta = m_helix; // helix angle 

	    // Read Wheel parameters
		double Rw = aWheel1V1.getDiaRight();   // Wheel radius in the right side that directly generates rake face
		double RL = aWheel1V1.getDiaLeft();  // Wheel radius in the left side
		double RM = aWheel1V1.getDiaMax();   //Wheel radius in the midle side, biggest diameter
		double thickness = aWheel1V1.getThicknessLeft() + aWheel1V1.getThicknessRight();
		double u_d = aWheel1V1.getThicknessRight();
		double TH_R = u_d;
		double TH_L = thickness - u_d;
		double r_Recess = dRecessRatio*R;
		double r_Core2 = dCore2Ratio*R;
		
		//temp variable
		double ep_precision = 0.001;
		double anpha = PI/2 - settingAngle;
		double Ome = 2*PI;
		double r_Recess_min = r_Recess+1;
		double r_Core2_min = r_Core2+1;
		int i_while_loop =0;
		double r_Recess_min1 =0;
		double r_Recess_min2 =0;
		
		//============================//
		/* ===Determine Y0 is 0? ====*/
		//===========================//
		
		//OUTPUT: Y0, Z0
		double Y0= 0, Z0 = Rw + r_Core2;
		double Ysign =0;
		double Y01 =0, Y02=0;
		int bool =1;
		int flag =1;
		
		// Determine Recess radius
		r_Recess_min = CFluting.Elip_Circle(RL, R, Y0, Z0, thickness, settingAngle, beta);
		
		if(r_Recess_min >r_Recess)
		{
			Y01   =  0;
			Y02   = (Rw*Math.cos(anpha) + r_Recess);
			Ysign = 1;
			
			
		}
		else
		{
			Y02 =0;
			Y01 = -(Rw*Math.cos(anpha) + r_Recess);
			Ysign = -1;			
		}
		
		// While loop here
		
		while((Math.abs(r_Recess_min - r_Recess) > ep_precision)&&(i_while_loop<20))
		{
			i_while_loop++;
			
			for(int i_loop =1; i_loop<=3; i_loop++)
			{
				if(i_loop ==1)
				{
					Y0 = Y01;
				}
				else if(i_loop==2)
				{
					Y0 = Y02;
				}
				else if(i_loop>2)
				{
					Y0 = (Y01 + Y02)/2;
				}
				
				// Calculate required Z0 to satisfy Core
				// Obtain the recess radius for calcutin Y0.
				
				if(i_loop ==1)
				{
					Z0 = findY_stf_rcore(Rw, r_Recess, Y0, r_Core2, settingAngle, beta);
					r_Recess_min1 = CFluting.Elip_Circle(RL, R, Y0, Z0, thickness, settingAngle, beta);
				}
				else if(i_loop == 2)
				{
					Z0 = findY_stf_rcore(Rw, r_Recess, Y0, r_Core2, settingAngle, beta);
					r_Recess_min2 = CFluting.Elip_Circle(RL, R, Y0, Z0, thickness, settingAngle, beta);
				
					if((r_Recess_min1 -r_Recess)*(r_Recess_min2 -r_Recess)>0) // Y is out range
					{
						bool =0;
						Y0   =0;
						flag = -1;
					//	JOptionPane.showMessageDialog(null, "Y is out search range in recessing", "Error", JOptionPane.ERROR_MESSAGE);
					
					}
					
					
				}
				else // >2 main while loop to find <Y0, Z0>
				{
					if(Y01 > Y02)
					{
						bool = 0;
						Y0   = 0;
						flag = -2;
					//	JOptionPane.showMessageDialog(null, "Y is out search range in recessing Y01 >Y02", "Error", JOptionPane.ERROR_MESSAGE);
					
					}
					
					if(i_while_loop ==20)
					{
						bool = 0;
						Y0   = 0;
						flag = -3;
					//	JOptionPane.showMessageDialog(null, "Y is out search range in recessing, iloop reached ", "Error", JOptionPane.ERROR_MESSAGE);
											
					}
					
					Z0 = findY_stf_rcore(Rw, r_Recess, Y0, r_Core2, settingAngle, beta);
					
					if(flag_out ==1)
					{
						r_Recess_min = CFluting.Elip_Circle(RL, R, Y0, Z0, thickness, settingAngle, beta);
						
					}
					
					if(flag_out ==1)
					{
						if(Ysign ==-1)
						{
							if(r_Recess_min > (r_Recess +ep_precision))
		                        {Y01 =(Y01 + Y02)/2;}
		                    else
		                        {Y02 =(Y01 + Y02)/2;}
							
						}
						else if(Ysign ==1)
						{
							if(r_Recess_min > (r_Recess +ep_precision))
		                        {Y01 =(Y01 + Y02)/2;}
		                    else
		                        {Y02 =(Y01 + Y02)/2;}
						}
							
						
					}
					else if(flag_out == 0)
					{
						Y01 = (Y01 + Y02)/2;
						
					}
					else if(flag_out == -1)
					{
						Y02 = (Y01 + Y02)/2;
					}
						
					
				}				
				
				
			}
			
			
		}		
		
		// Set <I, G>
		this.Gi.set(0, 0, 0); // X =0
		this.Gi.set(1, 0, Y0);
		this.Gi.set(2, 0, Z0);
		
		this.Ii.set(0, 0, Math.cos(anpha));
		this.Ii.set(1, 0, Math.sin(anpha));
		this.Ii.set(2, 0, 0);
		
	  System.out.println("<Y0, Z0> = <" + Y0 + " ," + Z0 + ">");	
		
	}
	
	
	public String toString()
	{
		String info;
		info = "I<"+ Ii.get(0, 0) + "," + Ii.get(1, 0) + "," + Ii.get(2, 0)+">" + "\r\n" 
		        + "G<"+ Gi.get(0, 0) + "," + Gi.get(1, 0) + "," + Gi.get(2, 0)+">";
		
		return info;
	}
	
	public void getContactLine()
	{
		double R  = getdWorkRadius();
		double Y0 = Gi.get(1, 0);
		double Z0 = Gi.get(2, 0);
		double beta = getdHelixAngle();
		
		
		contactLine.contactpoint_generation(R, Y0, Z0, beta, PI/2 - settingAngle, wheel1V1);
		
		//return ctl;
		
	}
	
	public ContactLine getCalculatedCTLine()
	{
		return contactLine;
	}
	
	public double[][] getProfile()
	{
		return contactLine.getfullProfile();
	}
	
	/** Calculate wheel location and write NC for file for machining Recess.
	 * theRecess object is modified after this function.
	 */
	public void calAndWriteNCforRecessing(CBlank theBlank, CWheel1V1 the1V1wheel, double flute_setting_angle)
	{
		
		// Find the Wheel location in grinding recess
		findYZforRecess(theBlank, the1V1wheel, flute_setting_angle);

		// Generate Contact Line from the found Wheel location.
		getContactLine();
		
		double xMax = contactLine.getXmax_inCTLine();
		double endAngle = contactLine.getPolarAngleofEndPoint();		
		
		
	}
	
	
	/** Obtain Axesposition for recessing operation
	 * 
	 * @return
	 */
	
	public CMachine_Axes getWheelPose()
	{
		machine_Axes = CinverseKinematic.cvWheelPose_toAxes(Ii, Gi);
		
		return machine_Axes;
	}
	
	/** Writing the NCCode into NC file by using found AxesPosition.
	 * 
	 * @param ncFile
	 * @param wheelPoseforFlute
	 * @param theBlank
	 */
	public void writeNCCodetoFile(BufferedWriter bw_ncFile,CMachine_Axes wheelPoseforFlute, CBlank theBlank)
	{
						
	 calBackDistance();	
	 calCutLenOffset();
	 double back_dis = 2*getdWorkRadius() + back_Distance;
	 
		// Get the NC code at the three points in grinding the flute.
	 List<CMachine_Axes> NCcode3Points = CinverseKinematic.getStartnEndPositions(wheelPoseforFlute, 
			                                                      theBlank, back_dis, cutLen_Offset);
	
	 // Loop through 3 points and write the NC code.
	 int numofTeeth = theBlank.getInumofTeeth();	 
	 CMachine_Axes mca_current = NCcode3Points.get(0);
	 CMachine_Axes mca_end = NCcode3Points.get(2);
	 
	 double del_A_perFlute;
	 
	
	 //-((pi-Endpoint_angle)-m_margin)*180/pi;
	 double del_AOffset = -(  (PI- contactLine.getPolarAngleofEndPoint()) - getdMarginThick()  )*180/PI;
	 
	 // require the feed either
	 for(int i=0; i< numofTeeth; i++)
	 {
		 	 
		 del_A_perFlute = 360*(i)/numofTeeth;
		 // Approaching with i== 1;
		 if(i == 0)
		 {			 
			 HFile.bwriteln(bw_ncFile, "");
			 HFile.bwriteln(bw_ncFile, "(Approaching)");			 
			 String str = String.format("G90G00 X0.0 W0.0 A%.3f", mca_current.A*180/PI + del_AOffset);			 
			 HFile.bwriteln(bw_ncFile, str);
			 
			 String str_YZW = String.format("Y%.3f Z%.3f W%.3f", mca_current.Y,mca_current.Z, -mca_current.W*180/PI);		
			 HFile.bwriteln(bw_ncFile, str_YZW);
			 
		 }
		 		 		 
		 // Move to XZ position
		 HFile.bwriteln(bw_ncFile, "");
		 HFile.bwriteln(bw_ncFile, String.format("(>> RECESS TEETH %d)", i+1));
		 HFile.bwriteln(bw_ncFile, String.format("G90G00 X%.3f Z%.3f A%.3f", 
				          mca_current.X, mca_current.Z, mca_current.A*180/PI + del_A_perFlute + del_AOffset));
		 
		 // Wheel go down Y--> Y start.
		 HFile.bwriteln(bw_ncFile, "(WHEEL DOWN)");
		 HFile.bwriteln(bw_ncFile, String.format("G90G00 Y%.3f", mca_current.Y));
		 
		 HFile.bwriteln(bw_ncFile, "");
		 // Approach the starting point
		 HFile.bwriteln(bw_ncFile, String.format("G90G01 X%.3f Z%.3f A%.3f F20", 
		          mca_current.X, mca_current.Z, mca_current.A*180/PI  +del_A_perFlute + del_AOffset));
		 
		 //Machining
		 HFile.bwriteln(bw_ncFile, "(MACHINING TO THE END OF THE RECESS)");
		 HFile.bwriteln(bw_ncFile, String.format("G90G01 X%.3f Z%.3f A%.3f F20", 
				 mca_end.X, mca_end.Z,mca_end.A*180/PI +del_A_perFlute + del_AOffset));
		 
		 // Wheel going up and perform the next operation.
		 HFile.bwriteln(bw_ncFile, "");
		 HFile.bwriteln(bw_ncFile, String.format("G00G91 Y %.3f", 5*theBlank.getdWorkRadius()));// Go up an amount 2R.
		 
	 }
	 
		
	}
	
	
	public void prinToConsole_FullProfile()
	{
		int NUMOF_PROFILE_POINTS = contactLine.getfullProfile().length;
		
		for(int i =0; i<NUMOF_PROFILE_POINTS ; i++)
		{
			System.out.println(contactLine.getfullProfile()[i][0] + ", "+contactLine.getfullProfile()[i][1]);
		}
		
	}
	
	/** THis function rotate the recess profile to be standard. base of the margin thickness and 
	 * 
	 */
	public void getStandardRecessProfile()
	{
		
		int len = contactLine.fullProfile.length;
		
		//double[][] rotatedProfile  = new double[len][];		
		
		double x1 = contactLine.fullProfile[len-1][0];
		double y1 = contactLine.fullProfile[len-1][1];
		double d = Math.sqrt(x1*x1 + y1*y1);
		double rotatedAngle = 0;
		
		if( x1 >=0 &&y1 >0)
		{
			rotatedAngle = Math.asin(x1/d) +getEndpointAngle();
		}
		else if(x1 <0 &&y1 >=0)
		{
			rotatedAngle = Math.asin(x1/d) +getEndpointAngle();
		}
		else if(x1 <=0 &&y1 <0)
		{
			rotatedAngle = -Math.asin(x1/d) - PI +getEndpointAngle();	
			
		}
		else if(x1 >0 &&y1 >= 0)
		{
			rotatedAngle = Math.asin(x1/d) - 2*PI +getEndpointAngle();
		}
        
		//rotatedAngle;
		
		
		//System.out.println(rotatedAngle*180/PI);
		double xi, yi;
		
		for(int i =0; i< contactLine.fullProfile.length; i++)
		{
			
				xi = contactLine.fullProfile[i][0];
				yi = contactLine.fullProfile[i][1];
				contactLine.fullProfile[i][0] = Math.cos(rotatedAngle)*xi - Math.sin(rotatedAngle)*yi ;
				contactLine.fullProfile[i][1] = Math.sin(rotatedAngle)*xi + Math.cos(rotatedAngle)*yi ;
				if(i ==0)
				{
					System.out.println("X0: " +contactLine.fullProfile[i][0] + 
							           "Y0: " +contactLine.fullProfile[i][1]); 
					
				}			
		
			
		}
		
		
		
	}
	

}
