package com.training.functions;

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

import com.training.constants.Constants;
import com.training.interfaces.IApplicationActivity;

/*
 *Allows user to select a point from the list.
 */
public class AttributeSelectionFragment extends Fragment {
    private IApplicationActivity callback;
    private Button btn_AngDist, btn_XYDist, btn_Back;
    private static final String TAG = "AttrSelFrag";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attribute_selection, container, false);

        /*
         *Initializing member variables.
         */
        btn_AngDist = (Button) view.findViewById(R.id.btn_AngDist);
        btn_XYDist = (Button) view.findViewById(R.id.btn_XYDist);
        btn_Back = (Button) view.findViewById(R.id.btn_Back);

        buttonsFunctions();
        return view;
    }

    public void buttonsFunctions() {
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.moveToPointCreatorFragment();
            }
        });

        btn_AngDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.moveToRelPointCreatorFragment(Constants.Attribute.ANGLE_DISTANCE);
            }
        });

        btn_XYDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.moveToRelPointCreatorFragment(Constants.Attribute.XY_DISTANCE);
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