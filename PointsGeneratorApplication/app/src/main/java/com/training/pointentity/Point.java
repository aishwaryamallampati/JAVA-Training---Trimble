package com.training.pointentity;

public class Point {
    /*
     * The name of the point.
     */
    private String name;
    /*
     * The coordinates of the point object P(x,y).
     */
    private double x;
    private double y;

    public String getName() {
        return this.name;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    /*
     * constructor to create new point.
     */
    public Point(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
