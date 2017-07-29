package GeometryPackage;

public class CMain_Test {


	public static void main(String[] args)
	{
		//=== Creat a blank with====//
		
		//teeths =2, Radius =6, Helix Angle=30; cutting length = 30;
		CBlank endMill = new CBlank(2, 6, 30, 30);
		
		//Creat a Fluting opertation
		CFluting flutingOperation = new CFluting(6, 0.6);
		
		// Creatint a Recess Operation
		CRecess recessOperation = new CRecess(0.8, 0.9, 20);
		
		// Creating a 1V1 Wheel		
		CWheel1V1 wheel1v1 = new CWheel1V1(52, 50, 51, 6, 1);
		
		
		/**=======MAIN CODE ==============**/
		// What to do here?
		
		// Calculate wheel location <I,G>
		// Calculate Contact line, 
		// Calculate flute profile
		// Calculte 2D shape
		// Calculate 
		
		
		
	}
	
	
}
