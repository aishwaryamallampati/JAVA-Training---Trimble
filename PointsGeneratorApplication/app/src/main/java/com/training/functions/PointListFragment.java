package com.training.functions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.training.adapter.PointsAdapter;
import com.training.interfaces.IApplicationActivity;
import com.training.pointentity.Point;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class PointListFragment extends Fragment {

    public static final String TAG = "PtListFrag";
    private ArrayList<Point> pointList;
    private ListView lv_PointList;
    private PointsAdapter pointsAdapter;
    private Button btn_UsePoint;
    private TextView tv_Heading;
    private IApplicationActivity callback;

    @SuppressLint("ValidFragment")
    public PointListFragment(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_list, container, false);
        setHasOptionsMenu(true);

        lv_PointList = (ListView) view.findViewById(R.id.lv_PointList);
        pointsAdapter = new PointsAdapter(lv_PointList.getContext(), R.layout.pointlist_row_layout, pointList);
        lv_PointList.setAdapter(pointsAdapter);

        tv_Heading = (TextView) view.findViewById(R.id.tv_Heading);
        btn_UsePoint = (Button) view.findViewById(R.id.btn_UsePoint);
        btn_UsePoint.setVisibility(View.INVISIBLE);

        return view;
    }

    public void updatePointList() {
        pointsAdapter.notifyDataSetChanged();
    }

    public void selectPoint() {
        tv_Heading.setText("Select Point");

        lv_PointList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                view.setSelected(true);
                final Point selectedPoint = pointList.get(position);
                tv_Heading.setText(selectedPoint.getName() + "(" + selectedPoint.getX() + "," + selectedPoint.getY() + ") is selected.");

                btn_UsePoint.setVisibility(View.VISIBLE);
                btn_UsePoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_Heading.setText("Point List");
                        btn_UsePoint.setVisibility(View.INVISIBLE);
                        callback.moveToAttributeSelectionFragment(selectedPoint);
                    }
                });
            }

        });
    }

    public boolean OnCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         *Handle action bar item clicks here.
         */
        int id = item.getItemId();
        /*
         *noinspection SimplifiableIfStatement
         */
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (IApplicationActivity) context;
            Log.d(TAG, "onAttach() called");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
        Log.d(TAG, "onAttach() called");
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
