package com.training.pointentity;

import java.io.Serializable;

public class Point implements Serializable {
    /*
     * The name of the point.
     */
    private String name;

    public String getName() {
        return this.name;
    }

    /*
     * The coordinates of the point object P(x,y).
     */
    private double x;
    private double y;

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
