package com.training.PointEntity;

import java.util.Scanner;

import com.training.constants.Constants;
import com.training.ObjectEntity.Objects;

public class Point extends Objects {
	/**
	 * noNamePoints variable keeps track of points to which user has not given
	 * any name.
	 */
	static int noNamePoints = 0;

	/*
	 * constructor to create new point.
	 */
	public Point(String type) {
		super(type);
		createNewPoint();
	}

	/*
	 * constructor to create relative point.
	 */
	public Point(String type, double x, double y) {
		super(type);
		createRelativePoint(x, y);
	}

	/*
	 * Creates new point.
	 */
	private void createNewPoint() {
		Scanner input = new Scanner(System.in);
		boolean isDouble = false;
		System.out.println("Enter the input for the point(name x y/x y):");
		if (input.hasNextDouble()) {
			super.x = input.nextDouble();
			isDouble = false;
			do {
				if (input.hasNextDouble()) {
					super.y = input.nextDouble();
				} else {
					System.out.println("Invalid Input.");
					System.out.println("Enter valid value for y coordinate:");
					isDouble = false;
					input.next();
				}
			} while (!(isDouble));
			noNamePoints = noNamePoints + 1;
			super.name = Constants.POINT_PREFIX
					+ Integer.toString((noNamePoints));
		} else {
			super.name = input.next();
			isDouble = false;
			do {
				if (input.hasNextDouble()) {
					super.y = input.nextDouble();
				} else {
					System.out.println("Invalid Input.");
					System.out.println("Enter valid value for x coordinate:");
					isDouble = false;
					input.next();
				}
			} while (!(isDouble));
			isDouble = false;
			do {
				if (input.hasNextDouble()) {
					super.y = input.nextDouble();
				} else {
					System.out.println("Invalid Input.");
					System.out.println("Enter valid value for y coordinate:");
					isDouble = false;
					input.next();
				}
			} while (!(isDouble));
		}
		System.out.println("The new point created is " + super.name + "("
				+ super.x + "," + super.y + ")");
	}

	/*
	 * Creates a new point relative to the existing point.
	 */
	private void createRelativePoint(double x, double y) {
		Scanner input = new Scanner(System.in);
		boolean isValid = false;

		String attributeStr;
		Constants.PointAttribute attribute = Constants.PointAttribute.ANGLE_DISTANCE;
		do {
			System.out.println("Angle and Distance(AD)");
			System.out.println("X Distance and Y Distance(DD)");
			System.out
					.println("Enter the attribute from the above options(AD/DD):");
			attributeStr = input.next();
			attributeStr = attributeStr.toUpperCase();

			for (Constants.PointAttribute a : Constants.PointAttribute.values()) {
				if (a.getSymbol().equals(attributeStr)) {
					isValid = true;
					attribute = Constants.PointAttribute.valueOf(a.name());
					break;
				}
			}
			if (!(isValid)) {
				System.out.println("Invalid Input");
			}
		} while (!(isValid));

		switch (attribute) {
			case ANGLE_DISTANCE:
				double angle = super
						.getValueFromUser("Enter the angle(in degrees):");
				double distance = super
						.getValueFromUser("Enter the distance from the point:");
				distance = Math.abs(distance);
				super.x = distance * (Math.cos(Math.toRadians(angle))) + x;
				super.y = distance * (Math.sin(Math.toRadians(angle))) + y;
				break;
			case XY_DISTANCE:
				double xDistance = super
						.getValueFromUser("Enter the x distance from the point:");
				double yDistance = super
						.getValueFromUser("Enter the y distance from the point:");
				super.x = xDistance + x;
				super.y = yDistance + y;
				break;
		}

		System.out.println("Enter the name of the new point:");
		input.nextLine();
		super.name = input.nextLine();

		System.out.println("The new point created is " + super.name + "("
				+ super.x + "," + super.y + ")");
		return;
	}
}
