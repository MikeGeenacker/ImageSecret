package com.jelle;

import java.awt.image.BufferedImage;

public class Main {
    public static void main(String... args) {
        int regionWidth = 8;
        int regionHeight = 8;
        ImageHandler imageHandler = new ImageHandler("img/voorbeeld3.jpg");
        BufferedImage img = imageHandler.getOriginalImg();
        RegionHandler regionHandler = new RegionHandler(img, regionWidth, regionHeight);

        imageHandler.buildImageFromArray();


    }
}
