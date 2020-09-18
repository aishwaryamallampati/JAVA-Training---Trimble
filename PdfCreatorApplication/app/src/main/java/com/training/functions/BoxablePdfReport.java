package com.training.functions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.widget.Toast;

import com.training.constants.Constants;
import com.training.interfaces.IPdfReport;
import com.wopata.boxable.ColoredColumnTable;
import com.wopata.boxable.Row;
import com.wopata.boxable.columns.ColoredColumn;
import com.wopata.boxable.utils.ColorUtils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * BoxablePdfReport  class can perform the following functions:
 * 1.Adds border, header and footer to every page of the pdf document.
 * 2.Text can be added to the pdf. The methods support both content break and no content break options.
 * 3.Line chart can be added to the document.
 * 4.A table can be created with the given data and added to pdf.
 * 5.Can return the current page of the pdf document.
 * 6.A new page can be created and content can be added to it.
 * Note: If a pdf is created using this class, then all the content of the pdf must be added using the methods of this class only.
 */
public class BoxablePdfReport implements IPdfReport {
    private File pdfFile;
    private PDDocument pdDocument;
    private PDPage page;
    private PDPageContentStream contentStream;
    private float xPageStart, yPageStart, xCursor, yCursor, currPageWidth, pageMargin;
    private String pageHeader, pageFooter;
    private Context context;

    public BoxablePdfReport(Context context) {
        this.context = context;
    }

    /**
     * Pdf document will be created.
     */
    @Override
    public void createPdfDocument(String dirName, String pdfName) throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + dirName);
            if (!Dir.exists()) {
                Dir.mkdir();
            }
            //Creating pdf file in the external storage.
            pdfFile = new File(Dir, pdfName);
        } else {
            Toast.makeText(context, "SD card not found.", Toast.LENGTH_SHORT).show();
        }
        //Creating pdf document.
        pdDocument = new PDDocument();

        try {
            //Adding first page to the pdf document.
            addNewPage();
        } catch (IOException e) {
            throw new IOException("Pdf document cannot be added to pdf. Pdf export failed.");
        }

        currPageWidth = page.getMediaBox().getWidth();
        pageMargin = 0;
        xPageStart = pageMargin;
        yPageStart = page.getMediaBox().getHeight() - pageMargin;
        xCursor = xPageStart;
        yCursor = yPageStart;
    }

    /**
     * Each page in the pdf document will have the same page margin.
     */
    @Override
    public void setPageMargin(float pageMargin) {
        this.pageMargin = pageMargin;
        currPageWidth = currPageWidth - (2 * this.pageMargin);

        xPageStart = xPageStart + this.pageMargin;
        yPageStart = yPageStart - this.pageMargin;

        xCursor = xPageStart;
        yCursor = yPageStart;
    }

    /**
     * Once page header is set, each page in the pdf document will have the same header.
     */
    @Override
    public void setHeaderText(String pageHeader) {
        this.pageHeader = pageHeader;
        float strHeight = Constants.HEADER_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * Constants.FONT_SIZE;
        yPageStart = yPageStart - strHeight - Constants.BOTTOM_PADDING;
        yCursor = yPageStart;
    }

    /**
     * Once page footer is set, each page in the pdf document will have the same footer.
     */
    @Override
    public void setFooterText(String pageFooter) {
        this.pageFooter = pageFooter;
    }

    /**
     * addParagraph() adds text to the pdf. This method supports content break.
     * If the content does not fit in the current page, it is split into parts and written onto different pages.
     */
    @Override
    public void addParagraph(String text) throws IOException {
        yCursor = yCursor - Constants.TOP_PADDING;
        float strHeight = Constants.TEXT_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * Constants.FONT_SIZE;

        try {
            //If text exceeds page width, it is split into multiple lines.
            List<String> lines = splitTextIntoLines(text);

            //Writing each line of the input text to the pdf document.
            contentStream.beginText();
            contentStream.setFont(Constants.TEXT_FONT, Constants.FONT_SIZE);
            contentStream.newLineAtOffset(xCursor + Constants.LEFT_PADDING, yCursor - strHeight);
            for (String line : lines) {
                //Checking whether the current line can fit in the page.
                if (isEndOfPage(yCursor - strHeight - Constants.BOTTOM_PADDING)) {
                    yCursor = yPageStart - Constants.TOP_PADDING;
                    contentStream.beginText();
                    contentStream.setFont(Constants.TEXT_FONT, Constants.FONT_SIZE);
                    contentStream.newLineAtOffset(xCursor + Constants.LEFT_PADDING, yCursor - strHeight);
                }
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, (-strHeight - Constants.BOTTOM_PADDING));
                yCursor = yCursor - strHeight - Constants.BOTTOM_PADDING;
            }
            contentStream.endText();
            yCursor = yCursor - Constants.BOTTOM_PADDING;
        } catch (IOException e) {
            throw new IOException("Text cannot be added to pdf. Pdf export failed.");
        }
    }

    /**
     * addParagraph() adds text to the pdf.
     * The entire text will be added to one page if noContentBreak boolean is set to true.
     * Content break is supported when noContent Break boolean is set to false.
     */
    @Override
    public void addParagraph(String text, boolean noContentBreak) throws IOException {
        yCursor = yCursor - Constants.TOP_PADDING;
        float strHeight = Constants.TEXT_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * Constants.FONT_SIZE;

        try {
            if (noContentBreak) {
                //If text exceeds page width, it is split into multiple lines.
                List<String> lines = splitTextIntoLines(text);

                //Writing each line of the input text to the pdf document.
                contentStream.beginText();
                contentStream.setFont(Constants.TEXT_FONT, Constants.FONT_SIZE);
                contentStream.newLineAtOffset(xCursor + Constants.LEFT_PADDING, yCursor - strHeight);
                //Checking whether the all the lines of the text fit in the current page.
                if (isEndOfPage((yCursor - ((lines.size()) * (strHeight + Constants.BOTTOM_PADDING))))) {
                    yCursor = yPageStart - Constants.TOP_PADDING;
                    contentStream.beginText();
                    contentStream.setFont(Constants.TEXT_FONT, Constants.FONT_SIZE);
                    contentStream.newLineAtOffset(xCursor + Constants.LEFT_PADDING, yCursor - strHeight);
                }
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, (-strHeight - Constants.BOTTOM_PADDING));
                    yCursor = yCursor - strHeight - Constants.BOTTOM_PADDING;
                }
                contentStream.endText();
                yCursor = yCursor - Constants.BOTTOM_PADDING;
            } else {
                addParagraph(text);
            }
        } catch (IOException e) {
            throw new IOException("Text cannot be added to pdf. Pdf export failed.");
        }
    }

    /**
     * Adds input image to the pdf with center alignment.
     */
    @Override
    public void drawImage(Bitmap imageBitmap, float imageWidth, float imageHeight) throws IOException {
        yCursor = yCursor - Constants.TOP_PADDING;

        float xStartImage = ((currPageWidth - imageWidth) / 2);
        float yStartImage = (yCursor - imageHeight);

        //Checking whether image can fit in current page or not.
        if (isEndOfPage((yStartImage))) {
            yStartImage = (yCursor - imageHeight);
        }

        try {
            //Adding image to pdf.
            PDImageXObject pdImage = LosslessFactory.createFromImage(pdDocument, imageBitmap);
            contentStream.drawImage(pdImage, xStartImage, yStartImage, imageWidth, imageHeight);

            yCursor = yStartImage - Constants.BOTTOM_PADDING;
        } catch (IOException e) {
            throw new IOException("Image cannot be added to pdf. Pdf export failed.");
        }
    }

    /**
     * Draws a line chart in the pdf with a rectangle border.
     */
    @Override
    public void drawLineChart(Bitmap lineChartBitmap, String xAxisTitle, String yAxisTitle, float lineChartWidth, float lineChartHeight) throws IOException {
        yCursor = yCursor - Constants.TOP_PADDING;

        float xStartLineChart = ((currPageWidth - lineChartWidth) / 2);
        float yStartLineChart = (yCursor - lineChartHeight - Constants.LINE_CHART_MARGIN);

        //Checking whether line chart can fit in current page or not.
        if (isEndOfPage((yStartLineChart - Constants.LINE_CHART_MARGIN))) {
            yStartLineChart = (yCursor - lineChartHeight - Constants.LINE_CHART_MARGIN);
        }

        try {
            //Exporting line chart to pdf.
            PDImageXObject pdLineChartImg = LosslessFactory.createFromImage(pdDocument, lineChartBitmap);
            contentStream.drawImage(pdLineChartImg, xStartLineChart, yStartLineChart, lineChartWidth, lineChartHeight);

            // Write yAxisTitle vertically.
            float yAxisTitleWidth = Constants.TEXT_FONT.getStringWidth(yAxisTitle) / 1000 * Constants.FONT_SIZE;
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0);
            contentStream.setFont(Constants.TEXT_FONT, Constants.FONT_SIZE);
            Matrix matrix = Matrix.getRotateInstance(Math.toRadians(90), xStartLineChart - Constants.RIGHT_PADDING, yStartLineChart + ((lineChartHeight - yAxisTitleWidth) / 2));
            contentStream.setTextMatrix(matrix);
            contentStream.showText(yAxisTitle);
            contentStream.endText();

            // Write xAxisTitle.
            float xAxisTitleWidth = Constants.TEXT_FONT.getStringWidth(xAxisTitle) / 1000 * Constants.FONT_SIZE;
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0);
            contentStream.setFont(Constants.TEXT_FONT, 12);
            contentStream.newLineAtOffset(xStartLineChart + ((lineChartWidth - xAxisTitleWidth) / 2), yStartLineChart - Constants.TOP_PADDING);
            contentStream.showText(xAxisTitle);
            contentStream.endText();

            //Draw a border rectangle to line chart
            contentStream.addRect(xStartLineChart - Constants.LINE_CHART_MARGIN, yStartLineChart - Constants.LINE_CHART_MARGIN, lineChartWidth + (2 * Constants.LINE_CHART_MARGIN), lineChartHeight + (2 * Constants.LINE_CHART_MARGIN));
            contentStream.setStrokingColor(0, 0, 0);
            contentStream.closeAndStroke();
            contentStream.fill();

            yCursor = yStartLineChart - Constants.LINE_CHART_MARGIN - Constants.BOTTOM_PADDING;
        } catch (IOException e) {
            throw new IOException("Line chart cannot be added to pdf. Pdf export failed.");
        }
    }

    /**
     * Draws table in the pdf with the data passed as input.
     */
    @Override
    public void drawDataTable(int noOfCols, int noOfRows, String[] colHeadings, String[][] dataTable) throws IOException {
        yCursor = yCursor - Constants.TOP_PADDING;

        //Initialize table
        float tableWidth = currPageWidth - (2 * Constants.DATA_TABLE_MARGIN);
        float yStart = yCursor;
        float bottomMargin = Constants.DATA_TABLE_MARGIN;
        float yStartNewPage = yPageStart;

        try {
            ArrayList<ColoredColumn> columns = new ArrayList<>();
            for (int i = 0; i < noOfCols; i++) {
                columns.add(new ColoredColumn(ColorUtils.getColor(Color.WHITE), (100f / noOfCols)));
            }

            ColoredColumnTable table = new ColoredColumnTable(yStart, yStartNewPage, bottomMargin, tableWidth, Constants.DATA_TABLE_MARGIN, pdDocument, page, columns);

            Row<PDPage> row = table.createPrefilledRow(80f);
            for (int i = 0; i < noOfCols; i++) {
                row.getCells().get(i).setText(colHeadings[i]);
            }
            table.setHeader(row);

            for (int i = 0; i < noOfRows; i++) {
                row = table.createPrefilledRow(80f);
                for (int j = 0; j < noOfCols; j++) {
                    row.getCells().get(j).setText(dataTable[i][j]);
                }
            }
            float yTableBottom = table.draw();

            //If table exceeds the current page, the table breaks and is drawn in the next page automatically.
            //So, the current page needs to be updated.
            if (table.getCurrentPage() != page) {
                contentStream.close();
                page = table.getCurrentPage();
                contentStream = new PDPageContentStream(pdDocument, page, true, true);
            }

            yCursor = yTableBottom - Constants.BOTTOM_PADDING;
        } catch (IOException e) {
            throw new IOException("Data table cannot be added to pdf. Pdf export failed.");
        }
    }


    /**
     * Returns the current page of the pdf document.
     */
    public PDPage getCurrentPage() {
        return page;
    }

    /**
     * Adds a new page to document and moves cursors to that new page..
     */
    @Override
    public void moveToNextPage() throws IOException {
        addNewPage();
    }

    /**
     * Closes the pdf document.
     */
    @Override
    public void endDocument() throws IOException {
        try {
            contentStream.close();

            //Adding border,header and footer to each page of the pdf document.
            for (int i = 0; i < pdDocument.getNumberOfPages()/*pdDocument.getDocumentCatalog().getPages().getCount()*/; i++) {
                PDPage page = (pdDocument.getDocumentCatalog().getPages().get(i));
                drawPageBorder(page);
                writePageHeader(page);
                writePageFooter(page);
            }

            //Closing pdf document.
            pdDocument.save(pdfFile);
            pdDocument.close();
        } catch (IOException e) {
            throw new IOException("Pdf document cannot be closed. Pdf export failed.");
        }
    }

    /**
     * Adds a new page to the pdf document.
     */
    private void addNewPage() throws IOException {
        page = new PDPage();
        pdDocument.addPage(page);

        if (contentStream != null)
            contentStream.close();
        contentStream = new PDPageContentStream(pdDocument, page);

        xCursor = xPageStart;
        yCursor = yPageStart;
    }

    /**
     * isEndOfPage() creates a new page if the content exceeds the current page.
     */
    private boolean isEndOfPage(float yCursor) throws IOException {
        boolean isEndOfPage = yCursor <= Constants.PAGE_BOTTOM_MARGIN;
        if (isEndOfPage) {
            try {
                addNewPage();
            } catch (IOException e) {
                throw new IOException("New page cannot be added. Pdf export failed.");
            }
        }

        return isEndOfPage;
    }

    /**
     * PageHeader text will be written at the top with center alignment.
     */
    private void writePageHeader(PDPage page) throws IOException {
        float strWidth = Constants.HEADER_FONT.getStringWidth(pageHeader) / 1000 * Constants.FONT_SIZE;

        PDPageContentStream headerContentStream = new PDPageContentStream(pdDocument, page, true, true);
        headerContentStream.beginText();
        headerContentStream.setNonStrokingColor(15, 38, 192);
        headerContentStream.setFont(Constants.HEADER_FONT, Constants.FONT_SIZE);
        headerContentStream.newLineAtOffset(((currPageWidth - strWidth) / 2), yPageStart + Constants.BOTTOM_PADDING);
        headerContentStream.showText(pageHeader);
        headerContentStream.endText();
        headerContentStream.close();
    }

    /**
     * Page border is drawn with the specified page margin.
     */
    private void drawPageBorder(PDPage page) throws IOException {
        PDPageContentStream borderContentStream = new PDPageContentStream(pdDocument, page, true, true);
        borderContentStream.addRect(pageMargin, pageMargin, currPageWidth, page.getMediaBox().getHeight() - (2 * pageMargin));
        borderContentStream.setStrokingColor(0, 0, 0);
        borderContentStream.closeAndStroke();
        borderContentStream.fill();
        borderContentStream.close();
    }

    /**
     * PageFooter text will be written at the bottom with center alignment.
     */
    private void writePageFooter(PDPage page) throws IOException {
        float strWidth = Constants.TEXT_FONT.getStringWidth(pageFooter) / 1000 * Constants.FONT_SIZE;
        float strHeight = Constants.TEXT_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * Constants.FONT_SIZE;

        PDPageContentStream footerContentStream = new PDPageContentStream(pdDocument, page, true, true);
        footerContentStream.beginText();
        footerContentStream.setNonStrokingColor(0, 0, 0);
        footerContentStream.setFont(Constants.TEXT_FONT, Constants.FONT_SIZE);
        footerContentStream.newLineAtOffset(((currPageWidth - strWidth) / 2), pageMargin + strHeight);
        footerContentStream.showText(pageFooter);
        footerContentStream.endText();
        footerContentStream.close();
    }

    /**
     * If the input text exceeds page width, the text is split into multiple lines.
     */
    private List<String> splitTextIntoLines(String text) throws IOException {
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;
        float pageWidth = currPageWidth - Constants.LEFT_PADDING - Constants.RIGHT_PADDING;
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            float textSize = Constants.FONT_SIZE * Constants.TEXT_FONT.getStringWidth(subString) / 1000;
            if (textSize > pageWidth) {
                if (lastSpace < 0)
                    lastSpace = spaceIndex;
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }
        return lines;
    }
}
