//Finding distance between two points given by the user.
import java.util.Scanner;

public class DistanceFinder {
	public static final double METERS_TO_FEET = 3.28084;
	public static final double METERS_TO_INCHES = 39.3701;

	public enum Metric{
		METER("m"),
		FEET("ft"),
		INCH("in");
		
		private String unit;
		
		Metric(String unit){
			this.unit = unit;
		}
		
		public String getUnit() {
			return unit;
		}	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean isValid = false;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter the coordinates of two points to find distance between them.");
		double x1 = getCoordinate("x1");
		double y1 = getCoordinate("y1");
		double x2 = getCoordinate("x2");
		double y2 = getCoordinate("y2");
		
		double distance = Math.sqrt(Math.pow(x2-x1, 2)+ Math.pow(y2-y1, 2));
	
		System.out.println("Enter the name of the output metric(m/ft/in):");
		String optionStr = input.next();
		optionStr = optionStr.toLowerCase();
		Metric option = Metric.METER;
		for(Metric t: Metric.values()){
			if(t.getUnit().equals(optionStr)){
				option = Metric.valueOf(t.name());	
				isValid = true;
			}
		}
		
		if(!(isValid)){
			System.out.println("Invalid Input. So the default output metric is meter.");
		}
			
		switch(option){
		case METER:
			System.out.println("distance = "+distance+option.getUnit());
			break;
		case FEET:
			distance = distance * METERS_TO_FEET;
			System.out.println("distance = "+distance+option.getUnit());
			break;
		case INCH:
			distance = distance * METERS_TO_INCHES;
			System.out.println("distance = "+distance+option.getUnit());
			break;
		}	
		input.close();
	}
	
	public static double getCoordinate(String coordinate){
		Scanner input = new Scanner(System.in);
		double value =0;
		boolean isDouble;
		
		do{
			System.out.println("Enter " + coordinate +":");
			if(input.hasNextDouble()){
				value = input.nextDouble();
				isDouble = true;
			}
			else{
				System.out.println("Invalid Input");
				isDouble = false;
				input.next();
			}
		}while(!(isDouble));
		
		return value;
	}
}
		