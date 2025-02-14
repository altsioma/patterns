package org.lessons;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RotateCommandTest {
    @Test()
    @DisplayName("Объект с мгновенной угловой скоростью можно повернуть")
    void testRotate() {
        IRotatingObject obj = mock(IRotatingObject.class);
        // Исходный угол: 2/8 * 360° = 90°
        when(obj.getAngle()).thenReturn(new Angle((byte) 2, (byte) 8));
        // Мгновенная угловая скорость объекта задана и равна: 1/8 * 360° = 45°
        when(obj.getAngularVelocity()).thenReturn(new Angle((byte) 1, (byte) 8));

        // Поворачиваем объект
        new RotateCommand(obj).execute();

        // Новый угол должен быть 3/8 * 360° = 135°
        verify(obj).setAngle(new Angle((byte) 3, (byte) 8));
    }

    @Test()
    @DisplayName("Попытка повернуть объект, у которого невозможно определить угол наклона, приводит к ошибке")
    void testGetLocationException() {
        IRotatingObject obj = mock(IRotatingObject.class);
        final String exceptionMessage = "Ошибка получения исходного угла";
        // При запросе исходного угла получаем ошибку
        when(obj.getAngle()).thenThrow(new IllegalStateException(exceptionMessage));

        // Ожидаем, что вызов команды поворота приведёт к исключению
        Exception exception = assertThrows(IllegalStateException.class, () -> new RotateCommand(obj).execute());

        // Проверяем сообщение ошибки
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test()
    @DisplayName("Объект, у которого нет угловой скорости повернуть невозможно")
    void testGetVelocityException() {
        IRotatingObject obj = mock(IRotatingObject.class);
        final String exceptionMessage = "Мгновенная угловая скорость неопределенна";
        //  У объекта не задано положение в пространстве
        when(obj.getAngularVelocity()).thenThrow(new IllegalStateException(exceptionMessage));

        // Ожидаем, что вызов метода приведёт к исключению
        Exception exception = assertThrows(IllegalStateException.class, () -> new RotateCommand(obj).execute());

        // Проверяем сообщение ошибки
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test()
    @DisplayName("Попытка повернуть объект, у которого невозможно изменить положение в пространстве, приводит к ошибке")
    void testSetVelocityException() {
        IRotatingObject obj = mock(IRotatingObject.class);
        final String exceptionMessage = "Невозможно установить новое положение";

        // Исходный угол: 2/8 * 360° = 90°
        when(obj.getAngle()).thenReturn(new Angle((byte) 2, (byte) 8));
        // Мгновенная угловая скорость объекта задана и равна: 1/8 * 360° = 45°
        when(obj.getAngularVelocity()).thenReturn(new Angle((byte) 1, (byte) 8));

        // Настраиваем setAngle так, чтобы он выбрасывал исключение
        doThrow(new IllegalStateException(exceptionMessage))
                .when(obj).setAngle(new Angle((byte) 3, (byte) 8));

        // Ожидаем, что вызов метода приведёт к исключению
        Exception exception = assertThrows(IllegalStateException.class, () -> new RotateCommand(obj).execute());

        // Проверяем сообщение ошибки
        assertEquals(exceptionMessage, exception.getMessage());
    }
}


