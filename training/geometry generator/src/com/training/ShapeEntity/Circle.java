package com.training.ShapeEntity;

public class Circle extends Shape {
	private double radius;

	public Circle(String type) {
		super(type);
		this.radius = super.getValueFromUser("Enter the radius of circle:");
		super.setAttribute();
	}

	@Override
	public double computePerimeter() {
		double perimeter = 2 * Math.PI * radius;
		return perimeter;
	}

	@Override
	public double computeArea() {
		double area = Math.PI * Math.pow(radius, 2);
		return area;
	}
}
