package com.jelle;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Main {
    public static void main(String... args) {
        int regionWidth = 2;
        int regionHeight = 2;
        int stringSize = 500000;
        String alphabet = "abcdefghijklmnopqrstuvwxyz .,?!";
        Random r = new Random();
        ImageHandler imageHandler = new ImageHandler("img/voorbeeld3.jpg");
        BufferedImage img = imageHandler.getOriginalImg();
        RegionHandler regionHandler = new RegionHandler(img, regionWidth, regionHeight);

        StringBuilder sb = new StringBuilder(stringSize);
        for (int i = 0; i < stringSize; i++) {
            sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }

        Encoder enc = new Encoder(sb.toString());
//        regionHandler.encodeAllRegions(enc);
//        regionHandler.buildImageFromRegionsColor();
        regionHandler.encodeSortedRegions(enc, Comparators.ascColorOffset);
//        regionHandler.encodeSortedRegions(enc, Comparators.descColorOffset);
        regionHandler.buildImageFromRegionsColor();
        imageHandler.buildImageFromArray();
    }
}
