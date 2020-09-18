package com.training.functions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.training.constants.Constants;
import com.training.interfaces.IApplicationActivity;
import com.training.pointentity.Point;

import java.util.ArrayList;

/*
 *The app performs two functions:
 * 1.Displays all points created on the left pane.
 * 2.Provides a menu on right pane to create new and relative points.
 */
public class ApplicationActivity extends AppCompatActivity implements IApplicationActivity {
    /**
     * ArrayList to store all point objects created.
     */
    private ArrayList<Point> pointList = new ArrayList<Point>();
    private PointListFragment pointListFragment;
    private Point selectedPoint;
    private static final String TAG = "Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        pointListFragment = new PointListFragment(pointList);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.ll_left, pointListFragment);
        fragmentTransaction.commit();

        moveToPointCreatorFragment();
    }

    @Override
    public void updatePointList() {
        pointListFragment.updatePointList();
    }

    @Override
    public void selectPoint() {
        pointListFragment.selectPoint();
    }

    @Override
    public void exportPoints() {
        ExportPointsAsyncTask exportPointsAsyncTask = new ExportPointsAsyncTask(this, pointList);
        exportPointsAsyncTask.execute();
    }

    @Override
    public void moveToPointCreatorFragment() {
        PointCreatorFragment pointCreatorFragment = new PointCreatorFragment(pointList);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ll_right);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ll_right, pointCreatorFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void moveToNewPointCreatorFragment() {

        NewPointCreatorFragment newPointCreatorFragment = new NewPointCreatorFragment(pointList);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ll_right);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ll_right, newPointCreatorFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void moveToAttributeSelectionFragment(Point selectedPoint) {
        this.selectedPoint = selectedPoint;
        AttributeSelectionFragment attributeSelectionFragment = new AttributeSelectionFragment();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ll_right);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ll_right, attributeSelectionFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void moveToRelPointCreatorFragment(Constants.Attribute pointAttrChosen) {

        RelPointCreatorFragment relPointCreatorFragment = new RelPointCreatorFragment(selectedPoint, pointAttrChosen, pointList);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ll_right);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ll_right, relPointCreatorFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}

