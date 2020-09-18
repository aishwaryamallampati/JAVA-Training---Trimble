package com.wopata.boxable;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;


/**
 * Created by dgautier on 3/18/2015.
 */
public abstract class AbstractPageTemplate extends PDPage{

    protected abstract PDDocument getDocument();

    protected abstract float yStart();

    protected void addPicture(PDImageXObject ximage, float cursorX, float cursorY, int width, int height) throws IOException {

        PDPageContentStream contentStream = new PDPageContentStream(getDocument(), this, true, false);
        contentStream.drawImage(ximage, cursorX, cursorY, width, height);
        contentStream.close();
    }


}
