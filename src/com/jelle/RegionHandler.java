package com.jelle;

import java.awt.image.BufferedImage;

public class RegionHandler extends ImageHandler {
    private static int fileCounter;
    private int xRegions;
    private int yRegions;
    private int regionWidth;
    private int regionHeight;
    private Region[][] regions;

    public RegionHandler(BufferedImage img, int regionWidth, int regionHeight) {
        super(img);
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;
        xRegions = img.getWidth() / regionWidth;
        yRegions = img.getHeight() / regionHeight;

        regions = new Region[xRegions][yRegions];

        createRegions();
    }

    public void encodeAllRegions(Encoder enc) {
        String code = enc.getCode();
        int regionVolume = regionWidth * regionHeight;
        int neededRegions = ((code.length() * (enc.getControlPixels() + 1)) / regionVolume) + 1;
        int charsPerRegion = regionVolume / (enc.getControlPixels() + 1);

        for (int i = 0; i <= neededRegions / xRegions; i++) {
            for (int j = 0; j < xRegions && j < neededRegions - (i * xRegions); j++) {
                int startIndex = (j * charsPerRegion) + (i * charsPerRegion * xRegions);
                int endIndex = Math.min(code.length(), startIndex + charsPerRegion);

//                if(startIndex > endIndex) {
//                    break;
//                }
                System.out.println("i: " + i );
                System.out.println("j: " + j );
                System.out.println(code.substring(startIndex, endIndex).length());
                encodeRegion(j, i, code.substring(startIndex, endIndex), enc);
            }
        }
    }

    private void encodeRegion(int x, int y, String code, Encoder enc) {
        regions[x][y] = enc.encode(regions[x][y], code);
    }

    private void createRegions() {
        for (int i = 0; i < xRegions; i++) {
            for (int j = 0; j < yRegions; j++) {
                regions[i][j] = new Region(originalImg, i * regionWidth, j * regionHeight, regionWidth, regionHeight);
            }
        }
    }

    public void buildImageFromRegionsInt() {
        int[] totalArray = new int[xRegions * yRegions * regionWidth * regionHeight];

        for (int yR = 0; yR < yRegions; yR++) {
            for (int rH = 0; rH < regionHeight; rH++) {
                for (int xR = 0; xR < xRegions; xR++) {
                    for (int rW = 0; rW < regionWidth; rW++) {
                        totalArray[(yRegions * yR) + (rH * regionHeight) + (xR * xRegions) + rW] = regions[xR][yR].getPixelInt(rW, rH);
                    }
                }
            }
        }

        fileCounter++;

        encodedImg.setRGB(0, 0, width, height, totalArray, 0, width);
        saveImageToFile(encodedImg, "img/resultaatRegionRebuilder" + fileCounter + ".png");
    }

    public void buildImageFromRegionsColor() {
        int[] totalArray = new int[xRegions * yRegions * regionWidth * regionHeight];
        int index;

        for (int yR = 0; yR < yRegions; yR++) {
            for (int rH = 0; rH < regionHeight; rH++) {
                for (int xR = 0; xR < xRegions; xR++) {
                    for (int rW = 0; rW < regionWidth; rW++) {
                        index = (yR * xRegions * regionWidth * regionHeight) + (rH * xRegions * regionWidth) + (xR * regionWidth) + rW;
                        totalArray[index] = regions[xR][yR].getPixelColor(rW, rH).getRGB();
                    }
                }
            }
        }

        fileCounter++;

        encodedImg.setRGB(0, 0, width, height, totalArray, 0, width);
        saveImageToFile(encodedImg, "img/resultaatRegionRebuilder" + fileCounter + ".png");
    }


    public Region getRegion(int x, int y) {
        return regions[x][y];
    }

    public Region[][] getAllRegions() {
        return regions;
    }
}
