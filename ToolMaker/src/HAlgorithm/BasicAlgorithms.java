package HAlgorithm;

import java.lang.Math.*;
import java.util.concurrent.TimeUnit;

/** This class containt basic functions of the Project.
 * All functions is implemented as static functions.
 * 
 * @author HIENBN
 *
 */
public class BasicAlgorithms {
	
	
	/** Test in Main
	 * 
	 */
	
	public static void main(String[] args)
	{
	  // Test
		
		// [flag_its,r_min,Polar_shift,Open_ang] = Elip_Circle(Ru,R,yG,zG,t,anpha,beta);
		double Ru = 50;
		double R  = 3;
		double yG = 2.8797;
	    double zG =	52.4427;
	    double t  = 6.8;
	    double anpha = 0.5585;
		double beta  = 0.5236;
		
		// r_min = 2.6925
		
		// Ellips equation:
		//
		//
		//
		double[] e = {Ru*Math.sin(anpha), Ru};
		double[] y = {yG, zG};
		double[] x = {0.0,0.0};
		double minDistance =0.0;
		
		long startTime = System.nanoTime();    
		// ... the code being measured ...    
		
		
		minDistance = DistancePointEllipse (e, y, x);
			
		
		System.out.println("Minimun distance is: " + minDistance);
		
		
		
	}
	

	/**<h1> Find the distance from a point to an Elipse </h1>
	 * <p>
	 * 
	 * Elipse:
	 *   x0^2/e0^2 + x1^2/e1^2 -1 =0;
	 *   
	 * <p> This function require : y0, y1>0; e0>e1; 
	 *  
	 *  Reference: Distance from a point to an elipse, elipsoid.
	 * 
	 */	
	
	
	public static double distancePointStandard(double[] e, double[] y, double[] x)
	{
		double distance;
		   
		if(y[1]>0.0)
		{
			if(y[0]>0.0)
			{
				// Bound for t0, t1:				
				double[] esqr = { e[0]*e[0], e[1]*e[1] };
	            double[] ey = { e[0]*y[0], e[1]*y[1] };
	            double t0 = -esqr[1] + ey[1];  //ok
	            double t1 = -esqr[1] + Math.sqrt(ey[0]*ey[0] + ey[1]*ey[1]);
	            double t = t0;
	            
	             final int imax = 100000;// just example
	             
	             for (int i = 0; i < imax; ++i)
	             {
	                 t = (0.5)*(t0 + t1);
	                 if (t == t0 || t == t1)
	                 {
	                     break;
	                 }

	                 double[] r = { ey[0]/(t + esqr[0]), ey[1]/(t + esqr[1]) };
	                 double f = r[0]*r[0] + r[1]*r[1] - 1;
	                 
	                 // Set precision if |f|<0.0001
	                 if (f > 0.00001)
	                 {
	                     t0 = t;
	                 }
	                 else if (f < -0.00001)
	                 {
	                     t1 = t;
	                 }
	                 else
	                 {
	                     break;
	                 }
	                 
	                 if(i%2 == 0)
	                 {
	                	// System.out.println("Loop: " + i);
	                 }
	             }

	             x[0] = esqr[0]*y[0]/(t + esqr[0]);
	             x[1] = esqr[1]*y[1]/(t + esqr[1]);
	             double[] d = { x[0] - y[0], x[1] - y[1] };
	             distance = Math.sqrt(d[0]*d[0] + d[1]*d[1]);
	             
	            
			}
			else //y[0] =0
			{
				x[0] = (double)0.0;
	            x[1] = e[1];
	            distance = Math.abs(y[1] - e[1]);				
			}
			
		}
		else //y1 =0
		{
			double denom0 = e[0]*e[0] - e[1]*e[1];
	        double e0y0 = e[0]*y[0];
	        if (e0y0 < denom0)
	        {
	            // y0 is inside the subinterval.
	            double x0de0 = e0y0/denom0;
	            double x0de0sqr = x0de0*x0de0;
	            x[0] = e[0]*x0de0;
	            x[1] = e[1]*Math.sqrt(Math.abs((double)1 - x0de0sqr));
	            double d0 = x[0] - y[0];
	            distance = Math.sqrt(d0*d0 + x[1]*x[1]);
	        }
	        else
	        {
	            // y0 is outside the subinterval.  The closest ellipse point has
	            // x1 == 0 and is on the domain-boundary interval (x0/e0)^2 = 1.
	            x[0] = e[0];
	            x[1] = 0.0;
	            distance = Math.abs(y[0] - e[0]);
	        }
			
		}		
		
		
		return distance;
		
	}
	
	/** This is main point to call,
	 * this function convert parameters to standard form.
	 * 	 
	 */
	
	//----------------------------------------------------------------------------
	// The ellipse is (x0/e0)^2 + (x1/e1)^2 = 1.  The query point is (y0,y1).
	// The function returns the distance from the query point to the ellipse.
	// It also computes the ellipse point (x0,x1) that is closest to (y0,y1).
	//----------------------------------------------------------------------------
	
	public static double DistancePointEllipse (final double[] e, final double[] y, double[] x)
	{
		double etem0 = e[0];
		double etem1 = e[1];
	    // Determine reflections for y to the first quadrant.
	    boolean[] reflect = new boolean[2];
	    int i, j;
	    for (i = 0; i < 2; ++i)
	    {
	        reflect[i] = (y[i] < 0);  //interesting
	    }

	    // Determine the axis order for decreasing extents.
	    int[] permute = new int[2];
	    if (e[0] < e[1])
	    {
	        permute[0] = 1;  permute[1] = 0;
	    }
	    else
	    {
	        permute[0] = 0;  permute[1] = 1;
	    }

	    int[] invpermute = new int[2];
	    for (i = 0; i < 2; ++i)
	    {
	        invpermute[permute[i]] = i;
	    }

	    double[] locE = new double[2];
	    double[] locY = new double[2];
	    for (i = 0; i < 2; ++i)
	    {
	        j = permute[i];
	        locE[i] = e[j];
	        locY[i] = y[j];
	        if (reflect[j])
	        {
	            locY[i] = -locY[i];
	        }
	    }

	    double[] locX = new double[2];
	    double distance = distancePointStandard(locE, locY, locX);

	    // Restore the axis order and reflections.
	    for (i = 0; i < 2; ++i)
	    {
	        j = invpermute[i];
	        if (reflect[i])
	        {
	            locX[j] = -locX[j];
	        }
	        x[i] = locX[j];
	    }

        boolean bInsideCheck = (y[0]*y[0]/(e[0]*e[0]) + y[1]*y[1]/(e[1]*e[1])- 1 >= 0);
        if(bInsideCheck)
        	{return distance;}
        else
        {
        	{return -distance;}
        }
	}
	
	
}
