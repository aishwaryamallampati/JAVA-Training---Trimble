package com.training.ShapeEntity;

import com.training.constants.Constants;
import com.training.ObjectEntity.Objects;
import com.training.interfaces.IAttribute;

import java.util.Scanner;

abstract class Shape extends Objects implements IAttribute {

	private Constants.Metric metric;

	public Shape(String type) {
		super(type);
		super.setName();
		this.metric = Constants.Metric.METER;
	}

	/*
	 * setOutputMetric method allows the user to set the output metric.
	 */
	private double setOutputMetric(double value) {
		Scanner input = new Scanner(System.in);

		String metricStr;
		boolean isValid = false;
		do {
			System.out.println("Enter the name of the output metric(m/ft/in):");
			metricStr = input.next();
			metricStr = metricStr.toLowerCase();

			for (Constants.Metric t : Constants.Metric.values()) {
				if (t.getUnit().equals(metricStr)) {
					this.metric = Constants.Metric.valueOf(t.name());
					isValid = true;
				}
			}
			if (!(isValid)) {
				System.out.println("Invalid Input");
			}
		} while (!(isValid));

		switch (this.metric) {
			case METER:
				break;
			case FEET:
				value = value * Constants.METERS_TO_FEET;
				break;
			case INCH:
				value = value * Constants.METERS_TO_INCHES;
				break;
		}
		return value;
	}

	/*
	 * setAttribute method prints the perimeter or area of the given shape as
	 * per user request.
	 */
	protected void setAttribute() {
		Scanner input = new Scanner(System.in);

		Constants.Attribute attribute = Constants.Attribute.PERIMETER;
		if (!(this.type.equals(Constants.Object.LINE.name()))) {
			String attributeStr;
			boolean isValid = false;
			do {
				System.out.println("Enter the attribute for the " + this.type
						+ "(PERIMETER/AREA):");
				attributeStr = input.next();
				attributeStr = attributeStr.toUpperCase();

				for (Constants.Attribute a : Constants.Attribute.values()) {
					if (a.name().equals(attributeStr)) {
						isValid = true;
					}
				}
				if (!(isValid)) {
					System.out.println("Invalid Input");
				}
			} while (!(isValid));
			attribute = Constants.Attribute.valueOf(attributeStr);
		}

		switch (attribute) {
			case PERIMETER:
				double perimeter = computePerimeter();
				perimeter = setOutputMetric(perimeter);
				System.out.println("The perimeter of the " + this.type + " is "
						+ perimeter + this.metric.getUnit());
				getNoOfPoints(perimeter);
				break;
			case AREA:
				double area = computeArea();
				System.out.println("The area of the " + this.type + " is " + area
						+ "" + this.metric.getUnit());
				break;
		}
	}

	/*
	 * getNoOfPoints method finds the no of points that can be plotted on the
	 * perimeter of the given shape.
	 */
	private void getNoOfPoints(double perimeter) {
		Scanner input = new Scanner(System.in);
		String optionStr;
		boolean isValid = false;
		boolean isDouble = false;
		do {
			System.out
					.println("Do you want to know the number of points that can be plotted on the perimeter(YES/NO):");
			optionStr = input.next();
			optionStr = optionStr.toUpperCase();

			for (Constants.Choice c : Constants.Choice.values()) {
				if (c.name().equals(optionStr)) {
					isValid = true;
				}
			}
			if (!(isValid)) {
				System.out.println("Invalid Input");
			}
		} while (!(isValid));
		Constants.Choice option = Constants.Choice.valueOf(optionStr);

		switch (option) {
			case YES:
				double distance = 0;
				do {
					System.out.println("Enter the distance between two points ("
							+ this.metric.getUnit() + ") :");
					if (input.hasNextDouble()) {
						distance = input.nextDouble();
						isDouble = true;
						if (distance <= 0) {
							System.out.println("Invalid Input");
							isDouble = false;
						}
					} else {
						System.out.println("Invalid Input");
						isDouble = false;
						input.next();
					}
				} while (!(isDouble));
				int noOfPoints = (int) (perimeter / distance);
				System.out
						.println("Number of points plotted along the perimeter of the "
								+ this.type + " is " + noOfPoints);
				break;
			case NO:
				break;
		}
	}

	public Constants.Metric getMetric() {
		return this.metric;
	}
}
