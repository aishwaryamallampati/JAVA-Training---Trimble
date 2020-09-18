package com.training.ShapeEntity;

public class Square extends Shape {
	private double side;

	public Square(String type) {
		super(type);
		this.side = super.getValueFromUser("Enter the length of side of square:");
		super.setAttribute();
	}

	@Override
	public double computePerimeter() {
		double perimeter = 4 * side;
		return perimeter;
	}

	@Override
	public double computeArea() {
		double area = side * side;
		return area;
	}
}
