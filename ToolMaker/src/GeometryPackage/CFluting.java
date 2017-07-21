package GeometryPackage;
import java.io.Serializable;

import Jama.*;

import HAlgorithm.BasicAlgorithms;
import java.lang.Math.*;

import javax.swing.JOptionPane;

/**
 * <h1>This Class describe the Geometry of Flute</h1>
 * 
 * @author HIENBN
 *
 */
public class CFluting extends CBlank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final double PI = Math.PI;
	
	
	// Flute Geometry
	private double dRakeAngle; // [4] Rake angle
	private double dCoreRatio; // [5] Coreradius

	private ContactLine contactLine  = new ContactLine();
	private boolean profile_isarranged = false;
	
	private boolean test_bool;
		
	public static void main(String[] args) {
		// find_anpha_BS( double theta, CBlank aBlank, CWheel1V1 aWheel1V1);
		
		//final double  theta = 40*PI/180;
		double        settingAngle =32*PI/180;
		CBlank theBlank = new CBlank(2, 3, 30*PI/180, 20);
		CWheel1V1 aWheel1V1 = new CWheel1V1(50, 50, 50, 7, 0.001);

		CFluting theFlute = new CFluting(10*PI/180, 0.6);
		//theFlute.find_anpha_BS(1.047, theBlank, aWheel1V1);
		
		CRecess aRecess = new CRecess(0.8,0.8, 20*PI/180);
		
		//aRecess.findYZforRecess( theBlank,  aWheel1V1,  settingAngle);
		
		CWheelLocation wheelpose = theFlute.WheelLCTforFirstFlute(theBlank, aWheel1V1,  settingAngle);
		
		//double Z0 = CRecess.findY_stf_rcore(50, 3, 2.8797, 2.7, 32*PI/180, 30*PI/180);
		
		//System.out.println( Z0);
		
		//R,Y0,Z0,beta,Lamda_cal,Rw,mT,u_d,wheel_ang1,wheel_ang2,Num_flute
		//3.0000, -5.3419, 51.3827, 0.5236, 1.0145, 50.0000, 7.0000, 6.8000, 0, 0, 1.0000
		
		/*
		double[][] contactpoint = new double[2000][4];
		ContactLine theContactLine = new ContactLine();
		theFlute.contactpoint_generation(3, 2.885363, 52.1457397,30*PI/180, 32*PI/180, aWheel1V1,theContactLine);
		theContactLine.ObtainFlutingProfile(3, 0.5236);
		System.out.println(theContactLine.getFluteAngle());
		*/
		
		
		
		// Test wheel location for multplie flute endmill
		//double  m_Aopen_ds_IP = 2*PI/4 - 20*PI/180;
		//CWheelSetting_out wheelpose ;
		//wheelpose = theFlute.find_theta_BS(0.6, m_Aopen_ds_IP, theBlank, aWheel1V1);
		
		//System.out.println(wheelpose.anpha +"," + wheelpose.theta +"," + wheelpose.flag_int);
			
		int a =5;
		int b =a;
		
		
	}

	/**
	 * Default Constructor
	 */
	public CFluting() {
		dRakeAngle = 8;  // default rake angle of endmill.
		dCoreRatio = 0.6;// default core radius.

	}

	/**
	 * Fully specified construtor
	 * 
	 * @param dRake
	 * @param dCore
	 */
	public CFluting(double dRake, double dCore) {
		dRakeAngle = dRake;
		dCoreRatio = dCore;
	}
	
	public CFluting(CBlank aBlank, double dRake, double dCore) 
	{
		super(aBlank);
		dRakeAngle = dRake;
		dCoreRatio = dCore;
	}

	/**
	 * Copy Constructor
	 * 
	 * @param other
	 */
	public CFluting(CFluting other) {
		dRakeAngle = other.dRakeAngle;
		dCoreRatio = other.dCoreRatio;

	}

	/////////////////////////////////////
	// GETTERS and SETTERS // ////////////////////////////////////////////
	// //
	////////////////////////////////////

	/**
	 * Get Rake angle
	 * 
	 * @return
	 */
	public double getdRakeAngle() {
		return dRakeAngle;
	}

	/**
	 * Set Rake angle
	 * 
	 * @param dRakeAngle
	 */
	public void setdRakeAngle(double dRakeAngle) {
		this.dRakeAngle = dRakeAngle;
	}

	/**
	 * Get Core ratio
	 * 
	 * @return
	 */
	public double getdCoreRatio() {
		return dCoreRatio;
	}

	/**
	 * Set Core ratio
	 * 
	 * @param dCoreRatio
	 */
	public void setdCoreRatio(double dCoreRatio) {
		this.dCoreRatio = dCoreRatio;

	}

	////////////////////////////////////////////
	// ==========METHODS=======================//
	////////////////////////////////////////////

	// public CWheelLocation getWheelLocationforFlute()

	/**
	 * This function returns the minimum distance of origin point to the ellipse
	 *
	 * @param Ru
	 *            :double wheel size radius
	 * @param R
	 *            :double workpiece size radius
	 * @param yG
	 *            :double wheel center yG location
	 * @param zG
	 *            :double wheel center zG location
	 * @param t
	 *            :double wheel center t location
	 * @param anpha
	 *            :double wheel Wheel setting angle
	 * @param beta
	 *            :Workpiece helix angle
	 */
	public static double Elip_Circle(double Ru, double R, double yG, double zG, double t, double anpha, double beta) {

		double[] e = { Ru * Math.sin(anpha), Ru };
		double[] y = { yG - t*Math.cos(anpha), zG };
		double[] x = { 0.0, 0.0 };
		double minDistance = 0.0;

		// Calculate Minimum distance between a orignal point <0, 0> and an ellipse with center <yG, zG>.
		minDistance = BasicAlgorithms.DistancePointEllipse(e, y, x);

		return minDistance;
	}

	/**
	 * Given theta value, this function finds anpha to satisfy r_core.
	 * 
	 * [flag,anpha,anpha1,Y0,Z0,r_min] 
	 * <p>Calling syntax:</p>
	 * <code>
	 * r_min = find_anpha_BS(theta,r_Core,Flute_structe)
	 * </code>
	 * <p>
	 * Purpose: find the value of anpha such the the core radius is generated as
	 * design output: 0 no found : 1 found
	 * 
	 */

	public CWheelSetting_out find_anpha_BS(double theta, CBlank aBlank, CWheel1V1 aWheel1V1) 
	
	{
		int flagResult = 1;

		// Re-trieve the data:
		/*
		 * Output :
		 * 
		 * % flag : 1 found, 0 or other means failed. 
		 * % anpha : fould value of
		 * anhpha 
		 * % anhpha1: angle between wheel axis and workpiece axis 
		 * % Y0 :
		 * found Y-axis Wheel location 
		 * % Z0 : found Z axis of wheel center
		 * location % r_min : return rcore, if flag =1 r_min = rcore, otherwise
		 * they are 
		 * % different
		 * 
		 * %% Search range : 
		 * % Anpha = [10, 80]: Degree 
		 * % Theta = [5, 90] :
		 * Anpha1 = -10*pi/180; 
		 * % Lower bound Anpha2 = 80*pi/180; 
		 * % Upper bound
		 * rCorePrecision = 0.01;
		 * %Precision of core radius
		 * 
		 */

		double anpha1 = -10 * PI / 180; // anpha low bound
		double anpha2 = 80 * PI / 180;  // anpha upper bound
		double rCorePrecision = 0.001;  // function stop as precision reach this limit

		// Read Flute parameters
		double R = aBlank.getdWorkRadius();  // Work radius
		double m_helix = (aBlank.getdHelixAngle()); // Helix angle
		double m_rake_ra = (dRakeAngle);            // Rake angle in cross-section
		double m_rakea = Math.atan(Math.tan(m_rake_ra) * Math.cos(m_helix)); // rake angle in orthogonal cutting plane

		// Read Wheel parameters
		double Rw = aWheel1V1.getDiaRight();   // Wheel radius in the right side that directly generates rake face
		double DiaL = aWheel1V1.getDiaLeft();  // Wheel radius in the left side
		double DiaM = aWheel1V1.getDiaMax();   //Wheel radius in the midle side, biggest diameter

		/*
		 * %% Resulting parameter
		 * 
		 * %Tc=[-cos(m_helix) sin(m_helix) 0]; Bc=[sin(m_helix) cos(m_helix) 0];
		 * OC=[0 0 R]; Nc=[0 0 1]; N_FC=sin(m_rakea)*Nc+cos(m_rakea)*Bc;
		 * 
		 * % Intermedate variable (Wheel geometry) % R_Wheel_mid
		 * =RWheel(WHEEL_PARA(4),WHEEL_PARA); % R_Wheel_end
		 * =RWheel(mT,WHEEL_PARA);
		 * 
		 * 
		 * % While-loop here bool =1; i_whileloop =0; r_min = r_Core +2; %
		 * Initial condition flag =1;
		 * 
		 * 
		 */

		double[][] Tc_arr = { { -Math.cos(m_helix) }, { Math.sin(m_helix) }, { 0 } };
		Matrix Tc = new Matrix(Tc_arr);  // tangent vector

		double[][] Bc_arr = { { Math.sin(m_helix) }, { Math.cos(m_helix) }, { 0 } };
		Matrix Bc = new Matrix(Bc_arr);  // Bi-normal vector

		double[][] OC_arr = { { 0 }, { 0 }, { R } };
		Matrix OC = new Matrix(OC_arr);  // vector form center to cutting end point in the flute

		double[][] Nc_arr = { { 0 }, { 0 }, { 1 } };
		Matrix Nc = new Matrix(Nc_arr);  // normal vector at cutting point

		// N_FC=sin(m_rakea)*Nc+cos(m_rakea)*Bc;
		Matrix N_FC = (Nc.times(Math.sin(m_rakea))).plus(Bc.times(Math.cos(m_rakea)));

		/** While-loop here */
		boolean bool = true;
		int i_whileloop = 0;
		double r_Core = dCoreRatio * R;
		double r_min = r_Core + 2; // Initial condition
		int flag = 1;

		double anpha = 0;
		Matrix nGC;    // Vector connect wheel center to the first cutting point of the flute
		Matrix vG;     // Wheel center location in workpiece coordinate

		double rmin_1 = 0, rmin_2 = 0;
		double W_rotate =0;
		double Y0=0, Z0=0;

		while ((Math.abs(r_min - r_Core) > rCorePrecision) && (i_whileloop <= 100) && (bool == true)) {
			i_whileloop++;
			//System.out.println("Loop number:" + i_whileloop);

			for (int i_loop = 1; i_loop <= 3; i_loop++) {
				switch (i_loop) {
				case 1:
					anpha = anpha1;
					break;
				case 2:
					anpha = anpha2;
					break;
				case 3:
					anpha = (anpha1 + anpha2) / 2;
					break;

				default:
					break;

				}

				// nGC=[sin(theta)*cos(anpha+m_helix),
				// -sin(theta)*sin(anpha+m_helix), cos(theta)];
				
				double[][] nGC_arr = { { Math.sin(theta) * Math.cos(anpha + m_helix) },
						{ Math.sin(-theta) * Math.sin(anpha + m_helix) }, { Math.cos(theta) } };

				nGC = new Matrix(nGC_arr);
				vG = OC.plus(nGC.times(Rw)); // G=OC+Rw*nGC;

				
				Matrix I_tem = Matrix.cross(N_FC, nGC);

				double dI_temLength = Math.sqrt(I_tem.get(0, 0) * I_tem.get(0, 0) + I_tem.get(1, 0) * I_tem.get(1, 0)
						+ I_tem.get(2, 0) * I_tem.get(2, 0));

				I_tem = I_tem.times(1 / dI_temLength); // Normalize
				Matrix I = Matrix.cross(I_tem, nGC);

				double phi_rotate = -Math.atan(I.get(2, 0) / I.get(1, 0));
				 Y0 = Math.cos(phi_rotate) * vG.get(1, 0) - Math.sin(phi_rotate) * vG.get(2, 0);
				 Z0 = Math.sin(phi_rotate) * vG.get(1, 0) + Math.cos(phi_rotate) * vG.get(2, 0);

				//System.out.print("Y0 : " + Y0 + "  Z0: " + Z0 + "\r\n");

				double dI = Math.sqrt(I.get(1, 0) * I.get(1, 0) + I.get(2, 0) * I.get(2, 0));

				double A_rotate = Math.asin(I.get(2, 0) / dI);

				double temp = -I.get(2, 0) * Math.sin(A_rotate) + I.get(1, 0) * Math.cos(A_rotate);
				W_rotate = Math.atan(I.get(0, 0) / temp);

				//System.out.print("G : " + vG.get(0, 0) + ", " + vG.get(1, 0) + "," + vG.get(2, 0) + "\r\n");
				//System.out.print("I : " + I.get(0, 0) + ", " + I.get(1, 0) + "," + I.get(2, 0) + "\r\n");

				double lamda = W_rotate;

				if (i_loop == 1) {
					rmin_1 = Elip_Circle(Rw, R, Y0, Z0, 0, lamda, m_helix);

				} else if (i_loop == 2) {
					rmin_2 = Elip_Circle(Rw, R, Y0, Z0, 0, lamda, m_helix);

					if ((rmin_1 < r_Core) && (rmin_2 < r_Core)) {
						bool = false;
						System.out.println("So sad");	
						flagResult =0;
						
					} else if ((rmin_1 > r_Core) && (rmin_2 > r_Core)) {
						bool = false;
						System.out.println("Where is wrong code?");	
						flagResult = -1;
					}

				}

				else if (i_loop > 2) // i_loop>2
				{
					r_min = Elip_Circle(Rw, R, Y0, Z0, 0, lamda, m_helix);

					if (r_min > (r_Core + rCorePrecision))
						{
						anpha2 = (anpha1 + anpha2) / 2;
						
						}
					else
						
						{
						anpha1 = (anpha1 + anpha2) / 2;
						
						}

				}

			}
			
			System.out.println("Closest Core is rMin1:" + r_min);	

		}
		
		CWheelSetting_out result = new CWheelSetting_out(bool, flagResult, anpha, PI/2 -W_rotate, Y0,  Z0,  r_min);
				
		return result;

	}
	
	
	//////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
	//[anpha,mTheta,bool] = find_theta_BS(r_Core_IP,m_Aopen_ds_IP,Flute_structe)

	/**
	 * <h1>This function calculate anpha and theta that satisfy flute geometry
	 * </h1>
	 * 
	 * Usage: For fluting multiple flute end mills.
	 * 
	 * DESCRIPTION: 
	 *   - Read FLUTE and WHEEL's geometry 
	 *   -
	 * 
	 * PRAYER: 
	 *       Be with me God. 
       Purpose: 
             Bring groly to God. 
       Comments: 
              - Using binary search to find wheel location using Theta and Anpha. % 1.
	 * Search range = [10,90];
	 * 
	 * 
	 * @param r_Core_IP
	 *            : Core radius mm
	 * @param m_Aopen_ds_IP
	 *            : Fluting angle -radial
	 * @return
	 */
	
	public  CWheelSetting_out find_theta_BS(double r_Core_IP,double m_Aopen_ds_IP,
			                                          CBlank aBlank, CWheel1V1 aWheel1V1 )
	{
		int flag =0;
		
		// Load flute geometry
		// Read Flute parameters
		double R = aBlank.getdWorkRadius(); // Work radius
		double m_helix = (aBlank.getdHelixAngle()); // Helix angle
		double m_rake_ra = (dRakeAngle); // Rake angle in cross-section
													
		double m_rakea = Math.atan(Math.tan(m_rake_ra) * Math.cos(m_helix));
	           // rake angle in orthogonal cutting plane

	    // Read Wheel parameters
	    double Rw = aWheel1V1.getDiaRight();   // Wheel radius in the right side that directly generates rake face
		double DiaL = aWheel1V1.getDiaLeft();  // Wheel radius in the left side
	    double DiaM = aWheel1V1.getDiaMax();   //Wheel radius in the midle side, biggest diameter
	    double mT = aWheel1V1.getThicknessLeft() + aWheel1V1.getThicknessRight();
		double u_d = aWheel1V1.getThicknessRight();
		double TH_R = u_d;
		double TH_L = mT - u_d;
		double Rmid = aWheel1V1.getDiaMax();
		double Rleft = aWheel1V1.getDiaLeft();
		
		// Calculate wheel angles
		double wheel_ang1,wheel_ang2=0;
		wheel_ang1= Math.atan((Rmid - Rw)/TH_R);
		
		if(TH_L>0)
		{wheel_ang2 = Math.atan((Rmid - Rleft)/TH_L);}
		else
		{ wheel_ang2 =0; }
		
	    
		double m_Aopen_ds = m_Aopen_ds_IP;
		
		
	    // bounds
	    double Theta1 = 10 * PI / 180; // anpha low bound
		double Theta2 = 110 * PI / 180;  // anpha upper bound
		double rCorePrecision = 0.001;  // function stop as precision reach this limit
		double m_AopenPres = 1*PI/180;
		
		double m_Aopen_cal = m_Aopen_ds+1;  // % Initilized the Open angle
		int i_whileloop =0;
		int bool =1;
		int flagout =0;
		
		// temperary variables
		double mTheta =0;
		double anpha = 0,anpha1,Y0,Z0,r_minreturn1;
		double m_Aopen_cal1 =0,m_Aopen_cal2 =0;
		
		//CWheelSetting_out wheelpose1 = new CWheelSetting_out();
		//CWheelSetting_out wheelpose2 = new CWheelSetting_out();
		CWheelSetting_out wheelpose = new CWheelSetting_out();
		ContactLine ctline = new ContactLine();
		// while loop
		
		while( (Math.abs(m_Aopen_cal -m_Aopen_ds)>m_AopenPres)&&(i_whileloop<100 ))
		{
			i_whileloop++;
			
			for(int i_loop=1; i_loop<=3; i_loop++)
			{
				if(i_loop == 1) // low bound
				{
					mTheta = Theta1;
				}
				else if(i_loop == 2) // upper bound
				{
					mTheta = Theta2;
				}
				else if(i_loop > 2) // mid point
				{
					mTheta = (Theta1 + Theta2)/2;
				}
				
				//CWheelSetting_out find_anpha_BS(double theta, CBlank aBlank, CWheel1V1 aWheel1V1) 
				if(i_loop == 1) // low bound
				{
					wheelpose = find_anpha_BS( mTheta,  aBlank,  aWheel1V1) ;
					Y0 = wheelpose.Y0;
					Z0 = wheelpose.Z0;
					anpha1 = wheelpose.anpha1;				
					anpha = wheelpose.anpha;
					contactpoint_generation(R, Y0, Z0, m_helix, anpha1, aWheel1V1, ctline);
					ctline.ObtainFlutingProfile(R, m_helix);
					m_Aopen_cal1 = ctline.getFluteAngle();
					
				}
				else if(i_loop == 2) // upper bound
				{
					wheelpose = find_anpha_BS( mTheta,  aBlank,  aWheel1V1) ;
					Y0 = wheelpose.Y0;
					Z0 = wheelpose.Z0;
					anpha1 = wheelpose.anpha1;		
					anpha = wheelpose.anpha;
					contactpoint_generation(R, Y0, Z0, m_helix, anpha1, aWheel1V1, ctline);
					ctline.ObtainFlutingProfile(R, m_helix);
					m_Aopen_cal2 = ctline.getFluteAngle();
					
					if(((m_Aopen_cal1 - m_Aopen_ds)<0)&&((m_Aopen_cal2 - m_Aopen_ds)<0))
					{
		                bool = 0; //% Out the search range.
		                mTheta = 0;
		                flagout =0;
					}
		            else if(((m_Aopen_cal1 - m_Aopen_ds)>0)&&((m_Aopen_cal2 - m_Aopen_ds)>0))
		            {
		                bool = -1;// % Out the search range.
		                mTheta = 0;
		                flagout =-1;
		            }
		            
					
					
				}
				else if(i_loop > 2) // mid point
				{
					if(Theta1 > Theta2)
					{
		                bool = -2; // Not found
		                anpha = 0;
		                flagout =-2;
					}
		           
		            
		            if(i_whileloop ==100)
		            {
		                bool = -3; // % Not found
		                anpha = 0;
		                flagout =-3;
		                //msgbox('Wheel PST for flute failed, change design factors!', 'Error','error');
		                JOptionPane.showMessageDialog(null, " Wheel PST for flute failed, change design factors!", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		            
		            // If pass through checks
		            // calculate the flute angle in midle points
		            wheelpose = find_anpha_BS( mTheta,  aBlank,  aWheel1V1) ;
					Y0 = wheelpose.Y0;
					Z0 = wheelpose.Z0;
					anpha1 = wheelpose.anpha1;		
					anpha = wheelpose.anpha;
					if(wheelpose.flag_int ==1)
					{
						
					  contactpoint_generation(R, Y0, Z0, m_helix, anpha1, aWheel1V1, ctline);
					  ctline.ObtainFlutingProfile(R, m_helix);
					  m_Aopen_cal = ctline.getFluteAngle();
					}
					
					if(wheelpose.flag_int ==1)
					{
						// Compare the result and reduce the bounds.
		                if(m_Aopen_cal > (m_Aopen_ds +m_AopenPres))
		                    {Theta2 =(Theta1 + Theta2)/2;}
		                else
		                    {Theta1 =(Theta1 + Theta2)/2;}
		                
						
					}
					else if(wheelpose.flag_int ==0)
					{
						Theta2 =(Theta1 + Theta2)/2;
					}
					else if(wheelpose.flag_int == -1)
					{
						Theta1 =(Theta1 + Theta2)/2;
					}		            
					
				}
				
					
			}		
			
			
		}	
		
		
		
		return new CWheelSetting_out(flagout, anpha, mTheta);	
	}
	
	
	///////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////// 
    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
	
	/**<p> Compute contactline and cross-section in the workpiece</p>
	 * 
	 * Given workpiece geometry, wheel position and Wheel gemetry.
	 * 
	 * @param R        : Work radius
	 * @param Y0       : <Y0,Z0> wheel location
	 * @param Z0
	 * @param m_helix  : Helix angle
	 * @param anpha    : setting angle 32/30
	 * @param aWheel   : wheel geometry
	 * 
	 * contactpoint_generation(R,Y0,Z0,beta,anpha,Rw,thickness,u_d,wheel_ang1,wheel_ang2,Num_flute)
	 * 
	 */
	public  void contactpoint_generation(double R,double Y0,double Z0, 
			                            double beta,double anpha, CWheel1V1 aWheel1V1, ContactLine theContactLine)
	{    
	    //Get the wheel Geometry:
		
		
		// Output:
		//double [][] contactpoint = new double[2000][4];
		int countactCount =0;
		//contactpoint[0][0] = 1;
		int countCTPEnvelop =0;
		int countCTPFirst =0;
		int countCTPEnd =0;
		
		double Rw, Rmid, Rleft, thickness, u_d, TH_L, TH_R, wheel_ang1, wheel_ang2;
		
		Rw = aWheel1V1.getDiaRight();
		thickness = aWheel1V1.getThicknessLeft() + aWheel1V1.getThicknessRight();
		u_d = aWheel1V1.getThicknessRight();
		TH_R = u_d;
		TH_L = thickness - u_d;
		Rmid = aWheel1V1.getDiaMax();
		Rleft = aWheel1V1.getDiaLeft();
		
		// Calculate wheel angles
		wheel_ang1 = -Math.atan((Rmid - Rw)/TH_R);
		
		if(TH_L>0)
		{wheel_ang2 = -Math.atan((Rmid - Rleft)/TH_L);}
		else
		{ wheel_ang2 =0; }
		
		
		
		//Gi=[-R*phi/tan(beta) (Y0*cos(phi)+Z0*sin(phi)) (-Y0*sin(phi)+Z0*cos(phi))];
		//Ii=[cos(anpha) sin(anpha)*cos(phi) -sin(anpha)*sin(phi)];
		
		//Sub varibles
		double t=0, phi=0, Ome = 2*PI;
		
		double[][] Gi_arr = { {-R*phi/Math.tan(beta)}, {Y0*Math.cos(phi)+Z0*Math.sin(phi)}, 
				                                     {-Y0*Math.sin(phi)+Z0*Math.cos(phi)} };
		double[][] Ii_arr = {{Math.cos(anpha)}, {Math.sin(anpha)*Math.cos(phi)},{-Math.sin(anpha)*Math.sin(phi)}};
		
		Matrix Gi = new Matrix(Gi_arr);
		Matrix Ii = new Matrix(Ii_arr);
		
		//dGi=Ome*[-R/tan(beta) (-Y0*sin(phi)+Z0*cos(phi)) (-Y0*cos(phi)-Z0*sin(phi))];
		double[][] dGi_arr = { {Ome*(-R/Math.tan(beta))}, {Ome*(-Y0*Math.sin(phi)+Z0*Math.cos(phi))}, 
				               {Ome*(-Y0*Math.cos(phi)-Z0*Math.sin(phi))} };
		
		Matrix dGi = new Matrix(dGi_arr);
		
		//Set up local coordinate system
		// ZL=Ii;
		// XL=[0 -sin(phi) -cos(phi)];
		// YL=cross(ZL,XL);

		Matrix ZL=Ii;
		double[][] XL_arr = {{0},{-Math.sin(phi)}, {-Math.cos(phi)}};
		Matrix XL= new Matrix(XL_arr);
		Matrix YL= Matrix.cross(ZL,XL);
		
		final int WHEELSLICE_MAX =100;
		double step_u=thickness/WHEELSLICE_MAX;
		double u; // Wheel offset section
		double Ru=Rw, dRu=0, wheel_ang;
		
		// DECLARE SUB VARIABLES HERE
		double A,B,C;
		double angle1=0, angle=0, angle_left_u_d =0, angle_right_u_d=0;		
		double step_ang_non_en, angle_sub_non;
		Matrix contact =null, Pside = null;
		
		double dcontac,detphi, phct, Phi_cross;
		double min_sp = 0, max_sp = 0, sp =0, dPside =0;
		
		
		
		
		for(int i =0; i<=WHEELSLICE_MAX; i++)
		{
			u = -i*step_u;
			
			if(u>- u_d) // Wheel section in the right side
			{
				wheel_ang = wheel_ang1;
		        Ru=Rw+u*Math.tan(wheel_ang);
		        dRu=Math.tan(wheel_ang);
			}
			else if(u<=-u_d) //Wheel section in the left side
			{
				wheel_ang = wheel_ang2;
		        Ru=Rw-(u+u_d)*Math.tan(wheel_ang)-u_d*(Math.tan(wheel_ang1));
		        dRu=-Math.tan(wheel_ang);
			}
			
			/*
			% set up some subparameters for calculation
		    A=dot(dGi,XL)+ Ome*sin(anpha)*u + Ome*sin(anpha)*Ru*dRu;
		    B=dot(dGi,YL);
		    C=dRu*dot(dGi,ZL);
		    */
			
			A = Matrix.dot(dGi, XL) + Ome*Math.sin(anpha)*u + Ome*Math.sin(anpha)*Ru*dRu;
			B = Matrix.dot(dGi,YL);
		    C = dRu*Matrix.dot(dGi,ZL);
		    
			/*
			 * angle1=asin(A/sqrt(A^2+B^2)); 
			 * 
			 * if(cos(angle1)*B<0)
			 *    angle1=pi-angle1; 
			 * end 
			 * if(C<sqrt(A^2+B^2))
			 *   angle=angle1+asin(-C/sqrt(A^2+B^2));
			 *  
			 *  else 
			 *    angle=angle1; 
			 *  end
			 * 
			 */
		    
		    angle1= Math.asin(A/Math.sqrt(A*A + B*B));
		    
		    if(Math.cos(angle1)*B<0)
		    { 
		    	angle1 = PI - angle1;
		    }
		    
		    if(C<Math.sqrt(A*A + B*B))
		    {
		    	angle=angle1 + Math.asin(-C/Math.sqrt(A*A + B*B));
		    }
		    else
		    { 
		    	angle=angle1; 
		    }
		    
		    
		    /************************/
		    
		    //Handling non-enveloped contact point
		    //Calculation of non-enveloped contact line
		    //Saving two angle at sharp points and generate non-enveloped contact line
		    
		    if((u<=-(u_d-step_u))&&(u>=-(u_d+step_u)))
		    {
		        if((u>-u_d)&&(u<=-(u_d-step_u)))
		        {
		            angle_left_u_d = angle;
		            
		        }
		        else if((u<= -u_d)&&(u>= -(u_d+step_u)))
		        {  
		            angle_right_u_d = angle;
		            
		        }
		     }
		    
		    if((u>-(u_d+step_u))&&(u<=-u_d))
	        
		    {
	     	   step_ang_non_en=(angle_right_u_d-angle_left_u_d)/20;
	     	   
	     	   // Coordinate of contact point of the SHARP POINT
	     	  for(int ii =0; ii<=10; ii++)
	     	  {
	     		  
	     		 angle_sub_non= angle_left_u_d+ step_ang_non_en*ii;	     	            
	     	      ///contact= Gi + u*ZL  + Ru*(XL*Math.cos(angle_sub_non) - YL*Math.sin(angle_sub_non));
	     		 Matrix tem1 = (Gi.plus(ZL.times(u))).plus(XL.times(Ru*Math.cos(angle_sub_non)));
	     	     Matrix tem2 = YL.times(-Math.sin(angle_sub_non)*Ru);	     	     
	     	     contact = tem1.plus(tem2);
	     	     
	     	    dcontac=Math.sqrt((contact.get(1,0)*contact.get(1,0)) + (contact.get(2,0)*contact.get(2,0)));
	            
	            detphi=contact.get(1,0)*Math.tan(beta)/R;
	            phct= Math.atan(contact.get(2,0)/contact.get(1,0));
	            
	            if(Math.cos(phct)*(contact.get(1,0)/dcontac)<0)
	            { 
	            	phct= PI+phct; 
	            }
	            
	            Phi_cross=phct-detphi;
	     	     
	            // if inside the Workpiece then at the sideface
	            if(dcontac <=R && t==0)
	            {
	            	//contactpoint[countactCount][0] = contact.get(0,0);
	            	//contactpoint[countactCount][1] = contact.get(0,1);
	            	//contactpoint[countactCount][2] = contact.get(0,2);
	            	//contactpoint[countactCount][0] = 0; 
	            	//countactCount++;
	            	theContactLine.contactPointEnvelope[countCTPEnvelop][0] =contact.get(0,0);
	            	theContactLine.contactPointEnvelope[countCTPEnvelop][1] =contact.get(1,0);
	            	theContactLine.contactPointEnvelope[countCTPEnvelop][2] =contact.get(2,0);
	            	theContactLine.contactPointEnvelope[countCTPEnvelop][3] =1;
	            	countCTPEnvelop++;
	            	
	            }
	     	            
	     	  }
	     	   
		    }
		    
		    // Envelope Point
			Matrix tem1 = (Gi.plus(ZL.times(u))).plus(XL.times(Ru * Math.cos(angle)));
			Matrix tem2 = YL.times(-Math.sin(angle)*Ru);
			contact = tem1.plus(tem2);

			dcontac = Math.sqrt((contact.get(1, 0) * contact.get(1, 0)) + (contact.get(2, 0) * contact.get(2, 0)));

			detphi = contact.get(0, 0) * Math.tan(beta) / R;
			phct = Math.atan(contact.get(2, 0) / contact.get(1, 0));
		    
			if(Math.cos(phct)*(contact.get(1,0)/dcontac)<0)
            { 
            	phct= PI+phct; 
            }
            
            Phi_cross=phct-detphi;
     	     
            // if inside the Workpiece then at the sideface
            if(dcontac <=R && t==0)
            {
            	double test = contact.get(0,0);
            	
            	//contactpoint[countactCount][0] = test;
            	//contactpoint[countactCount][1] = contact.get(1,0);
            	//contactpoint[countactCount][2] = contact.get(2,0);
            	//contactpoint[countactCount][3] = 1; 
            	
            	theContactLine.contactPointEnvelope[countCTPEnvelop][0] =contact.get(0,0);
            	theContactLine.contactPointEnvelope[countCTPEnvelop][1] =contact.get(1,0);
            	theContactLine.contactPointEnvelope[countCTPEnvelop][2] =contact.get(2,0);
            	theContactLine.contactPointEnvelope[countCTPEnvelop][3] =1;
            	countCTPEnvelop++;
            }
            
            // FIND THE CROSS-SECTION OF THE SIDE FACE.
            
            if(u==0)
            {
            	 min_sp=angle+PI;
                 max_sp=angle+2*PI;
                 sp = min_sp;
                 
                 while(sp<max_sp)
                 {
                	 
                	 //Pside=Gi+u*ZL+Ru*(XL*cos(sp)-YL*sin(sp));
                	 Matrix tem3 = (Gi.plus(ZL.times(u))).plus(XL.times(Ru * Math.cos(sp)));
         			 Matrix tem4 = YL.times(-Math.sin(sp)*Ru);
         			 Pside = tem3.plus(tem4);         			 
                     dPside=Math.sqrt((Pside.get(1, 0) * Pside.get(1, 0)) + (Pside.get(2, 0) * Pside.get(2, 0)));
         			 
                     if(dPside<R)
                     {
                    	 theContactLine.contactPoint_firstsleeve[countCTPFirst][0] = Pside.get(0,0);
                    	 theContactLine.contactPoint_firstsleeve[countCTPFirst][1] = Pside.get(1,0);
                    	 theContactLine.contactPoint_firstsleeve[countCTPFirst][2] = Pside.get(2,0);
                    	 theContactLine.contactPoint_firstsleeve[countCTPFirst][3] = 2; 
                    	 countCTPFirst++;     
                     }
                	
                	
                	double deta_Pside=Pside.get(0, 0)*Math.tan(beta)/R;
                    double ph_Pside=Math.atan(Pside.get(2, 0)/Pside.get(1, 0));
                    if(Math.cos(ph_Pside)*(Pside.get(1, 0)/dPside)<0)
                    { 
                    	ph_Pside = PI+ph_Pside;                    
                    }
                    
                    double Phi_Pside=-deta_Pside+ph_Pside;                                        
                    // update angple                    
                    sp = sp+0.005;                    
                 }
               
            }
            
            
         // Semeng3: FIND THE CONTACT LINE OF THE END FACE.
            
            if( (u>=-thickness-step_u)&&(u<=-thickness))
            {
           	    min_sp=angle;
                max_sp=angle+ PI;
                sp = min_sp;
                
                while(sp <max_sp)
                {
					Matrix tem3 = (Gi.plus(ZL.times(u))).plus(XL.times(Ru * Math.cos(sp)));
					Matrix tem4 = YL.times(-Math.sin(sp)*Ru);
					Pside = tem3.plus(tem4);
					dPside = Math.sqrt((Pside.get(1, 0) * Pside.get(1, 0)) + (Pside.get(2, 0) * Pside.get(2, 0)));

					if (dPside < R) 
					{
						//contactpoint[countactCount][0] = Pside.get(0, 0);
						//contactpoint[countactCount][1] = Pside.get(1, 0);
						//contactpoint[countactCount][2] = Pside.get(2, 0);
						//contactpoint[countactCount][3] = 3; 
						theContactLine.contactPointEndsleeve[countCTPEnd][0]= Pside.get(0, 0);
						theContactLine.contactPointEndsleeve[countCTPEnd][1]= Pside.get(1, 0);
						theContactLine.contactPointEndsleeve[countCTPEnd][2]= Pside.get(2, 0);
						theContactLine.contactPointEndsleeve[countCTPEnd][3]= 3;
						
						countCTPEnd++;
					}

					double deta_Pside = Pside.get(0, 0) * Math.tan(beta) / R;
					double ph_Pside = Math.atan(Pside.get(2, 0) / Pside.get(1,0));
					if (Math.cos(ph_Pside) * (Pside.get(1,0) / dPside) < 0) {
						ph_Pside = PI + ph_Pside;
					}

					double Phi_Pside = -deta_Pside + ph_Pside;
                	                	               	
                	//update angle
                	sp = sp+0.005;                	
                	
                }
                
            }
		    		    
			
		}
		
		// Summary the object
		countactCount = countCTPFirst + countCTPEnvelop + countCTPEnd;
		theContactLine.numPoint_firstSleev = countCTPFirst;
		theContactLine.numPoint_Envelope = countCTPEnvelop;
		theContactLine.numPoint_Endsleeve = countCTPEnd;
		theContactLine.totalContactPoints = countactCount;
		theContactLine.reArrangeCTLINE();
		theContactLine.ObtainFlutingProfile( R,  beta);
		
		contactLine = theContactLine;
		
		int a =0;
		int b =a;
				
	}
	
	
	
	// This function find the Wheel location for fluting process.
	// Using Bi-section algorithm to find the wheel location
	/*
	 * %% Purpose: Find wheel lct that satisfy core radii, rake angle, setting
	 * %% angle for grinding flute of two-flutes.
	 * 
	 * %% Input: 
	 * % - Flute geometry 
	 * % - Wheel geometry 
	 * %% Output:
	 * % - I : wheel axis orientation determined by geometrical engagement 
	 * % - G : Wheel center lct determines by Anpha, Theta 
	 * % - Algorith uses binary search
	 * 
	 */
	
	
	public CWheelLocation WheelLCTforFirstFlute(CBlank aBlank, CWheel1V1 aWheel1V1, double settingAngle)
	{
		CWheelLocation wheellct = new CWheelLocation();
		CWheelSetting_out wheelsettingOut = new CWheelSetting_out();
		
		//temp variables
		//Matrix I = new Matrix(3,1); // Wheel axis orientation.
		//Matrix G = new Matrix(3,1); // Wheel center location.
		
		// Read Flute parameters
		double R = aBlank.getdWorkRadius(); // Work radius
		double m_helix = (aBlank.getdHelixAngle()); // Helix angle
		double m_rake_ra = (dRakeAngle); // Rake angle in
													// cross-section
		double m_rakea = Math.atan(Math.tan(m_rake_ra) * Math.cos(m_helix));
		// rake angle in orthogonal cutting plane

		// Read Wheel parameters
		double Rw = aWheel1V1.getDiaRight();
		// Wheel radius in the right side that directly generates rake face
		double DiaL = aWheel1V1.getDiaLeft();
		// Wheel radius in the left side
		double DiaM = aWheel1V1.getDiaMax();
		// Wheel radius in the midle side, biggest diameter
		double r_Core = dCoreRatio *R;
		
		
		double t1 = aWheel1V1.getThicknessRight();
		double mT = t1 + aWheel1V1.getThicknessLeft();
		
		double wheelAngleRight = aWheel1V1.getWheelangRight();
		double wheelAngleLeft =  aWheel1V1.getWheelangLeft();
		
		double lamda_ds= PI/2 - settingAngle;
		double lamda_PRECISION= 0.5*PI/180;     //Lamda Precision
		
		double Theta1 = 10*PI/180;             //Lower bound 
		double Theta2 = 110*PI/180;           // Upper bound		
		double lamda_cal = lamda_ds +1;       //Initial calculated Lamda.
		
		double mTheta =Theta1; // current value of Theta
		// Bi-section algoritm here
		
		int i_whileloop =0;
		boolean flag = true;
		double Lamda1 =0, Lamda2 =0;
		
		
		
		while((Math.abs(lamda_cal - lamda_ds)>lamda_PRECISION )&&(i_whileloop<=100))
		{
			i_whileloop++; // This to avoid infinite loop.
			
			// loop 3 time for two bound and 
			for(int i_loop =1; i_loop<=3; i_loop++)
			{
				if(i_loop ==1) //% Compute anpha = anpha1
			            mTheta = Theta1;
			    else if(i_loop ==2) //%Compute anpha = anpha2
			            mTheta = Theta2;
			    else if(i_loop >2) //% Compute anpha = anpha3
			            mTheta = (Theta1 + Theta2)/2;
				
				if(i_loop ==1)
				{
					wheelsettingOut = find_anpha_BS(mTheta,aBlank,aWheel1V1);
				    Lamda1 = wheelsettingOut.anpha1;
				}
				
				else if(i_loop ==2)
				{
					wheelsettingOut = find_anpha_BS(mTheta,aBlank,aWheel1V1);
				    Lamda2 = wheelsettingOut.anpha1;
					
				    //
				    if((Lamda1- lamda_ds)*(Lamda2- lamda_ds)>0)
				    {
				    	wheellct.bool = false;
				    	//JOptionPane.showMessageDialog(null, "No setting is found, change geometrical parameters please",
				    	//		                       "Warning", JOptionPane.WARNING_MESSAGE);
				    	
				    	System.out.println("No setting is found" + "i_whileloop:" +i_whileloop);
				    	
				    	break;
				    	
				    }
				    
				}
				
				else if(i_loop ==3)
				{
					if(Theta1 >Theta2)
					{
						wheellct.bool = false;
						JOptionPane.showMessageDialog(null, "No setting is found, Theta1 >Theta2",
			                       "Warning", JOptionPane.WARNING_MESSAGE);
	
					}
					
					if(i_whileloop ==100)
					{
						wheellct.bool = false;
						JOptionPane.showMessageDialog(null, "No setting is found, change geometrical parameters please i_loop =100",
			                       "Warning", JOptionPane.WARNING_MESSAGE);
	
						
					}
					
					wheelsettingOut = find_anpha_BS(mTheta,aBlank,aWheel1V1);
				    lamda_cal = wheelsettingOut.anpha1;
				    
				    /// Test
				    System.out.println("Loop:" + i_whileloop + " ,Lam1:" + Lamda1 + ", Lam2:" + Lamda2 +", Lam:" + lamda_cal + " Anpha" +wheelsettingOut.anpha);
				    
				    
					if(wheelsettingOut.flag_int ==1)
					{
						// Compare the result and reduce the bounds.
		                if(lamda_cal > (lamda_ds + lamda_PRECISION))
		                { Theta1 =(Theta1 + Theta2)/2;}
		                else		                    
		                {Theta2 =(Theta1 + Theta2)/2;}	
					}
					else if(wheelsettingOut.flag_int ==0)
					{
						Theta2 =(Theta1 + Theta2)/2;
					}
				    
					else if(wheelsettingOut.flag_int ==-1)
					{
						Theta1 =(Theta1 + Theta2)/2;
					}
				    
				    	
				    
					
				}				
			       
				
			}			
			
			
		}
		
		
		// Determine I, and G for return the Result.
		double[][] Tc_arr = { { -Math.cos(m_helix) }, { Math.sin(m_helix) }, { 0 } };
		Matrix Tc = new Matrix(Tc_arr);  // tangent vector

		double[][] Bc_arr = { { Math.sin(m_helix) }, { Math.cos(m_helix) }, { 0 } };
		Matrix Bc = new Matrix(Bc_arr);  // Bi-normal vector

		double[][] OC_arr = { { 0 }, { 0 }, { R } };
		Matrix OC = new Matrix(OC_arr);  // vector form center to cutting end point in the flute

		double[][] Nc_arr = { { 0 }, { 0 }, { 1 } };
		Matrix Nc = new Matrix(Nc_arr);  // normal vector at cutting point

		// N_FC=sin(m_rakea)*Nc+cos(m_rakea)*Bc;
		Matrix N_FC = (Nc.times(Math.sin(m_rakea))).plus(Bc.times(Math.cos(m_rakea)));
		
		Matrix nGC;    // Vector connect wheel center to the first cutting point of the flute
		Matrix vG;     // Wheel center location in workpiece coordinate

		
		double  anpha = wheelsettingOut.anpha;
		double W_rotate =0;
		double Y0=0, Z0=0;
		double[][] nGC_arr = { { Math.sin(mTheta) * Math.cos(anpha + m_helix) },
				{ Math.sin(-mTheta) * Math.sin(anpha + m_helix) }, { Math.cos(mTheta) } };

		nGC = new Matrix(nGC_arr);
		vG = OC.plus(nGC.times(Rw)); // G=OC+Rw*nGC;

		
		Matrix I_tem = Matrix.cross(N_FC, nGC);

		double dI_temLength = Math.sqrt(I_tem.get(0, 0) * I_tem.get(0, 0) + I_tem.get(1, 0) * I_tem.get(1, 0)
				+ I_tem.get(2, 0) * I_tem.get(2, 0));

		I_tem = I_tem.times(1 / dI_temLength); // Normalize
		Matrix I = Matrix.cross(I_tem, nGC);   // Wheel axis orientation.

		double phi_rotate = -Math.atan(I.get(2, 0) / I.get(1, 0));
		 Y0 = Math.cos(phi_rotate) * vG.get(1, 0) - Math.sin(phi_rotate) * vG.get(2, 0);
		 Z0 = Math.sin(phi_rotate) * vG.get(1, 0) + Math.cos(phi_rotate) * vG.get(2, 0);

		//System.out.print("Y0 : " + Y0 + "  Z0: " + Z0 + "\r\n");

		double dI = Math.sqrt(I.get(1, 0) * I.get(1, 0) + I.get(2, 0) * I.get(2, 0));

		double A_rotate = Math.asin(I.get(2, 0) / dI);

		double temp = -I.get(2, 0) * Math.sin(A_rotate) + I.get(1, 0) * Math.cos(A_rotate);
		W_rotate = Math.atan(I.get(0, 0) / temp);
		
		wheellct.setWheelOrientation(I);
		wheellct.setWheelCenter(vG);
		
		
		System.out.println("mTheta = " +mTheta + ", anpha = " + anpha );
		
		// Calculate ContactLine
		System.out.println("Y0 = " + Y0 + "Z0 = " + Z0);
		contactpoint_generation( R, Y0, Z0, m_helix, (PI/2-W_rotate),  aWheel1V1,  contactLine);
		
		
		return wheellct;
		
	}
	
	
	public ContactLine getCalculatedCTLine()
	{
		return contactLine;
	}
	
	
	/** Rotate the flute profile to the standard position.
	 * 
	 */
	public void getstandarlizeFluteProfile()
	{
		
		// Determine the angle require for rotation.
			double x1 = contactLine.fullProfile[0][0];
			double y1 = contactLine.fullProfile[0][1];
			double d = Math.sqrt(x1*x1 + y1*y1);
			double rotatedAngle = 0;
			int icase =0;
			
			if( x1 >=0 &&y1 >=0)
			{
				rotatedAngle = Math.asin(x1/d);
				icase = 1;
			}
			else if(x1 <0 &&y1 >=0)
			{
				rotatedAngle = Math.asin(x1/d);
				icase = 2;
			}
			else if(x1 <=0 &&y1 <0)
			{
				rotatedAngle = -Math.asin(x1/d) - PI;
				
				icase = 3;
			}
			else if(x1 >0 &&y1 <= 0)
			{
				rotatedAngle = -Math.asin(x1/d) -PI;
				icase = 4;
			}

			
			
			//System.out.println("X1 = " +x1 + " Y1 =" +y1 );
			//System.out.println(rotatedAngle*180/PI + " Case:" + icase);
			
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
			
			profile_isarranged = true;
			
		}
		
	public double[][] getProfile()
	{
		return contactLine.getfullProfile();
	}
	
	
	public CWheelLocation getWheelLCT(CWheelSetting_out wheelsettingOut, CWheel1V1 aWheel1V1)
	{
		CWheelLocation wheellct = new CWheelLocation();
		
		// Read Flute parameters
				double R = this.getdWorkRadius(); // Work radius
				double m_helix = this.getdHelixAngle(); // Helix angle
				double m_rake_ra = (dRakeAngle); // Rake angle in
															// cross-section
				double m_rakea = Math.atan(Math.tan(m_rake_ra) * Math.cos(m_helix));
		         
				double mTheta = wheelsettingOut.theta;
				
		        double Rw = aWheel1V1.getDiaRight();
		
		// Determine I, and G for return the Result.
				double[][] Tc_arr = { { -Math.cos(m_helix) }, { Math.sin(m_helix) }, { 0 } };
				Matrix Tc = new Matrix(Tc_arr);  // tangent vector

				double[][] Bc_arr = { { Math.sin(m_helix) }, { Math.cos(m_helix) }, { 0 } };
				Matrix Bc = new Matrix(Bc_arr);  // Bi-normal vector

				double[][] OC_arr = { { 0 }, { 0 }, { R } };
				Matrix OC = new Matrix(OC_arr);  // vector form center to cutting end point in the flute

				double[][] Nc_arr = { { 0 }, { 0 }, { 1 } };
				Matrix Nc = new Matrix(Nc_arr);  // normal vector at cutting point

				// N_FC=sin(m_rakea)*Nc+cos(m_rakea)*Bc;
				Matrix N_FC = (Nc.times(Math.sin(m_rakea))).plus(Bc.times(Math.cos(m_rakea)));
				
				Matrix nGC;    // Vector connect wheel center to the first cutting point of the flute
				Matrix vG;     // Wheel center location in workpiece coordinate

				
				double  anpha = wheelsettingOut.anpha;
				double W_rotate =0;
				double Y0=0, Z0=0;
				double[][] nGC_arr = { { Math.sin(mTheta) * Math.cos(anpha + m_helix) },
						{ Math.sin(-mTheta) * Math.sin(anpha + m_helix) }, { Math.cos(mTheta) } };

				nGC = new Matrix(nGC_arr);
				vG = OC.plus(nGC.times(Rw)); // G=OC+Rw*nGC;

				
				Matrix I_tem = Matrix.cross(N_FC, nGC);

				double dI_temLength = Math.sqrt(I_tem.get(0, 0) * I_tem.get(0, 0) + I_tem.get(1, 0) * I_tem.get(1, 0)
						+ I_tem.get(2, 0) * I_tem.get(2, 0));

				I_tem = I_tem.times(1 / dI_temLength); // Normalize
				Matrix I = Matrix.cross(I_tem, nGC);   // Wheel axis orientation.

				double phi_rotate = -Math.atan(I.get(2, 0) / I.get(1, 0));
				 Y0 = Math.cos(phi_rotate) * vG.get(1, 0) - Math.sin(phi_rotate) * vG.get(2, 0);
				 Z0 = Math.sin(phi_rotate) * vG.get(1, 0) + Math.cos(phi_rotate) * vG.get(2, 0);

				//System.out.print("Y0 : " + Y0 + "  Z0: " + Z0 + "\r\n");

				double dI = Math.sqrt(I.get(1, 0) * I.get(1, 0) + I.get(2, 0) * I.get(2, 0));

				double A_rotate = Math.asin(I.get(2, 0) / dI);

				double temp = -I.get(2, 0) * Math.sin(A_rotate) + I.get(1, 0) * Math.cos(A_rotate);
				W_rotate = Math.atan(I.get(0, 0) / temp);
				
				wheellct.setWheelOrientation(I);
				wheellct.setWheelCenter(vG);
		
		
		
		return wheellct;
		
	}
	

}
