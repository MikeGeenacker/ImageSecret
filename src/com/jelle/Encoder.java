package com.jelle;

import java.awt.*;
import java.util.HashMap;

public class Encoder {
    private String code;
    private int controlPixels;

    public Encoder(String code, int controlPixels) {
        this.code = code.toLowerCase();
        this.controlPixels = controlPixels;
    }

    public Encoder(String code) {
        this.code = code.toLowerCase();
        controlPixels = 1;
    }

    public Region encode(Region region, String codePart) {
        char[] charArray = codePart.toCharArray();
        int charIndex = 0;
        int modulo = keyMap.size() / 3;
        int pixelsPerChar = controlPixels + 1;
        int charPerRow = region.getWidth() / pixelsPerChar;
        int numRows = (charArray.length * pixelsPerChar) / region.getWidth();

        for (int i = 0; i <= numRows; i++) {
            for (int j = 1; j <= (charArray.length - (i * charPerRow)) / pixelsPerChar && j <= region.getWidth() / pixelsPerChar; j++) {
                int rControl = region.getPixelColor((j * pixelsPerChar) - 2, i).getRed();
                int rCode = region.getPixelColor((j * pixelsPerChar) - 1, i).getRed();
                int gControl = region.getPixelColor((j * pixelsPerChar) - 2, i).getGreen();
                int gCode = region.getPixelColor((j * pixelsPerChar) - 1, i).getGreen();
                int bControl = region.getPixelColor((j * pixelsPerChar) - 2, i).getBlue();
                int bCode = region.getPixelColor((j * pixelsPerChar) - 1, i).getBlue();

                if (rControl > 246 || rCode > 246 || gControl > 246 || gCode > 246 || bControl > 246 || bCode > 246
                        || rControl < 9 || rCode < 9 || gControl < 9 || gCode < 9 || bControl < 9 || bCode < 9) {
                    continue;
                }

                if (keyMap.get(charArray[charIndex]) == null) {
                    charIndex++;
                    continue;
                }
                int hash = keyMap.get(charArray[charIndex]) % modulo;

                if (keyMap.get(charArray[charIndex]) < modulo) {
                    rCode += hash;
                    if (rControl % 2 == 0) {
                        rControl++;
                    }
                    if (gControl % 2 == 1) {
                        gControl++;
                    }
                    bControl = bControl - (gControl % 10) + hash;
                } else if (keyMap.get(charArray[charIndex]) < 2 * modulo) {
                    gCode += hash;
                    if (gControl % 2 == 0) {
                        gControl++;
                    }
                    if (bControl % 2 == 1) {
                        bControl++;
                    }
                    rControl = rControl - (bControl % 10) + hash;
                } else {
                    bCode += hash;
                    if (bControl % 2 == 0) {
                        bControl++;
                    }
                    if (rControl % 2 == 1) {
                        rControl++;
                    }
                    bControl = bControl - (gControl % 10) + hash;
                }

                System.out.println(rControl);
                region.setPixel((j * pixelsPerChar) - 2, i, new Color(rControl, gControl, bControl));
                region.setPixel((j * pixelsPerChar) - 1, i, new Color(rCode, gCode, bCode));

                charIndex++;
                if (charIndex == charArray.length) {
                    return region;
                }
            }
        }
        return region;
    }

    public String getCode() {
        return code;
    }

    private HashMap<Character, Integer> keyMap = new HashMap<>() {{
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
        put('.', 27);
        put('!', 27);
        put(',', 28);
        put('?', 29);
    }};

    public int getControlPixels() {
        return controlPixels;
    }

    public void setControlPixels(int controlPixels) {
        this.controlPixels = controlPixels;
    }
}