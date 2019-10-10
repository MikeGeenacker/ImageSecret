package com.mike;

import java.awt.*;

public class ColorCalculator {

    public class ColorOffset {
        public float red;
        public float green;
        public float blue;

        public ColorOffset(float red, float green, float blue) {
            if(red < -255 || red > 255) {
                throw new IllegalArgumentException("Kleur rood out of bounds");
            }
            if(green < -255 || green > 255) {
                throw new IllegalArgumentException("Kleur groen out of bounds");
            }
            if(blue < -255 || blue > 255) {
                throw new IllegalArgumentException("Kleur blauw out of bounds");
            }

            this.red = red;
            this.green = green;
            this.blue = blue;
        }

    }
    protected ColorOffset[] colorOffsets(Color average, Color[] data) {
        ColorOffset[] offsets = new ColorOffset[data.length];

        for(int i = 0; i < data.length; i++) {
            float redDiff = data[i].getRed() - average.getRed();
            float greenDiff = data[i].getGreen() - average.getGreen();
            float blueDiff = data[i].getBlue() - average.getBlue();
            offsets[i] = new ColorOffset(redDiff, greenDiff, blueDiff);
        }

        return offsets;
    }
    protected Color avgColor(Color[] data) {
        long red = 0;
        long green = 0;
        long blue = 0;

        for(Color c : data) {
            red += c.getRed();
            green += c.getGreen();
            blue += c.getBlue();
        }

        red /= data.length;
        green /= data.length;
        blue /= data.length;

        return new Color((int)red, (int)green, (int)blue);
    }
    protected ColorOffset avgColorOffset(ColorOffset[] offsets) {
        float red = 0;
        float green = 0;
        float blue = 0;

        for(ColorOffset co : offsets) {
            red += co.red;
            green += co.green;
            blue += co.blue;
        }

        red /= offsets.length;
        green /= offsets.length;
        blue /= offsets.length;

        return new ColorOffset(red, green, blue);
    }
}
