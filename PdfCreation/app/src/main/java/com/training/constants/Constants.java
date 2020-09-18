package com.training.constants;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Constants {
    public static final String DIR_NAME = "/PdfCreation";
    public static final String PDF_FILE_NAME = "Report";
    public final static String ST_UNDERSCORE = "_";
    public static final String FILE_EXTENSION_PDF = ".pdf";
    public static final float TOP_PADDING = 3;
    public static final float BOTTOM_PADDING = 3;
    public static final float LEFT_PADDING = 3;
    public static final float RIGHT_PADDING = 3;
    public static final float FONT_SIZE = 12;
    public static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    public static final PDFont HEADER_FONT = PDType1Font.HELVETICA_BOLD;
    public static final float PAGE_BOTTOM_MARGIN = 40;
    public static final float LINE_CHART_MARGIN = 30;
    public static final float DATA_TABLE_MARGIN = 30;
}

