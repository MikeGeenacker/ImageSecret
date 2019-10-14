package com.jelle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Region {
    private Color[][] colorArray;
    private int[][] intArray;
    private Color colorAvgColor;
    private int intAvgColor;
    private float[] avgOffsets;
    private float avgOffset;
    private int startX;
    private int startY;
    private int width;
    private int height;

    public Region(BufferedImage image, int startX, int startY, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;

        colorArray = new Color[width][height];
        intArray = new int[width][height];
        avgOffsets = new float[3];

        initRegion(image);
        calcAvgColor();
        calcOffset();
    }

    private void initRegion(BufferedImage image) {
        int[] array = image.getRGB(startX, startY, width, height, null, 0, width);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                intArray[i][j] = array[j * width + i];
                colorArray[i][j] = new Color(array[j * width + i]);
            }
        }
    }

    private void calcAvgColor() {
        int intSum = 0;
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                intSum += intArray[i][j];

                redSum += colorArray[i][j].getRed();
                greenSum += colorArray[i][j].getGreen();
                blueSum += colorArray[i][j].getBlue();
            }
        }

        intAvgColor = intSum / (width * height);
        colorAvgColor = new Color(redSum / (width * height), greenSum / (width * height), blueSum / (width * height));
    }

    private void calcOffset() {
        float intSum = 0;
        float redSum = 0;
        float greenSum = 0;
        float blueSum = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                intSum += intArray[i][j] - intAvgColor;
                redSum += colorArray[i][j].getRed() - colorAvgColor.getRed();
                greenSum += colorArray[i][j].getGreen() - colorAvgColor.getGreen();
                blueSum += colorArray[i][j].getBlue() - colorAvgColor.getBlue();
            }
        }

        avgOffset = intSum / (width * height);
        avgOffsets[0] = redSum / (width * height);
        avgOffsets[1] = greenSum / (width * height);
        avgOffsets[2] = blueSum / (width * height);
    }

    public void setPixel(int x, int y, Color c) {
        colorArray[x][y] = c;
    }

    public void setPixel(int x,int y,int c) {
        intArray[x][y] = c;
    }

    public Color getPixelColor(int x, int y) {
        return colorArray[x][y];
    }

    public int getPixelInt(int x,int y) {
        return intArray[x][y];
    }


    public Color[][] getColorArray() {
        return colorArray;
    }

    public int[][] getIntArray() {
        return intArray;
    }

    public Color getColorAvgColor() {
        return colorAvgColor;
    }

    public int getIntAvgColor() {
        return intAvgColor;
    }

    public float getAvgOffset() {
        return avgOffset;
    }

    public float[] getAvgOffsets() {
        return avgOffsets;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
