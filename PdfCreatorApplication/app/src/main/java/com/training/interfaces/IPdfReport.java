package com.training.interfaces;

import android.graphics.Bitmap;

import java.io.IOException;

public interface IPdfReport {
    /**
     * Pdf document will be created.
     */
    void createPdfDocument(String dirName, String pdfName) throws IOException;

    /**
     * Adds a new page to document and moves cursors to that new page.
     */
    void moveToNextPage() throws IOException;

    /**
     * Each page in the pdf document will have the same page margin.
     */
    void setPageMargin(float pageMargin);

    /**
     * Once page header is set, each page in the pdf document will have the same header.
     */
    void setHeaderText(String pageHeader);

    /**
     * Once page footer is set, each page in the pdf document will have the same footer.
     */
    void setFooterText(String pageFooter);

    /**
     * addParagraph() adds text to the pdf. This method supports content break.
     * If the content does not fit in the current page, it is split into parts and written onto different pages.
     */
    void addParagraph(String text) throws IOException;

    /**
     * addParagraph() adds text to the pdf.
     * The entire text will be added to one page if noContentBreak boolean is set to true.
     * Content break is supported when noContent Break boolean is set to false.
     */
    void addParagraph(String text, boolean noContentBreak) throws IOException;

    /**
     * Adds input image to the pdf with center alignment.
     */
    void drawImage(Bitmap imageBitmap, float imageWidth, float imageHeight) throws IOException;

    /**
     * Draws a line chart in the pdf with a rectangle border.
     */
    void drawLineChart(Bitmap lineChartBitmap, String xAxisTitle, String yAxisTitle, float lineChartWidth, float lineChartHeight) throws IOException;

    /**
     * Draws table in the pdf with the data passed as input.
     */
    void drawDataTable(int noOfCols, int noOfRows, String[] colHeadings, String[][] dataTable) throws IOException;

    /**
     * Closes the pdf document.
     */
    void endDocument() throws IOException;
}
