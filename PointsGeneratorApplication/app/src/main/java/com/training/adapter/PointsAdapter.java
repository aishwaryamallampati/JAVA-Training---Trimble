package com.training.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.training.constants.Constants;
import com.training.functions.R;
import com.training.pointentity.Point;

import java.util.ArrayList;

/*
 *PointsAdapter is used to display the properties of point objects in the listView.
 */
public class PointsAdapter extends ArrayAdapter<Point> {

    public static final String TAG = "PtAdapter";
    private Context context;
    private int resource;
    private int lastPosition = -1;
    private ArrayList<Point> pointList;

    /*
     *To make ListView scrolling smooth
     */
    static class ViewHolder {
        TextView pointName;
        TextView x;
        TextView y;
    }

    public PointsAdapter(Context context, int resource, ArrayList<Point> pointList) {
        super(context, resource, pointList);
        this.context = context;
        this.resource = resource;
        this.pointList = pointList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        /*
         *create new view for showing the animation
         */
        final View animView;

        if (convertView == null) {
            Log.d(TAG, "view null");
            LayoutInflater inflator = LayoutInflater.from(this.context);
            convertView = inflator.inflate(this.resource, parent, false);

            holder = new ViewHolder();
            holder.pointName = (TextView) convertView.findViewById(R.id.pointName);
            holder.x = (TextView) convertView.findViewById(R.id.x);
            holder.y = (TextView) convertView.findViewById(R.id.y);

            animView = convertView;
            convertView.setTag(holder);
        } else {
            Log.d(TAG, "view is not null");
            holder = (ViewHolder) convertView.getTag();
            animView = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        animView.startAnimation(animation);

        lastPosition = position;

        /*
         * Get the data item for this position
         */
        Point point = pointList.get(position);
        /*
         *Populate the data into the template view using the data object
         */
        holder.pointName.setText(point.getName());
        holder.x.setText(Constants.LEFT_BRACE + String.valueOf(point.getX()) + Constants.COMMA);
        holder.y.setText(String.valueOf(point.getY()) + Constants.RIGHT_BRACE);

        /*
         *Return the completed view to render on screen
         */
        return convertView;
    }
}

