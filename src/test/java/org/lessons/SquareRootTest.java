package org.lessons;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

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
        double EPSILON = 1e-11;
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

    @ParameterizedTest
    @ArgumentsSource(CustomArgumentsProvider.class)
    @DisplayName("проверка на валидность коэффициентов")
    public void testNaNArguments(double a, double b, double c) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SquareRoot.solve(a, b, c));
        assertEquals("Коэффициенты заданы неверно.", exception.getMessage());
    }
}
