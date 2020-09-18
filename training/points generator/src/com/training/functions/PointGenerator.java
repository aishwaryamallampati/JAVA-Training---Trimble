package com.training.functions;

/**
 * A menu is created with following functionalities:
 * (i)creating new point
 * (ii) creating a new point relative to the existing point.
 */
import java.util.ArrayList;
import java.util.Scanner;

import com.training.entity.Point;

public class PointGenerator {

	/**
	 * ArrayList to store all point objects created.
	 */
	static ArrayList<Point> pointList = new ArrayList<Point>();

	/**
	 * noNamePoints variable keeps track of points to which user has not given
	 * any name.
	 */
	static int noNamePoints = 0;

	enum Creation {
		NEW, RELATIVE
	};

	enum Choice {
		YES, NO
	};

	public enum Attribute {
		ANGLE_DISTANCE("AD"), XY_DISTANCE("DD");

		private String symbol;

		Attribute(String symbol) {
			this.symbol = symbol;
		}

		public String getSymbol() {
			return symbol;
		}
	};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		boolean isValid = false;

		String optionStr;
		Choice option;
		do {
			String creationStr;
			isValid = false;
			do {
				System.out
						.println("Enter the type of point creation(NEW/RELATIVE):");
				creationStr = input.next();
				creationStr = creationStr.toUpperCase();
				for (Creation c : Creation.values()) {
					if (c.name().equals(creationStr)) {
						isValid = true;
						break;
					}
				}
				if (!(isValid)) {
					System.out.println("Invalid Input");
				}
			} while (!(isValid));
			Creation creation = Creation.valueOf(creationStr);

			switch (creation) {
				case NEW:
					createPoint();
					break;
				case RELATIVE:
					display();
					findPoint();
					break;
			}

			isValid = false;
			do {
				System.out.println("Do you want to continue(YES/NO):");
				optionStr = input.next();
				optionStr = optionStr.toUpperCase();
				for (Choice c : Choice.values()) {
					if (c.name().equals(optionStr)) {
						isValid = true;
						break;
					}
				}
				if (!(isValid)) {
					System.out.println("Invalid Input");
				}
			} while (!(isValid));
			option = Choice.valueOf(optionStr);
		} while (option.name() == Choice.YES.name());
		input.close();
	}

	static void createPoint() {
		Scanner input = new Scanner(System.in);

		System.out.println("Enter the input(name x y/x y):");
		if (input.hasNextDouble()) {
			double x = input.nextDouble();
			double y = input.nextDouble();
			createPoint(x, y);
		} else {
			String name = input.next();
			double x = input.nextDouble();
			double y = input.nextDouble();
			createPoint(name, x, y);
		}
	}

	static void createPoint(double x, double y) {
		Scanner input = new Scanner(System.in);
		noNamePoints = noNamePoints + 1;
		String name = "P" + Integer.toString((noNamePoints));
		pointList.add(new Point(name, x, y));
		System.out.println("The new point created is " + name + "(" + x + ","
				+ y + ")");
		return;
	}

	static void createPoint(String name, double x, double y) {
		Scanner input = new Scanner(System.in);
		pointList.add(new Point(name, x, y));
		System.out.println("The new point created is " + name + "(" + x + ","
				+ y + ")");
		return;
	}

	static void findPoint() {
		Scanner input = new Scanner(System.in);
		String name;
		boolean isValid = false;

		do {
			System.out
					.println("Enter the name of the existing point you want to use from the above list:");
			name = input.nextLine();
			for (int i = 0; i < pointList.size(); i++) {
				Point obj = pointList.get(i);
				if (name.equals(obj.getName())) {
					isValid = true;
					createRelativePoint(obj);
				}
			}
			if (!(isValid)) {
				System.out.println("Invalid Point.");
			}
		} while (!(isValid));
	}

	static void createRelativePoint(Point obj) {
		Scanner input = new Scanner(System.in);
		boolean isValid = false;

		String attributeStr;
		Attribute attribute = Attribute.ANGLE_DISTANCE;
		do {
			System.out.println("Angle and Distance(AD)");
			System.out.println("X Distance and Y Distance(DD)");
			System.out
					.println("Enter the attribute from the above options(AD/DD):");
			attributeStr = input.next();
			attributeStr = attributeStr.toUpperCase();

			for (Attribute a : Attribute.values()) {
				if (a.getSymbol().equals(attributeStr)) {
					isValid = true;
					attribute = Attribute.valueOf(a.name());
					break;
				}
			}
			if (!(isValid)) {
				System.out.println("Invalid Input");
			}
		} while (!(isValid));

		double newX = 0, newY = 0;
		switch (attribute) {
			case ANGLE_DISTANCE:
				double angle = getValue("Enter the angle(in degrees):");
				double distance = getValue("Enter the distance from the point:");
				distance = Math.abs(distance);
				newX = distance * (Math.cos(Math.toRadians(angle))) + obj.getX();
				newY = distance * (Math.sin(Math.toRadians(angle))) + obj.getY();
				break;
			case XY_DISTANCE:
				double xDistance = getValue("Enter the x distance from the point:");
				double yDistance = getValue("Enter the y distance from the point:");
				newX = xDistance + obj.getX();
				newY = yDistance + obj.getY();
				break;
		}

		System.out.println("Enter the name of the new point:");
		input.nextLine();
		String name = input.nextLine();
		createPoint(name, newX, newY);
		return;
	}

	static void display() {
		System.out.println("All points created:");
		for (int i = 0; i < pointList.size(); i++) {
			Point obj = pointList.get(i);
			System.out.println(obj.getName() + "(" + obj.getX() + ","
					+ obj.getY() + ")");
		}
		return;
	}

	static double getValue(String text) {
		Scanner input = new Scanner(System.in);
		double value = 0;
		boolean isDouble;

		do {
			System.out.println(text);
			if (input.hasNextDouble()) {
				value = input.nextDouble();
				isDouble = true;
			} else {
				System.out.println("Invalid Input");
				isDouble = false;
				input.next();
			}
		} while (!(isDouble));
		return value;
	}
}
