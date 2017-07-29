package machineKinematic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.Math.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import GeometryPackage.CBlank;
import Jama.Matrix;
import HAlgorithm.HFile;

/**
 * <h1>This class contains statics functions those convert the wheel pose to
 * machine coordinate</h1>
 * 
 *
 *
 */
public class CinverseKinematic {

	public static void main(String[] args) {
		
		String line = "G90G00Y0.0";
		String axesName  = "A";
		int idA = 6;
		
		//double vale = getValue( line, axesName, idA);
		String string_out = verifyNCLine( line);
		
		System.out.println(string_out);
		

	}

	/**
	 * <h1>Convert Wheel pose to required machine's axes.</h1>
	 * 
	 * @param I
	 *            : Wheel axis orientation.
	 * @param G
	 *            : Wheel center location.
	 * @return : Machine axes required and Wheel center in work-piece
	 *         coordinate.
	 */
	public static CMachine_Axes cvWheelPose_toAxes(Matrix Iinput, Matrix Ginput) {
		// Need to load machine setting into here.

		// Check I and G are vector 3x1;

		// Swap I(2)<---->I(3)

		// copy but not modified I, G.

		Matrix I;
		Matrix Gi;

		I = Iinput.copy();
		Gi = Ginput.copy();

		double temp = I.get(1, 0);
		I.set(1, 0, I.get(2, 0));
		I.set(2, 0, temp);

		temp = Gi.get(1, 0);
		Gi.set(1, 0, Gi.get(2, 0));
		Gi.set(2, 0, temp);

		int test_mode = 1;

		CMachine_Axes machine_axes = new CMachine_Axes();

		// Determine corresponding A,W and G:
		double A = 0, W = 0;
		double dI12 = Math.sqrt(I.get(1, 0) * I.get(1, 0) + I.get(2, 0) * I.get(2, 0));
		A = Math.asin(I.get(1, 0) / dI12);

		// Rotation matrix of A axis
		// [1 0 0;0 cos(A_ang) -sin(A_ang);0 sin(A_ang) cos(A_ang)]
		double[][] ro_a = { { 1, 0, 0 }, { 0, Math.cos(-A), -Math.sin(-A) }, { 0, Math.sin(-A), Math.cos(-A) } };
		Matrix RO_A = new Matrix(ro_a); // Rotation matrix of A axis.
		Matrix I1 = RO_A.times(I);// after rotation A I---> I1 : I1 = MA*I;

		// Required W-axis
		W = Math.atan(I1.get(0, 0) / I1.get(2, 0));

		double[][] ro_w = { { Math.cos(W), 0, Math.sin(W) }, { 0, 1, 0 }, { -Math.sin(W), 0, Math.cos(W) } };
		Matrix RO_W = new Matrix(ro_w);

		// =============TEST=====================
		double[][] ro_a1 = { { 1, 0, 0 }, { 0, Math.cos(A), -Math.sin(A) }, { 0, Math.sin(A), Math.cos(A) } };
		Matrix RO_A1 = new Matrix(ro_a1); // Rotation matrix of A axis.

		double[][] ro_w1 = { { Math.cos(W), 0, Math.sin(W) }, { 0, 1, 0 }, { -Math.sin(W), 0, Math.cos(W) } };
		Matrix RO_W1 = new Matrix(ro_w1);

		Matrix Gotepm = (Gi.transpose()).times(RO_A1.times(RO_W1));
		Matrix Go = Gotepm.transpose();

		if (test_mode == 1) {
			System.out.println("I:" + I.get(0, 0) + "," + I.get(1, 0) + "," + I.get(2, 0));
			System.out.println("G:" + Go.get(0, 0) + "," + Go.get(1, 0) + "," + Go.get(2, 0));
			System.out.println("Required Axes: A=" + A + ", W = " + W);
		}

		// Assign to object
		machine_axes.A = A;
		machine_axes.W = W;
		machine_axes.G = Go;

		return machine_axes;

	}

	/**
	 * getStartnEndPositions return the machine axes's position at the starting
	 * and end point.
	 * <p>
	 * 
	 * @param cutLen
	 *            : cutting length -mm
	 * @param R
	 *            : radius of workpice-mm
	 * @parame helix : helix angle - degree.
	 * 
	 * @param mca_atTipofcuttingedge
	 *            : machine axes at the grinding point
	 * @return machine axes positions at three points: approaching, grinding and
	 *         end points.
	 */
	public static List<CMachine_Axes> getStartnEndPositions(CMachine_Axes mca_atTipofcuttingedge, CBlank aBlank,
			double offset_dis, double cutLenOff) {

		double ctl = aBlank.getDcutLen() + cutLenOff;
		double helix = aBlank.getdHelixAngle();
		double R = aBlank.getdWorkRadius();

		List<CMachine_Axes> mca_start_tip_endpoint = new ArrayList<CMachine_Axes>();

		// Machine axes at three position:
		CMachine_Axes mca_approachingPoint = new CMachine_Axes();
		CMachine_Axes mca_grindingPoint = new CMachine_Axes();
		CMachine_Axes mca_EndPoint = new CMachine_Axes();

		mca_approachingPoint.W = mca_atTipofcuttingedge.W;
		mca_grindingPoint.W = mca_atTipofcuttingedge.W;
		mca_EndPoint.W = mca_atTipofcuttingedge.W;

		// NC code at the grinding position.
		mca_grindingPoint.X = -mca_atTipofcuttingedge.G.get(0, 0);
		mca_grindingPoint.Y = mca_atTipofcuttingedge.G.get(1, 0);
		mca_grindingPoint.Z = mca_atTipofcuttingedge.G.get(2, 0);
		mca_grindingPoint.A = mca_atTipofcuttingedge.A;

		// NC code at the end poing of grinding path
		mca_EndPoint.X = mca_grindingPoint.X + ctl * Math.cos(mca_atTipofcuttingedge.W);
		mca_EndPoint.Y = mca_grindingPoint.Y;
		mca_EndPoint.Z = mca_grindingPoint.Z - ctl * Math.sin(mca_atTipofcuttingedge.W);
		mca_EndPoint.A = mca_grindingPoint.A + ctl * Math.tan(helix) / R;// required
																			// amount
																			// of
																			// A-axis.

		// Approaching is point offset offset_dis in X axis from the grinding
		// point
		double xOffset = offset_dis * Math.cos(mca_atTipofcuttingedge.W);
		double zOffset = -offset_dis * Math.sin(mca_atTipofcuttingedge.W);
		double aOffset = offset_dis * Math.tan(helix) / R; // Radial

		mca_approachingPoint.X = mca_grindingPoint.X - xOffset;
		mca_approachingPoint.Z = mca_grindingPoint.Z - zOffset;
		mca_approachingPoint.A = mca_grindingPoint.A - aOffset;
		mca_approachingPoint.Y = mca_grindingPoint.Y;

		// Add to the list.
		mca_start_tip_endpoint.add(mca_approachingPoint);
		mca_start_tip_endpoint.add(mca_grindingPoint);
		mca_start_tip_endpoint.add(mca_EndPoint);

		return mca_start_tip_endpoint;
	}

	// if you don't love your work, then it will be a suffer for you.
	public static void writeNCCodetoFile(List<CMachine_Axes> list3Points, Path file) {

	}

	/**
	 * Read NC File and translate and interpolate it for simulation use.
	 * 
	 * NC File: O3110 Translated: ISdata/NC_code_sim.txt
	 * 
	 */
	public static void interpolateNCCODE() {

		// ======PREPRATE FILE TO READ AND WRITE===============/

		// Global Variable
		BufferedWriter bufWrite = null;
		BufferedReader buffRead = null;
		String line; // Line of NC code

		String filelct = new File("").getAbsolutePath();
		// System.out.println(filelct);
		// File to Read
		Path file = Paths.get("./src/" + "/Simulation_Pack/O3110");

		Charset charset = Charset.forName("US-ASCII"); // Charset using western
														// charaters.

		try {

			buffRead = Files.newBufferedReader(file, charset);

			line = null;

			// Analyse the NC code and interpolated it.

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Prepare a file to write
		Path file_output = Paths.get("./src/" + "/Simulation_Pack/ISdata/NC_code_sim.txt");

		HFile.openNewFile(file_output);
		HFile.clear(file_output);
		
		// Open a new write.
		
		try {
			bufWrite = Files.newBufferedWriter(file_output, charset, StandardOpenOption.CREATE);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.err.println("The directory does not exist");
			e1.printStackTrace();
		}
		

		// =============================================

		// Present machine at some safe position.
		final double X_HOME, Y_HOME, Z_HOME, A_HOME, W_HOME, F_START;
		X_HOME = 100;
		Y_HOME = 77;
		Z_HOME = 0;
		A_HOME = 0;
		W_HOME = 0;
		F_START = 9999;

		double mX, mY, mZ, mA, mW, mF;      //Previous value in G90
		double vlX, vlY, vlZ, vlA, vlW, vlF;// New Value in G90
		
		// Initialize the machine axes positions
		mX = X_HOME;
		mY = Y_HOME;
		mZ = Z_HOME;
		mA = A_HOME;
		mW = W_HOME;
		mF = F_START;
		
		vlX = mX;
		vlY = mY;
		vlZ = mZ;
		vlA = mA;
		vlW = mW;
		vlF = mF;
		

		
		int idX = -1, idY =-1, idZ = -1, idA = -1, idW= -1, idF = -1;
		
		double mMode = 1; // G90G01
		double mHome = 0; // Is at home position.

		double delta_inter = 0.5; // degree
		double max_delta; // To be for division in interpolation. 
		double n_inter = 1;
		String fmString = null;
		

		try {

			buffRead = Files.newBufferedReader(file, charset);

			line = null;

	       /*=====Analyse the NC code and interpolated it===*/
			
			while ((line = buffRead.readLine()) != null) 
			{
				boolean validNCLine = true;
				
				line =line.replaceAll("\\s+","");// Remove all whitespace
				
				line = removeParathesis(line);
				
			// Out put must the validLine value
				// Ignore if it is comment of contain #
				if(line.isEmpty())
				{
					validNCLine = false;					
				}
				else					
				{
					//Simplify the String.
					line = verifyNCLine( line);
					if(line.isEmpty())
					{
						validNCLine = false;
					}
									
				}
				
				
				// If line contains NC code, then do INTERPOLATION
				if(validNCLine)
				{
					
				   //Check has G90 or G91	
					if(line.contains("G90"))
					{
						mMode =1; 
					}
					else if(line.contains("G91"))
					{
						mMode =0;
					}
				  // Check Gcode to go to home position	
					if(line.contains("G30"))
					{
						mHome =1;
					}
					else
					{
						mHome =0;
					}
					
					//Read value of X, Y, Z, A, W in the line.
					idX = -1; idY =-1; idZ = -1; idA = -1; idW= -1; idF = -1; // Reset at -1
					
					idX = line.indexOf("X");
					idY = line.indexOf("Y");
					idZ = line.indexOf("Z");
					idA = line.indexOf("A");
					idW = line.indexOf("W");
					idF = line.indexOf("F");
					
					if (mMode == 1) {
						// Get value of X axis
						if (idX >= 0) {
							vlX = getValue(line, "X", idX);

						} else {
							vlX = mX;
						}

						// Get value of Y axis
						if (idY >= 0) {
							vlY = getValue(line, "Y", idY);

						} else {
							vlY = mY;
						}

						// Get value of Z axis
						if (idZ >= 0) {
							vlZ = getValue(line, "Z", idZ);

						} else {
							vlZ = mZ;
						}

						// Get value of A axis
						if (idA >= 0) {
							vlA = getValue(line, "A", idA);

						} else {
							vlA = mA;
						}

						// Get value of W axis
						if (idW >= 0) {
							vlW = getValue(line, "W", idW);

						} else {
							vlW = mW;
						}
						// Get value of F axis
						if (idF >= 0) {
							vlF = getValue(line, "F", idF);

						} else {
							vlF = mF;
						}

					}
					else 
				/** Relative mode  */
					{
						// Get value of X axis
						if (idX >= 0) {
							vlX = mX + getValue(line, "X", idX);

						} else {
							vlX = mX;
						}

						// Get value of Y axis
						if (idY >= 0) {
							vlY = mY + getValue(line, "Y", idY);

						} else {
							vlY = mY;
						}

						// Get value of Z axis
						if (idZ >= 0) {
							vlZ = mZ + getValue(line, "Z", idZ);

						} else {
							vlZ = mZ;
						}

						// Get value of A axis
						if (idA >= 0) {
							vlA = mA + getValue(line, "A", idA);

						} else {
							vlA = mA;
						}

						// Get value of W axis
						if (idW >= 0) {
							vlW = mW + getValue(line, "W", idW);

						} else {
							vlW = mW;
						}
						// Get value of F axis
						if (idF >= 0) {
							vlF = getValue(line, "F", idF);

						} else {
							vlF = mF;
						}

					}
					
					
					// HOME POSITION
					if(mHome == 1)
					{
						if (idX >= 0) {vlX = X_HOME;}
						if (idY >= 0) {vlY = Y_HOME;}
						if (idZ >= 0) {vlZ = Z_HOME;}
						if (idA >= 0) {vlA = A_HOME;}
						if (idW >= 0) {vlW = W_HOME;}						
						
					}
					
					
					
					
					
					
					/**========INTERPOLATION==============*/
					ArrayList<Double> deltas = new ArrayList<Double>();
					deltas.add( Math.abs(vlX - mX) );
					deltas.add( Math.abs(vlY - mY) );
					deltas.add( Math.abs(vlZ - mZ) );
					deltas.add( Math.abs(vlA - mA) );
					deltas.add( Math.abs(vlW - mW) );
					
				    max_delta = Collections.max(deltas);
					
				   // If resolition is small enought
				    if((max_delta <= delta_inter)&&(max_delta>0))
				    {
				    	fmString = String.format("%.4f %.4f %.4f %.4f %.4f", vlX, vlY, vlZ, vlA, vlW);
				    	HFile.bwriteln(bufWrite, fmString);
				    	
				    }
				    else
				    {
				    	n_inter = Math.round(max_delta/ delta_inter);
				    	if(n_inter >50) { n_inter =  50;} // No more than 50 interpolated points
				    	if(max_delta ==0){n_inter = 0;}
				    	
				    	//HFile.bwriteln(bufWrite, line);
				    	
				    	// Interpolate
				    	for(int i=1; i<n_inter +1 ; i++)
				    	{
				    		fmString = String.format("%.4f %.4f %.4f %.4f %.4f", 
				    				                (mX+(i-1)*(vlX - mX)/n_inter), 
				    				                (mY+(i-1)*(vlY - mY)/n_inter), 
				    				                (mZ+(i-1)*(vlZ - mZ)/n_inter), 
				    				                (mA+(i-1)*(vlA - mA)/n_inter), 
				    				                (mW+(i-1)*(vlW - mW)/n_inter));
				    		
				    		//System.out.println(fmString);
					    	HFile.bwriteln(bufWrite, fmString);
				    	}
				    	
				    	
				    }
				    					
				}		
				
				// Reset all axis
			    mX = vlX;
                mY = vlY;
                mZ = vlZ;
                mA = vlA;
                mW = vlW;
                mF = vlF;
                mHome=0;	
				
			}

			
		//	HFile.bflush(bufWrite);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/** Simplify the NC line
	 * <p>[1] Remove text in parathesis "( Removed )"
	 * <p>[2] Set String to be empty if it contains # charater.
	 * @param line
	 * @return
	 */
	public static String verifyNCLine(String line)
	{
		String str_out = "";
		
		line = removeParathesis(line);
		
		
		if( line.contains("#") )
		{
			str_out = "";
		}
		else
		{
			str_out = line;
		}
		
		//line= str_out;
		
		return str_out;
		
	}
	
	
	public static double getValue(String line, String axesName, int idA)
	{

		
		double value =0;
		
		String subString = line.substring(idA + 1);
		
		value = extractDoublefromString(subString);
		
		
		return value;		
	}
	
	/** Extract the double from a string : sscanf in C
	 * 
	 * @param inputStr
	 * @return
	 */
	public static double extractDoublefromString(String inputStr)
	{
		System.out.println(inputStr);
		
		
		double dOut = 0;
		
		int indexStart=0;
		int indexEnd = 0;
		String str_double;
		int numofDot = 0;
		int numofMinusSign = 0;
		
		int str_len = inputStr.length();
		
		// Check length of the String
		if(str_len ==0)
		{
			try {
				throw new NCSyntaxException("NC syntax Error, I cannot find double value from String!" + inputStr);
			} catch (NCSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Find the double value.
		char interate_char = inputStr.charAt(0);
		
		if(((Character.isDigit(interate_char) )|(interate_char == '.')|(interate_char == '-')))
		{
			
		}
		else
		{
			try {
				throw new NCSyntaxException("NC syntax Error :"  + inputStr);
			} catch (NCSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Check if char is digit of . charater.
		//boolean goodChar = ( (Character.isDigit(interate_char) )|(interate_char == '.') );
		
		while( ((Character.isDigit(interate_char) )|(interate_char == '.')|(interate_char == '-'))&& (numofDot<2)&&(indexEnd<str_len)&&(numofMinusSign<2))
		{
			interate_char = inputStr.charAt(indexEnd);
			
			if(interate_char == '.')
			{
				numofDot++;
			}
			
			if(interate_char == '-')
			{
				numofMinusSign++;
			}
			
			
			indexEnd++;			
		}
		
		str_double = inputStr.substring(0, indexEnd-1);
		
		dOut = Double.parseDouble(str_double);
		
		
		return dOut;
		
	}
	
	
	public static String removeParathesis( String line)
	{
		String str_out = "";
		
		if(line.contains("("))
		{
			
			int index_start = line.indexOf("(");
			if(!line.contains(")"))
			{
				try 
				{
					throw new NCSyntaxException("Parathesis is unblanced! in line:" + line);
				} catch (NCSyntaxException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			}
			
			int index_end = line.indexOf(")");
			
			if(  index_end   !=   (line.length() -1) )
			{
				try 
				{
					throw new NCSyntaxException("Parathesis should be positioned in the end of line: " + line);
				} catch (NCSyntaxException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			// Remove (...) in the NC line
			if(index_start ==0)
			{
				str_out = "";
				line = str_out;
				
				return str_out;
			}
			else
			{
				str_out = line.substring(0, index_start);
				line = str_out;
			}
			
			if( line.contains("#") )
			{
				str_out = "";
			}
			else
			{
				str_out = line;
			}
			
			
		}
		else
		{
			str_out = line;
		}
		
		
		
		return  str_out;
	}
	

}
