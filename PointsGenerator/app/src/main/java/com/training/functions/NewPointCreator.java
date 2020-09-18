package com.training.functions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.training.interfaces.IPointCreator;
import com.training.pointentity.Point;
import com.training.pointentity.PointsAdapter;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;
import static com.training.constants.Constants.*;

/*
Creates new point with the inputs given by the user.
 */
@SuppressLint("ValidFragment")
public class NewPointCreator extends Fragment {
    private ArrayList<Point> pointList;

    public void setPointList(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    private PointsAdapter pointsAdapter;

    public void setPointsAdapter(PointsAdapter pointsAdapter) {
        this.pointsAdapter = pointsAdapter;
    }

    /*
     * class objects created to call methods of the class. IPointCreator
     * interface is used to avoid cyclic dependency between classes.
     */
    private IPointCreator pointCreator;

    @SuppressLint("ValidFragment")
    public NewPointCreator(PointCreator pointCreator) {
        this.pointCreator = pointCreator;
    }

    private EditText xValue, yValue, pointName;
    private Button createPointButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_point_creator, container, false);

        xValue = (EditText) view.findViewById(R.id.xValue);
        yValue = (EditText) view.findViewById(R.id.yValue);
        pointName = (EditText) view.findViewById(R.id.name);
        createPointButton = (Button) view.findViewById(R.id.createPointButton);

        /*
        createPointButton is enabled only after user enters all the required inputs.
         */
        createPointButton.setEnabled(false);

        xValue.addTextChangedListener(createPointTextWatcher);
        yValue.addTextChangedListener(createPointTextWatcher);
        pointName.addTextChangedListener(createPointTextWatcher);

        createPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double x = Double.parseDouble(xValue.getText().toString());
                double y = Double.parseDouble(yValue.getText().toString());
                String name = pointName.getText().toString();

                /*
                Adding new point to the pointList and updating the listView.
                 */
                pointList.add(new Point(x, y, name));
                pointsAdapter.notifyDataSetChanged();

                pointCreator.setPointList(pointList);
                pointCreator.setPointsAdapter(pointsAdapter);

                /*
                clearing all the text input fields.
                 */
                xValue.getText().clear();
                yValue.getText().clear();
                pointName.getText().clear();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, (Fragment) pointCreator);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    /*
    createPointTextWatcher method checks whether user has given all the needed inputs.
     */
    private TextWatcher createPointTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String xString = xValue.getText().toString().trim();
            String yString = yValue.getText().toString().trim();
            String nameString = pointName.getText().toString().trim();
            createPointButton.setEnabled(!xString.isEmpty() && !yString.isEmpty() && !nameString.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}
