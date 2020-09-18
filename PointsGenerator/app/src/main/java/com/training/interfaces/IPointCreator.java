package com.training.interfaces;

import com.training.pointentity.Point;
import com.training.pointentity.PointsAdapter;

import java.util.ArrayList;

/*
 * IPointCreator interface is created to solve cyclic redundancy between PointCreator and NewPointCreator classes.
 */
public interface IPointCreator {
    void setPointList(ArrayList<Point> pointList);

    void setPointsAdapter(PointsAdapter pointsAdapter);
}
