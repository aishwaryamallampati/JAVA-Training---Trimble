package com.training.functions;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.training.constants.Constants;

/**
 * ExportChartAsyncTask creates a pdf document in the external storage and exports line chart to pdf.
 */
public class ExportChartAsyncTask extends AsyncTask<String, String, String> {
    private static final String TAG = "Export Chart";
    private Context context;
    //Line chart properties
    private Bitmap lineChartBitmap;
    private String xAxisTitle, yAxisTitle;
    //Data table properties
    private int noOfCols, noOfRows;
    private String[] colHeadings;
    private String[][] dataTable;
    private ProgressDialog progressDialog;

    ExportChartAsyncTask(Context context, Bitmap lineChartBitmap, String xAxisTitle, String yAxisTitle, int noOfCols, int noOfRows, String[] colHeadings, String[][] dataTable) {
        this.context = context;
        this.lineChartBitmap = lineChartBitmap;
        this.xAxisTitle = xAxisTitle;
        this.yAxisTitle = yAxisTitle;
        this.noOfCols = noOfCols;
        this.noOfRows = noOfRows;
        this.colHeadings = colHeadings;
        this.dataTable = dataTable;
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setTitle("Progress Dialog");
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "pre execute");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        PdfReportGenerator pdfReport = new PdfReportGenerator(context);
        pdfReport.createReport(lineChartBitmap, xAxisTitle, yAxisTitle, noOfCols, noOfRows, colHeadings, dataTable);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "chart exported.");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            Toast.makeText(context, "Chart saved to " + Constants.PDF_FILE_NAME, Toast.LENGTH_SHORT).show();
        }
    }
}
