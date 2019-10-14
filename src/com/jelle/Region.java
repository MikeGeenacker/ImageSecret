package com.jelle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Region {
    private Color[][] colorArray;
    private int[] coordinates;
    private Color avgColor;
    private float[] avgRGBOffset;
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
        avgRGBOffset = new float[3];
        coordinates = new int[2];

        coordinates[0] = startX / width;
        coordinates[1] = startY / height;

        initRegion(image);
        calcAvgColor();
        calcOffset();
    }

    private void initRegion(BufferedImage image) {
        int[] array = image.getRGB(startX, startY, width, height, null, 0, width);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                colorArray[i][j] = new Color(array[j * width + i]);
            }
        }
    }

    private void calcAvgColor() {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                redSum += colorArray[i][j].getRed();
                greenSum += colorArray[i][j].getGreen();
                blueSum += colorArray[i][j].getBlue();
            }
        }

        avgColor = new Color(redSum / (width * height), greenSum / (width * height), blueSum / (width * height));
    }

    private void calcOffset() {
        float redSum = 0;
        float greenSum = 0;
        float blueSum = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                redSum += colorArray[i][j].getRed() - avgColor.getRed();
                greenSum += colorArray[i][j].getGreen() - avgColor.getGreen();
                blueSum += colorArray[i][j].getBlue() - avgColor.getBlue();
            }
        }

        avgRGBOffset[0] = redSum / (width * height);
        avgRGBOffset[1] = greenSum / (width * height);
        avgRGBOffset[2] = blueSum / (width * height);
        avgOffset = 0;

        for(int i = 0; i < avgRGBOffset.length; i++) {
            avgOffset += Math.abs(avgRGBOffset[i]);
        }
        avgOffset /= 3;
    }

    public void setPixel(int x, int y, Color c) {
        colorArray[x][y] = c;
    }

    public Color getPixelColor(int x, int y) {
        return colorArray[x][y];
    }

    public Color[][] getColorArray() {
        return colorArray;
    }

    public Color getAvgColor() {
        return avgColor;
    }

    public float getAvgOffset() {
        return avgOffset;
    }

    public float[] getAvgOffsets() {
        return avgRGBOffset;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
