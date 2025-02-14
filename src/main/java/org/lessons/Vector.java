package org.lessons;

import java.util.Arrays;

public class Vector {
    private final int[] coords;

    public Vector(int... coords) {
        this.coords = Arrays.copyOf(coords, coords.length);
    }

    public static Vector plus(Vector vector1, Vector vector2) {
        if (vector1.coords.length != vector2.coords.length) {
            throw new IllegalArgumentException("Массивы должны быть одинаковой длины");
        }

        int[] result = new int[vector1.coords.length];

        for (int i = 0; i < vector1.coords.length; i++) {
            result[i] = vector1.coords[i] + vector2.coords[i];
        }

        return new Vector(result);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector vector = (Vector) obj;
        return Arrays.equals(coords, vector.coords);
    }
}



