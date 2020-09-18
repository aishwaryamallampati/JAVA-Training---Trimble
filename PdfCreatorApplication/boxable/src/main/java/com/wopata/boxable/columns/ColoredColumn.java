package com.wopata.boxable.columns;

import org.apache.pdfbox.pdmodel.graphics.color.PDColor;

/**
 * Created by ardouin on 24/02/16.
 */
public class ColoredColumn {

    PDColor color;
    float witdhPercent;

    public PDColor getColor() {
        return color;
    }

    public float getWitdhPercent() {
        return witdhPercent;
    }

    public ColoredColumn(PDColor color, float witdhPercent) {
        this.color = color;
        this.witdhPercent = witdhPercent;
    }
}
