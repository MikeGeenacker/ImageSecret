package com.mike;

import javax.imageio.ImageIO;
import javax.swing.text.Keymap;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Main {

    static BufferedImage img = null;
    static int[] pixels = null;
    static Color[] colors = null;
    static ColorCalculator calc;

    public static void main(String[] args) {
        calc = new ColorCalculator();
        try {
            img = ImageIO.read(new File("img/voorbeeld7.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int w = img.getWidth();
        int h = img.getHeight();
        pixels = img.getRGB(0, 0, w, h, null, 0, w);

        colors = new Color[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            colors[i] = new Color(pixels[i]);
        }

        Color avgColor = calc.avgColor(colors);

        RegionSearcher searcher = new RegionSearcher(img.getWidth(), img.getHeight(), 1080, 1920);
        ArrayList<Region> regions = searcher.makeRegions(colors.length / (1920 * 1080), colors);

        Encoder enc = new Encoder(regions.get(0), "String  ");
//        enc.encode();

        BufferedImage encodedImg = colorToImage(enc.region);

        File f = new File("encodedImg.png");
        try {
            ImageIO.write(encodedImg, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //subverdelen in regions

        //ColorOffset[] offsets = colorOffsets(avgColor, colors);

        //ColorOffset avg = avgColorOffset(offsets);

        //System.out.printf("[R: %f, G: %f, B: %f]", avg.red, avg.green, avg.blue);
    }

    // TODO, waarschijnlijk gaat het hier fout
    public static BufferedImage colorToImage(Region r) {
        Color[] pixels = r.getPixels();

        BufferedImage img = new BufferedImage(r.getWidth(), r.getHeight(), BufferedImage.TYPE_INT_RGB);

        // TODO, fix x en y, coordinate of out bounds
        // TODO Jelle programeer aap fix dit
        // TODO jelle geef info
        // aspect ratio, geen idee waarom
        boolean isLandscape;
        float aspectRatio;
        if(r.getWidth() > r.getHeight()) {
            aspectRatio = (float) r.getWidth() / r.getHeight();
            isLandscape = true;
        }
        else {
            aspectRatio = (float) r.getHeight() / r.getWidth();
            isLandscape = false;
        }

        for(int x = 0; x < r.getWidth(); x++) {
            int i = x;
            for(int y = 0; y < r.getHeight()-850; y++) {
                // ????? int conversie op float operatie (6.9 > 6) laatste pixel meenemen?
                int j;
                if (isLandscape)
                    j = (int) Math.floor((y* (r.getHeight() * aspectRatio)));
                else
                    j = (int) Math.floor((y * (r.getWidth() * aspectRatio)));

//                if (j >= img.getHeight() * img.getWidth())
//                    j = img.getHeight() * img.getWidth() - 2;

                img.setRGB(x, y, pixels[i + j].getRGB());
            }
        }
        return img;
    }


    public static class Encoder {
        private Region region;
        private String code;

        // TODO, hoofdletters, cijfers etc
        public Encoder(Region r, String code) {
            region = r;
            this.code = code.toLowerCase();
        }

        public void encode() {
            char[] cArray = code.toCharArray();
            // TODO probeert de text te encoden zonder rekening te houden
            // TODO met beschikbare ruimte
            for (int i = 0; i < cArray.length; i++) {
                encodeCharacter(cArray[i], i * 2, i * 2 + 1);
            }
        }

        public void encodeCharacter(char c, int control, int data) {
            region.getPixels()[data] = region.getPixels()[control];

            int charCode = KeyMap.keyMap.get(c);

            Color pixel = region.getPixels()[data];

            // TODO, geen edge case behandeld voor values > 255
            // TODO charcode verdelen over r/g/b values
            pixel = new Color(pixel.getRed() + charCode, pixel.getGreen(), pixel.getBlue());

            region.setPixel(data, pixel);


        }


    }

    public static class KeyMap {
        public static HashMap<Character, Integer> keyMap = new HashMap<>() {{
            put('a', 0);
            put('b', 1);
            put('c', 2);
            put('d', 3);
            put('e', 4);
            put('f', 5);
            put('g', 6);
            put('h', 7);
            put('i', 8);
            put('j', 9);
            put('k', 10);
            put('l', 11);
            put('m', 12);
            put('n', 13);
            put('o', 14);
            put('p', 15);
            put('q', 16);
            put('r', 17);
            put('s', 18);
            put('t', 19);
            put('u', 20);
            put('v', 21);
            put('w', 22);
            put('x', 23);
            put('y', 24);
            put('z', 25);
            put(' ', 26);
        }};


    }


}
