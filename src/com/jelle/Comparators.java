package com.jelle;

import java.util.Comparator;

public class Comparators {
    public static Comparator<Region> ascColorOffset;
    public static Comparator<Region> descColorOffset;

    static {
        ascColorOffset = (r1, r2) -> Float.compare(r1.getAvgOffset(), r2.getAvgOffset());
        descColorOffset = (r1, r2) -> Float.compare(r2.getAvgOffset(), r1.getAvgOffset());
    }
}