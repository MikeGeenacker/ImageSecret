package com.jelle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Region {
    private Color[][] colorArray;
    private int width;
    private int height;

    public Region(BufferedImage image, int startX, int startY, int width, int height) {
        this.width = width;
        this.height = height;

        int[] array = image.getRGB(startX, startY, width, height, null, 0, width);
        colorArray = new Color[width][height];

       for(int i = 0; i < width; i++) {
           for(int j = 0; j < height; j++) {
               colorArray[i][j] = new Color(array[j * width + i]);
           }
       }
    }
}
