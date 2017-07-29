package Jama;

public class QickTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double[][] ap1 = new double[][] {{1},{2},{3}};
		double[][] ap2 = new double[][] {{1},{4},{4}};
		double[][] ap3 = new double[][] {{3},{2},{1}};
		
		Matrix p1 = new Matrix(ap1);
		Matrix p2 = new Matrix(ap2);
		Matrix p3 = new Matrix(ap3); 
		
		Matrix normal = Matrix.getNormalof3Points(p1, p2, p3);
		
		System.out.println("normal = " + normal.get(0, 0)+ " ," + normal.get(1, 0)+ " ," + normal.get(2, 0));
		
	}

}
