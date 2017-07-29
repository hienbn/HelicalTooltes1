package GeometryPackage;

import Jama.Matrix;

/**
 * This class is containt the description of the contactline
 * 
 * 
 * @author HIENBN
 *
 */

public class ContactLine {

	public static final double PI = Math.PI;
	// Fields
	double[][] contactPoint_firstsleeve = new double[300][4];
	double[][] contactPointEnvelope = new double[200][4];;
	double[][] contactPointEndsleeve = new double[300][4];
	double[][] fullContactLine;
	double[][] fullProfile;

	public int numPoint_firstSleev = 0;
	public int numPoint_Envelope   = 0;
	public int numPoint_Endsleeve  = 0;
	public int totalContactPoints  = 0;

	public double FluteAngle = 0;

	public ContactLine() {

	}

	// Refine the Contactline
	public double[][] reArrangeCTLINE() {
		totalContactPoints = numPoint_firstSleev + numPoint_Envelope + numPoint_Endsleeve;

		fullContactLine = new double[totalContactPoints][4];
		int ctpIndex = 0;

		// Add contactPoint in the first Sleeve in the reverse order
		for (int i = 0; i < numPoint_firstSleev; i++) {
			fullContactLine[ctpIndex] = contactPoint_firstsleeve[i];
			ctpIndex++;
		}

		// Enveloped ContactPoints
		for (int i = 0; i < numPoint_Envelope; i++) {
			fullContactLine[ctpIndex] = contactPointEnvelope[i];
			ctpIndex++;
		}

		// Contact point in the end sleeve
		for (int i = 0; i < numPoint_Endsleeve; i++) {
			fullContactLine[ctpIndex] = contactPointEndsleeve[i];
			ctpIndex++;
		}

		return fullContactLine;

	}

	// Return the cross-section from the contactLine
	public void ObtainFlutingProfile(double R, double m_Helix) {

		fullProfile = new double[totalContactPoints][4];
		double deta_Phi;
		double ph_Phi;
		double ph_Cross;
		double distancetoOrigin;

		for (int i = 0; i < totalContactPoints; i++) 
		{
			deta_Phi = fullContactLine[i][0] * Math.tan(m_Helix) / R;
			ph_Phi = Math.atan(fullContactLine[i][2] / fullContactLine[i][1]);
			distancetoOrigin = Math.sqrt(fullContactLine[i][2] * fullContactLine[i][2] + fullContactLine[i][1] * fullContactLine[i][1]);
			
			if (Math.cos(ph_Phi) * fullContactLine[i][1] < 0) {
				ph_Phi = ph_Phi + PI;
			}

			ph_Cross = ph_Phi - deta_Phi;// Shifted Angle of contact Points into
											// Work-profile.

			// Copy to the array
			fullProfile[i][0] = distancetoOrigin * Math.cos(ph_Cross);
			fullProfile[i][1] = distancetoOrigin * Math.sin(ph_Cross);
			fullProfile[i][2] = distancetoOrigin;
			fullProfile[i][3] = fullContactLine[i][3]; //

		}

	}

	public double getFluteAngle() {
		if (numPoint_firstSleev > 0) {
			double x1 = fullProfile[0][0];
			double y1 = fullProfile[0][1];
			double d1 = fullProfile[0][2];

			double xe = fullProfile[totalContactPoints - 1][0];
			double ye = fullProfile[totalContactPoints - 1][1];
			double de = fullProfile[totalContactPoints - 1][2];

			FluteAngle = Math.acos((x1 * xe + y1 * ye) / (d1 * de)); 
			// angle<OA,
			// OB> =
			// OA.OB/(dOA*dOB)
			// --->
			// dot
			// product
			// only
			// true
			// for
			// angle
			// <180
			// degree>
			// System.out.println("The 1st point" + x1 + "," + y1+ "," +d1);
			// System.out.println("The end point" + xe + "," + ye+ "," +de);
		}

		else {

		}

		return FluteAngle;
	}

	/** Get the generated contactLine, set fields.
	 * 
	 * @param R
	 * @param Y0
	 * @param Z0
	 * @param beta
	 * @param anpha
	 * @param aWheel1V1
	 */
	public  void contactpoint_generation(double R, double Y0, double Z0, double beta, double anpha,
			CWheel1V1 aWheel1V1) 
	{
		// Get the wheel Geometry:

		// Output:
		// double [][] contactpoint = new double[2000][4];
		int countactCount = 0;
		// contactpoint[0][0] = 1;
		int countCTPEnvelop = 0;
		int countCTPFirst = 0;
		int countCTPEnd = 0;

		double Rw, Rmid, Rleft, thickness, u_d, TH_L, TH_R, wheel_ang1, wheel_ang2;

		Rw = aWheel1V1.getDiaRight();
		thickness = aWheel1V1.getThicknessLeft() + aWheel1V1.getThicknessRight();
		u_d = aWheel1V1.getThicknessRight();
		TH_R = u_d;
		TH_L = thickness - u_d;
		Rmid = aWheel1V1.getDiaMax();
		Rleft = aWheel1V1.getDiaLeft();

		// Calculate wheel angles
		wheel_ang1 = -Math.atan((Rmid - Rw) / TH_R);

		if (TH_L > 0) {
			wheel_ang2 = -Math.atan((Rmid - Rleft) / TH_L);
		} else {
			wheel_ang2 = 0;
		}

		// Gi=[-R*phi/tan(beta) (Y0*cos(phi)+Z0*sin(phi))
		// (-Y0*sin(phi)+Z0*cos(phi))];
		// Ii=[cos(anpha) sin(anpha)*cos(phi) -sin(anpha)*sin(phi)];

		// Sub varibles
		double t = 0, phi = 0, Ome = 2 * PI;

		double[][] Gi_arr = { { -R * phi / Math.tan(beta) }, { Y0 * Math.cos(phi) + Z0 * Math.sin(phi) },
				{ -Y0 * Math.sin(phi) + Z0 * Math.cos(phi) } };
		double[][] Ii_arr = { { Math.cos(anpha) }, { Math.sin(anpha) * Math.cos(phi) },
				{ -Math.sin(anpha) * Math.sin(phi) } };

		Matrix Gi = new Matrix(Gi_arr);
		Matrix Ii = new Matrix(Ii_arr);

		// dGi=Ome*[-R/tan(beta) (-Y0*sin(phi)+Z0*cos(phi))
		// (-Y0*cos(phi)-Z0*sin(phi))];
		double[][] dGi_arr = { { Ome * (-R / Math.tan(beta)) }, { Ome * (-Y0 * Math.sin(phi) + Z0 * Math.cos(phi)) },
				{ Ome * (-Y0 * Math.cos(phi) - Z0 * Math.sin(phi)) } };

		Matrix dGi = new Matrix(dGi_arr);

		// Set up local coordinate system
		// ZL=Ii;
		// XL=[0 -sin(phi) -cos(phi)];
		// YL=cross(ZL,XL);

		Matrix ZL = Ii;
		double[][] XL_arr = { { 0 }, { -Math.sin(phi) }, { -Math.cos(phi) } };
		Matrix XL = new Matrix(XL_arr);
		Matrix YL = Matrix.cross(ZL, XL);

		final int WHEELSLICE_MAX = 100;
		double step_u = thickness / WHEELSLICE_MAX;
		double u; // Wheel offset section
		double Ru = Rw, dRu = 0, wheel_ang;

		// DECLARE SUB VARIABLES HERE
		double A, B, C;
		double angle1 = 0, angle = 0, angle_left_u_d = 0, angle_right_u_d = 0;
		double step_ang_non_en, angle_sub_non;
		Matrix contact = null, Pside = null;

		double dcontac, detphi, phct, Phi_cross;
		double min_sp = 0, max_sp = 0, sp = 0, dPside = 0;

		for (int i = 0; i <= WHEELSLICE_MAX; i++) {
			u = -i * step_u;

			if (u > -u_d) // Wheel section in the right side
			{
				wheel_ang = wheel_ang1;
				Ru = Rw + u * Math.tan(wheel_ang);
				dRu = Math.tan(wheel_ang);
			} else if (u <= -u_d) // Wheel section in the left side
			{
				wheel_ang = wheel_ang2;
				Ru = Rw - (u + u_d) * Math.tan(wheel_ang) - u_d * (Math.tan(wheel_ang1));
				dRu = -Math.tan(wheel_ang);
			}

			/*
			 * % set up some subparameters for calculation A=dot(dGi,XL)+
			 * Ome*sin(anpha)*u + Ome*sin(anpha)*Ru*dRu; B=dot(dGi,YL);
			 * C=dRu*dot(dGi,ZL);
			 */

			A = Matrix.dot(dGi, XL) + Ome * Math.sin(anpha) * u + Ome * Math.sin(anpha) * Ru * dRu;
			B = Matrix.dot(dGi, YL);
			C = dRu * Matrix.dot(dGi, ZL);

			/*
			 * angle1=asin(A/sqrt(A^2+B^2));
			 * 
			 * if(cos(angle1)*B<0) angle1=pi-angle1; end if(C<sqrt(A^2+B^2))
			 * angle=angle1+asin(-C/sqrt(A^2+B^2));
			 * 
			 * else angle=angle1; end
			 * 
			 */

			angle1 = Math.asin(A / Math.sqrt(A * A + B * B));

			if (Math.cos(angle1) * B < 0) {
				angle1 = PI - angle1;
			}

			if (C < Math.sqrt(A * A + B * B)) {
				angle = angle1 + Math.asin(-C / Math.sqrt(A * A + B * B));
			} else {
				angle = angle1;
			}

			/************************/

			// Handling non-enveloped contact point
			// Calculation of non-enveloped contact line
			// Saving two angle at sharp points and generate non-enveloped
			// contact line

			if ((u <= -(u_d - step_u)) && (u >= -(u_d + step_u))) {
				if ((u > -u_d) && (u <= -(u_d - step_u))) {
					angle_left_u_d = angle;

				} else if ((u <= -u_d) && (u >= -(u_d + step_u))) {
					angle_right_u_d = angle;

				}
			}

			if ((u > -(u_d + step_u)) && (u <= -u_d))

			{
				step_ang_non_en = (angle_right_u_d - angle_left_u_d) / 20;

				// Coordinate of contact point of the SHARP POINT
				for (int ii = 0; ii <= 10; ii++) {

					angle_sub_non = angle_left_u_d + step_ang_non_en * ii;
					/// contact= Gi + u*ZL + Ru*(XL*Math.cos(angle_sub_non) -
					/// YL*Math.sin(angle_sub_non));
					Matrix tem1 = (Gi.plus(ZL.times(u))).plus(XL.times(Ru * Math.cos(angle_sub_non)));
					Matrix tem2 = YL.times(-Math.sin(angle_sub_non) * Ru);
					contact = tem1.plus(tem2);

					dcontac = Math
							.sqrt((contact.get(1, 0) * contact.get(1, 0)) + (contact.get(2, 0) * contact.get(2, 0)));

					detphi = contact.get(1, 0) * Math.tan(beta) / R;
					phct = Math.atan(contact.get(2, 0) / contact.get(1, 0));

					if (Math.cos(phct) * (contact.get(1, 0) / dcontac) < 0) {
						phct = PI + phct;
					}

					Phi_cross = phct - detphi;

					// if inside the Workpiece then at the sideface
					if (dcontac <= R && t == 0) {
						// contactpoint[countactCount][0] = contact.get(0,0);
						// contactpoint[countactCount][1] = contact.get(0,1);
						// contactpoint[countactCount][2] = contact.get(0,2);
						// contactpoint[countactCount][0] = 0;
						// countactCount++;
						this.contactPointEnvelope[countCTPEnvelop][0] = contact.get(0, 0);
						this.contactPointEnvelope[countCTPEnvelop][1] = contact.get(1, 0);
						this.contactPointEnvelope[countCTPEnvelop][2] = contact.get(2, 0);
						this.contactPointEnvelope[countCTPEnvelop][3] = 1;
						countCTPEnvelop++;

					}

				}

			}

			// Envelope Point
			Matrix tem1 = (Gi.plus(ZL.times(u))).plus(XL.times(Ru * Math.cos(angle)));
			Matrix tem2 = YL.times(-Math.sin(angle) * Ru);
			contact = tem1.plus(tem2);

			dcontac = Math.sqrt((contact.get(1, 0) * contact.get(1, 0)) + (contact.get(2, 0) * contact.get(2, 0)));

			detphi = contact.get(0, 0) * Math.tan(beta) / R;
			phct = Math.atan(contact.get(2, 0) / contact.get(1, 0));

			if (Math.cos(phct) * (contact.get(1, 0) / dcontac) < 0) {
				phct = PI + phct;
			}

			Phi_cross = phct - detphi;

			// if inside the Workpiece then at the sideface
			if (dcontac <= R && t == 0) {
				double test = contact.get(0, 0);

				// contactpoint[countactCount][0] = test;
				// contactpoint[countactCount][1] = contact.get(1,0);
				// contactpoint[countactCount][2] = contact.get(2,0);
				// contactpoint[countactCount][3] = 1;

				this.contactPointEnvelope[countCTPEnvelop][0] = contact.get(0, 0);
				this.contactPointEnvelope[countCTPEnvelop][1] = contact.get(1, 0);
				this.contactPointEnvelope[countCTPEnvelop][2] = contact.get(2, 0);
				this.contactPointEnvelope[countCTPEnvelop][3] = 1;
				countCTPEnvelop++;
			}

			// FIND THE CROSS-SECTION OF THE SIDE FACE.

			if (u == 0) {
				min_sp = angle + PI;
				max_sp = angle + 2 * PI;
				sp = min_sp;

				while (sp < max_sp) {

					// Pside=Gi+u*ZL+Ru*(XL*cos(sp)-YL*sin(sp));
					Matrix tem3 = (Gi.plus(ZL.times(u))).plus(XL.times(Ru * Math.cos(sp)));
					Matrix tem4 = YL.times(-Math.sin(sp) * Ru);
					Pside = tem3.plus(tem4);
					dPside = Math.sqrt((Pside.get(1, 0) * Pside.get(1, 0)) + (Pside.get(2, 0) * Pside.get(2, 0)));

					if (dPside < R) {
						this.contactPoint_firstsleeve[countCTPFirst][0] = Pside.get(0, 0);
						this.contactPoint_firstsleeve[countCTPFirst][1] = Pside.get(1, 0);
						this.contactPoint_firstsleeve[countCTPFirst][2] = Pside.get(2, 0);
						this.contactPoint_firstsleeve[countCTPFirst][3] = 2;
						countCTPFirst++;
					}

					double deta_Pside = Pside.get(0, 0) * Math.tan(beta) / R;
					double ph_Pside = Math.atan(Pside.get(2, 0) / Pside.get(1, 0));
					if (Math.cos(ph_Pside) * (Pside.get(1, 0) / dPside) < 0) {
						ph_Pside = PI + ph_Pside;
					}

					double Phi_Pside = -deta_Pside + ph_Pside;
					// update angple
					sp = sp + 0.005;
				}

			}

			// Semeng3: FIND THE CONTACT LINE OF THE END FACE.

			if ((u >= -thickness - step_u) && (u <= -thickness)) {
				min_sp = angle;
				max_sp = angle + PI;
				sp = min_sp;

				while (sp < max_sp) {
					Matrix tem3 = (Gi.plus(ZL.times(u))).plus(XL.times(Ru * Math.cos(sp)));
					Matrix tem4 = YL.times(-Math.sin(sp) * Ru);
					Pside = tem3.plus(tem4);
					dPside = Math.sqrt((Pside.get(1, 0) * Pside.get(1, 0)) + (Pside.get(2, 0) * Pside.get(2, 0)));

					if (dPside < R) {
						// contactpoint[countactCount][0] = Pside.get(0, 0);
						// contactpoint[countactCount][1] = Pside.get(1, 0);
						// contactpoint[countactCount][2] = Pside.get(2, 0);
						// contactpoint[countactCount][3] = 3;
						this.contactPointEndsleeve[countCTPEnd][0] = Pside.get(0, 0);
						this.contactPointEndsleeve[countCTPEnd][1] = Pside.get(1, 0);
						this.contactPointEndsleeve[countCTPEnd][2] = Pside.get(2, 0);
						this.contactPointEndsleeve[countCTPEnd][3] = 3;

						countCTPEnd++;
					}

					double deta_Pside = Pside.get(0, 0) * Math.tan(beta) / R;
					double ph_Pside = Math.atan(Pside.get(2, 0) / Pside.get(1, 0));
					if (Math.cos(ph_Pside) * (Pside.get(1, 0) / dPside) < 0) {
						ph_Pside = PI + ph_Pside;
					}

					double Phi_Pside = -deta_Pside + ph_Pside;

					// update angle
					sp = sp + 0.005;

				}

			}

		}

		// Summary the object
		countactCount = countCTPFirst + countCTPEnvelop + countCTPEnd;
		this.numPoint_firstSleev = countCTPFirst;
		this.numPoint_Envelope = countCTPEnvelop;
		this.numPoint_Endsleeve = countCTPEnd;
		this.totalContactPoints = countactCount;
		this.reArrangeCTLINE();
		int a = 0;
		int b = a;

		
		// Obtain the Profile 
		ObtainFlutingProfile( R,  beta);
		
	}
	
	/** Return the deepest point in X-direction of the contact Line. (X_max)
	 * 
	 * @return
	 */
	public double getXmax_inCTLine()
	{
		// Assign the deepest point is the first point in the contatct line.
		double x_MAX = Math.abs(fullContactLine[0][0]);
		
		for(int i = 0; i < totalContactPoints; i++)
		{
			if(x_MAX< Math.abs(fullContactLine[i][0]))
			{
				x_MAX = Math.abs(fullContactLine[i][0]);
			}
			
		}
		
		return x_MAX;		
	}
	
	/** Return the position of the endpoint (Angle in polar coordinate).
	 * 
	 * @return
	 */
	public double getPolarAngleofEndPoint()
	{
		double angle_of_Endoint =0;
		
		// X and Y position of the end point.
		double xe = fullProfile[totalContactPoints -1][0];
		double ye = fullProfile[totalContactPoints -1][1];
		
		double dRadius = Math.sqrt(xe*xe + ye*ye);
				
		
		// Determine the Angle according to the belonging quarter.
		if((xe > 0)&&(ye >= 0))// 1st quarter			
		{
			angle_of_Endoint= 2*PI- Math.asin(ye/dRadius);
		}
		else if((xe <= 0)&&(ye>  0))//2nd Quarter
		{
			angle_of_Endoint= PI/2- Math.asin(ye/dRadius);
		}
			
		else if((xe < 0)&&(ye <= 0))//3rd Quarter
		{
			angle_of_Endoint= PI/2- Math.asin(ye/dRadius);	
		}
			
		else if((xe >= 0)&&(ye < 0)) //4th Quarter
		{
			angle_of_Endoint= PI + Math.asin(ye/dRadius);
		}
					
		
		return angle_of_Endoint;
		
	}
	
	
	public double[][] getfullProfile()
	{
		return fullProfile;
	}
	
}
