package org.lessons;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class MoveCommandTest {
    @Test()
    @DisplayName("Для объекта, находящегося в точке (12, 5) и движущегося со скоростью (-7, 3) движение меняет положение объекта на (5, 8)")
    void testMove() {
        IMovingObject obj = mock(IMovingObject.class);
        // Объект находится в точке 12, 5
        when(obj.getLocation()).thenReturn(new Vector(12, 5));
        // Объект движется со скоростью (-7, 3)
        when(obj.getVelocity()).thenReturn(new Vector(-7, 3));

        // Двигаем объект
        new MoveCommand(obj).execute();

        // Движение меняет положение объекта на (5, 8)
        verify(obj).setLocation(new Vector(5, 8));
    }

    @Test()
    @DisplayName("Попытка сдвинуть объект, у которого невозможно прочитать положение в пространстве, приводит к ошибке")
    void testGetLocationException() {
        IMovingObject obj = mock(IMovingObject.class);
        final String exceptionMessage = "Ошибка при получении координат";
        //  У объекта не задано положение в пространстве
        when(obj.getLocation()).thenThrow(new IllegalStateException(exceptionMessage));

        // Ожидаем, что вызов команды движения приведёт к исключению
        Exception exception = assertThrows(IllegalStateException.class, () -> new MoveCommand(obj).execute());

        // Проверяем сообщение ошибки
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test()
    @DisplayName("Попытка сдвинуть объект, у которого невозможно прочитать значение мгновенной скорости, приводит к ошибке")
    void testGetVelocityException() {
        IMovingObject obj = mock(IMovingObject.class);
        final String exceptionMessage = "Мгновенная скорость неопределенна";
        //  У объекта нет мгновенной скорости
        when(obj.getVelocity()).thenThrow(new IllegalStateException(exceptionMessage));

        // Ожидаем, что вызов команды движения приведёт к исключению
        Exception exception = assertThrows(IllegalStateException.class, () -> new MoveCommand(obj).execute());

        // Проверяем сообщение ошибки
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test()
    @DisplayName("Попытка сдвинуть объект, у которого невозможно изменить положение в пространстве, приводит к ошибке")
    void testSetVelocityException() {
        IMovingObject obj = mock(IMovingObject.class);
        final String exceptionMessage = "Невозможно установить новое положение";
        // Объект находится в точке 12, 5
        when(obj.getLocation()).thenReturn(new Vector(12, 5));
        // Объект движется со скоростью (-7, 3)
        when(obj.getVelocity()).thenReturn(new Vector(-7, 3));
        // Настраиваем setLocation так, чтобы он выбрасывал исключение
        doThrow(new IllegalStateException(exceptionMessage))
                .when(obj).setLocation(new Vector(5, 8));

        // Ожидаем, что вызов команды движения приведёт к исключению
        Exception exception = assertThrows(IllegalStateException.class, () -> new MoveCommand(obj).execute());

        // Проверяем сообщение ошибки
        assertEquals(exceptionMessage, exception.getMessage());
    }
}
