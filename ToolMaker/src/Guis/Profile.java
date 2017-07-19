package Guis;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import GeometryPackage.CFluting;
import GeometryPackage.CRecess;
import GeometryPackage.ContactLine;
import HAlgorithm.HFile;
import Jama.Matrix;

public class Profile {
	
	
	//Add this class to Server
	// fields
	public static final double PI = Math.PI;
	
	private double outradius;
    private double incribed_radius;
	private int numofTeeth;
	private double margin;
	
	private  double cuttingLen;
	private static int numofSection =30;
	private double helixAng;
	
	//List to hold each generated curves.
    List<List<Double>> listFinalPoint = new ArrayList<List<Double>>();
    
    
    //Constructors
    //List<Double> finalCross = new ArrayList<Double>();     
    //List<Double> finalCross_closed = new ArrayList<Double>();
    
    List<List<Double>> listFullCross_Section = new ArrayList<List<Double>>();
    
    /** This function calculate the final shape of Cross-section
     * 
     * @param clt1  : Profile of fluting operation
     * @param clt2  : Profile of recessing operation
     */
    
    /** This constructor form the cross section of Insert.
     * 
     * @param theFlute
     * @param theRecess
     */
    public Profile(CFluting theFlute, CRecess theRecess)
    {
    	outradius = theFlute.getdWorkRadius();
    	incribed_radius = outradius*theFlute.getdCoreRatio();
    	numofTeeth = theFlute.getInumofTeeth();
    	margin     = theRecess.getdMarginThick();
    	cuttingLen = theFlute.getDcutLen();
    	helixAng = theFlute.getdHelixAngle();
    	
    	intersect_two_Cross( theFlute,  theRecess); // Points in flute and recess curve
    	addthePointsInLand();                       // Add points in Margin
    	extendFullProfile();                        // Determine full profile
    }
    
    
    public List<List<Double>> getIntersectedProfile()
    {
    	return listFullCross_Section;
    }
    
    // Return workradius
    public double getOutRadius()
    {
    	return outradius;
    }
    
    public double getIncribeRadius()
    {
    	return incribed_radius;
    }

    
    
    /** This function calculate the remain profile of Recess and Flute
     * 
     * @param theFlute
     * @param theRecess
     */
    public void intersect_two_Cross(CFluting theFlute, CRecess theRecess)
    {
    	
    	double[][] ctl1 = theFlute.getProfile();
		int numPoint_ctl1 = ctl1.length;
    	
		if (theFlute.getInumofTeeth() == 2) 
		{
			

			double[][] ctl2 = theRecess.getProfile();
			int numPoint_ctl2 = ctl2.length;

			// double[][] finalprofile_1teeth = new double[numPoint_ctl1 +
			// numPoint_ctl2][4];
			// List<List<Double>> listFinalPoint = new
			// ArrayList<List<Double>>();

			// THis part find the minimum distance between two point of two
			// curve
			// Teperary varible
			double dis_min, dist_ij = 0;
			int i_Its = 0, j_Its = 0;

			dis_min = Math.sqrt((ctl1[0][0] - ctl2[0][0]) * (ctl1[0][0] - ctl2[0][0])
					+ (ctl1[0][1] - ctl2[0][1]) * (ctl1[0][1] - ctl2[0][1]));

			for (int i = 0; i < numPoint_ctl1; i++) {
				for (int j = 0; j < numPoint_ctl2; j++) {
					dist_ij = Math.sqrt((ctl1[i][0] - ctl2[j][0]) * (ctl1[i][0] - ctl2[j][0])
							+ (ctl1[i][1] - ctl2[j][1]) * (ctl1[i][1] - ctl2[j][1]));

					// If two are closer then record.
					if (dist_ij < dis_min) {
						dis_min = dist_ij;
						i_Its = i;
						j_Its = j;

					}

				}

			}

			// Add trim two curves and record the point in the final Profile

			for (int i = 0; i <= i_Its; i++) {

				if( ((i % 5) ==0)||(i ==i_Its))
				{
				listFinalPoint.add(Arrays.asList(ctl1[i][0], ctl1[i][1]));
				}

			}

			for (int j = j_Its; j < numPoint_ctl2; j++) {
				
				if( ((j%5)==0)||(j==numPoint_ctl2-1)||(j == j_Its) )
				{
				listFinalPoint.add(Arrays.asList(ctl2[j][0], ctl2[j][1]));
				}

			}

		}

		else 
		{
			// Cross-section is Flute cross section.
			for (int i = 0; i < ctl1.length; i++) 
			{

				listFinalPoint.add(Arrays.asList(ctl1[i][0], ctl1[i][1]));

			}
			

		}
    	
    	
    	
    	
    }
    
    
    
    
    
    public void addthePointsInLand()
    {
    	int numofPointinMargin = (int) Math.round(margin/(PI/180));
    	double margin_div = (double)margin/numofPointinMargin;
    	
    	double xe, ye, xn, yn,ye1;
    	double rotationAngle;
    	
    	xe = listFinalPoint.get(listFinalPoint.size() -1).get(0);
    	ye1 = listFinalPoint.get(listFinalPoint.size() -1).get(1);
    	
    	if( ye1>= 0)
    	{
    	    ye =  Math.sqrt(outradius*outradius - xe*xe);
    	}
    	else
    	{
    		ye =  -Math.sqrt(outradius*outradius - xe*xe);
    	}
    	
    	// Rotate this point by small angle margin_div
    	for(int i = 0; i <=numofPointinMargin; i++)
    	{
    		
    		rotationAngle = i*margin_div;    		
    		xn = xe*Math.cos(rotationAngle) - ye*Math.sin(rotationAngle);
    		yn = xe*Math.sin(rotationAngle) + ye*Math.cos(rotationAngle);
    		listFinalPoint.add(Arrays.asList(xn, yn));	
    		
    		//System.out.println("Xn, Yn =" + xn + ", " + yn);
    	}
    	
    	//System.out.println("Margin:" + margin + " Points : " + numofPointinMargin);
    	
    }
    
    
    public void extendFullProfile()
    {
    	double rotation_angle_one_flute = 2*PI/numofTeeth ;
    	
    	double xn, yn, xj, yj;
    	
    	for(int i = 1; i<= numofTeeth ; i++)
    	{
    		rotation_angle_one_flute = i*2*PI/numofTeeth;
    		
    		for(int j =0; j< listFinalPoint.size(); j++ )
    		{
    			
    			xj = listFinalPoint.get(j).get(0);
    			yj = listFinalPoint.get(j).get(1);
    			
    			xn = xj*Math.cos(rotation_angle_one_flute) - yj*Math.sin(rotation_angle_one_flute);
        		yn = xj*Math.sin(rotation_angle_one_flute) + yj*Math.cos(rotation_angle_one_flute);
        		listFullCross_Section.add(Arrays.asList(xn, yn));	
    			
        		// test
        		//
    		}
    		
    	}
    	
    	write3dWorkpiece( );
    	
    }
    
    
    /** This functions write stl file of the generated workpiece.
     * 
     */
    public static void writeStlWorkpiece( )
    {
    	
    }
    
    // This function writes the 3D object in stl format.
    // Using fullProfile cross-section.
    public void write3dWorkpiece( )
    {
    	Path Work_STL = Paths.get("./src/Simulation_Pack/Workpiece.STL");		
		BufferedWriter bw_WP_STL = null;
		
		// Open file and initialize the content of the file.
		try {
			// Open file and clear the remained data in it.
			bw_WP_STL = Files.newBufferedWriter(Work_STL, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
			 
			 
		} catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		HFile.bwriteln(bw_WP_STL, "solid Workpiece");
		// Write detail here.		
		// Top cross section
		List<Double> zerosPoint = new ArrayList<Double>();
		
		zerosPoint.add(0.0);
		zerosPoint.add(0.0);
		zerosPoint.add(0.0);
		
		List<Double> point1, point2, point3;
		
		for(int i =1; i< listFullCross_Section.size(); i++)
		{            
			
			point1 = new ArrayList<>(listFullCross_Section.get(i-1));
	    	point2 = new ArrayList<>(zerosPoint);
	    	point3 = new ArrayList<>(listFullCross_Section.get(i));
	    	point1.add(0.0);
	    	point2.add(0.0);
	    	point3.add(0.0);
			writeTriangular( bw_WP_STL,point1, point2, point3);
			
		}
		
		
		
		// Write the envelope of endmill
		double z_step = cuttingLen/numofSection;
		double rotate_i1,rotate_i0;
		
		List<List<Double>> section_3Di1,section_3Di0 ;
		//section_3Di0 = rotateSection(listFullCross_Section, 0.0);
		
		for(int i =1; i< numofSection ; i++)
		{
			rotate_i0 = -(i-1)*z_step*Math.tan(helixAng)/outradius;
			rotate_i1 = -i*z_step*Math.tan(helixAng)/outradius;
			section_3Di0 = rotateSection(listFullCross_Section, rotate_i0);
			section_3Di1 = rotateSection(listFullCross_Section, rotate_i1);
			writeToSTL_connectionOfTwoSection(bw_WP_STL, section_3Di0, section_3Di1);
			
		}
		
		
		HFile.bwriteln(bw_WP_STL, "endsolid Workpiece");
		
    }
    
    
    public void writeTriangular(BufferedWriter bw_WP_STL,List<Double> point1, List<Double> point2, List<Double> point3)
    {
    	//point1 = new ArrayList<>(point1);
    	//point2 = new ArrayList<>(point2);
    	//point3 = new ArrayList<>(point3);
    	//point1.add(0.0);
    	//point2.add(0.0);
    	//point3.add(0.0);
    	
    	Double[] dP1 = point1.toArray(new Double[point1.size()]);
    	//dP1[2] = 0.0;
    	
    	double[][] aP1 = new double[3][1];
    	
    	aP1[0][0] = dP1[0];
    	aP1[1][0] = dP1[1];
    	aP1[2][0] = dP1[2];
    	
    	Matrix mP1 = (new Matrix(aP1)).copy();    	
    	
        dP1 = point2.toArray(new Double[point2.size()]);    	
        //dP1[2] = 0.0;
        
    	aP1[0][0] = dP1[0];
    	aP1[1][0] = dP1[1];
    	aP1[2][0] = dP1[2];
    	
    	Matrix mP2 = (new Matrix(aP1)).copy();
    	
    	//  Point 3
    	dP1 = point3.toArray(new Double[point3.size()]);    	
    	//dP1[2] = 0.0;
    	aP1[0][0] = dP1[0];
    	aP1[1][0] = dP1[1];
    	aP1[2][0] = dP1[2];
    	
    	Matrix mP3 = (new Matrix(aP1)).copy();
    	
    	Matrix normal = Matrix.getNormalof3Points(mP1, mP2, mP3);
    	
    	// Write normal:
    	String formatedStr = String.format("   facet normal %.2f %.2f %.2f", 
    			                                normal.get(2, 0),normal.get(0, 0),normal.get(1, 0));
    	HFile.bwriteln(bw_WP_STL, formatedStr);
    	
    	HFile.bwriteln(bw_WP_STL, "      outer loop");
    	
    	// P1
        formatedStr = String.format("         vertex %.2f %.2f %.2f", 
                                            point1.get(2), point1.get(0), point1.get(1));
    	HFile.bwriteln(bw_WP_STL, formatedStr);
    	
    	// P2
        formatedStr = String.format("         vertex %.2f %.2f %.2f", 
                                            point2.get(2), point2.get(0), point2.get(1));
    	HFile.bwriteln(bw_WP_STL, formatedStr);
    	
    	// P3
        formatedStr = String.format("         vertex %.2f %.2f %.2f", 
                                            point3.get(2), point3.get(0), point3.get(1));
    	HFile.bwriteln(bw_WP_STL, formatedStr);
    	
    	HFile.bwriteln(bw_WP_STL, "      outer loop");
    	HFile.bwriteln(bw_WP_STL, "   endfacet");    	    	
    	
    }
    
    public void writeToSTL_connectionOfTwoSection(BufferedWriter bw_WP_STL, List<List<Double>> sec_i0, List<List<Double>> sec_i1)
    {
    	int numofPoint = sec_i0.size();
    	List<Double> p1;
    	List<Double> p2;
    	List<Double> p3;
    	List<Double> p4;
    	
    	for(int i =0; i< numofPoint-1; i++)
    	{
    		p1 = sec_i0.get(i);
    		p2 = sec_i1.get(i);
    		p3 = sec_i0.get(i+1);
    		p4 = sec_i1.get(i+1);
    		
    		// Write the Triangular
    		writeTriangular(bw_WP_STL, p1, p3, p2);
    		writeTriangular(bw_WP_STL, p2, p3, p4);
    		
    	}
    	
    }
    
    
    /** Return the cross-section with 3D coordinate.
     * 
     * @param section2D : Worpirce cross-section
     * @return
     */
    public List<List<Double>> rotateSection(List<List<Double>> section2D, double rotatedAmount)
    {
    	List<List<Double>> section_next = new ArrayList<List<Double>>();
    	
    	double x1, y1;
    	List<Double> point_tem = new ArrayList<Double>();   
    	
    	double z1 = rotatedAmount*outradius/Math.tan(helixAng);
    	
    	for(int i =0; i< section2D.size(); i++)
    	{
    		
    		x1 = section2D.get(i).get(0)*Math.cos(rotatedAmount) -section2D.get(i).get(1)*Math.sin(rotatedAmount);
    		y1 = section2D.get(i).get(0)*Math.sin(rotatedAmount) +section2D.get(i).get(1)*Math.cos(rotatedAmount);
    		//point_tem.add(x1);
    		//point_tem.add(y1);
    		//point_tem.add(z1);    		
    		section_next.add(Arrays.asList(x1, y1, z1));
    		
    	}    	
    	
    	
    	return section_next;
    	
    }
   
  
}
