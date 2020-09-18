//Finding the perimeter /area of the given shape.
import java.util.Scanner;

public class ShapeAttributesFinder {
	public static final double METERS_TO_FEET = 3.28084;
	public static final double METERS_TO_INCHES = 39.3701;
	
	enum Shape{
		SQUARE,RECTANGLE,CIRCLE,LINE
	};
	
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
	};
	
	enum Attribute{
		PERIMETER,AREA
	};
	enum Choice{
		YES,NO
	};

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner input = new Scanner(System.in);
        boolean isValid = false; 
        boolean isDouble = false;
        
        String shapeStr;
        do{
        	System.out.println("Enter the shape(SQUARE/RECTANGLE/CIRCLE/LINE):");
            shapeStr = input.next();
            shapeStr = shapeStr.toUpperCase();
            
            for(Shape s: Shape.values()){
    			if(s.name().equals(shapeStr)){	
    				isValid = true;
    			}
    		}
            if(!(isValid)){
            	System.out.println("Invalid Input");
            }
        }while(!(isValid));
        Shape shape = Shape.valueOf(shapeStr);
       
        double resultPerimeter = 0,resultArea = 0;
        switch(shape){
        case SQUARE:
        	double side = getValue("Enter the length of side of square:");
            resultPerimeter = 4*side;
            resultArea = side*side;
            break;
        case RECTANGLE:
        	double length = getValue("Enter the length of rectangle:");
        	double breadth = getValue("Enter the breadth of rectangle:");
            resultPerimeter = 2*(length + breadth);
            resultArea = length * breadth;
            break;
        case CIRCLE:
        	double radius = getValue("Enter the radius of circle:");
            resultPerimeter = 2*Math.PI*radius;
            resultArea = Math.PI*Math.pow(radius,2);
            break;
        case LINE:
        	System.out.println("Enter the coordinates of end points of the line.");
    		double x1 = getValue("Enter x1:");
    		double y1 = getValue("Enter y1:");
    		double x2 = getValue("Enter x2:");
    		double y2 = getValue("Enter y2:");
            resultPerimeter = Math.sqrt(Math.pow(x2-x1, 2)+ Math.pow(y2-y1, 2));
            break;    
        }
        
        //Output the results
        String metricStr;
        Metric metric = Metric.METER;
        isValid = false;
        do{
        	System.out.println("Enter the name of the output metric(m/ft/in):");
            metricStr = input.next();
            metricStr = metricStr.toLowerCase();
            
            for(Metric t: Metric.values()){
    			if(t.getUnit().equals(metricStr)){
    				metric = Metric.valueOf(t.name());
    				isValid = true;
    			}
    		}
    		if(!(isValid)){
    			System.out.println("Invalid Input");
    		}
        }while(!(isValid));
        
        switch(metric){
        case METER:
            break;
        case FEET:
            resultPerimeter = resultPerimeter * METERS_TO_FEET;
            resultArea = resultArea * METERS_TO_FEET;
            break;
        case INCH:
            resultPerimeter = resultPerimeter * METERS_TO_INCHES;
            resultArea = resultArea * METERS_TO_INCHES;
            break;
        }
        
        Attribute attribute = Attribute.PERIMETER;
        if(!(shapeStr.equals("LINE"))){
        	String attributeStr;
            isValid = false;
            do{
            	System.out.println("Enter the attribute for the shape(PERIMETER/AREA):");
                attributeStr = input.next();
                attributeStr = attributeStr.toUpperCase();
                
                for(Attribute a: Attribute.values()){
        			if(a.name().equals(attributeStr)){	
        				isValid = true;
        			}
        		}
                if(!(isValid)){
                	System.out.println("Invalid Input");
                }
            }while(!(isValid));
            attribute = Attribute.valueOf(attributeStr);
        }
        
        switch(attribute){
        case PERIMETER:
            System.out.println("The perimeter of the " + shape + " is " + resultPerimeter + metric.getUnit());
            String optionStr;
            isValid = false;
            do{
            	System.out.println("Do you want to know the number of points that can be plotted on the perimeter(YES/NO):");
            	optionStr = input.next();
            	optionStr = optionStr.toUpperCase();
            	
            	for(Choice c: Choice.values()){
        			if(c.name().equals(optionStr)){	
        				isValid = true;
        			}
        		}
                if(!(isValid)){
                	System.out.println("Invalid Input");
                }
            }while(!(isValid));
            Choice option = Choice.valueOf(optionStr);
            
            switch(option){
            case YES:
            	double distance=0;
            	do{
            		System.out.println("Enter the distance between two points ("+ metric.getUnit() +") :");
                    if(input.hasNextDouble()){
                    	distance = input.nextDouble();
                    	isDouble = true;
                    	if(distance<=0){
                    		System.out.println("Invalid Input");
            				isDouble = false;
                    	}
        			}
        			else{
        				System.out.println("Invalid Input");
        				isDouble = false;
        				input.next();
        			}
            	}while(!(isDouble));
                int noOfPoints = (int)(resultPerimeter/distance);
                System.out.println("Number of points plotted along the perimeter of the " + shape +" is " + noOfPoints);
                break;
            case NO:
            	break;
            }
            break;
        case AREA:
            System.out.println("The area of the " + shape + " is " + resultArea + "" + metric.getUnit());
            break;
        }
        
        input.close();
    }
    
    public static double getValue(String text){
		Scanner input = new Scanner(System.in);
		double value =0;
		boolean isDouble;
		
		do{
			System.out.println(text);
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

