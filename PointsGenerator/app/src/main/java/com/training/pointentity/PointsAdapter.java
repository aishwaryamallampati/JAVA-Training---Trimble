package com.training.pointentity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.training.constants.Constants;
import com.training.functions.R;

import java.util.ArrayList;

/*
PointsAdapter is used to display the properties of point objects in the listView.
 */
public class PointsAdapter extends ArrayAdapter<Point> {

    private Context context;
    private int resource;

    public PointsAdapter(Context context, int resource, ArrayList<Point> pointList) {
        super(context, resource, pointList);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflator = LayoutInflater.from(this.context);
        convertView = inflator.inflate(this.resource, parent, false);

        /*
         *Lookup view for data population
         */
        TextView tvPointName = (TextView) convertView.findViewById(R.id.pointName);
        TextView tvX = (TextView) convertView.findViewById(R.id.x);
        TextView tvY = (TextView) convertView.findViewById(R.id.y);

        /*
         * Get the data item for this position
         */
        Point point = getItem(position);
        /*
         *Populate the data into the template view using the data object
         */
        tvPointName.setText(point.getName());
        tvX.setText(Constants.LEFT_BRACE + String.valueOf(point.getX()) + Constants.COMMA);
        tvY.setText(String.valueOf(point.getY()) + Constants.RIGHT_BRACE);

        /*
         *Return the completed view to render on screen
         */
        return convertView;
    }
}