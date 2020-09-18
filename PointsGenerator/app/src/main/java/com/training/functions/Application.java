package com.training.functions;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.training.pointentity.Point;
import com.training.pointentity.PointsAdapter;

import java.util.ArrayList;

/*
 *The app performs two functions:
 * 1.Displays all points created on the left pane.
 * 2.Provides a menu on right pane to create new and relative points.
 */
public class Application extends AppCompatActivity {
    /*
     * class objects created to call methods of the class.
     */
    private PointCreator pointCreator = new PointCreator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        /**
         * ArrayList to store all point objects created.
         */
        ArrayList<Point> pointList = new ArrayList<Point>();

        /*
        Setting parameters for the list view to display list of points created.
         */
        ListView pointListView = (ListView) findViewById(R.id.pointListView);
        PointsAdapter pointsAdapter = new PointsAdapter(pointListView.getContext(), R.layout.fragment_point_listing, pointList);
        pointListView.setAdapter(pointsAdapter);

        pointCreator.setPointList(pointList);
        pointCreator.setPointsAdapter(pointsAdapter);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, pointCreator);
        fragmentTransaction.commit();
    }
}
