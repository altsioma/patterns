package org.lessons;

public class SquareRoot {
    private static final double EPSILON = 1e-11;

    public static double[] solve(double a, double b, double c) {
        // Проверка на недопустимые значения
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c)) {
            throw new IllegalArgumentException("Коэффициенты не могут быть NaN.");
        }
        if (Double.isInfinite(a) || Double.isInfinite(b) || Double.isInfinite(c)) {
            throw new IllegalArgumentException("Коэффициенты не могут быть бесконечными.");
        }


        //коэффициент a не может быть равен 0
        if (Math.abs(a) < EPSILON) {
            throw new IllegalArgumentException("Коэффициент 'a' не может быть равен нулю для квадратного уравнения.");
        }

        // Вычисляем дискриминант
        double D = b * b - 4 * a * c;

        if (D < -EPSILON) {
            // Нет корней
            return new double[0];
        }

        if (Math.abs(D) < EPSILON) {
            // Один корень
            double x1 = -b / (2 * a);
            return new double[]{x1, x1};
        }

        // Два корня
        double x1 = -b + Math.sqrt(D) / (2 * a);
        double x2 = -b - Math.sqrt(D) / (2 * a);
        return new double[]{x1, x2};
    }
}
