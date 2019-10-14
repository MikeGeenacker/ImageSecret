package com.jelle;

import java.awt.*;
import java.util.HashMap;

public class Encoder {
    private String code;
    private int controlPixels;
    private static int count;

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

                if (keyMap.get(charArray[charIndex]) == null) {
                    charIndex++;
                    continue;
                }

                count++;

                int value = keyMap.get(charArray[charIndex]) % modulo;
                int r = region.getPixelColor((j * pixelsPerChar) - 1, i).getRed();
                int g = region.getPixelColor((j * pixelsPerChar) - 1, i).getGreen();
                int b = region.getPixelColor((j * pixelsPerChar) - 1, i).getBlue();

                if (keyMap.get(charArray[charIndex]) < modulo) {
                    r = Math.min(r + value, 255);
                } else if (keyMap.get(charArray[charIndex]) < 2 * modulo) {
                    g = Math.min(g + value, 255);
                } else {
                    b = Math.min(b + value, 255);
                }

                region.setPixel((j * pixelsPerChar) - 1, i, new Color(r, g, b));

                charIndex++;
                if (charIndex == charArray.length) {
                    return region;
                }
            }
        }
        return region;
    }

    public static int getCount() {
        return count;
    }

    public String getCode() {
        return code;
    }

    public HashMap<Character, Integer> keyMap = new HashMap<>() {{
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

    public int getControlPixels() {
        return controlPixels;
    }

    public void setControlPixels(int controlPixels) {
        this.controlPixels = controlPixels;
    }
}
