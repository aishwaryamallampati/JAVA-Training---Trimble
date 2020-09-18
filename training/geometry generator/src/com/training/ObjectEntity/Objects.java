package com.training.ObjectEntity;

import java.util.Scanner;

public class Objects {
	/*
	 * Member variable type tells us whether the object is a
	 * SQUARE/RECTANGLE/CIRCLE/LINE/POINT.
	 */
	protected String type;
	protected String name;

	/*
	 * The coordinates of the point object P(x,y).
	 */
	protected double x;
	protected double y;

	public Objects(String type) {
		this.type = type;
	}

	/*
	 * setName method sets the user input as name of the object.
	 */
	protected void setName() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the name of the " + this.type + ":");
		this.name = input.nextLine();
	}

	/*
	 * setValue method checks the user input and returns it.
	 */
	protected double getValueFromUser(String text) {
		Scanner input = new Scanner(System.in);
		double value = 0;
		boolean isDouble = false;

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

	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

}
