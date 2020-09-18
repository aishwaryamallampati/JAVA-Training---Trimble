package com.wopata.boxable.utils;

import android.graphics.Color;

import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;

/**
 * Created by ardouin on 23/02/16.
 */
public class ColorUtils {

    public static PDColor getColor(int red, int green , int blue){
        float[] components = new float[]{(float)red / 255.0F, (float)green / 255.0F, (float)blue / 255.0F};
        return new PDColor(components, PDDeviceRGB.INSTANCE);
    }

    public static PDColor getColor(int color){
        float[] components = new float[]{(float) Color.red(color)/ 255.0F, (float)Color.green(color) / 255.0F, (float)Color.blue(color)/ 255.0F};
        return new PDColor(components, PDDeviceRGB.INSTANCE);
    }
}
