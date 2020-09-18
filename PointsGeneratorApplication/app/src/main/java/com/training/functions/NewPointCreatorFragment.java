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
import android.widget.EditText;
import android.widget.Toast;

import com.training.interfaces.IApplicationActivity;
import com.training.pointentity.Point;

import java.util.ArrayList;

/*
Creates new point with the inputs given by the user.
 */
@SuppressLint("ValidFragment")
public class NewPointCreatorFragment extends Fragment {
    private ArrayList<Point> pointList;
    private IApplicationActivity callback;
    private EditText et_X, et_Y, et_Name;
    private Button btn_CreatePoint, btn_Back;
    private static final String TAG = "NewPtFrag";

    @SuppressLint("ValidFragment")
    public NewPointCreatorFragment(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_point_creator, container, false);

        /*
         * Initializing member variables.
         */
        et_X = (EditText) view.findViewById(R.id.et_X);
        et_Y = (EditText) view.findViewById(R.id.et_Y);
        et_Name = (EditText) view.findViewById(R.id.et_Name);
        btn_CreatePoint = (Button) view.findViewById(R.id.btn_CreatePoint);
        btn_Back = (Button) view.findViewById(R.id.btn_Back);

        buttonsFunction();
        return view;
    }

    public void buttonsFunction() {
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.moveToPointCreatorFragment();
            }
        });

        btn_CreatePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String xString = et_X.getText().toString().trim();
                String yString = et_Y.getText().toString().trim();
                String nameString = et_Name.getText().toString().trim();

                if (!xString.isEmpty() && !yString.isEmpty() && !nameString.isEmpty()) {
                    double x = Double.parseDouble(et_X.getText().toString());
                    double y = Double.parseDouble(et_Y.getText().toString());
                    String name = et_Name.getText().toString();

                    /*
                     *Rounding values to 3 decimal places.
                     */
                    x = (double) Math.round(x * 1000) / 1000;
                    y = (double) Math.round(y * 1000) / 1000;

                    /*
                     *Adding new point to the pointList and updating the listView.
                     */
                    pointList.add(new Point(x, y, name));

                    /*
                     *clearing all the text input fields.
                     */
                    et_X.getText().clear();
                    et_Y.getText().clear();
                    et_Name.getText().clear();

                    callback.updatePointList();
                    callback.moveToPointCreatorFragment();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter all inputs.", Toast.LENGTH_SHORT).show();
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
