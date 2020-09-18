package com.training.functions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.training.interfaces.IPointCreator;
import com.training.pointentity.Point;
import com.training.pointentity.PointsAdapter;

import java.util.ArrayList;

/*
Displays a menu to create new and relative points.
 */
public class PointCreator extends Fragment implements IPointCreator {
    private ArrayList<Point> pointList;

    public void setPointList(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    private PointsAdapter pointsAdapter;

    public void setPointsAdapter(PointsAdapter pointsAdapter) {
        this.pointsAdapter = pointsAdapter;
    }

    /*
     * class objects created to call methods of the class.
     */
    private NewPointCreator newPointCreator;

    public PointCreator() {
        newPointCreator = new NewPointCreator(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_creator, container, false);

        Button relativeButton = (Button) view.findViewById(R.id.relativeButton);
        relativeButton.setEnabled(false);
        
        Button newButton = (Button) view.findViewById(R.id.newButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPointCreator.setPointList(pointList);
                newPointCreator.setPointsAdapter(pointsAdapter);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, newPointCreator);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

}
