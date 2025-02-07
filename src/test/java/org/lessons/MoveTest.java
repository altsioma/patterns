package org.lessons;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


class MoveTest {
    /**
     * Попытка сдвинуть объект, у которого невозможно прочитать положение в пространстве, приводит к ошибке
     * Попытка сдвинуть объект, у которого невозможно прочитать значение мгновенной скорости, приводит к ошибке
     * Попытка сдвинуть объект, у которого невозможно изменить положение в пространстве, приводит к ошибке
     * 1 балл
     */

    @Test()
    @DisplayName("Для объекта, находящегося в точке (12, 5) и движущегося со скоростью (-7, 3) движение меняет положение объекта на (5, 8)")
    void testMove() {
        IMovingObject obj = mock(IMovingObject.class);

        // Объект находится в точке 12, 5
        when(obj.getLocation()).thenReturn(new int[]{12, 5});
        // Объект движется со скоростью (-7, 3)
        when(obj.getVelocity()).thenReturn(new int[]{-7, 3});

        // Двигаем объект
        new MoveCommand(obj).Execute();

        verify(obj).setLocation(new int[]{5,8})

    }
}