package Guis;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GeometryPackage.CFluting;
import GeometryPackage.CRecess;

/** This is class for drawing cross-section of the endmill.
 * 
 * @author HIENBN
 *
 */

public class CrossSection extends JFrame{
	
	// Size of cross-section.
	public static final int CANVAS_WIDTH = 640;
	public static final int CANVAS_HIGH = 640;
	
	//Declare an instance of the drawing canvas
	private DrawCanvas canvas;

	
	// Fields:
	List<List<Double>>  pointsCurve;

	Profile endmill2fluteProfile;
	double outRadius;
	double innerRadius;
	
	// Constructor
	
	
	
	// Constructor to set up GUI components and envent handles
	public CrossSection(CFluting theFlute, CRecess theRecess)
	{
		//List<List<Double>> pointsCurve;
		
	    endmill2fluteProfile = new Profile( theFlute,  theRecess);
		
		this.pointsCurve = endmill2fluteProfile.getIntersectedProfile();
		
		outRadius = endmill2fluteProfile.getOutRadius();
		innerRadius = endmill2fluteProfile.getIncribeRadius();
		
		
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HIGH));
	  
		//Set the Drawing JPanel as JFrame's content-pane
		Container cp = getContentPane();
		cp.add(canvas);
		// or "setContentPane (canvas)"		
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Cross-section");
		setVisible(true);
		
		
				
	}
	
	/**
	  * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
	  */
	@SuppressWarnings("serial")
	private class DrawCanvas extends JPanel
	{
		
		
		
		@Override
		public void paintComponent(Graphics g1)
		{
			Graphics2D g = (Graphics2D)g1;
			AffineTransform oldTransform = g.getTransform();
			
			//g.scale(5, 5);
			//g.translate(10, 10);
			
			
			super.paintComponent(g); // Paint parent' BG
			setBackground(Color.GRAY);// setBG color of this Panel
			
			
			
			// Your custom painting codes. For example,
	         // Drawing primitive shapes
	         /*
			 g.setColor(Color.YELLOW);    // set the drawing color
	         g.drawLine(30, 40, 100, 200);
	         g.drawOval(150, 150, 100, 100);
	         g.drawRect(200, 210, 20, 30);
	         g.setColor(Color.RED);       // change the drawing color
	         g.fillOval(300, 310, 30, 50);
	         g.fillRect(400, 350, 60, 50);
	         // Printing texts
	         g.setColor(Color.WHITE);
	         g.setFont(new Font("Monospaced", Font.PLAIN, 12));
	         g.drawString("Testing custom drawing ...", 10, 20);
	         */
			
			drawCurve(g, pointsCurve);
			
			
			
	       //  g.setTransform(oldTransform);
			
		}
		
		
		public void drawCurve(Graphics2D g, List<List<Double>> arraypoints)
		{
			Color previous_COLOR = g.getColor();
			g.setColor(Color.BLUE);
			
			 AffineTransform at = AffineTransform.getScaleInstance(50, 50);
			 at.translate(outRadius +1 , outRadius +1);

			 Shape tranformedShape, tfPoint, outCircle, inCircle;
			 
			
			for(int i =0; i<arraypoints.size()-1; i++)
			{
			    tranformedShape = at.createTransformedShape(new Line2D.Double(arraypoints.get(i).get(0), -arraypoints.get(i).get(1), 
			    		                                      arraypoints.get(i+1).get(0), -arraypoints.get(i+1).get(1))) ;
				g.draw(tranformedShape);			
								
			}
			
			//g.drawOval(150, 150, 100, 100);
			outCircle = at.createTransformedShape( new Ellipse2D.Double(-outRadius, -outRadius, 2*outRadius, 2*outRadius));
			inCircle =   at.createTransformedShape( new Ellipse2D.Double(-innerRadius, -innerRadius, 2*innerRadius, 2*innerRadius));
			
			tfPoint = at.createTransformedShape(new Line2D.Double(0.0, 0.0, 0.0, 0.0));
			
			g.setColor(Color.CYAN);
			g.draw(outCircle);
			
			g.setColor(Color.YELLOW);
			g.draw(inCircle);
			g.draw(tfPoint);
					
						
		}		
		
	}
	
	
	
}
