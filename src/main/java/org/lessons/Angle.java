package org.lessons;

public class Angle {
    // угол
    private final byte _d;
    // количество делений в полной окружности
    private final byte _n;

    public Angle(byte d, byte n) {
        if (d >= n) {
            throw new IllegalArgumentException("d должен быть больше n");
        }
        this._d = d;
        this._n = n;
    }

    // Складывает два угла только если они имеют одинаковое число делений
    static public Angle plus(Angle a1, Angle a2) {
        if (a1._n != a2._n) {
            throw new IllegalArgumentException("Число делений Angle должно быть одинаково");
        }
        return new Angle((byte) ((a1._d + a2._d) % a1._n), a1._n);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Angle angle = (Angle) obj;
        return _d == angle._d && _n == angle._n;
    }
}
