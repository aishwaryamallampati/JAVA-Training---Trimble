//Assignment1.1:Finding the maximum two numbers in a matrix given by the user.
import java.util.Scanner;

public class TwoMaxFinder {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		
		//Taking input from user
		int noOfRows=0,noOfColumns=0;
		boolean isInt = false;
		
		do{
			System.out.println("Enter number of rows of matrix:");
			if(input.hasNextInt()){
				noOfRows = input.nextInt();
				isInt = true;
				if(noOfRows < 1){
					isInt = false;
					System.out.println("Invalid Input");
				}
			}
			else{
				System.out.println("Invalid Input");
				isInt = false;
				input.next();
			}
		}while(!(isInt));
		
		do{
			System.out.println("Enter number of columns of matrix:");
			if(input.hasNextInt()){
				noOfColumns = input.nextInt();
				isInt = true;
				if(noOfColumns < 1){
					isInt = false;
					System.out.println("Invalid Input");
				}
			}
			else{
				System.out.println("Invalid Input");
				isInt = false;
				input.next();
			}
		}while(!(isInt));
		
		double matrix[][] = new double[noOfRows][noOfColumns];
		boolean isDouble =false;
		for(int i = 0; i < noOfRows; i++){
			for(int j=0; j<noOfColumns;j++){
				do{
					System.out.println("Enter the [" + (i+1) +"," + (j+1) +"] element of the matrix:");
					if(input.hasNextDouble()){
						matrix[i][j] = input.nextDouble();
						isDouble = true;
					}
					else{
						System.out.println("Invalid Input");
						isDouble = false;
						input.next();
					}	
				}while(!(isDouble));
			}
		}
		
		//Finding two maximum numbers in the given matrix1
		double firstMax = matrix[0][0], secondMax = matrix[0][0];
		for(int i = 0; i < noOfRows; i++){
			for(int j=0; j<noOfColumns;j++){
				if(matrix[i][j] > firstMax){
					secondMax = firstMax;
					firstMax = matrix[i][j];
				}
				else if(matrix[i][j] > secondMax){
					secondMax = matrix[i][j];
				}
			}
		}
		System.out.println("OUTPUT:");
		System.out.println("first Max = "+firstMax);
		System.out.println("second Max = "+secondMax);
		input.close();
	}
}
