package com.training.functions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.training.interfaces.IApplicationActivity;
import com.training.pointentity.Point;

import java.util.ArrayList;

/*
Displays a menu to create new and relative points.
 */
@SuppressLint("ValidFragment")
public class PointCreatorFragment extends Fragment {
    public static final String TAG = "PtCreatorFrag";
    private ArrayList<Point> pointList;
    private IApplicationActivity callback;
    Button btn_New, btn_Relative, btn_Export_Points;

    @SuppressLint("ValidFragment")
    public PointCreatorFragment(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_creator, container, false);

        /*
         * Initializing member variables.
         */
        btn_New = (Button) view.findViewById(R.id.btn_New);
        btn_Relative = (Button) view.findViewById(R.id.btn_Relative);
        btn_Export_Points = (Button) view.findViewById(R.id.btn_Export_Points);

        buttonsFunction();

        return view;
    }

    public void buttonsFunction() {
        btn_New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.moveToNewPointCreatorFragment();
            }
        });

        btn_Relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pointList.size() > 0) {
                    callback.selectPoint();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Create new points first.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Export_Points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pointList.size() > 0) {
                    callback.exportPoints();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Create new points first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called");
        try {
            callback = (IApplicationActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
        Log.d(TAG, "onDetach() called");
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
