package com.jelle;

import java.awt.image.BufferedImage;

public class RegionHandler extends ImageHandler{
    private int xRegions;
    private int yRegions;
    private int regionWidth;
    private int regionHeight;
    private Region[][] regions;

    public RegionHandler(BufferedImage img, int regionWidth, int regionHeight) {
        super(img);
        this.regionWidth = regionWidth;
        this. regionHeight = regionHeight;
        xRegions = img.getWidth() / regionWidth;
        yRegions = img.getHeight() / regionHeight;

        regions = new Region[xRegions][yRegions];

        createRegions();
    }

    private void createRegions() {
        for(int i = 0; i < xRegions; i++)
            for(int j = 0; j < yRegions; j++) {
                regions[i][j] = new Region(originalImg, i * regionWidth, j * regionHeight, regionWidth, regionHeight);
            }
    }

    public void encodeAllRegions() {

    }

    public void encodeRegion(int x, int y) {
        // TODO
    }

    public Region getRegion(int x, int y) {
        return regions[x][y];
    }

    public Region[][] getAllRegions() {
        return regions;
    }
}
