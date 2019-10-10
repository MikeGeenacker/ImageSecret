package com.mike;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionSearcher {
    private ArrayList<Region> regions;
    private int imgWidth;
    private int imgHeight;
    private int regionWidth;
    private int regionHeight;

    public RegionSearcher(int imgWidth, int imgHeight, int regionWidth, int regionHeight) {
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;

    }

    public ArrayList<Region> makeRegions(int numOfRegions, Color[] pixels) {
        // per img loopen we door alle regions heen
        // per region loopen we door de hoogte heen en kopieren we de rows
        ArrayList<Region> output = new ArrayList<>();
        final int ROW_OFFSET = regionHeight * imgWidth;
        final int REGIONS_PER_ROW = imgWidth / regionWidth;
        ColorCalculator calc = new ColorCalculator();

        for(int i = 0; i < numOfRegions; i++) {
            Region r;
            int rowIndex = i % REGIONS_PER_ROW;

            ArrayList<Color> rows = new ArrayList<>();
            for(int j = 0; j < regionHeight; j++) {
                int x = (i * regionWidth) % imgWidth;
                int y = rowIndex * ROW_OFFSET + (j * imgWidth);

                rows.addAll(Arrays.asList(Arrays.<Color>copyOfRange(pixels, x + y, x + y + regionWidth)));
            }
            Color[] temp = new Color[rows.size()];
            temp =  rows.toArray(temp);
            r = new Region(regionWidth, regionHeight, temp, calc);
            output.add(r);

        }
        //TODO debug
        System.out.printf("Original:R:%d G:%d B:%d / New:R:%d G:%d B:%d", pixels[20000].getRed(), pixels[20000].getGreen(), pixels[20000].getBlue(),
                                                                            output.get(0).getPixel(20000).getRed(), output.get(0).getPixel(20000).getGreen(), output.get(0).getPixel(20000).getBlue());
        return output;
    }

    public Color[] toPixels(ArrayList<Region> regions) {
        return null;
    }

    // Doet het niet
    public ArrayList<Region> getRegions(int numofRegions, Color[] pixels) {
        ArrayList<Region> regions = new ArrayList<>();
        ColorCalculator colorCalc = new ColorCalculator();
        for (int i = 0; i < numofRegions; i++) {
            ArrayList<Color> region = new ArrayList<>();
            int numOfRegionsInWidth = imgWidth / regionWidth;

            int rowIndex = numofRegions % numOfRegionsInWidth;
            for (int j = 0; j < regionHeight; j++) {
                int x = (i * regionWidth) % imgWidth;
                int y = j * imgWidth + (rowIndex * imgWidth);

                List<Color> row = Arrays.asList(Arrays.copyOfRange(pixels, x + y, (x + y) + regionWidth));

                region.addAll(row);
            }
            // rare java shit die moet gebeuren
            Color[] regionColor = new Color[region.size()];
            regionColor = region.toArray(regionColor);
            regions.add(new Region(regionWidth, regionHeight, regionColor, colorCalc));
        }

        return regions;
    }
}
