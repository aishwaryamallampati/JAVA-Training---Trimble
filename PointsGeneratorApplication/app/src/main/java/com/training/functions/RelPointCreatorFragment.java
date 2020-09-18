package com.training.functions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.training.constants.Constants;
import com.training.interfaces.IApplicationActivity;
import com.training.pointentity.Point;

import java.util.ArrayList;

/*
 *Creates relative points with the inputs given by the user.
 */
@SuppressLint("ValidFragment")
public class RelPointCreatorFragment extends Fragment {

    public static final String TAG = "RelPtFrag";
    private Point selectedPoint;
    private Constants.Attribute pointAttrChosen;
    private ArrayList<Point> pointList;
    private IApplicationActivity callback;
    private TextView tv_Input1, tv_Input2;
    private EditText et_Input1, et_Input2, et_Name;
    private Button btn_CreatePoint, btn_Back;
    private double x, y;

    @SuppressLint("ValidFragment")
    public RelPointCreatorFragment(Point selectedPoint, Constants.Attribute pointAttrChosen, ArrayList<Point> pointList) {
        this.selectedPoint = selectedPoint;
        this.pointAttrChosen = pointAttrChosen;
        this.pointList = pointList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rel_point_creator, container, false);

        /*
         *Initializing member variables.
         */
        tv_Input1 = (TextView) view.findViewById(R.id.tv_Input1);
        tv_Input2 = (TextView) view.findViewById(R.id.tv_Input2);
        et_Input1 = (EditText) view.findViewById(R.id.et_Input1);
        et_Input2 = (EditText) view.findViewById(R.id.et_Input2);
        et_Name = (EditText) view.findViewById(R.id.et_Name);
        btn_CreatePoint = (Button) view.findViewById(R.id.btn_CreatePoint);
        btn_Back = (Button) view.findViewById(R.id.btn_Back);

        switch (pointAttrChosen) {
            case ANGLE_DISTANCE:
                tv_Input1.setText("Angle:");
                tv_Input2.setText("Dist:");
                break;
            case XY_DISTANCE:
                tv_Input1.setText("X Dist:");
                tv_Input2.setText("Y Dist:");
                break;
        }

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
                String input1String = et_Input1.getText().toString().trim();
                String input2String = et_Input2.getText().toString().trim();
                String nameInputString = et_Name.getText().toString().trim();

                if (!input1String.isEmpty() && !input2String.isEmpty() && !nameInputString.isEmpty()) {
                    switch (pointAttrChosen) {
                        case ANGLE_DISTANCE:
                            double angle = Double.parseDouble(et_Input1.getText().toString());
                            double distance = Double.parseDouble(et_Input2.getText().toString());

                            distance = Math.abs(distance);
                            x = distance * (Math.cos(Math.toRadians(angle))) + selectedPoint.getX();
                            y = distance * (Math.sin(Math.toRadians(angle))) + selectedPoint.getY();

                            /*
                             *Rounding values to 3 decimal places.
                             */
                            x = (double) Math.round(x * 1000) / 1000;
                            y = (double) Math.round(y * 1000) / 1000;
                            break;
                        case XY_DISTANCE:
                            double xDistance = Double.parseDouble(et_Input1.getText().toString());
                            double yDistance = Double.parseDouble(et_Input2.getText().toString());

                            x = xDistance + selectedPoint.getX();
                            y = yDistance + selectedPoint.getY();

                            /*
                             *Rounding values to 3 decimal places.
                             */
                            x = (double) Math.round(x * 1000) / 1000;
                            y = (double) Math.round(y * 1000) / 1000;
                            break;
                    }
                    /*
                     *Adding new point to the pointList and updating the listView.
                     */
                    pointList.add(new Point(x, y, et_Name.getText().toString()));

                    /*
                     *clearing all the text input fields.
                     */
                    et_Input1.getText().clear();
                    et_Input2.getText().clear();
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
