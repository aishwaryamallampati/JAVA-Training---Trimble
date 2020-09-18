package com.training.functions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import com.training.constants.Constants;
import com.training.interfaces.IPdfReport;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * PdfReportGenerator class creates a pdf and saves it in external storage.
 */
public class PdfReportGenerator {

    private IPdfReport pdfReport;
    private Context context;

    public PdfReportGenerator(Context context) {
        this.context = context;
        pdfReport = new BoxablePdfReport(context);
    }

    public void createReport(Bitmap lineChartBitmap, String xAxisTitle, String yAxisTitle, int noOfCols, int noOfRows, String[] colHeadings, String[][] dataTable) {
        //Get current date time with Date()
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        Date date = new Date();
        String strDate = dateFormat.format(date);
        Bitmap imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + Constants.DIR_NAME + "/qml_app_logo.png");
        String text = "Dimensions in the data are often displayed on axes. " +
                "If a horizontal and a vertical axis are used, they are usually referred to as the x-axis and y-axis respectively." +
                "Each axis will have a scale, denoted by periodic graduations and usually accompanied by numerical or categorical indications. " + "Each axis will typically also have a label displayed outside or beside it, briefly describing the dimension represented. If the scale is numerical, the label will often be suffixed with the unit of that scale in parentheses. " +
                "For example, Distance traveled (m) is a typical x-axis label and would mean that the distance traveled, in units of meters, is related to the horizontal position of the data within the chart.";
        try {
            //Creating a pdf document and adding content to it.
            pdfReport.createPdfDocument(Constants.DIR_NAME, Constants.PDF_FILE_NAME);
            pdfReport.setPageMargin(10);
            pdfReport.setHeaderText("Report");
            pdfReport.drawLineChart(lineChartBitmap, xAxisTitle, yAxisTitle, 400, 200);
            pdfReport.drawDataTable(noOfCols, noOfRows, colHeadings, dataTable);
            pdfReport.drawImage(imageBitmap, 400, 300);
            pdfReport.addParagraph("content break" + text, false);
            pdfReport.moveToNextPage();
            pdfReport.drawLineChart(lineChartBitmap, xAxisTitle, yAxisTitle, 400, 200);
            pdfReport.drawDataTable(noOfCols, noOfRows, colHeadings, dataTable);
            pdfReport.drawImage(imageBitmap, 400, 300);
            pdfReport.addParagraph("content break" + text, true);
            pdfReport.setFooterText(strDate);
            pdfReport.endDocument();
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
