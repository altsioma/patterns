package org.lessons;

import java.util.stream.DoubleStream;

public class SquareRoot {
    private static final double EPSILON = 1e-9;

    public static double[] solve(double a, double b, double c) {
        // Проверка на недопустимые значения
        if (DoubleStream.of(a, b, c).anyMatch(Double::isNaN) || DoubleStream.of(a, b, c).anyMatch(Double::isInfinite))
            throw new IllegalArgumentException("Коэффициенты заданы неверно.");

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

        if (Math.abs(D) <= EPSILON) {
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
