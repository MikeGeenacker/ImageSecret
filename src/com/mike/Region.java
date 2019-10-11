package com.mike;

import java.awt.*;

public class Region {
    private int width;
    private int height;
    private Color[] pixels;
    private Color averageColor;
    private ColorCalculator.ColorOffset averageColorOffset;

    public Region (int width, int height, Color[] pixels, ColorCalculator c) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.averageColor = c.avgColor(pixels);
        this.averageColorOffset = c.avgColorOffset(c.colorOffsets(averageColor, pixels));
    }

    public Color[] getPixels() {
        return pixels;
    }

    public void setPixel(int pos, Color pixel) {
        pixels[pos] =  pixel;
    }

    public Color getPixel(int pos) {
        return pixels[pos];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

