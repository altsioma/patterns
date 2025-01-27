package org.lessons;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SquareRootTest {
    @Test()
    @DisplayName("корней нет (возвращается пустой массив)")
    public void testNoRoots() {
        double[] roots = SquareRoot.solve(1, 0, 1);
        double expected = 0;

        assertEquals(expected, roots.length);
    }

    @Test()
    @DisplayName("есть два корня кратности 1 (x1=1, x2=-1)")
    public void testMultiplicityOne() {
        double[] roots = SquareRoot.solve(1, 0, -1);
        double[] expected = new double[]{1, -1};

        assertArrayEquals(expected, roots);
    }

    @Test()
    @DisplayName("есть один корень кратности 2 (x1=x2=-1)")
    public void testMultiplicityTwo() {
        double EPSILON = 1e-12;
        double[] roots = SquareRoot.solve(1, 2, 1 + EPSILON);
        double[] expected = new double[]{-1, -1};

        assertArrayEquals(expected, roots);
    }

    @Test()
    @DisplayName("коэффициент a не может быть равен 0 (будет исключение)")
    public void testExceptionOnZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SquareRoot.solve(0, 1, 1); // a == 0
        });
        assertEquals("Коэффициент 'a' не может быть равен нулю для квадратного уравнения.", exception.getMessage());
    }

    @Test()
    @DisplayName("проверка на NaN в коэффициентах")
    public void testNaNArguments() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SquareRoot.solve(Double.NaN, Double.NaN, Double.NaN));
        assertEquals("Коэффициенты не могут быть NaN.", exception.getMessage());
    }

    @Test()
    @DisplayName("проверка на POSITIVE_INFINITY в коэффициентах")
    public void testInfiniteArguments() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SquareRoot.solve(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertEquals("Коэффициенты не могут быть бесконечными.", exception.getMessage());
    }

    @Test()
    @DisplayName("проверка на NEGATIVE_INFINITY в коэффициентах")
    public void testNegativeInfiniteArguments() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SquareRoot.solve(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertEquals("Коэффициенты не могут быть бесконечными.", exception.getMessage());
    }
}
