package com.training.functions;

import java.util.ArrayList;
import java.util.Scanner;
import com.training.ObjectEntity.Objects;
import com.training.PointEntity.Point;
import com.training.constants.Constants;

/*
 * PointOperations class takes user input to decide 
 * whether to create a new or a relative point.
 */
public class PointOperations {
	/*
	 * createPoint method determines whether the point to be created is of type
	 * new or relative to existing point.
	 */
	public static void createPoint(ArrayList<Objects> objectList) {
		Scanner input = new Scanner(System.in);
		boolean isValid = false;
		String creationStr;
		do {
			System.out
					.println("Enter the type of point creation(NEW/RELATIVE):");
			creationStr = input.next();
			creationStr = creationStr.toUpperCase();
			for (Constants.Creation c : Constants.Creation.values()) {
				if (c.name().equals(creationStr)) {
					isValid = true;
					break;
				}
			}
			if (!(isValid)) {
				System.out.println("Invalid Input");
			}
		} while (!(isValid));
		Constants.Creation creation = Constants.Creation.valueOf(creationStr);

		switch (creation) {
			case NEW:
				objectList.add(new Point(Constants.Object.POINT.name()));
				break;
			case RELATIVE:
				displayPoints(objectList);
				Objects obj = findPoint(objectList);
				objectList.add(new Point(Constants.Object.POINT.name(), obj.getX(),
						obj.getY()));
				break;
		}
	}

	/*
	 * findPoint method finds the existing point object which the user wants to
	 * use to create new point.
	 */
	private static Objects findPoint(ArrayList<Objects> objectList) {
		Scanner input = new Scanner(System.in);
		String name;
		boolean isValid = false;
		Objects obj = objectList.get(0);

		do {
			System.out
					.println("Enter the name of the existing point you want to use from the above list:");
			name = input.nextLine();
			for (int i = 0; i < objectList.size(); i++) {
				obj = objectList.get(i);
				/*
				 * checks only objects of type POINT.
				 */
				if (obj.getType().equals(Constants.Object.POINT.name())) {
					if (name.equals(obj.getName())) {
						isValid = true;
						break;
					}
				}
			}
			if (!(isValid)) {
				System.out.println("Invalid Point.");
			}
		} while (!(isValid));
		return obj;
	}

	/*
	 * displayPoints method prints all the existing points created.
	 */
	private static void displayPoints(ArrayList<Objects> objectList) {
		System.out.println("All points created:");
		for (int i = 0; i < objectList.size(); i++) {
			Objects obj = objectList.get(i);
			if (obj.getType().equals(Constants.Object.POINT.name())) {
				System.out.println(obj.getName() + "(" + obj.getX() + ","
						+ obj.getY() + ")");
			}
		}
		return;
	}
}
