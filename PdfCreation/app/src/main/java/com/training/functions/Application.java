package com.training.functions;

import android.graphics.Color;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * The application performs two functions:
 * 1.Displays line chart.
 * 2.On clicking save button, exports line chart to pdf.
 */
public class Application extends AppCompatActivity {
    private LineChart lineChart;
    private TextView tv_x_axis_report_date, tv_wallLength;
    //data Table properties
    private int noOfCols, noOfRows;
    private String[] colHeadings;
    private String[][] dataTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application);

        lineChart = findViewById(R.id.lineChart);
        Button btn_saveChart = findViewById(R.id.btn_saveChart);
        tv_x_axis_report_date = findViewById(R.id.tv_x_axis_report_date);
        tv_wallLength = findViewById(R.id.tv_wallLength);

        int dataSetSize = setLineChartData();
        setBasicLineChartProperties(dataSetSize);
        lineChart.invalidate();

        btn_saveChart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String xAxisTitle = tv_x_axis_report_date.getText().toString();
                String yAxisTitle = tv_wallLength.getText().toString();
                configureDataTable();
                ExportChartAsyncTask exportChartAsyncTask = new ExportChartAsyncTask(Application.this, lineChart.getChartBitmap(), xAxisTitle, yAxisTitle, noOfCols, noOfRows, colHeadings, dataTable);
                exportChartAsyncTask.execute();
            }
        });
    }

    private int setLineChartData() {
        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0, 60f));
        yValues.add(new Entry(1, 70f));
        yValues.add(new Entry(2, 20f));
        yValues.add(new Entry(3, 10f));
        yValues.add(new Entry(4, 68f));
        yValues.add(new Entry(5, 63f));
        yValues.add(new Entry(6, 60f));
        yValues.add(new Entry(7, 70f));
        yValues.add(new Entry(8, 20f));
        yValues.add(new Entry(9, 10f));
        yValues.add(new Entry(10, 68f));
        yValues.add(new Entry(11, 63f));

        LineDataSet set = new LineDataSet(yValues, "Data Set");
        set.setFillAlpha(110);
        set.setColor(Color.RED);
        set.setLineWidth(3f);
        set.setValueTextColor(Color.GREEN);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        return yValues.size();
    }

    private void setBasicLineChartProperties(int dataSetSize) {
        //X-Axis properties
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) (dataSetSize - 1));
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);

        //Y-Axis properties
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setGranularityEnabled(true);
        yAxisLeft.setGranularity(1f);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        // Disable legend
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        // Chart properties
        lineChart.setDescription(null);
        lineChart.setPinchZoom(false);
        lineChart.setScaleEnabled(false);
        lineChart.setHighlightPerTapEnabled(false);
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.setClickable(false);

        //Refer https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport
        // Restricting the max display items in x axis to MAX_CHARTS_ITEMS_IN_VIEW
        // By setting this parameter the x-axis scrolling is automatically enabled
        lineChart.setVisibleXRangeMinimum(3f);
        lineChart.setVisibleXRangeMaximum(3f);
        lineChart.moveViewToX((float) (dataSetSize - 1));
    }

    private void configureDataTable() {
        noOfCols = 2;

        colHeadings = new String[noOfCols];
        colHeadings[0] = "X";
        colHeadings[1] = "Y";

        List<ILineDataSet> dataSets = lineChart.getLineData().getDataSets();
        ILineDataSet dataSet = dataSets.get(0);
        int lowestVisibleX = (int) Math.ceil(lineChart.getLowestVisibleX());
        int highestVisibleX = (int) Math.floor(lineChart.getHighestVisibleX());

        noOfRows = highestVisibleX - lowestVisibleX + 1;

        dataTable = new String[noOfRows][noOfCols];

        int j = 0;
        for (int i = lowestVisibleX; i <= highestVisibleX; i++) {
            Entry entry = dataSet.getEntryForIndex(i);
            dataTable[j][0] = Float.toString(i);
            dataTable[j][1] = Float.toString(entry.getY());
            j = j + 1;
        }
    }
}
