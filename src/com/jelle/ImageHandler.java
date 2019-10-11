package com.jelle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandler {
    private static int fileCounter;
    protected BufferedImage originalImg;
    protected BufferedImage encodedImg;
    protected int width;
    protected int height;
    protected int arrayLength;
    protected int[] colorAsIntegerArray;
    protected Color[] colorArray;

    public ImageHandler(BufferedImage img) {
        originalImg = img;
        encodedImg = new BufferedImage(originalImg.getWidth(), originalImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        colorAsIntegerArray = null;
    }

    public ImageHandler(String filePath) {
        loadImage(filePath);
        encodedImg = new BufferedImage(originalImg.getWidth(), originalImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        colorAsIntegerArray = originalImg.getRGB(0,0, width, height, null, 0, width);
        arrayLength = width * height;
        colorArray = new Color[arrayLength];
        convertToColor();
    }

    public void loadImage(String filePath) {
        try {
            originalImg = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = originalImg.getWidth();
        height = originalImg.getHeight();
    }

    public void saveImageToFile(String filePath) {
        try {
            ImageIO.write(originalImg, "jpg", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void convertToColor() {
        for(int i = 0; i < arrayLength; i++) {
            colorArray[i] = new Color(colorAsIntegerArray[i]);
        }
    }

    public void buildImageFromArray() {
        fileCounter++;
        encodedImg.setRGB(0,0,width, height, colorAsIntegerArray, 0, width);
        saveImageToFile("img/resultaat" + fileCounter + ".jpg");
    }

    public Color[] getColorArray() {
        return colorArray;
    }

    public BufferedImage getOriginalImg() {
        return originalImg;
    }
}
