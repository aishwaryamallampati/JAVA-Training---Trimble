/*
 * This project performs new object creation of SQUARE/RECTANGLE/CIRCLE/LINE/POINT.
 */
package com.training.functions;

import java.util.ArrayList;
import java.util.Scanner;
import com.training.ShapeEntity.*;
import com.training.ObjectEntity.Objects;
import com.training.constants.Constants;

public class GeometryGenerator {
	/**
	 * ArrayList to store all objects created.
	 */
	static ArrayList<Objects> objectList = new ArrayList<Objects>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		boolean isValid = false;

		/*
		 * Taking user input on what type of object to be created.
		 */
		String optionStr;
		Constants.Choice option;
		do {
			String objectStr;
			isValid = false;
			do {
				System.out
						.println("Enter the object you want to create(SQUARE/RECTANGLE/CIRCLE/LINE/POINT):");
				objectStr = input.next();
				objectStr = objectStr.toUpperCase();

				for (Constants.Object s : Constants.Object.values()) {
					if (s.name().equals(objectStr)) {
						isValid = true;
						break;
					}
				}
				if (!(isValid)) {
					System.out.println("Invalid Input");
				}
			} while (!(isValid));
			Constants.Object object = Constants.Object.valueOf(objectStr);

			/*
			 * New object creation as per user request.
			 */
			switch (object) {
				case SQUARE:
					objectList.add(new Square(object.name()));
					break;
				case RECTANGLE:
					objectList.add(new Rectangle(object.name()));
					break;
				case CIRCLE:
					objectList.add(new Circle(object.name()));
					break;
				case LINE:
					objectList.add(new Line(object.name()));
					break;
				case POINT:
					PointOperations.createPoint(objectList);
					break;
			}

			/*
			 * the below block helps to use the program again.
			 */
			isValid = false;
			do {
				System.out.println("Do you want to continue(YES/NO):");
				optionStr = input.next();
				optionStr = optionStr.toUpperCase();
				for (Constants.Choice c : Constants.Choice.values()) {
					if (c.name().equals(optionStr)) {
						isValid = true;
						break;
					}
				}
				if (!(isValid)) {
					System.out.println("Invalid Input");
				}
			} while (!(isValid));
			option = Constants.Choice.valueOf(optionStr);
		} while (option.name() == Constants.Choice.YES.name());
		input.close();
	}
}
