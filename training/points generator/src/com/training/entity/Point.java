package com.training.entity;

public class Point {
	private double x, y;
	private String name;

	public Point(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
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
