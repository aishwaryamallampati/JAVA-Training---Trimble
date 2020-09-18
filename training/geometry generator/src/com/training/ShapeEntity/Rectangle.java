package com.training.ShapeEntity;

public class Rectangle extends Shape {
	private double length;
	private double breadth;

	public Rectangle(String type) {
		super(type);
		this.length = super.getValueFromUser("Enter the length of rectangle:");
		this.breadth = super.getValueFromUser("Enter the breadth of rectangle:");
		super.setAttribute();
	}

	@Override
	public double computePerimeter() {
		double perimeter = 2 * (length + breadth);
		return perimeter;
	}

	@Override
	public double computeArea() {
		double area = length * breadth;
		return area;
	}
}
