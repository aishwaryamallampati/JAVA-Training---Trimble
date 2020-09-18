package com.training.ShapeEntity;

public class Line extends Shape {
	private double x1, y1, x2, y2;

	public Line(String type) {
		super(type);
		System.out.println("Enter the coordinates of end points of the line.");
		x1 = super.getValueFromUser("Enter x1:");
		y1 = super.getValueFromUser("Enter y1:");
		x2 = super.getValueFromUser("Enter x2:");
		y2 = super.getValueFromUser("Enter y2:");
		super.setAttribute();
	}

	@Override
	public double computePerimeter() {
		double perimeter = Math.sqrt(Math.pow(x2 - x1, 2)
				+ Math.pow(y2 - y1, 2));
		return perimeter;
	}

	@Override
	public double computeArea() {
		return 0;
	}
}
